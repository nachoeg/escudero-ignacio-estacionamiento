package com.cespi.estacionamiento.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cespi.estacionamiento.filters.AuthFilter;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.repositories.UserRepository;
import com.cespi.estacionamiento.services.AuthService;

@Configuration
public class AppConfig {

  @Bean
  public CommandLineRunner run(UserRepository userRepository) throws Exception {
    return (_) -> {
      if (userRepository.findAll().isEmpty()) {
        userRepository.save(new User("usuario1@mail.com", "1234567891", "usuario1"));
        userRepository.save(new User("usuario2@mail.com", "1234567892", "usuario2"));
        userRepository.save(new User("usuario3@mail.com", "1234567893", "usuario3"));
      }
    };
  }

  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter() {
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
  public FilterRegistrationBean<AuthFilter> authFilter(AuthService authService) {
    FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthFilter(authService));
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1);
    return registrationBean;
  }

}