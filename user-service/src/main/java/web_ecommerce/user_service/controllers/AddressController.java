package web_ecommerce.user_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.user_service.dtos.DistrictDto;
import web_ecommerce.user_service.dtos.ProvinceDto;
import web_ecommerce.user_service.dtos.WardDto;
import web_ecommerce.user_service.services.AddressService;

@RestController
@RequiredArgsConstructor
public class AddressController extends BaseController {
    private static final String root = "/user";
    private final AddressService addressService;

    @GetMapping(value = V1 + root + "/districts/{provinceCode}")
    public Response<Page<DistrictDto>> getDistrict(Pageable pageable, @PathVariable String provinceCode) {
        return addressService.getAllDistrict(pageable, provinceCode);
    }

    @GetMapping(value = V1 + root + "/provinces")
    public Response<Page<ProvinceDto>> getProvince(Pageable pageable) {
        return addressService.getAllProvince(pageable);
    }

    @GetMapping(value = V1 + root + "/wards/{districtCode}")
    public Response<Page<WardDto>> getWard(Pageable pageable, @PathVariable String districtCode) {
        return addressService.getAllWard(pageable, districtCode);
    }
}
