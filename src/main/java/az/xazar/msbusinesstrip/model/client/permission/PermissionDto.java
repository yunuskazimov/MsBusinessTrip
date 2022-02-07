package az.xazar.msbusinesstrip.model.client.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private Long id;
    private Long userId;
    private Role role;
}
