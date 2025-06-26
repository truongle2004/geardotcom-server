package web_ecommerce.sale_service.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.sale_service.dto.CategoryDTO;
import web_ecommerce.sale_service.dto.ProductDTO;
import web_ecommerce.sale_service.dto.VendorDTO;

import java.util.List;

public interface ProductService {

    Response<Page<ProductDTO>> getListProductByCategory(Pageable pageable, String category, String vendor);
    Response<ProductDTO> getById(String id);
    Response<List<CategoryDTO>> getAllProductCategory();
    Response<List<VendorDTO>> getAllVendor();
 }
