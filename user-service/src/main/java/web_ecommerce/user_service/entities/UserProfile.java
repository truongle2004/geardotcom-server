package web_ecommerce.user_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web_ecommerce.core.db.BaseEntityNonId;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile extends BaseEntityNonId {
    @Id
    private Integer id;
    private String phone;
    private String userId;
    private String gender;
    private LocalDate birthday;
}
