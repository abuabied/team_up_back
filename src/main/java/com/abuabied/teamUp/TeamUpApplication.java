package com.abuabied.teamUp;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TeamUpApplication {
	public static void main(String[] args) {
		SpringApplication.run(TeamUpApplication.class, args);
	}

	@GetMapping("/.well-known/pki-validation/1069245F457443EC4D519282B5B02C3C.txt")
	public ResponseEntity<String> cert() {
		return new ResponseEntity<String>("84ADED9F0B2D2F22EA3428011F3D270D5139FE039FD5717EE1524D5DDC4380F4\ncomodoca.com\n5910d1c7e91b155"
				, HttpStatus.OK);
	}
}
