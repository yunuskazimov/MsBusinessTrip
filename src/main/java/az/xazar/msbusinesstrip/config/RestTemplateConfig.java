package az.xazar.msbusinesstrip.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class RestTemplateConfig {

    @Bean
    public static RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

