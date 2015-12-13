package name.abhijitsarkar.javaee.travel.repository;

import name.abhijitsarkar.javaee.travel.domain.Route;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface RouteRepository extends CouchbaseRepository<Route, String>, RouteRepositoryCustom {
}