package com.startup.disco.delegate;

import com.startup.disco.delegate.command.CreateCompletionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenAIApiDelegate {
    private final static String GPT_MODEL = "gpt-3.5-turbo";
    private final static String GPT_MESSAGE_ROLE = "user";
    private final static float GPT_TEMPERATURE = 0.7f;

    private final RestTemplate restTemplate;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    public GptResponse createCompletions(String message) {
        final CreateCompletionCommand createCompletionCommand = CreateCompletionCommand.builder()
                .model(GPT_MODEL)
                .messages(List.of(CreateCompletionCommand.Message.builder()
                        .role(GPT_MESSAGE_ROLE)
                        .content(message)
                        .build()))
                .temperature(GPT_TEMPERATURE)
                .build();

        final URI uri = UriComponentsBuilder.fromUriString(openAiApiUrl)
                .build()
                .toUri();

        final HttpEntity<CreateCompletionCommand> request = new HttpEntity<>(createCompletionCommand);
        final ResponseEntity<GptResponse> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                GptResponse.class
        );

        return response.getBody();
    }
}
