package com.goonok;

public class Main {
    public static void main(String[] args) {
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.startTransaction();
    }
}