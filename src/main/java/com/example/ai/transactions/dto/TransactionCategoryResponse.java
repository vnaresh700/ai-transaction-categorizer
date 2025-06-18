package com.example.ai.transactions.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class TransactionCategoryResponse {
    private Map<String, String> transactionCategories; // transactionId -> category
    private String monthlySummary;
    private List<String> suspiciousTransactionIds;

    public Map<String, String> getTransactionCategories() {
        return transactionCategories;
    }
    public String getMonthlySummary() {
        return monthlySummary;
    }
    public List<String> getSuspiciousTransactionIds() {
        return suspiciousTransactionIds;
    }
    public void setTransactionCategories(Map<String, String> transactionCategories) {
        this.transactionCategories = transactionCategories;
    }
    public void setMonthlySummary(String monthlySummary) {
        this.monthlySummary = monthlySummary;
    }
    public void setSuspiciousTransactionIds(List<String> suspiciousTransactionIds) {
        this.suspiciousTransactionIds = suspiciousTransactionIds;
    }

}