package com.gamesys.test.runners;

import com.gamesys.test.models.Price;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class PricesRunnerTest {

    PricesRunner runner = new PricesRunner();

    @Test
    public void getPricesJSONFromSource() {
        Assert.assertNull(runner.getPricesJSONFromSource("http://unknown.com"));
        Assert.assertNull(runner.getPricesJSONFromSource("https://google.com"));
        Assert.assertNotNull(runner.getPricesJSONFromSource(runner.url));
    }


    @Test
    public void getPricesFromJSONArray() {
        JSONArray array = new JSONArray();
        array.add(new JSONObject());
        Assert.assertEquals(0, runner.getPricesFromJSONArray(array).size());


        JSONArray resultArray = runner.getPricesJSONFromSource(runner.url);
        if (resultArray.size() > 0) {
            JSONObject resultObject = (JSONObject) resultArray.get(0);
            Price price = runner.getPricesFromJSONArray(resultArray).get(0);
            Assert.assertEquals(
                    runner.getDateFromString((String) resultObject.get("date"), (String) resultObject.get("minute")),
                    price.getDate());
            Assert.assertEquals((Double) resultObject.get("open"), price.getOpen());
            Assert.assertEquals((Double) resultObject.get("low"), price.getLow());
            Assert.assertEquals((Double) resultObject.get("high"), price.getHigh());
            Assert.assertEquals((Double) resultObject.get("close"), price.getClose());
        }
    }

    @Test
    public void getDateFromString() throws ParseException {
        Assert.assertNull(runner.getDateFromString(null, null));
        Assert.assertNull(runner.getDateFromString("2019", "00"));
        Assert.assertNull(runner.getDateFromString("2019aaaa", "00"));
        Assert.assertNull(runner.getDateFromString("20190302", "00aa"));
        Assert.assertEquals(runner.sdf.parse("2019-03-02 00:00"), runner.getDateFromString("20190302", "00:00"));
    }
}