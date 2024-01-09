package sru.edu.group1.workorders.security;

import java.io.IOException;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sru.edu.group1.workorders.services.CustomUserDetailsService;

/**
 * This class handles most of the web security, including but not limited to the way a user's role is processed and where they go when they login
 * While most of this code was created by Group 1, the basic idea of the code itself was taken from Zac Frelion's Login project.
 *
 * A Lot of the other code was taken from this source: https://www.baeldung.com/spring-redirect-after-login
 * mostly for putting the users onto the right landing pages.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

		
		@Bean
		 UserDetailsService userDetailsService() {
			return new CustomUserDetailsService();
		}
		
		@Bean
		 BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean
		 DaoAuthenticationProvider authenticationProvider() {
			
			// sets properties of the Dao Authentication Provider
			
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService());
			authProvider.setPasswordEncoder(passwordEncoder());
			
			return authProvider;
		}
		
		@Bean
		public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
			return new MySimpleUrlAuthenticationSuccessHandler();
		}
	
		public class MySimpleUrlAuthenticationSuccessHandler
		  implements AuthenticationSuccessHandler {
		 
		    protected Log logger = LogFactory.getLog(this.getClass());

		    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

		    @Override
		    public void onAuthenticationSuccess(HttpServletRequest request, 
		      HttpServletResponse response, Authentication authentication)
		      throws IOException {
		 
		        handle(request, response, authentication);
		        clearAuthenticationAttributes(request);
		    }
		
		    protected void handle(
		            HttpServletRequest request,
		            HttpServletResponse response, 
		            Authentication authentication
		    ) throws IOException {

		        String targetUrl = determineTargetURL(authentication);

		        if (response.isCommitted()) {
		            logger.debug(
		                    "Response has already been committed. Unable to redirect to "
		                            + targetUrl);
		            return;
		        }

		        redirectStrategy.sendRedirect(request, response, targetUrl);
		    }
		    
		    protected void clearAuthenticationAttributes(HttpServletRequest request) {
		        HttpSession session = request.getSession(false);
		        if (session == null) {
		            return;
		        }
		        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		    }
		}
		protected String determineTargetURL(final Authentication authentication)
		{
			Map<String, String> roleTargetUrlMap = new HashMap<>();
			roleTargetUrlMap.put("User", "/group/userLanding");
			roleTargetUrlMap.put("Admin", "/admin/Landing");
			roleTargetUrlMap.put("Tech", "/tech/Landing");
			roleTargetUrlMap.put("Manager", "/manager/Landing");
			final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (final GrantedAuthority grantedAuthority : authorities)
			{
				String authorityName = grantedAuthority.getAuthority();
				if(roleTargetUrlMap.containsKey(authorityName))
				{
					return roleTargetUrlMap.get(authorityName);
				}
			}
			
			throw new IllegalStateException();
		}
		
		
		protected void configure(final AuthenticationManagerBuilder auth) throws Exception
		{
			auth.authenticationProvider(authenticationProvider());
		}
		
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception
		{
			http
				.authorizeHttpRequests((authz) -> {
					try {
						authz
								.requestMatchers("/group/**").hasAnyAuthority("Admin", "User", "Tech", "Manager")
								.requestMatchers("/tech/**").hasAnyAuthority("Tech")
								.requestMatchers("/manager/**").hasAnyAuthority("Manager")
								.requestMatchers("/admin/**").hasAnyAuthority("Admin")
								.requestMatchers("/excel/**").hasAnyAuthority("Admin", "Manager")
								.anyRequest().permitAll()
								.and()
								.formLogin()
									.loginProcessingUrl("/login")
									.usernameParameter("email")
									.successHandler(myAuthenticationSuccessHandler())
									.permitAll()
								.and()
								.logout().logoutUrl("/").logoutSuccessUrl("/").permitAll();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
						)
				.httpBasic();
			return http.build();
		}
	
}


