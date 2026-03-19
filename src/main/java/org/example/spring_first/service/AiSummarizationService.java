package org.example.spring_first.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AiSummarizationService {

    // --- ENTERPRISE STANDARD: Proper SLF4J Logging ---
    private static final Logger log = LoggerFactory.getLogger(AiSummarizationService.class);

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

        // --- ENTERPRISE STANDARD: Java Text Blocks for clean, readable prompts ---
        String prompt = """
                You are a Principal Release Engineer converting raw developer telemetry into a formal Release Artifact.
                
                TARGET AUDIENCE: %s
                
                FORMATTING MANDATES:
                - Use ONLY standard ASCII/alphanumeric characters and basic markdown punctuation (*, -, #).
                - Graphical symbols, unicode icons, and pictograms are strictly prohibited. Output will fail validation if they are included.
                - Tone must be dry, analytical, and highly technical.
                - Categorize the logs under these exact headers if applicable:
                ### System Enhancements
                ### Patches & Bug Fixes
                ### Infrastructure Changes
                
                EXAMPLE EXPECTED OUTPUT:
                ### System Enhancements
                - Implemented stateless JWT session token generation for the identity gateway.
                ### Patches & Bug Fixes
                - Resolved race condition in concurrent database connection pool.
                
                RAW TELEMETRY LOGS:
                %s
                """.formatted(audience, rawLogs);

        try {
            // Cleaned up imports so we can just use Map.of and List.of directly
            Map<String, Object> requestMap = Map.of(
                    "contents", List.of(
                            Map.of("parts", List.of(
                                    Map.of("text", prompt)
                            ))
                    ),
                    "generationConfig", Map.of(
                            "temperature", 0.0 // Forces absolute mathematical determinism
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
            // Logs the exact error securely without halting the JVM thread
            log.error("Neural Graph synthesis failed for telemetry pipeline.", e);
            return "### Pipeline Warning\nNeural graph synthesis failed. Raw telemetry fallback:\n\n" + rawLogs;
        }
    }
}