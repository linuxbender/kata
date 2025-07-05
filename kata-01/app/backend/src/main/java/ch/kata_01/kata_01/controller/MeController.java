package ch.kata_01.kata_01.controller;

import ch.kata_01.kata_01.service.SecurityService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {
    private final SecurityService securityService;

    public MeController(SecurityService securityService) {
        this.securityService = securityService;

    }

    @GetMapping
    public ResponseEntity<String> me() {
        ResponseCookie cookie = ResponseCookie.from("SESSION_ID", securityService.generateToken())
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(3600) // 30 minutes
                .sameSite("Strict")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Hello World");
    }
}
