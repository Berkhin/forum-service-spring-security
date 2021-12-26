package telran.b7a.security.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.POST, "/account/register/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();

		http.csrf().disable(); // cross site request forgery

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // turn off cookie

		http.authorizeRequests()
//		.antMatchers(HttpMethod.POST, "/account/register/**")
//			.permitAll()
				.antMatchers("/forum/posts/**").permitAll().antMatchers("/account/password/**").authenticated()
				.antMatchers("/forum/**", "/account/**").access("@customSecurity.isPasswordExpired(authentication.name)")
				.antMatchers("/account/user/{login}/role/{role}").hasRole("ADMINISTRATOR")
				.antMatchers(HttpMethod.PUT, "/account/user/{login}/**").access("#login == authentication.name")
				.antMatchers(HttpMethod.DELETE, "/account/user/{login}/**")
				.access("#login == authentication.name or hasRole('ADMINISTRATOR')")
				.antMatchers(HttpMethod.POST, "/forum//post/{author}/**").access("#author == authentication.name")
				.antMatchers(HttpMethod.PUT, "/forum/post/{id}/comment/{author}/**")
				.access("#author == authentication.name").antMatchers(HttpMethod.DELETE, "/forum/post/{id}/**")
				.access("@customSecurity.checkPostAuthority(#id, authentication.name) or hasRole('MODERATOR')")
				.antMatchers(HttpMethod.PUT, "/forum/post/{id}/**")
				.access("@customSecurity.checkPostAuthority(#id, authentication.name)").anyRequest().authenticated();

	}
}
