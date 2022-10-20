package ro.bithat.dms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class LoggingConfiguration {

    @Bean
    public Filter logbackFilter() {
        LogbackFilter logbackFilter = new LogbackFilter();
        return logbackFilter;
    }
}
