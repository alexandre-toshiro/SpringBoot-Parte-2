package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
         
@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;// Informa a mensagem de erro de acordo com o idioma configurado na máquina.

	// Quando se trata uma exceção o Spring devolve a resposta como 200 e não
	// queremos isso, queremos que devolva o erro normalmente, por isso colocamos
	// essa notação. E entre (), passamos o bad request.
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	// Diz para o spring que quando acontecer uma exceção em algum controller
	// chamará esse método. Passa a exceção dentro dos ()
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		
		List<ErroDeFormularioDto> dto = new ArrayList<>(); // Declara a lista de formulários DTOs
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		// Para cada field error, criaremos uma item dentro da lista de ErroFormularioDto.
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale()); // Pega a mensagem de erro e informa de acordo com a localidade da pessoa.
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
}
