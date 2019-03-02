package com.gamesys.test.controllers;

import com.gamesys.test.models.Price;
import com.gamesys.test.services.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricesController {

    @Autowired
    PricesService pricesService;

    @GetMapping(path="/prices")
    public Iterable<Price> getResult(){
        return pricesService.getLastTenPrices();
    }

}
