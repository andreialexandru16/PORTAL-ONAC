package ro.bithat.dms.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * The Spring application configuration
 *
 * @author Christoph Guse
 *
 */
//@Configuration
public class HtmlEmbeddingApplicationConfiguration {

    /**
     * This servlet filter was found in
     * https://spring.io/blog/2015/06/08/cors-support-in-spring-framework.
     *
     * It is important to understand this CORS configuration may be a security
     * leak and was created for demonstration purposes only. Ensure to configure
     * the allowed origins!
     */
//    @Bean
    public FilterRegistrationBean corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        bean.setOrder(0);
        return bean;

    }

}
