package web_ecommerce.user_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.user_service.dtos.WardDto;
import web_ecommerce.user_service.entities.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    @Query(value = "select new web_ecommerce.user_service.dtos.WardDto(w.id, w.code, w.name, w.codename, w.divisionType, w.shortCodename, w.districtCode) from Ward w " +
            "where w.districtCode = :districtCode " +
            "order by w.code")
    Page<WardDto> getAll(Pageable pageable, String districtCode);
}
