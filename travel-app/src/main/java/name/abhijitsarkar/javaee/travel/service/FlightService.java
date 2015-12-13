package name.abhijitsarkar.javaee.travel.service;

import name.abhijitsarkar.javaee.travel.domain.Airport;
import name.abhijitsarkar.javaee.travel.domain.Flight;
import name.abhijitsarkar.javaee.travel.domain.Page;
import name.abhijitsarkar.javaee.travel.domain.Route;
import name.abhijitsarkar.javaee.travel.repository.AirportRepository;
import name.abhijitsarkar.javaee.travel.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import rx.Observable;
import rx.Subscriber;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Iterator;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author Abhijit Sarkar
 */
@Service
public class FlightService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    private AirportRepository airportRepo;
    @Autowired
    private RouteRepository routeRepo;

    public Page<Flight> findFlights(String srcAirportFaa, String destAirportFaa,
                                    LocalDate departureDate, int pageSize, int pageNum) {
        Observable<Page<Route>> page = routeRepo.findRoutes(
                srcAirportFaa, destAirportFaa, departureDate.getDayOfWeek(), pageSize, pageNum);

        Page<Route> routesPage = new Page<>();

        Airport srcAirport = Airport.builder().build();
        Airport destAirport = Airport.builder().build();

        Page<Flight> flightsPage = new Page<>();

        page.subscribe(new Subscriber<Page<Route>>() {
            @Override
            public void onCompleted() {
                airportRepo.findByFaaCodesIn(asList(srcAirportFaa, destAirportFaa))
                        .subscribe(new Subscriber<Collection<Airport>>() {
                            @Override
                            public void onCompleted() {
                                Assert.state(srcAirport != null, "Failed to find source airport.");
                                Assert.state(destAirport != null, "Failed to find destination airport.");

                                Collection<Route> data = routesPage.getData();

                                ZoneOffset srcAirportTimeZoneOffset = srcAirport.getTimeZoneOffset();

                                Collection<Flight> flights = data.stream().map(r ->
                                        Flight.builder()
                                                .srcAirportName(srcAirport.getName())
                                                .srcCity(srcAirport.getCity())
                                                .srcCountry(srcAirport.getCountry())
                                                .srcFaaCode(srcAirport.getFaaCode())
                                                .srcTimeZoneOffset(srcAirportTimeZoneOffset)
                                                .destAirportName(destAirport.getName())
                                                .destCity(destAirport.getCity())
                                                .destCountry(destAirport.getCountry())
                                                .destFaaCode(destAirport.getFaaCode())
                                                .destTimeZoneOffset(destAirport.getTimeZoneOffset())
                                                .stops(r.getStops())
                                                .aircraft(r.getAircraft())
                                                .airline(r.getAirline())
                                                .flightNum(r.getFlightNum())
                                                .departureTime(r.getDepartureTimeUTC()
                                                        .withOffsetSameInstant(srcAirportTimeZoneOffset)
                                                        .toLocalTime())
                                                .departureDate(departureDate).build()
                                ).collect(toList());

                                flightsPage.setData(flights);
                                flightsPage.setNumPages(routesPage.getNumPages());
                                flightsPage.setPageNum(routesPage.getPageNum());
                                flightsPage.setPageSize(routesPage.getPageSize());
                            }

                            @Override
                            public void onError(Throwable e) {
                                LOGGER.error("Failed to find airports by faa codes: {}, {}.",
                                        srcAirportFaa, destAirportFaa, e);
                            }

                            @Override
                            public void onNext(Collection<Airport> all) {
                                int numAirports = all.size();

                                Assert.state(numAirports == 2,
                                        String.format("Expected 2 airports but got %d.", numAirports));

                                Iterator<Airport> it = all.iterator();

                                srcAirport.updateFrom(it.next());
                                destAirport.updateFrom(it.next());
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("Failed to find routes between {} and {} on {}.",
                        srcAirportFaa, destAirportFaa, departureDate, e);
            }

            @Override
            public void onNext(Page<Route> all) {
                routesPage.updateFrom(all);
            }
        });

        return flightsPage;
    }
}
