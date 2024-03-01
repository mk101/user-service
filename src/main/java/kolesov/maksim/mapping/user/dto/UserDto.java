package kolesov.maksim.mapping.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;

    private Boolean active;

    private AvatarDto avatar;
    private List<UserRoleDto> roles;

}
