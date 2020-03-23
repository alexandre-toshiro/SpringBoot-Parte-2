package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // Habilita o security do spring
@Configuration // Para o spring já carregar e ler as configurações no projeto ao ser inicializado.
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	// Por padrão o spring bloqueia tudo, até que se venha e faça todas as configurações necessárias
	// liberando os acessos
	
	@Autowired
	private AutentificacaoService autenticacaoService;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	// Configurações de autenticação.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
		// passamos qual a classe possui a lógica de auth.
	}
	
	// Configurações de autorização.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()//tudo que for /topicos com o método get, será permitido a todos. (métod o, URL)
		.antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
		.antMatchers(HttpMethod.POST,"/auth").permitAll()
		.anyRequest().authenticated()// qualquer outra requisição precisa de autentificação.
		.and().csrf().disable() // com o token a aplicação já está livre do ataque de csrf
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		// Informa ao spring que não é pr afazer autentificação(que fica em memória)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		// adiciona o tokenfilter antes da autenticação padrão.
		
	}
	
	// Configurações de recursos estásticos(requisições para js, css, imagens, etc) 
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
	
} 




