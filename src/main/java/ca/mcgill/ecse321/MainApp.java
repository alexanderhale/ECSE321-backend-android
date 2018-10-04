package ca.mcgill.ecse321;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import ca.mcgill.ecse321.config.JwtFilter;

@SpringBootApplication()
public class MainApp {

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/secure/*");

        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}