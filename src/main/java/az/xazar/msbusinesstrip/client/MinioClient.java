package az.xazar.msbusinesstrip.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Component
public class MinioClient {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public MinioClient(RestTemplate restTemplate
            , @Value("${client.minio.int.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    // @SneakyThrows
    public ResponseEntity<String> postToMinio(@PathVariable Long id,
                                              @Valid @RequestParam MultipartFile file,
                                              @RequestParam String type) {
        String url = String.format("%s/%d", apiUrl, id);
        return restTemplate.postForEntity(url, file, String.class);
    }

}

