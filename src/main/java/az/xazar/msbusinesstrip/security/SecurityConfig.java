package az.xazar.msbusinesstrip.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.isConfigured();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/businesstrip").permitAll();

        // http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests()
//                // resources, register, about sayfalarına herkes erişebilir
//                .antMatchers("/resources/**", "/register", "/about").permitAll()
//                // sadece ADMIN rolüne sahip
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                //.antMatchers("/admin/**").hasAuthority("ADMIN")
//                // giriş yapmış kullanıcılar
//                .antMatchers("/members/**").authenticated()
//                //  ADMIN ve DBA rolüne sahip kullanıcılar
//                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//                // sadece belirli IP sahip kullanıcılar
//                .antMatchers("/tr/**").hasIpAddress("88.168.0/24")
//                // diğer tüm istekler erişime kapalı
//                .anyRequest().denyAll();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
