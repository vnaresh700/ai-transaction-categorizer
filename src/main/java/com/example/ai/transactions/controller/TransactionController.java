package com.example.ai.transactions.controller;

import com.example.ai.transactions.dto.TransactionRequest;
import com.example.ai.transactions.dto.TransactionCategoryResponse;
import com.example.ai.transactions.ai.CategorizationService;
import com.example.ai.transactions.model.Transaction;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final CategorizationService categorizationService;

    public TransactionController(CategorizationService categorizationService) {
        this.categorizationService = categorizationService;
    }

    @PostMapping("/categorize")
    public TransactionCategoryResponse categorize(@RequestBody TransactionRequest request) {
        List<Transaction> transactions = request.getTransactions();
        TransactionCategoryResponse response = new TransactionCategoryResponse();
        response.setTransactionCategories(categorizationService.categorizeTransactions(transactions));
        response.setMonthlySummary(categorizationService.summarizeTransactions(transactions));
        response.setSuspiciousTransactionIds(categorizationService.detectSuspiciousTransactions(transactions));
        return response;
    }
}