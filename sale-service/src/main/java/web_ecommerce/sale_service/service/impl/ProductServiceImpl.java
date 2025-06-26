package web_ecommerce.sale_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.ImageEnum;
import web_ecommerce.sale_service.dto.CategoryDTO;
import web_ecommerce.sale_service.dto.ProductDTO;
import web_ecommerce.sale_service.dto.ProductImageDTO;
import web_ecommerce.sale_service.dto.VendorDTO;
import web_ecommerce.sale_service.enitty.Product;
import web_ecommerce.sale_service.repository.ProductImageRepository;
import web_ecommerce.sale_service.repository.ProductRepository;
import web_ecommerce.sale_service.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Value("${file_upload-url}")
    private String FILE_UPLOAD_URL;

    public ProductServiceImpl(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Response<Page<ProductDTO>> getListProductByCategory(Pageable pageable, String category, String vendor) {
        Page<ProductDTO> productDTOS = productRepository.getListProduct(pageable, ImageEnum.MAIN_IMAGE.getValue(), category, vendor);
        productDTOS.forEach(productDTO -> {
            productDTO.setImages(productImageRepository.getByProductId(productDTO.getId(), FILE_UPLOAD_URL));
        });
        return new Response<Page<ProductDTO>>().withDataAndStatus(productDTOS, HttpStatus.OK);
    }

    @Override
    public Response<ProductDTO> getById(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            return new Response<ProductDTO>().withDataAndStatus(null, HttpStatus.NOT_FOUND);
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.get().getId());
        productDTO.setHandle(product.get().getHandle());
        productDTO.setDescription(product.get().getDescription());
        productDTO.setTitle(product.get().getTitle());
        productDTO.setPrice(product.get().getPrice());
        productDTO.setPublishedScope(product.get().getPublishedScope());
        productDTO.setPurchaseCount(product.get().getPurchaseCount());
        productDTO.setAverageRating(product.get().getAverageRating());
        productDTO.setReviewCount(product.get().getReviewCount());
        productDTO.setTags(product.get().getTags());
        productDTO.setSoleQuantity(product.get().getSoleQuantity());
        productDTO.setNotAllowPromotion(product.get().getNotAllowPromotion());
        productDTO.setAvailable(product.get().getAvailable());
        productDTO.setPublishedScope(product.get().getPublishedScope());

        List<ProductImageDTO> productImageDTO = productImageRepository.getByProductId(productId, FILE_UPLOAD_URL);
        if (!productImageDTO.isEmpty()) {
            productDTO.setImages(productImageDTO);
        }

        return new Response<ProductDTO>().withDataAndStatus(productDTO, HttpStatus.OK);
    }

    @Override
    public Response<List<CategoryDTO>> getAllProductCategory() {
        return new Response<List<CategoryDTO>>().withDataAndStatus(productRepository.getAllProductCategory(), HttpStatus.OK);
    }

    @Override
    public Response<List<VendorDTO>> getAllVendor() {
        return new Response<List<VendorDTO>>().withDataAndStatus(productRepository.getAll(), HttpStatus.OK);
    }
}
