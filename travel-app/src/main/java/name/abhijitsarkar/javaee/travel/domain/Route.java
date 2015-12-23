package name.abhijitsarkar.javaee.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.OffsetTime;

/**
 * @author Abhijit Sarkar
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private String srcAirportFaa;
    private String destAirportFaa;
    private int stops;
    private String aircraft;
    private String airline;
    private String flightNum;
    private OffsetTime departureTimeUTC;
    private DayOfWeek departureDay;
}
