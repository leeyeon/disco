package com.startup.disco.delegate;

import com.startup.disco.delegate.command.CreateCompletionCommand;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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

    /**
     * gpt 상품 추천 검색 결과
     * @param pickDTO
     * @return
     */
    public List<ProductDTO> createRecommend(PickDTO pickDTO) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Content-Type", "application/json");
            return execution.execute(request, body);
        });

        final URI uri = UriComponentsBuilder.fromUriString(opanAiAdminUrl)
                .build()
                .toUri();

        final HttpEntity<PickDTO> request = new HttpEntity<>(pickDTO); // api 요청 쿼리 생성 (중요)
        final ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ProductDTO>>(){}
        );

        return response.getBody();

    }
}
