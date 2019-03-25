package com.haulmont.testtask.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InitDB {

    public static Connection connectToDB(){

        Connection result = null;
        try {
            result = DriverManager.getConnection("jdbc:hsqldb:file:autoServiceDB/autoDB", "SA", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
