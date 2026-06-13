package com.smartgrocery.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String welcome() {
        return """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"><title>Smart Grocery Backend</title></head>
            <body style="font-family: system-ui; max-width: 600px; margin: 4rem auto; padding: 2rem; text-align: center;">
                <h1 style="color: #0f766e;">Backend is running successfully</h1>
                <p>Smart Grocery API is up. Use the frontend at <a href="http://localhost:5173">http://localhost:5173</a> to shop.</p>
            </body>
            </html>
            """;
    }
}
