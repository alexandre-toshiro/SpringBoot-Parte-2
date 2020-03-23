package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

// Não podemos injetar aqui.A solução é passar no construtor, que obriga a passar o TokenService como par.
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	// Ondeper - Roda uma vez a cada req

	private TokenService tokenService;

	public AutenticacaoViaTokenFilter(TokenService tokenService) {
		super();
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Para cada req, será necessário a auth do TOKEN, pois a API é stateless

		String token = recuperarToken(request);
		boolean valido = tokenService.isTokenValid(token);
		System.out.println(valido);

		filterChain.doFilter(request, response);
		// Após as ações necessárias, seguir o fluxo da requisição.

	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
