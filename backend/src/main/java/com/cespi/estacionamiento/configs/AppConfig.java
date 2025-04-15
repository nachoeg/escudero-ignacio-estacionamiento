package com.cespi.estacionamiento.configs;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cespi.estacionamiento.dtos.HolidayDTO;
import com.cespi.estacionamiento.filters.AuthFilter;
import com.cespi.estacionamiento.models.Holiday;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.repositories.HolidayRepository;
import com.cespi.estacionamiento.repositories.UserRepository;
import com.cespi.estacionamiento.services.AuthService;

@Configuration
public class AppConfig {

  @Bean
  CommandLineRunner run(UserRepository userRepository, HolidayRepository holidayRepository,
      RestTemplate restTemplate) throws Exception {
    return (args) -> {
      if (userRepository.findAll().isEmpty()) {
        userRepository.save(new User("usuario1@mail.com", "1234567891", "usuario1"));
        userRepository.save(new User("usuario2@mail.com", "1234567892", "usuario2"));
        userRepository.save(new User("usuario3@mail.com", "1234567893", "usuario3"));
      }
      if (holidayRepository.findByYear(LocalDate.now().getYear()).isEmpty()) {
        String url = "https://api.argentinadatos.com/v1/feriados/" + LocalDate.now().getYear();
        HolidayDTO[] holidays = restTemplate.getForObject(url, HolidayDTO[].class);
        if (holidays != null) {
          for (HolidayDTO holiday : holidays) {
            LocalDate date = LocalDate.parse(holiday.getFecha());
            if (!holidayRepository.existsByDate(date)) {
              holidayRepository.save(new Holiday(holiday.getNombre(), date));
            }
          }
        }
      }
    };
  }

  @Bean
  FilterRegistrationBean<CorsFilter> corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:4200");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);

    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
  }

  @Bean
  FilterRegistrationBean<AuthFilter> authFilter(AuthService authService) {
    FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthFilter(authService));
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1);
    return registrationBean;
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

}