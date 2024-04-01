package mvc.configuration;

import mvc.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        logger.info("Calling configure() from SecurityConfig");
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/userpage/")
                    .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/useraddcontent/")
                    .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/useraddcontent/")
                    .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/show-messages/")
                    .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/users")
                    .hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                    .headers().frameOptions().disable()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login-page")
                    .defaultSuccessUrl("/userpage")
                .and()
                    .logout()
                    .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("test")).roles("ADMIN");

        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT U.LOGIN, U.PASSWORD, 1 FROM USERS U WHERE U.LOGIN=?")
                .authoritiesByUsernameQuery("SELECT U.LOGIN, 'ROLE_USER', 1 FROM USERS U WHERE U.LOGIN=?")
                .dataSource(jdbcTemplate.getDataSource())
                .passwordEncoder(passwordEncoder);

        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT LOGIN, PASSWORD, 1 FROM ADMINS WHERE LOGIN=?")
                .authoritiesByUsernameQuery("SELECT A.LOGIN, 'ROLE_ADMIN', 1 FROM USERS A WHERE A.LOGIN=?")
                .dataSource(jdbcTemplate.getDataSource())
                .passwordEncoder(passwordEncoder);
    }


}
