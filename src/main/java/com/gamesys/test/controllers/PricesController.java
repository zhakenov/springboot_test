package com.gamesys.test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricesController {

    @GetMapping(path="/prices")
    public ResponseEntity<Object> getResult(){
        return ResponseEntity.ok("Hello");
    }

}
