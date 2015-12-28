package name.abhijitsarkar.javaee.dailyupdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author Abhijit Sarkar
 */
public class DailyUpdateApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DailyUpdateApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DailyUpdateApp.class);
    }
}
