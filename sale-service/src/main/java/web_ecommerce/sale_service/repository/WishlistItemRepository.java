package web_ecommerce.sale_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.dto.WishlistItemDto;
import web_ecommerce.sale_service.enitty.WishlistItem;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, String> {

    @Query("SELECT new web_ecommerce.sale_service.dto.WishlistItemDto(" +
            "wi.id, wi.wishlistId, wi.productId, " +
            "p.title, p.handle, " +
            "p.price, p.available) " +
            "FROM WishlistItem wi " +
            "JOIN Product p on wi.productId = p.id " +
            "WHERE wi.wishlistId = :wishlistId ")
    Page<WishlistItemDto> findByWishlistIdAsDto(@Param("wishlistId") String wishlistId, Pageable pageable);

    Optional<WishlistItem> findByWishlistIdAndProductId(String wishlistId, String productId);

    boolean existsByWishlistIdAndProductId(String wishlistId, String productId);

    void deleteByWishlistId(String wishlistId);

    void deleteByWishlistIdAndProductId(String wishlistId, String productId);
}
