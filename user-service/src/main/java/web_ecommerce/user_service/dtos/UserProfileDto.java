package web_ecommerce.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private Integer id;
    private String phone;
    private String userId;
    private String gender;
    private LocalDate birthday;
}
