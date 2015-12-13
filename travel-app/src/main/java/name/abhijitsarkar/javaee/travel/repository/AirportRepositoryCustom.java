package name.abhijitsarkar.javaee.travel.repository;

import name.abhijitsarkar.javaee.travel.domain.Airport;
import rx.Observable;

import java.util.Collection;
import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public interface AirportRepositoryCustom {
    Observable<Collection<Airport>> findByFaaCodesIn(List<String> faaCodes);
}
