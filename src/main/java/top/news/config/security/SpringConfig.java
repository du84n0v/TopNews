package top.news.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtTokenFilter;

    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",

            "/api/v1/article/last-n-by-section/{sectionId}",
            "/api/v1/article/last-n-by-category/{categoryId}",
            "/api/v1/article/last-n-by-region/{regionId}",
            "/api/v1/article/last-12",
            "/api/v1/article/most-read-except/{articleId}",
            "/api/v1/article/increase-view-count-by-id/{articleId}",
            "/api/v1/article/increase-share-count-by-id/{articleId}",
            "/api/v1/article/filter",
            "/api/v1/article/get-by-id/{articleId}",

            "/api/v1/region/by-lang",
            "/api/v1/category/by-lang",
            "/api/v1/section/by-lang",

            "/api/v1/attaches/upload",
            "/api/v1/attaches/open/{fileId}",
            "/api/v1/attaches/download/{fileId}",
    };

    @Bean
    public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder passwordEncoder){
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
        ).addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(List.of("*"));
            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            cors.configurationSource(source);
        });

        return http.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
