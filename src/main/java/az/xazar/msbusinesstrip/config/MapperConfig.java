package az.xazar.msbusinesstrip.config;

import az.xazar.msbusinesstrip.mapper.BusinessTripMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public BusinessTripMapper btMapper() {
        return BusinessTripMapper.INSTANCE;
    }

}
