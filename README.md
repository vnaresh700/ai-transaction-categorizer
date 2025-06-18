# AI Transaction Categorizer

This project uses Spring Boot and Spring AI to automatically categorize bank transactions, summarize monthly activity, and detect suspicious transactions using Azure OpenAI.

## Features

- Categorizes a list of bank transactions using an LLM (Azure OpenAI)
- Provides a monthly summary of transactions
- Detects suspicious transactions

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring AI (Azure OpenAI)
- Maven

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Azure OpenAI resource (API key, endpoint, deployment name)

### Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/ai-transaction-categorizer.git
    cd ai-transaction-categorizer
    ```

2. Configure Azure OpenAI credentials in `src/main/resources/application.properties`:
    ```
    spring.ai.azure.openai.api-key=YOUR_AZURE_OPENAI_API_KEY
    spring.ai.azure.openai.endpoint=https://YOUR_RESOURCE_NAME.openai.azure.com/
    spring.ai.azure.openai.deployment-name=YOUR_DEPLOYMENT_NAME
    ```

3. Build and run the application:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

### API Usage

- **POST** `/api/transactions/categorize`
    - Request body: JSON with a list of transactions
    - Response: Categories, summary, and suspicious transaction IDs

Example request:
```json
{
  "transactions": [
    {
      "id": "1",
      "description": "Coffee shop",
      "amount": 4.50,
      "merchant": "Starbucks",
      "date": "2024-06-01"
    }
  ]
}
