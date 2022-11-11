package hh.excellence.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .withUser("admin").password("$2y$10$c/8VDFiswKo5diS5BX1hE.HGr6xOgGMBA1iJ0DLp9vL1Vga3.Xz32").roles("ADMIN");
    }

    @Configuration
    @Order(1)  
    public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                .authorizeRequests()
                    .anyRequest()
                        .permitAll()
                .and()
                    .csrf()
                        .disable();
        }     
    }

    @Configuration
    public static class DasboardWebSecurtiyConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()  
            .antMatchers("/css/**", "/js/**").permitAll()
            .anyRequest().authenticated()  
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/questions")
                .permitAll() 
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll();
        }
    }

}