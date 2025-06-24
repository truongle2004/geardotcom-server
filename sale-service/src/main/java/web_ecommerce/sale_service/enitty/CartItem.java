package web_ecommerce.sale_service.enitty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import web_ecommerce.core.db.BaseEntityNonId;
import web_ecommerce.core.validation.annotation.ColumnComment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntityNonId {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ColumnComment("Id sản phẩm")
    @Column(name = "product_id", nullable = false)
    private String productId;

    @ColumnComment("Id giỏ hàng")
    @Column(name = "cart_id", nullable = false)
    private String cartId;

    @ColumnComment("Số lượng")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    public CartItem(String productId, String cartId, int quantity) {
        this.productId = productId;
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
