package com.gamesys.test.controllers;

import com.gamesys.test.models.Price;
import com.gamesys.test.services.PricesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class PricesServiceTest {

    @Autowired
    PricesService pricesService;

    @Test
    public void processPrices() throws InterruptedException {

        List<Price> prices = new ArrayList<>();
        for(int i=1;i<=12;i++){
            Price price = new Price();
            price.setDate(new Date(System.currentTimeMillis()+i*60*1000));
            price.setOpen(i*1.1);
            price.setLow(i*1.2);
            price.setHigh(i*1.3);
            price.setClose(i*1.4);
            prices.add(price);
        }
        pricesService.processPrices(prices);


        List<Price> resultPrices = pricesService.getLastTenPrices();
        Assert.assertEquals(10, resultPrices.size());

        for(int i=0;i<resultPrices.size();i++){
            Price comparePrice = prices.get(prices.size()-1-i);
            Double compareAvg = (comparePrice.getOpen() + comparePrice.getLow()
                    + comparePrice.getHigh() + comparePrice.getClose())/4;
            Boolean compareRising = comparePrice.getClose()>comparePrice.getOpen();
            Assert.assertEquals(compareAvg, resultPrices.get(i).getAverage());
            Assert.assertEquals(compareRising, resultPrices.get(i).getRising());
            Assert.assertEquals(comparePrice.getDate(), resultPrices.get(i).getDate());
        }

    }
}