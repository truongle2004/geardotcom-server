package web_ecommerce.sale_service.enitty;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import web_ecommerce.core.db.BaseEntityNonId;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "product_vendors")
public class ProductVendor extends BaseEntityNonId {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "handle")
    private String handle;

    @Column(name = "product_category_id")
    private String product_category_id;

    @Column(name = "description")
    private String description;
}
