package az.xazar.msbusinesstrip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessTripDto {
    private Long id;
    private Long userId;
    private String note;
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String startDate;
    private String endDate;
    @Enumerated(EnumType.STRING)
    private Result result;
    private boolean deleted;

    private Long fileId;
    private String fileName;

    @SuppressWarnings("java:S1948")
    private MultipartFile file;
}
