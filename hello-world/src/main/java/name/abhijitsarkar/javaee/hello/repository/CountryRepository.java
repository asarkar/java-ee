package name.abhijitsarkar.javaee.hello.repository;

import name.abhijitsarkar.javaee.hello.domain.City;
import name.abhijitsarkar.javaee.hello.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Abhijit Sarkar
 */
public interface CountryRepository extends JpaRepository<Country, String> {
}
