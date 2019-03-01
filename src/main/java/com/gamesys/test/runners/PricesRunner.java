package com.gamesys.test.runners;

import com.gamesys.test.models.Price;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PricesRunner implements ApplicationRunner {

    String url;
    JSONParser jsonParser;
    SimpleDateFormat sdf;
    boolean needToGetPrices;

    public PricesRunner() {
        url = "https://api.iextrading.com/1.0/stock/aapl/chart/1d";
        jsonParser = new JSONParser();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        needToGetPrices = true;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (needToGetPrices){
            JSONArray pricesJSONArray = getPricesJSONFromSource(url);
            List<Price> prices = getPricesFromJSONArray(pricesJSONArray);
            System.out.println(prices.size());
            Thread.sleep(60*1000);
        }
    }

    public JSONArray getPricesJSONFromSource(String url) {
        Unirest.setTimeouts(30000, 30000);
        try {
            HttpResponse<String> response = Unirest.get(url).asString();
            if (response.getStatus() == 200) {
                Object responseObj = jsonParser.parse(response.getBody());
                if (responseObj instanceof JSONArray) {
                    return (JSONArray) responseObj;
                }
            }
        } catch (UnirestException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Price> getPricesFromJSONArray(JSONArray pricesJSONArray) {
        List<Price> prices = new ArrayList<>();

        try {
            for (int i = 0; i < pricesJSONArray.size(); i++) {
                JSONObject priceJSON = (JSONObject) pricesJSONArray.get(i);
                String dateStr = (String) priceJSON.get("date");
                String minuteStr = (String) priceJSON.get("minute");

                Date date = getDateFromString(dateStr, minuteStr);
                Double open = getDoubleFromObject(priceJSON.get("open"));
                Double low = getDoubleFromObject(priceJSON.get("low"));
                Double high = getDoubleFromObject(priceJSON.get("high"));
                Double close = getDoubleFromObject(priceJSON.get("close"));

                if(date!=null && open!=null && low!=null && high!=null && close!=null){
                    Price price = new Price(date, open, low, high, close);
                    prices.add(price);
                }
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return prices;
    }

    public Date getDateFromString(String date, String minute) {
        if (date!=null && minute!=null && date.length() == 8) {
            try {
                String dateStr = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" +
                        date.substring(6, 8) + " " + minute;
                return sdf.parse(dateStr);
            } catch (java.text.ParseException e) {
//                e.printStackTrace();
            }
        }
        return null;
    }

    private Double getDoubleFromObject(Object obj){
        if(obj instanceof Double){
            return (Double) obj;
        }else if(obj instanceof Long){
            return ((Long)obj).doubleValue();
        }
        return null;
    }
}
