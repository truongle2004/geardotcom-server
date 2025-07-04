package web_ecommerce.sale_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.dto.WishlistDto;
import web_ecommerce.sale_service.enitty.Wishlist;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String> {

    @Query("SELECT new web_ecommerce.sale_service.dto.WishlistDto(" +
            "w.id, w.userId, null) " +
            "FROM Wishlist w WHERE w.id = :id")
    Optional<WishlistDto> findByIdAsDto(@Param("id") String id);

    @Query("SELECT new web_ecommerce.sale_service.dto.WishlistDto(" +
            "w.id, w.userId, null) " +
            "FROM Wishlist w WHERE w.userId = :userId ORDER BY w.createdAt ASC")
    List<WishlistDto> findByUserIdAsDto(@Param("userId") String userId);

    Optional<Wishlist> findByUserId(String userId);
}
