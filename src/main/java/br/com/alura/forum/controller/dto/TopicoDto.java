package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.Topico;

/* DTO - DATA TRANSFER OBJECT
 * Não é uma boa prática retornar entidades JPA nos controllers, sendo mais indicado retornar as classes DTO
 * que irão retornar apenas os dados nevessários de determinada classe
 * Por ex: A classe de um usuário que possui senha, se retornarmos a classe, também retornaria a sua senha, o que é uma falha de segurança.
 * Através das DTO's nós blindamos a aplicação desse tipo de falha, entre outras.
 * */

// DTO E FORM - Dto são objetos que mandam informações da API para o cliente.

public class TopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public TopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static List<TopicoDto> converter(List<Topico> topicos) {
		
		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	}

}
