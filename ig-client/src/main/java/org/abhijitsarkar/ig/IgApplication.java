package org.abhijitsarkar.ig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.FormHttpMessageWriter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.config.DelegatingWebReactiveConfiguration;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.reactive.result.view.RequestDataValueProcessor;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

/**
 * @author Abhijit Sarkar
 */
@SpringBootApplication
public class IgApplication extends DelegatingWebReactiveConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(IgApplication.class, args);
    }

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(urlBasedViewResolver());
    }

    @Override
    protected void extendMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        super.extendMessageWriters(messageWriters);
        messageWriters.add(new FormHttpMessageWriter());
    }

    @Bean
    ViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(RedirectView.class);

        return viewResolver;
    }

    @Bean
    RequestDataValueProcessor requestDataValueProcessor() {
        return new NoOpRequestDataValueProcessor();
    }
}
