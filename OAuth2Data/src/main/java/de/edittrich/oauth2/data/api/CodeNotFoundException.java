package de.edittrich.oauth2.data.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CodeNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CodeNotFoundException(String code) {
		super("could not find code '" + code + "'.");
	}
}