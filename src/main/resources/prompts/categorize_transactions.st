Given the following transactions:
${transactions}

For each transaction, provide:
- Category (e.g., groceries, rent, travel)
- Monthly summary of spending
- List any suspicious transactions (unusual amount, unknown merchant, etc.)

Respond in JSON:
{
  "categories": { "transactionId1": "category1", ... },
  "summary": "Monthly summary here",
  "suspicious": ["transactionId2", ...]
}