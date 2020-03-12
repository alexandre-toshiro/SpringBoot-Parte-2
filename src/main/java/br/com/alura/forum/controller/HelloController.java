package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping("/")
	@ResponseBody // Faz com que o retorno apareça na página, do contrário o SB irá interpretar o
					// retorno como uma página e irá procurar a mesma no projeto..
	public String helo() {
		return "Hello World!!!";
	}
}
