package model;

import java.io.File;
import java.sql.*;

public class Database {
    private static String dbName = "cache.db";
    private static String dbURL = "jdbc:sqlite:" + dbName;

    public Database(){
        setUp();
    }

    public static void createDB(){
        File dbFile = new File(dbName);
        if (dbFile.exists()){
            System.out.println("Database already exists");
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            // If we get here that means no exception raised from getConnection - meaning it worked
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void setUp(){
        createDB();

        String createTable = """
                        CREATE TABLE IF NOT EXISTS cache (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            start_currency TEXT NOT NULL,
                            end_currency TEXT NOT NULL,
                            rate FLOAT NOT NULL
                        );
                        """;

        try(Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()){
            statement.execute(createTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clear(){
        String dropTable = "DROP TABLE IF EXISTS cache;";
        try(Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()){
            statement.execute(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getRate(String startCurrency, String endCurrency) {
        setUp();

        String query = """
                            SELECT rate
                            FROM cache
                            WHERE start_currency = ? AND end_currency = ?
                            order by id desc
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, startCurrency);
            statement.setString(2, endCurrency);
            ResultSet resultSet = statement.executeQuery();
//            no result in resultSet
            if (!resultSet.next()) {
                return 0;
            }
//            get last row in resultSet

            return resultSet.getDouble("rate");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean addConversation(String startCurrency, String endCurrency, double rate) {
        setUp();

        String query = """
                            INSERT INTO cache (start_currency, end_currency, rate)
                            VALUES (?, ?, ?);
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, startCurrency);
            statement.setString(2, endCurrency);
            statement.setDouble(3, rate);
            statement.executeUpdate();
            System.out.println("A new conversation has been added.");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    }
