package stud.apach.finaudit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/finaudit/login",
                        "/finaudit/register",
                        "/fonts/**",
                        "/js/**",
                        "/css/**",
                        "/images/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/finaudit/login")
                .loginPage("/finaudit/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/finaudit/", true)
                .failureUrl("/finaudit/login?error=true")
                .permitAll()
                .and()
                .logout()
                    .logoutUrl("/finaudit/logout")
                    .logoutSuccessUrl("/finaudit")
                    .and().exceptionHandling()
                .accessDeniedPage("/finaudit/access-denied");

    }
}
