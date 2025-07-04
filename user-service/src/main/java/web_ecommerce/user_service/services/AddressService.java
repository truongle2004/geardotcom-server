package web_ecommerce.user_service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.user_service.dtos.DistrictDto;
import web_ecommerce.user_service.dtos.ProvinceDto;
import web_ecommerce.user_service.dtos.WardDto;

import java.util.List;

public interface AddressService {
    Response<Page<DistrictDto>> getAllDistrict(Pageable pageable, String provinceCode);
    Response<Page<ProvinceDto>> getAllProvince(Pageable pageable);
    Response<Page<WardDto>> getAllWard(Pageable pageable, String districtCode);

}
