package name.abhijitsarkar.javaee.travel.repository;

import name.abhijitsarkar.javaee.travel.domain.Airport;
import org.springframework.data.couchbase.core.view.Query;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface AirportRepository extends CouchbaseRepository<Airport, String> {
    @Query("$SELECT_ENTITY$ WHERE icao = $1")
    Airport findByIcao(String airportCode);
}