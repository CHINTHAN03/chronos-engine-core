    package org.example.spring_first.controller;

    import lombok.RequiredArgsConstructor;
    import org.example.spring_first.dto.AuthenticationRequest;
    import org.example.spring_first.dto.AuthenticationResponse;
    import org.example.spring_first.dto.RegisterRequest;
    import org.example.spring_first.service.AuthenticationService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    public class AuthenticationController {

        private final AuthenticationService service;

        @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
            return ResponseEntity.ok(service.register(request));
        }

        @PostMapping("/authenticate")
        public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
            return ResponseEntity.ok(service.authenticate(request));
        }
    }