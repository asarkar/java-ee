package name.abhijitsarkar.javaee.travel.service;

import name.abhijitsarkar.javaee.travel.domain.Airport;
import name.abhijitsarkar.javaee.travel.repository.AirportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.unmodifiableCollection;

/**
 * @author Abhijit Sarkar
 */
@Service
public class AirportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirportService.class);

    @Autowired
    private AirportRepository airportRepository;

    public Collection<Airport> findByFaaCodes(List<String> faaCodes) {
        final Collection<Airport> airports = new ArrayList<>();

        airportRepository.findByFaaCodesIn(faaCodes).subscribe(new Subscriber<Collection<Airport>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("Failed to find airports by faa codes: {}.", faaCodes, e);
            }

            @Override
            public void onNext(Collection<Airport> all) {
                airports.addAll(all);
            }
        });

        return unmodifiableCollection(airports);
    }
}
