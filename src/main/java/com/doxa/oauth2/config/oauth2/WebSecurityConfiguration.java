package com.doxa.oauth2.config.oauth2;


import com.doxa.oauth2.security.client.RegisteredClientDetailsService;
import com.doxa.oauth2.security.user.EndUserDetailsService;
import com.doxa.oauth2.serviceImpl.JsonWebTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfiguration {
    private final List<String> origins = new ArrayList<>() {{
        //admin portal
        add("https://admin-dev.doxa-holdings.com");
        add("https://admin-stag.doxa-holdings.com");
        add("https://admin-qa.doxa-holdings.com");
        add("https://admin-uat.doxa-holdings.com");
        add("https://connex-admin.doxa-holdings.com");

        //purchase portal
        add("https://connex-dev.doxa-holdings.com");
        add("https://connex-stag.doxa-holdings.com");
        add("https://connex-qa.doxa-holdings.com");
        add("https://connex-uat.doxa-holdings.com");
        add("https://connex.doxa-holdings.com"); // production

        //developer portal
        add("https://developer-dev.doxa-holdings.com");
        add("https://developer-stag.doxa-holdings.com");
        add("https://developer-qa.doxa-holdings.com");
        add("https://developer-uat.doxa-holdings.com");
        add("https://connex-developer.doxa-holdings.com");
        
        //finance portal
        add("https://finance-dev.doxa-holdings.com");
        add("https://finance-stag.doxa-holdings.com");
        add("https://finance-qa.doxa-holdings.com");
        add("https://finance-uat.doxa-holdings.com");
        add("https://connex-finance.doxa-holdings.com");

        //subcon portal
        add("https://subcon-dev.doxa-holdings.com");
        add("https://subcon-stag.doxa-holdings.com");
        add("https://subcon-qa.doxa-holdings.com");
        add("https://subcon-uat.doxa-holdings.com");
        add("https://connex-subcon.doxa-holdings.com");

        //payment portal
        add("https://payment-dev.doxa-holdings.com");
        add("https://payment-stag.doxa-holdings.com");
        add("https://payment-qa.doxa-holdings.com");
        add("https://payment-uat.doxa-holdings.com");
        add("https://connex-payment.doxa-holdings.com");

        //fi portal
        add("https://fi-portal-dev.doxa-holdings.com");
        add("https://fi-portal-stag.doxa-holdings.com");
        add("https://fi-portal-qa.doxa-holdings.com");
        add("https://fi-portal-uat.doxa-holdings.com");
        add("https://connex-fi-portal.doxa-holdings.com");

        //others
        add("http://clmssit.doxa-holdings.com");
        add("http://localhost:4100");
        
    }};

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Configuration
    @Order(1)
    public static class PublicEndpoints extends WebSecurityConfigurerAdapter {

        private final RegisteredClientDetailsService registeredClientDetailsService;

        @Value("${doxa.portal.uri}")
        private String LOGOUT_SUCCESS_URL;

        public PublicEndpoints(@Autowired @Qualifier("registeredClientDetailsService")
                                       RegisteredClientDetailsService registeredClientDetailsService) {
            this.registeredClientDetailsService = registeredClientDetailsService;
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().mvcMatchers("/token", "/introspect",
                    "/revoke", "/userinfo", "/.well-known/openid-configuration", "/jwks");
        }

        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().requestMatchers(
                            r ->
                                    r.mvcMatchers(
                                            "/token",
                                            "/logout",
                                            "/forgotpassword",
                                            "/resetpassword-email",
                                            "/resetpassword-token",
                                            "/resetpassword-submit",
                                            "/actuator/**",
                                            "/favicon.ico",
                                            "/check_token",
                                            "/introspect",
                                            "/users/signin",
                                            "/revoke",
                                            "/userinfo",
                                            "/verify",
                                            "/.well-known/openid-configuration",
                                            "/jwks"))
                    .authorizeRequests(
                            a -> {
//                                a.mvcMatchers(POST, "/introspect").hasRole("CLIENT");
//                                a.mvcMatchers(POST, "/revoke").hasRole("CLIENT");
//                                a.mvcMatchers(POST, "/token").hasRole("CLIENT");
                                a.anyRequest().permitAll();
                            })
                    .formLogin()
                    .loginPage("/login")
                    .permitAll().and()
                    .userDetailsService(this.registeredClientDetailsService)
                    .logout(logout -> logout.permitAll()
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .logoutSuccessHandler((request, response, authentication) -> {
//                                response.setStatus(HttpServletResponse.SC_OK);
                                response.sendRedirect(LOGOUT_SUCCESS_URL);
                            }));
        }
    }


    @Configuration
    @Order(2)
    public static class ApiEndpoints extends WebSecurityConfigurerAdapter {

        @Autowired
        private EndUserDetailsService endUserDetailsService;
        @Autowired
        private JsonWebTokenService jsonWebTokenService;

        public ApiEndpoints(
                @Autowired @Qualifier("endUserDetailsService")
                        EndUserDetailsService endUserDetailsService) {
            this.endUserDetailsService = endUserDetailsService;
        }

        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf()
                    .disable()
                    .requestMatchers(r -> r.mvcMatchers("/api/**"))
                    .addFilterBefore(new ApiRequestFilter(jsonWebTokenService), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests(
                            a -> {
                                a.mvcMatchers("/verify").permitAll();
                                a.mvcMatchers("**/private/**").permitAll();
                                a.mvcMatchers("/api/**").authenticated();
                                a.anyRequest().denyAll();
                            })
//                    .httpBasic(withDefaults())
                    //.formLogin(withDefaults())
                    .userDetailsService(this.endUserDetailsService)
                    .cors()
                    .and()
                    .csrf()
                    .disable();
        }
    }

    @Configuration
    @Order(3)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final EndUserDetailsService endUserDetailsService;
        private final Doxa2FAHandler doxa2FAHandler;

        public FormLoginWebSecurityConfigurerAdapter(
                @Autowired @Qualifier("endUserDetailsService")
                        EndUserDetailsService endUserDetailsService,
                Doxa2FAHandler doxa2FAHandler) {
            this.endUserDetailsService = endUserDetailsService;
            this.doxa2FAHandler = doxa2FAHandler;
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests(authorize -> authorize
                            .antMatchers("/css/**", "**/js/**", "/images/**", "/verify", "/favicon.ico").permitAll()
                            .anyRequest().authenticated())
                    .cors()
                    .and().csrf()
                    .disable()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(doxa2FAHandler)
                    .and()
                    .userDetailsService(this.endUserDetailsService)
                    .headers().contentSecurityPolicy(
                            csp -> csp.policyDirectives(
                                    "upgrade-insecure-requests; default-src 'self' https:; " +
                                            "style-src 'self' stackpath.bootstrapcdn.com maxcdn.bootstrapcdn.com getbootstrap.com; " +
                                            "script-src code.jquery.com cdnjs.cloudflare.com " +
                                            "stackpath.bootstrapcdn.com;" +
                                            "font-src 'self' data:;" +
                                            "object-src to 'none'")
                    );
        }
    }

}
