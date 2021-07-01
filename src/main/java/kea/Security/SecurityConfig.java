package kea.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "SELECT Username, Password, Enabled FROM Users " +
                "WHERE Username=?")
        .authoritiesByUsernameQuery(
            "SELECT Username, Authority FROM Authorities " +
                "WHERE Username=?")
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_SECRETARY")
        .antMatchers("/createbooking").hasAnyAuthority("ROLE_ADMIN", "ROLE_SECRETARY", "ROLE_USER")
        .antMatchers("/updatebooking").hasAnyAuthority("ROLE_ADMIN", "ROLE_SECRETARY")
        .antMatchers("/delete/**").hasAnyAuthority("ROLE_ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin().permitAll()
        .and()
        .logout()
        .logoutSuccessUrl("/");
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/css/**", "/js/**", "/plugins/**");
  }
}
