package name.abhijitsarkar.javaee.hello.repository;

import name.abhijitsarkar.javaee.hello.domain.CountryLanguage;
import name.abhijitsarkar.javaee.hello.domain.CountryLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Abhijit Sarkar
 */
public interface CountryLanguageRepository extends JpaRepository<CountryLanguage, CountryLanguageId> {
}
