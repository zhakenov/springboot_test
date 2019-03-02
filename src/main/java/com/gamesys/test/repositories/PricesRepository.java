package com.gamesys.test.repositories;


import com.gamesys.test.models.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PricesRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final int BATCH_SIZE = 100;

    public void createOrUpdatePrices(List<Price> prices) {

        String query =
                "MERGE INTO prices(date, open, low, high, close, avg, is_rising) " +
                        "KEY(date) VALUES(?, ?, ?, ?, ?, ?, ?)";

        for (int i = 0; i < prices.size(); i += BATCH_SIZE) {
            List<Price> batchList = prices.subList(i, i + BATCH_SIZE > prices.size() ? prices.size() : i + BATCH_SIZE);
            jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Price price = batchList.get(i);
                    preparedStatement.setObject(1, price.getDate());
                    preparedStatement.setDouble(2, price.getOpen());
                    preparedStatement.setDouble(3, price.getLow());
                    preparedStatement.setDouble(4, price.getHigh());
                    preparedStatement.setDouble(5, price.getClose());
                    preparedStatement.setDouble(6, price.getAverage());
                    preparedStatement.setBoolean(7, price.getRising());
                }

                @Override
                public int getBatchSize() {
                    return batchList.size();
                }
            });
        }
    }

    public List<Price> getLastTenPrices(){
        String query = "SELECT * FROM prices ORDER BY id DESC LIMIT 10";
        return jdbcTemplate.query(query, new RowMapper<Price>() {
            @Override
            public Price mapRow(ResultSet rs, int i) throws SQLException {
                Price price = new Price();
                price.setId(rs.getInt("id"));
                price.setDate(new Date(rs.getTimestamp("date").getTime()));
                price.setOpen(rs.getDouble("open"));
                price.setLow(rs.getDouble("low"));
                price.setHigh(rs.getDouble("high"));
                price.setClose(rs.getDouble("close"));
                price.setAverage(rs.getDouble("avg"));
                price.setRising(rs.getBoolean("is_rising"));
                price.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));
                return price;
            }
        });
    }

}
