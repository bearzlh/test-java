package com.example.bear;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.sql.*;

public class BearApplicationTests {
    @Test
    public void contextLoads() {
        System.out.println("hello world");
    }

    @Test
    public void testRedis()
    {
        Jedis jedis = new Jedis("10.0.3.160", 6379);
        jedis.set("name", "bear");
        jedis.lpush("numbers","1");
        jedis.lpush("numbers","2");
        System.out.println(jedis.get("name"));
        System.out.println(jedis.lpop("numbers"));
    }

    @Test
    public void testMysql() {
        ResultSet rs = null;
        Statement stm = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://10.0.3.160/db_toushibao_main?user=root&password=123456");
            stm = conn.createStatement();
            rs = stm.executeQuery("select * from user_info limit 10");
            while (rs.next()) {
                System.out.println(rs.getString("user_name") + "\t" + rs.getString("user_email") + "\n");
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stm = null;
            }
        }
    }
}
