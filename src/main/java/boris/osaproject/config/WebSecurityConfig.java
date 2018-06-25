package boris.osaproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import boris.osaproject.security.RestAuthenticationEntryPoint;
import boris.osaproject.security.TokenAuthenticationFilter;
import boris.osaproject.service.CustomUserDetailsService;
import boris.osaproject.util.TokenHelper;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
	@Autowired
    private TokenHelper tokenHelper;
	

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( jwtUserDetailsService )
            .passwordEncoder(getPasswordEncoder());
    }

    private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence arg0, String arg1) {
				return true;
			}
			
			@Override
			public String encode(CharSequence arg0) {
				return arg0.toString();
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
        .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
        .authorizeRequests()
        .antMatchers("/auth/**", "/register").permitAll()
        .antMatchers("/users/**", "/posts/**").authenticated()
        .and()
        .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService), BasicAuthenticationFilter.class);

        http.csrf().disable();
	}
	

	
	
}
