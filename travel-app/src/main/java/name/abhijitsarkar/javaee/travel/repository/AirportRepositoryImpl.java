package name.abhijitsarkar.javaee.travel.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.ParameterizedN1qlQuery;
import com.couchbase.client.java.query.Statement;
import name.abhijitsarkar.javaee.travel.domain.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rx.Observable;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.i;
import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;
import static java.util.stream.Collectors.toList;

/**
 * @author Abhijit Sarkar
 */
@Repository
public class AirportRepositoryImpl implements AirportRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirportRepositoryImpl.class);

    private static final String FIELD_NAME = "airportname";
    private static final String FIELD_FAA = "faa";
    private static final String FIELD_ICAO = "icao";
    private static final String FIELD_CITY = "city";
    private static final String FIELD_COUNTRY = "country";
    private static final String FIELD_TIMEZONE = "tz";
    private static final String FIELD_TYPE = "type";
    private static final String VAL_TYPE = "airport";

    private static final String PARAM_FAA = "$faaCodes";

    private Statement findByFaaCodes;

    @Autowired
    private Bucket bucket;

    @PostConstruct
    void postConstruct() {
        findByFaaCodes = select(
                x(FIELD_NAME),
                x(FIELD_FAA),
                x(FIELD_ICAO),
                x(FIELD_CITY),
                x(FIELD_COUNTRY),
                x(FIELD_TIMEZONE)
        )
                .from(i(bucket.name()))
                .where(x(FIELD_FAA).in(x(PARAM_FAA))
                        .and(x(FIELD_TYPE).eq(s(VAL_TYPE))));
    }

    /*
     * Every time a Subscriber subscribes, the call() method is executed (implemented as a lambda expression here).
     * We can then call onNext, onComplete and onError as you wish, but must keep in mind that both onComplete and
     * onError should only be called once, and afterward no subsequent onNext is allowed to follow.
     *
     * No blocking call is needed, because the observable is completely handled on the current thread.
     */
    @Override
    public Observable<Collection<Airport>> findByFaaCodesIn(List<String> faaCodes) {
        return Observable.<Collection<Airport>>create(subscriber -> {
            ParameterizedN1qlQuery query = ParameterizedN1qlQuery.parameterized(findByFaaCodes,
                    JsonObject.create().put(PARAM_FAA, JsonArray.from(faaCodes)));

            LOGGER.debug("Executing query: {}.", query.n1ql());

            N1qlQueryResult result = bucket.query(query);

            LOGGER.debug("Query metrics: {}.", result.info());

            if (result.finalSuccess()) {
                List<Airport> airports = result.allRows().stream().map(row -> {
                    JsonObject value = row.value();

                    /* Get current time at airport timezone */
                    ZonedDateTime now = Instant.now().atZone(ZoneId.of(value.getString(FIELD_TIMEZONE)));

                    return Airport.builder()
                            .name(value.getString(FIELD_NAME))
                            .faaCode(value.getString(FIELD_FAA))
                            .icao(value.getString(FIELD_ICAO))
                            .city(value.getString(FIELD_CITY))
                            .country(value.getString(FIELD_COUNTRY))
                            .timeZoneOffset(now.getOffset())
                            .build();
                }).collect(toList());

                subscriber.onNext(airports);

                subscriber.onCompleted();
            } else {
                subscriber.onError(
                        new RuntimeException(
                                String.format("Failed to find airports by faa codes: %s.", faaCodes)
                        ));
            }
        });
    }
}
