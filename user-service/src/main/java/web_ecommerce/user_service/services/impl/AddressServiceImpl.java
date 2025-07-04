package web_ecommerce.user_service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.user_service.dtos.DistrictDto;
import web_ecommerce.user_service.dtos.ProvinceDto;
import web_ecommerce.user_service.dtos.WardDto;
import web_ecommerce.user_service.repositories.DistrictRepository;
import web_ecommerce.user_service.repositories.ProvinceRepository;
import web_ecommerce.user_service.repositories.WardRepository;
import web_ecommerce.user_service.services.AddressService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final WardRepository wardRepository;


    @Override
    public Response<Page<DistrictDto>> getAllDistrict(Pageable pageable, String provinceCode) {
        return new Response<Page<DistrictDto>>().withDataAndStatus(districtRepository.getAll(pageable, provinceCode), HttpStatus.OK);
    }

    @Override
    public Response<Page<ProvinceDto>> getAllProvince(Pageable pageable) {
        return new Response<Page<ProvinceDto>>().withDataAndStatus(provinceRepository.getAll(pageable), HttpStatus.OK);
    }

    @Override
    public Response<Page<WardDto>> getAllWard(Pageable pageable, String districtCode) {
        return new Response<Page<WardDto>>().withDataAndStatus(wardRepository.getAll(pageable, districtCode), HttpStatus.OK);
    }
}
