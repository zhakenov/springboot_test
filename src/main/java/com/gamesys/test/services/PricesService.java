package com.gamesys.test.services;

import com.gamesys.test.models.Price;
import com.gamesys.test.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricesService {

    @Autowired
    PricesRepository pricesRepository;

    public void processPrices(List<Price> prices){
        for(Price price:prices){
            double avg = (price.getOpen()+price.getLow()+price.getHigh()+price.getClose())/4;
            price.setAverage(avg);
            price.setRising(price.getClose()>price.getOpen());
        }
        pricesRepository.createOrUpdatePrices(prices);
    }

    public List<Price> getLastTenPrices(){
        return pricesRepository.getLastTenPrices();
    }
}
