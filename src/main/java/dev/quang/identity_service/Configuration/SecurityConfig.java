package dev.quang.identity_service.Configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.experimental.NonFinal;

@Configuration
public class SecurityConfig {
    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;

    static final String[] PUBLIC_POST_ENDPOINT = {
        "/auth"
    };
    
    static final String[] PUBLIC_GET_ENDPOINT = {
        "/auth"
    };
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINT).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> 
                oauth2.jwt(jwtconfigurer -> jwtconfigurer.decoder(jwtDecoder()))
            )
            .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean   
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512"); 
        return NimbusJwtDecoder
            .withSecretKey(secretKeySpec)
            .macAlgorithm(MacAlgorithm.HS512)
            .build();
    }
}
