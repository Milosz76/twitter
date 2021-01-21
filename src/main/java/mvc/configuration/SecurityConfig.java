package mvc.configuration;

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
import org.springframework.security.crypto.password.PasswordEncoder;


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
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/addadmin","/admins","/admin-success")
                    .hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users")
                    .hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                    .csrf().disable()
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
                .withUser("user")
                .password(passwordEncoder.encode("test")).roles("USER");

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
                .authoritiesByUsernameQuery("SELECT A.LOGIN, 'ROLE_ADMIN', 1 FROM ADMINS A WHERE A.LOGIN=?")
                .dataSource(jdbcTemplate.getDataSource())
                .passwordEncoder(passwordEncoder);
    }

}
