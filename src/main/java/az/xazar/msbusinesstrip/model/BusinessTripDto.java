package az.xazar.msbusinesstrip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessTripDto {
    private Long id;
    private Long userId;
    private String note;
    private LocalDate startDate;
    private LocalDate endDate;
    private String result;
    private boolean isDeleted;

    private String scannedFile;
}
