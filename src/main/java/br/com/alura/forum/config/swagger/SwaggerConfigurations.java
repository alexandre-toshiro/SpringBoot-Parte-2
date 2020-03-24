package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.model.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfigurations {

	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)// Informa o tipo de documentação
				.select().apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
				// diz a partir de qual pacote ele irá começar a ler o nosso projeto.
				.build().ignoredParameterTypes(Usuario.class)
				// ignora as classes de usuário para nnão pode ver a senha dos usupários.
				.globalOperationParameters(
						Arrays.asList(new ParameterBuilder().name("Authorization").description("Header para Token JWT")
								.modelRef(new ModelRef("string")).parameterType("header").required(false).build()));
	}
}
