package stud.apach.finaudit.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/finaudit").setViewName("index");
        registry.addViewController("/finaudit/index").setViewName("index");
        registry.addViewController("/finaudit/register").setViewName("register");
        registry.addViewController("/finaudit/login").setViewName("login");
        registry.addViewController("/finaudit/enterprises/create").setViewName("enterprise-create");
        registry.addViewController("/finaudit/enterprises").setViewName("view-enterprises");

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}
