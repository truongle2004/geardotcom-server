package web_ecommerce.user_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.user_service.dtos.DistrictDto;
import web_ecommerce.user_service.entities.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    @Query(value = "select new web_ecommerce.user_service.dtos.DistrictDto " +
            "(d.id, d.code,d.name, d.codename, d.divisionType, d.shortCodename, d.provinceCode) " +
            "from District d " +
            "where d.provinceCode = :provinceCode ")
    Page<DistrictDto> getAll(Pageable pageable, String provinceCode);
}
