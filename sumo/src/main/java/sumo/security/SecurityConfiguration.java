package sumo.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;

//@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private NegotiateSecurityFilter filter;
    private NegotiateSecurityFilterEntryPoint entryPoint;

    public SecurityConfiguration(NegotiateSecurityFilter filter, NegotiateSecurityFilterEntryPoint entryPoint) {
	this.filter = filter;
	this.entryPoint = entryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.inMemoryAuthentication()
		.withUser("user").password("{noop}user").authorities("ROLE_USER").and()
		.withUser("admin").password("{noop}admin").authorities("ROLE_ADMIN");
    }
  
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(sumo.util.Paths.getAnonymousPaths()); // Необходимо пля отображения "анонимных" URL в имперсонализированном браузере
    }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();
	http.headers().disable();

        http.authorizeRequests()
        .anyRequest().authenticated().and()
        .addFilterBefore(filter, BasicAuthenticationFilter.class).exceptionHandling()
        .authenticationEntryPoint(entryPoint);
	
//	http.authorizeRequests()
////	.antMatchers(sumo.util.Paths.getAnonymousPaths()).anonymous()
////	.antMatchers(sumo.util.Paths.getAuthenticatedPaths()).authenticated()
//	.antMatchers("wfc").anonymous()
//	.antMatchers("user").authenticated()
//		.and().addFilterBefore(filter, BasicAuthenticationFilter.class).exceptionHandling()
//		.authenticationEntryPoint(entryPoint);
  }
    

    
    
    
// FROM WAFFLE
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated().and()
//                .addFilterBefore(filter, BasicAuthenticationFilter.class).exceptionHandling()
//                .authenticationEntryPoint(entryPoint);
//    }

//	ORIGINALS DOEN    
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//	http.csrf().disable();
//	http.headers().disable();
//
//	http.authorizeRequests()
////	.antMatchers(sumo.util.Paths.getAnonymousPaths()).anonymous()
////	.antMatchers(sumo.util.Paths.getAuthenticatedPaths()).authenticated()
//	.antMatchers("wfc").anonymous()
//	.antMatchers("user").authenticated()
//		.and().addFilterBefore(filter, BasicAuthenticationFilter.class).exceptionHandling()
//		.authenticationEntryPoint(entryPoint);
//  }
    
    
    
}
