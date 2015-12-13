package name.abhijitsarkar.javaee.travel.service;

import name.abhijitsarkar.javaee.travel.domain.Route;
import name.abhijitsarkar.javaee.travel.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Subscriber;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;

/**
 * @author Abhijit Sarkar
 */
@Service
public class RouteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private RouteRepository routeRepository;

    public Collection<Route> findRoutes(String srcAirportFaa, String destAirportFaa, DayOfWeek departureDay) {
        final Collection<Route> routes = new ArrayList<>();

        routeRepository.findRoutes(srcAirportFaa, destAirportFaa, departureDay)
                .subscribe(new Subscriber<Collection<Route>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LOGGER.error("Failed to find routes between {} and {} on {}.",
                                srcAirportFaa, destAirportFaa, departureDay, e);
                    }

                    @Override
                    public void onNext(Collection<Route> all) {
                        routes.addAll(all);
                    }
                });

        return unmodifiableCollection(routes);
    }
}
