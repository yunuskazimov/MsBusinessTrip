package az.xazar.msbusinesstrip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessTripGetDto {
    private Long id;
    private Long userId;
    private String userName;
    private String note;
    private String startDate;
    private String endDate;
    @Enumerated(EnumType.STRING)
    private Result result;
    private boolean deleted;
}
