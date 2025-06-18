package com.example.ai.transactions.ai;

import com.example.ai.transactions.model.Transaction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategorizationService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @org.springframework.beans.factory.annotation.Value("classpath:prompts/categorize_transactions.st")
    private Resource categorizePromptTemplate;

    public CategorizationService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    private String buildPrompt(List<Transaction> transactions) {
        String txList = transactions.stream()
                .map(tx -> String.format("{\"id\": \"%s\", \"description\": \"%s\", \"amount\": %.2f, \"merchant\": \"%s\", \"date\": \"%s\"}",
                        tx.getId(), tx.getDescription(), tx.getAmount(), tx.getMerchant(), tx.getDate()))
                .collect(Collectors.joining(",\n", "[\n", "\n]"));
        try {
            String template = StreamUtils.copyToString(categorizePromptTemplate.getInputStream(), StandardCharsets.UTF_8);
            return template.replace("${transactions}", txList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load prompt template", e);
        }
    }

    private JsonNode callLLM(List<Transaction> transactions) {
        String prompt = buildPrompt(transactions);
        String response = String.valueOf(chatClient.prompt(prompt).call().chatResponse());
        try {
            return objectMapper.readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse LLM response", e);
        }
    }

    public Map<String, String> categorizeTransactions(List<Transaction> transactions) {
        JsonNode root = callLLM(transactions);
        Map<String, String> categories = new HashMap<>();
        if (root.has("categories")) {
            root.get("categories").fields().forEachRemaining(entry -> categories.put(entry.getKey(), entry.getValue().asText()));
        }
        return categories;
    }

    public String summarizeTransactions(List<Transaction> transactions) {
        JsonNode root = callLLM(transactions);
        return root.has("summary") ? root.get("summary").asText() : "";
    }

    public List<String> detectSuspiciousTransactions(List<Transaction> transactions) {
        JsonNode root = callLLM(transactions);
        List<String> suspicious = new ArrayList<>();
        if (root.has("suspicious") && root.get("suspicious").isArray()) {
            root.get("suspicious").forEach(node -> suspicious.add(node.asText()));
        }
        return suspicious;
    }
}