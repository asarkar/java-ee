package name.abhijitsarkar.javaee.hello.web;

import name.abhijitsarkar.javaee.hello.domain.CountryLanguage;
import name.abhijitsarkar.javaee.hello.repository.CountryLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Abhijit Sarkar
 */
@RestController
@RequestMapping(path = "languages", produces = APPLICATION_JSON_VALUE, method = GET)
public class CountryLanguageController {
    @Autowired
    private CountryLanguageRepository countryLanguageRepository;

    @RequestMapping
    public List<CountryLanguage> findAll() {
        return countryLanguageRepository.findAll();
    }
}
