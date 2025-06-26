package web_ecommerce.sale_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.dto.CategoryDTO;
import web_ecommerce.sale_service.dto.ProductDTO;
import web_ecommerce.sale_service.dto.VendorDTO;
import web_ecommerce.sale_service.enitty.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("select new web_ecommerce.sale_service.dto.ProductDTO(p.id, p.handle," +
            "p.title, p.warehouseId, p.productVendorId," +
            "p.productCategoryId, p.publishedScope, p.purchaseCount," +
            "p.averageRating, p.reviewCount, p.tags, p.soleQuantity," +
            "p.notAllowPromotion, p.available, concat(:file_upload_url,'/', pi.src), pi.alt, p.price) from Product p " +
            "join ProductImage pi on p.id = pi.productId " +
            "join ProductCategory pc on p.productCategoryId = pc.id " +
            "where pc.handle = :category and pi.position = :position ")
    Page<ProductDTO> getListProductByCategory(Pageable pageable,
                                              @Param("category") String category,
                                              @Param("position") int position,
                                              @Param("file_upload_url") String url);

    @Query("select new web_ecommerce.sale_service.dto.ProductDTO(p.id, p.handle," +
            "p.title, p.warehouseId, p.productVendorId," +
            "p.productCategoryId, p.publishedScope, p.purchaseCount," +
            "p.averageRating, p.reviewCount, p.tags, p.soleQuantity," +
            "p.notAllowPromotion, p.available, pi.src, pi.alt, p.price) from Product p " +
            "join ProductImage pi on p.id = pi.productId " +
            "join ProductCategory pc on p.productCategoryId = pc.id " + "join ProductVendor pv on p.productVendorId = pv.id " +
            "where pi.position = :position " + "AND (:category = 'all' OR :category = '' OR pc.handle = :category) " + "AND (:vendor = '' or pv.handle = :vendor) ")
    Page<ProductDTO> getListProduct(Pageable pageable, int position, String category, String vendor);

    @Query("select new web_ecommerce.sale_service.dto.CategoryDTO(pc.id, pc.name, pc.handle, pc.description) from ProductCategory pc")
    List<CategoryDTO> getAllProductCategory();

    @Query("select new web_ecommerce.sale_service.dto.ProductDTO(p.id, p.handle," +
            "p.title, p.warehouseId, p.productVendorId," +
            "p.productCategoryId, p.publishedScope, p.purchaseCount," +
            "p.averageRating, p.reviewCount, p.tags, p.soleQuantity," +
            "p.notAllowPromotion, p.available, concat(:file_upload_url,'/', pi.src), pi.alt, p.price) " +
            "from Product p " +
            "join ProductImage pi on p.id = pi.productId " +
            "where p.id in :ids and pi.position = :position")
    List<ProductDTO> getProductsByIds(@Param("ids") List<String> ids,
                                      @Param("position") int position,
                                      @Param("file_upload_url") String url);


    @Query("select new web_ecommerce.sale_service.dto.VendorDTO(pv.id, pv.name, pv.handle, pv.description) " +
            "from ProductVendor pv")
    List<VendorDTO> getAll();

}
