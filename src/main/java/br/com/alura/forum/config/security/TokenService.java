package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}") // Pega no application properties(injeção do properties)
	private String expiration;

	@Value("${forum.jwt.secret}") // Pega no application properties
	private String secret;

	public String gerarToken(Authentication authentication) {

		Usuario logado = (Usuario) authentication.getPrincipal();

		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		// data atual + tempo setado no properties

		return Jwts.builder().setIssuer("API do Fórum da Alura")// seta qual aplicação gerou o token
				.setSubject(logado.getId().toString())// identifica o usuário unico desse token
				.setIssuedAt(hoje) // seta a partir de qual data estará valendo o token
				.setExpiration(dataExpiracao).signWith(SignatureAlgorithm.HS256, secret).compact();

	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
