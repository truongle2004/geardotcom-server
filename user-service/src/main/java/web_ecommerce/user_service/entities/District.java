package web_ecommerce.user_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web_ecommerce.core.db.BaseEntityNonId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District extends BaseEntityNonId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer code;

    private String name;
    private String codename;
    private String divisionType;
    private String shortCodename;
    private Integer provinceCode;
}
