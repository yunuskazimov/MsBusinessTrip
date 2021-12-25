package az.xazar.msbusinesstrip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileNameDto {
    private Long id;
    private String scannedFile;
}
