package com.example.ai.transactions.dto;

import com.example.ai.transactions.model.Transaction;
import lombok.Data;
import java.util.List;

@Data
public class TransactionRequest {
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }
}