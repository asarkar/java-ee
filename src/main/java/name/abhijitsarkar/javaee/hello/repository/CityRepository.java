package name.abhijitsarkar.javaee.hello.repository;

import name.abhijitsarkar.javaee.hello.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Abhijit Sarkar
 */
public interface CityRepository extends JpaRepository<City, Long> {
}
