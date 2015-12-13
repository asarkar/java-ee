package name.abhijitsarkar.javaee.travel.web;

import name.abhijitsarkar.javaee.travel.domain.Route;
import name.abhijitsarkar.javaee.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Abhijit Sarkar
 */
@RestController
@RequestMapping(method = GET, path = "/routes")
public class RouteController {
    @Autowired
    private RouteService service;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.addCustomFormatter(new DayOfWeekFormatter());
    }

    @RequestMapping
    public Collection<Route> findRoutes(@RequestParam("src") String srcAirportFaa,
                                        @RequestParam("dest") String destAirportFaa,
                                        @RequestParam("day") DayOfWeek departureDay) {
        return service.findRoutes(srcAirportFaa, destAirportFaa, departureDay);
    }
}
