package name.abhijitsarkar.javaee.travel.repository;

import name.abhijitsarkar.javaee.travel.domain.Airport;
import name.abhijitsarkar.javaee.travel.domain.Route;
import rx.Observable;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public interface RouteRepositoryCustom {
    Observable<Collection<Route>> findRoutes(String srcAirportFaa, String destAirportFaa, DayOfWeek departureDay);
}
