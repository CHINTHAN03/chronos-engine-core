package org.example.spring_first.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AiSummarizationService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${chronos.ai.gemini.url}")
    private String geminiUrl;

    @Value("${chronos.ai.gemini.api-key}")
    private String apiKey;

    public AiSummarizationService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public String summarizeForExecutiveDeck(String rawLogs, String audience) {
        String prompt = "You are a Senior Technical Writer at a top-tier SaaS company. Convert the following raw developer logs into a Release Notes document formatted in standard Markdown.\n\n" +
                "TARGET AUDIENCE: " + audience + ". Adjust your technical depth and tone accordingly.\n\n" +
                "FORMATTING RULES:\n" +
                "- Group items logically into sections like '✨ New Features', '🐛 Bug Fixes', or '🔧 Under the Hood'.\n" +
                "- Do not include any conversational introductions or conclusions. Output ONLY the release notes.\n\n" +
                "Raw Logs:\n" + rawLogs;

        try {

            java.util.Map<String, Object> requestMap = java.util.Map.of(
                    "contents", java.util.List.of(
                            java.util.Map.of("parts", java.util.List.of(
                                    java.util.Map.of("text", prompt)
                            ))
                    ),
                    "generationConfig", java.util.Map.of(
                            "temperature", 0.7 // Forces the AI to generate more dynamic, varied text
                    )
            );

            String requestBody = objectMapper.writeValueAsString(requestMap);

            String responseJson = webClient.post()
                    .uri(geminiUrl + "?key=" + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode rootNode = objectMapper.readTree(responseJson);
            return rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

        } catch (Exception e) {
            System.err.println("AI Generation failed: " + e.getMessage());
            return " - " + rawLogs;
        }
    }
}