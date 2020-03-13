package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // Habilita o security do spring
@Configuration // Para o spring já carregar e ler as configurações no projeto ao ser inicializado.
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	// Por padrão o spring bloqueia tudo, até que se venha e faça todas as configurações necessárias
	// liberando os acessos
	
	
	// Configurações de autenticação.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	}
	
	// Configurações de autorização.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/topicos").permitAll()
		//tudo que for /topicos com o método get, será permitido a todos. (métod o, URL)
		.antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
		.anyRequest().authenticated()// qualquer outra requisição precisa de autentificação.
		.and().formLogin();// Já cria um formulário do Spring.
	}
	
	// Configurações de recursos estásticos(requisições para js, css, imagens, etc) 
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
} 




