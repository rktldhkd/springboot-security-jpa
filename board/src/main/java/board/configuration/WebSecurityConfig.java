package board.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }//passwordEncoder
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//csrf 기능 해제. 안하면 post전송 시 에러발생. form태그에 csrf값을 hidden태그에 넣어도 에러발생...
    	http.csrf().disable().httpBasic();
    	// /write, /admin, /users 에대한 경로는 다 인증을 진행하도록 한다. 나머지는 permitAll()
    	//'login' 요청이 있는 경우 인증을 진행하여, 인증이 성공한 경우 기본 경로로 설정한 '/' 으로 이동합니다.
    	//'logout' 요청이 있는 경우 로그아웃 처리를 하고, 성공한 경우 기본 경로로 설정한 '/' 으로 이동합니다.
        http
            .authorizeRequests()
                //.antMatchers("/write", "/write/*").authenticated()
                //.antMatchers("/admin", "/admin/*").authenticated()
                //.antMatchers("/users", "/users/*").authenticated()
                //.antMatchers("/resources/**", "/webjars/**", "/static/**", "/signup", "/").permitAll()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/")
                .and()
            .logout()	
            	.logoutUrl("/logout")
            	.logoutSuccessUrl("/")
            	.invalidateHttpSession(true)
                .permitAll()
		        .and();
    }	
    
    //인증방식 설정
    //users, authorities 테이블과 자동 매칭되서 인증하는데, 사용자가 정의한 테이블 이용 시,
    //usersByUsernameQuery, authoritiesByUsernameQuery 메소드에서 쿼리 사용하여 설정가능
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select username,password, enabled from jpa_users where username=?")
                .authoritiesByUsernameQuery("select username, authority from jpa_authorities where username=?")
                .passwordEncoder(passwordEncoder())
		;
	}
    
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/favicon.ico", "/css/**", "/image/**", "/js/**", "/webjars/**");
	}
}//class