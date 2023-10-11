package com.abuabied.teamUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TeamUpApplication {
	public static void main(String[] args) {
		SpringApplication.run(TeamUpApplication.class, args);
	}

	@PostMapping("/.well-known/pki-validation/1069245F457443EC4D519282B5B02C3C.txt")
	public ResponseEntity<String> cert() {
		return new ResponseEntity<String>("7A7C7AACCAFA4DF0A4AF033F8FCD4D9A2130558ABBC914B12A51FB7DED07C2AF\ncomodoca.com\n3eb98d7888a3e93\n"
				, HttpStatus.OK);
	}
}
