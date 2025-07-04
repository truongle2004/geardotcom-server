package web_ecommerce.sale_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.sale_service.dto.AddToWishlistDto;
import web_ecommerce.sale_service.dto.CreateWishlistDto;
import web_ecommerce.sale_service.dto.WishlistDto;
import web_ecommerce.sale_service.dto.WishlistItemDto;
import web_ecommerce.sale_service.enitty.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    Wishlist createWishlist(CreateWishlistDto createWishlistDto);

    Optional<WishlistDto> getWishlistById(String id);

    List<WishlistDto> getWishlistsByUserId(String userId);

    Optional<WishlistDto> getDefaultWishlistByUserId(String userId);

    Page<WishlistItemDto> getWishlistItems(String wishlistId, Pageable pageable);

    WishlistDto updateWishlist(String id, CreateWishlistDto updateWishlistDto);

    void deleteWishlist(String id);

    Response<?> addProductToWishlist(AddToWishlistDto addToWishlistDto, String userId);

    void removeProductFromWishlist(String wishlistId, String productId);

    void clearWishlist(String wishlistId);

    Boolean isProductInWishlist(String wishlistId, String productId);

    void setDefaultWishlist(String wishlistId, String userId);
}
