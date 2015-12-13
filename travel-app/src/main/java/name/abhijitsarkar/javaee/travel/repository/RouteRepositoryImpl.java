package name.abhijitsarkar.javaee.travel.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.ParameterizedQuery;
import com.couchbase.client.java.query.Query;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.Statement;
import name.abhijitsarkar.javaee.travel.domain.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseQueryExecutionException;
import org.springframework.stereotype.Repository;
import rx.Observable;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Collection;
import java.util.List;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.i;
import static com.couchbase.client.java.query.dsl.Expression.path;
import static com.couchbase.client.java.query.dsl.Expression.x;
import static com.couchbase.client.java.query.dsl.Sort.asc;
import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_TIME;
import static java.util.stream.Collectors.toList;

/**
 * @author Abhijit Sarkar
 */
@Repository
public class RouteRepositoryImpl implements RouteRepositoryCustom {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRepositoryImpl.class);

    private static final String FIELD_SRC_AIRPORT_FAA = "srcAirportFaa";
    private static final String FIELD_DEST_AIRPORT_FAA = "destAirportFaa";
    private static final String FIELD_AIRLINE = "airline";
    private static final String FIELD_FLIGHT_NUM = "flightNum";
    private static final String FIELD_AIRCRAFT = "aircraft";
    private static final String FIELD_STOPS = "stops";
    private static final String FIELD_DEPARTURE_TIME = "departureTimeUTC";
    private static final String FIELD_DEPARTURE_DAY = "departureDay";

    private Statement findRoutes;

    @Autowired
    private Bucket bucket;

    @PostConstruct
    void postConstruct() {
        findRoutes = select(
                path("route", "sourceairport").as(FIELD_SRC_AIRPORT_FAA),
                path("route", "destinationairport").as(FIELD_DEST_AIRPORT_FAA),
                path("route", "stops").as(FIELD_STOPS),
                path("route", "equipment").as(FIELD_AIRCRAFT),
                path("airln", "name").as(FIELD_AIRLINE),
                path("schedule", "flight").as(FIELD_FLIGHT_NUM),
                path("schedule", "utc").as(FIELD_DEPARTURE_TIME),
                path("schedule", "day").as(FIELD_DEPARTURE_DAY)
        )
                .from(i(bucket.name()).as(x("route")))
                .unnest(path("route", "schedule").as(x("schedule")))
                .join(i(bucket.name()).as(x("airln")))
                .onKeys(path("route", "airlineid"))
                .where(path("route", "sourceairport").eq(x("$srcAirportFaa"))
                        .and(path("route", "destinationairport").eq(x("$destAirportFaa")))
                        .and(path("schedule", "day").eq(x("$departureDay"))))
                .orderBy(asc(path("airln", "name")));
    }

    /*
     * Every time a Subscriber subscribes, the call() method is executed (implemented as a lambda expression here).
     * We can then call onNext, onComplete and onError as you wish, but must keep in mind that both onComplete and
     * onError should only be called once, and afterward no subsequent onNext is allowed to follow.
     *
     * No blocking call is needed, because the observable is completely handled on the current thread.
     */
    public Observable<Collection<Route>> findRoutes(String srcAirportFaa, String destAirportFaa, DayOfWeek departureDay) {
        return Observable.<Collection<Route>>create(subscriber -> {
            ParameterizedQuery query = Query.parameterized(findRoutes,
                    JsonObject.create()
                            .put(FIELD_SRC_AIRPORT_FAA, srcAirportFaa)
                            .put(FIELD_DEST_AIRPORT_FAA, destAirportFaa)
                            /* DayOfWeek is 1-based, field in document is 0-based */
                            .put(FIELD_DEPARTURE_DAY, departureDay.getValue() - 1));

            LOGGER.debug("Executing query: {}.", query.n1ql());

            QueryResult result = bucket.query(query);

            LOGGER.debug("Query metrics: {}.", result.info());

            if (result.finalSuccess()) {
                List<Route> routes = result.allRows().stream().map(row -> {
                    JsonObject value = row.value();

                    String departureTime = value.getString(FIELD_DEPARTURE_TIME);
                    OffsetTime departureTimeUTC = OffsetTime.of(LocalTime.parse(departureTime, ISO_TIME), UTC);

                    return Route.builder()
                            .srcAirportFaa(value.getString(FIELD_SRC_AIRPORT_FAA))
                            .destAirportFaa(value.getString(FIELD_DEST_AIRPORT_FAA))
                            .stops(value.getInt(FIELD_STOPS))
                            .aircraft(value.getString(FIELD_AIRCRAFT))
                            .airline(value.getString(FIELD_AIRLINE))
                            .flightNum(value.getString(FIELD_FLIGHT_NUM))
                            .departureTimeUTC(departureTimeUTC)
                            .departureDay(departureDay).build();
                }).collect(toList());

                subscriber.onNext(routes);

                subscriber.onCompleted();
            } else {
                subscriber.onError(
                        new CouchbaseQueryExecutionException(
                                String.format("Failed to find routes between %s and %s on %s.",
                                        srcAirportFaa, destAirportFaa, departureDay)
                        ));
            }
        });
    }
}
