package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
//	private final DataSource dataSource;
//	
//	private static final String USER_SQL = "SELECT email, password, true FROM user WHERE email = ?";
//	
//	private static final String ROLE_SQL = "SELECT email, role FROM user WHERE email = ?";
	
	private final UserDetailsService userDetailsService;
	
	public SecurityConfig(@Qualifier("UserDetailsServiceImpl") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/∗∗", "/css/∗∗", "/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ログイン不要ページの設定
        http.authorizeRequests()
            .antMatchers("/articles/new").authenticated()
            .anyRequest().permitAll();
        
        //ログイン処理
        http.formLogin()
        	.loginProcessingUrl("/login")
        	.loginPage("/login")
        	.failureUrl("/login")
        	.usernameParameter("email")
        	.passwordParameter("password")
        	.defaultSuccessUrl("/articles", true);
        
        http.logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //GETメソッドでリクエストを送る場合
        	//.logoutUrl("/logout") //POSTメソッドでリクエストを送る場合
        	.logoutSuccessUrl("/login");
        
        http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.usersByUsernameQuery(USER_SQL)
//			.authoritiesByUsernameQuery(ROLE_SQL)
//			.passwordEncoder(passwordEncoder());
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
