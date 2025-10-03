package com.example.social_media_platform_for_influencers.DTO;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "First name is required")
    private String FirstName;
    @NotBlank(message = "Last name is required")
    private String LastName;
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must depass 8 characters")
    private String Password;
    @NotBlank(message = "confirmPassword is required")
    @Size(min = 8, message = "confirmPassword must depass 8 characters")
    private String confirmPassword;
    @Size(max = 250, message = "Bio cannot exceed 250 characters")
    private String Bio;
    @NotBlank(message = "Username is required")
    private String Username;
    @NotNull(message = "Role is required")
    private RoleType roleType;
    private String adress;
}
