package com.example.demo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final DataSource dataSource;
	
	private static final String USER_SQL = "SELECT email, password, true FROM user WHERE email = ?";
	
	private static final String ROLE_SQL = "SELECT email, role FROM user WHERE email = ?";
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/∗∗", "/css/∗∗");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ログイン不要ページの設定
        http.authorizeRequests()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/signup").permitAll()
            .antMatchers("/users/admin").hasAuthority("ROLE_ADMIN")
            .anyRequest().permitAll();
            //.anyRequest().authenticated(); 
        
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
        	.logoutUrl("/logout")
        	.logoutSuccessUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(USER_SQL)
			.authoritiesByUsernameQuery(ROLE_SQL)
			.passwordEncoder(passwordEncoder());
	}
}
