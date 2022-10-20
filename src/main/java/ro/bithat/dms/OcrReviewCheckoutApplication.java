package ro.bithat.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.spring.events.annotation.EnableEventBus;

import java.util.Locale;

@EnableZuulProxy
@SpringBootApplication
@ComponentScan
@EnableEventBus
@EnableHystrix
@EnableHystrixDashboard
//@EnableTransactionManagement
@EntityScan
@EnableCaching
//@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
public class OcrReviewCheckoutApplication {

    public static void main(String[] args) {
    	Locale.setDefault(new Locale("ro", "RO"));
        SpringApplication.run(OcrReviewCheckoutApplication.class, args);
    }

}
