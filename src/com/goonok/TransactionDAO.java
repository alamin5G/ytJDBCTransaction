package com.goonok;

import java.awt.dnd.DragGestureEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    private static final String url = "jdbc:mysql://localhost:3306/testdb";
    private static final String user = "root";
    private static final String pass = "252646";
    private Connection connection;
    private String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
    private String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
    public TransactionDAO() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");
            connection = DriverManager.getConnection(url, user, pass);
            connection.setAutoCommit(false);
            System.out.println("Database connection established!");
        }catch (ClassNotFoundException c){
            System.err.println("Driver not loaded - " + c.getMessage());
        }catch (SQLException s){
            System.err.println("Connection failed at constructor TransactionDAO() - " + s.getMessage());
        }

    }

    public void startTransaction(){
        try {
           PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);
           PreparedStatement depositStatement = connection.prepareStatement(depositQuery);

           withdrawStatement.setDouble(1, 500);
           withdrawStatement.setString(2, "account124");
           depositStatement.setDouble(1, 500);
           depositStatement.setString(2, "account123");
           int withdrawn = withdrawStatement.executeUpdate();
           int deposited = depositStatement.executeUpdate();
           if (withdrawn > 0 && deposited > 0){
               connection.commit();
               System.out.println("Transaction Successful!");
           }else {
               connection.rollback();
               System.out.println("Transaction Failed!");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
