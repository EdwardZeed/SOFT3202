package au.edu.sydney.soft3202.task3.model;

import java.io.File;
import java.sql.*;

public class Database {
    private static String dbName = "Task3.db";

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

        String createUserTable =
                """
                        CREATE TABLE IF NOT EXISTS User (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            username TEXT NOT NULL
                        );
                """;

        String createGameStateTable =
                """
                        CREATE TABLE IF NOT EXISTS GameState (
                            username Text,
                            StateName TEXT,
                            state TEXT
                        );
                """;

        try(Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()){
            statement.execute(createUserTable);
            statement.execute(createGameStateTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void insertUser(String username){
        String insertUser =
                """
                        INSERT INTO User (username)
                        VALUES (?);
                """;
        try(Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement statement = conn.prepareStatement(insertUser)){

            statement.setString(1, username);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertGameState(String username, String StateName,String state){
        String insertGameState =
                """
                        INSERT INTO GameState (username, StateName, state)
                        VALUES (?, ?, ?);
                """;
        try(Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement statement = conn.prepareStatement(insertGameState)){

            statement.setString(1, username);
            statement.setString(2, StateName);
            statement.setString(3, state);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getGameState(String StateName){
        String getGameState =
                """
                        SELECT state
                        FROM GameState join User on GameState.username = User.username
                        WHERE StateName = ?;
                """;
        try(Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement statement = conn.prepareStatement(getGameState)){

            statement.setString(1, StateName);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                return result.getString("state");
            }
            else{
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateGameState(String username, String StateName, String state) {
        String updateGameState =
                """
                    UPDATE GameState
                    SET state = ?
                    WHERE username = ? AND StateName = ?;
                """;
        try(Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement statement = conn.prepareStatement(updateGameState)){

            statement.setString(1, state);
            statement.setString(2, username);
            statement.setString(3, StateName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



