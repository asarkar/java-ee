package name.abhijitsarkar.javaee.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Abhijit Sarkar
 */
@EnableWebSecurity
@Order(-10)
/* Good read: http://shazsterblog.blogspot.com/2014/07/spring-security-custom-filterchainproxy.html */
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http. //
                httpBasic().and(). //
                sessionManagement().sessionCreationPolicy(STATELESS).and().
                exceptionHandling().and(). //
                authorizeRequests(). //
                antMatchers(GET, "/movies/**").hasAnyAuthority("MOVIES", "ADMIN"). //
                antMatchers(GET, "/dailyupdate/**").hasAnyAuthority("MOVIES", "NEWS", "WEATHER", "ADMIN"). //
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    public <O extends FilterSecurityInterceptor> O postProcess(
//                            O fsi) {
                       /* Do nothing but can do many things */
//                        fsi.setAccessDecisionManager(accessDecisionManager());
//                        return fsi;
//                    }
//                })
                and().
                csrf().disable();
                // addFilter(basicAuthFilter());
    }

//    private Filter basicAuthFilter() throws Exception {
//        return new SuccessHandlingBasicAuthenticationFilter(super.authenticationManagerBean());
//    }
}
