package com.wiley.iss;

import com.wiley.iss.filter.WebAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * Created by ravuri on 4/18/17.
 */
@SpringBootApplication
// Uncomment when generating the war
public class SvcCatApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        
        return application.sources(SvcCatApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SvcCatApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        WebAuthenticationFilter uploadFilter = new WebAuthenticationFilter();
        registrationBean.setFilter(uploadFilter);
        registrationBean.addUrlPatterns("/webapi/*");

        return registrationBean;
    }
}
//Uncomment when application need to run using Spring boot.
//public class Application {
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//}