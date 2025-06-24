package web_ecommerce.sale_service.enitty;

import web_ecommerce.core.db.BaseEntityNonId;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web_ecommerce.core.validation.annotation.ColumnComment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage extends BaseEntityNonId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ColumnComment("ID bảng")
    private Long id;

    @Column(name = "product_id", nullable = false)
    @ColumnComment("ID bảng")
    private String productId;

    @Column(name = "src")
    @ColumnComment("URL hình")
    private String src;

    @Column(name = "alt")
    @ColumnComment("alt hình")
    private String alt;

    @Column(name = "position")
    @ColumnComment("URL hình")
    private int position;
}
