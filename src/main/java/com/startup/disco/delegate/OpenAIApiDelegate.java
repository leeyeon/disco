package com.startup.disco.delegate;

import com.startup.disco.delegate.command.CreateCompletionCommand;
import com.startup.disco.delegate.command.CreateGptRequest;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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

    @Value("${openai.admin.url}")
    private String opanAiAdminUrl;

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

    public ProductDTO createRecommend(PickDTO pickDTO) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Content-Type", "application/json");
            return execution.execute(request, body);
        });

        final URI uri = UriComponentsBuilder.fromUriString(opanAiAdminUrl)
                .build()
                .toUri();

        CreateGptRequest createGptRequest = CreateGptRequest.builder()
                .query("여성 캐쥬얼 니트 40000원 이하 추천해줘") // TODO pick에 맞게 쿼리 변경 필요
                .build();

        final HttpEntity<CreateGptRequest> request = new HttpEntity<>(createGptRequest); // api 요청 쿼리 생성 (중요)
        final ResponseEntity<ProductDTO> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                ProductDTO.class
        );

        return response.getBody();

    }
}
