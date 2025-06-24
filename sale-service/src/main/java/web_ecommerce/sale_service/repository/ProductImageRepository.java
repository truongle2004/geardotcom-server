package web_ecommerce.sale_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web_ecommerce.sale_service.dto.ProductImageDTO;
import web_ecommerce.sale_service.enitty.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query(value = "select new web_ecommerce.sale_service.dto.ProductImageDTO(pi.id, " +
            "concat(:file_upload_url,'/', pi.src) , pi.alt, pi.position) from ProductImage pi where pi.productId = :id")
    List<ProductImageDTO> getByProductId(@Param("id") String id, @Param("file_upload_url") String url);
}
