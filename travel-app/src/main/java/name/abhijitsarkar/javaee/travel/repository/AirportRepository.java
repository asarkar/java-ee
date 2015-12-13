package name.abhijitsarkar.javaee.travel.repository;

import name.abhijitsarkar.javaee.travel.domain.Airport;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface AirportRepository extends CouchbaseRepository<Airport, String>, AirportRepositoryCustom {
}