/*

-------notes about Spring Security-------

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

	@Bean
	public UserDetailsService userDetailsService() throws Exception {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
		return manager;
	}
}


*/









/*package org.sdr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity
//@ImportResource("src/main/resources/spring-security.xml")
public class PictoGuessSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/login", "register").permitAll()		
				.antMatchers("/home").authenticated()			
				.and()
			.formLogin()
				.loginPage("/login").failureUrl("/login-error").permitAll;	
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
}
*/

/*
protected void configure(HttpSecurity http) throws Exception {
	http
		.authorizeRequests()                                                                1
			.antMatchers("/resources/**", "/signup", "/about").permitAll()                  2
			.antMatchers("/admin/**").hasRole("ADMIN")                                      3
			.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")            4
			.anyRequest().authenticated()                                                   5
			.and()
		// ...
		.formLogin();
}



Inside the SecurityContextHolder we store details of the principal currently interacting with the application. Spring Security uses an Authentication object to represent this information. You won’t normally need to create an Authentication object yourself, but it is fairly common for users to query the Authentication object. You can use the following code block - from anywhere in your application - to obtain the name of the currently authenticated user, for example:

Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

if (principal instanceof UserDetails) {
String username = ((UserDetails)principal).getUsername();
} else {
String username = principal.toString();
}
The object returned by the call to getContext() is an instance of the SecurityContext interface. This is the object that is kept in thread-local storage. As we’ll see below, most authentication mechanisms within Spring Security return an instance of UserDetails as the principal.



protected void configure(HttpSecurity http) throws Exception {
	http
		.logout()                                                                1
			.logoutUrl("/my/logout")                                                 2
			.logoutSuccessUrl("/my/index")                                           3
			.logoutSuccessHandler(logoutSuccessHandler)                              4
			.invalidateHttpSession(true)                                             5
			.addLogoutHandler(logoutHandler)                                         6
			.deleteCookies(cookieNamesToClear)                                       7
			.and()
		...
}


5.8.2 JDBC Authentication

You can find the updates to support JDBC based authentication. The example below assumes that you have already defined a DataSource within your application. The jdbc-javaconfig sample provides a complete example of using JDBC based authentication.

@Autowired
private DataSource dataSource;

@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// ensure the passwords are encoded properly
	UserBuilder users = User.withDefaultPasswordEncoder();
	auth
		.jdbcAuthentication()
			.dataSource(dataSource)
			.withDefaultSchema()
			.withUser(users.username("user").password("password").roles("USER"))
			.withUser(users.username("admin").password("password").roles("USER","ADMIN"));
}

*/