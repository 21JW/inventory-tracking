package com.shopify.inventory;

import com.shopify.inventory.constant.DatabaseConst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;

@SpringBootApplication
@MapperScan(basePackages = {"com.shopify.inventory.dao"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class InventoryApplication
{
    public static void main(String[] args)
    {
        initDataBase();
        SpringApplication.run(InventoryApplication.class, args);
    }

    private static void initDataBase()
    {
        try
        {
            Properties props = Resources.getResourceAsProperties("application.properties");
            String url = props.getProperty("inventory.database.url");
            String username = props.getProperty("spring.datasource.username");
            String password = props.getProperty("spring.datasource.password");
            String driver = props.getProperty("spring.datasource.driver-class-name");

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = conn.prepareStatement(DatabaseConst.CHECK_DB_EXIST_SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(DatabaseConst.COUNT_COLUMN) == 0)
            {
                ScriptRunner runner = new ScriptRunner(conn);
                runner.setErrorLogWriter(null);
                runner.setLogWriter(null);
                Resources.setCharset(StandardCharsets.UTF_8);
                runner.runScript(Resources.getResourceAsReader(DatabaseConst.SQL_PATH));
            }
            ps.close();
            conn.close();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
