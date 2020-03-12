package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController // Para não repetir a anotação @Responsebody em todos os métodos.
@RequestMapping("/topicos") // Todas as req de /topicos cairão nesse controller.
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping // Nos método colocaremos apenas as notações com os verbos http.
	public List<TopicoDto> lista(String nomeCurso) { // nome curso é recebido como parametro na url.
		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		}
	}

	@PostMapping // @rebody Indica que é pra pegar do corpo da req e não do parâmetro de URL.
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		// Devolvemos um topicodto na corpo da resposta, que vai representar o conteúdo
		// criado.
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@GetMapping("/{id}") // Específica o parâmetro que este método irá receber na URL
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		/*
		 * Por padrão o SPRING irá olhar o nome do parâmetro do método e da URL, caso
		 * tenham o mesmo nome já irá associa-los automaticamente. (@PathVariable("id")
		 * Long codigo) - Caso o nome da váriavel seja diferente do path. Método para
		 * listar apenas um Tópico.
		 */

		Optional<Topico> topico = topicoRepository.findById(id); // repository irá buscar no banco, um por id.
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}

		return ResponseEntity.notFound().build();

	}

	@PutMapping("/{id}")
	@Transactional // avisa o spring para dispara o update no banco de dados, sem ela não é
					// atualizado.
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
			// O parâmetro do "ok" é o corpo que será devolvido como resposta pelo servidor.
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();

	}
}
