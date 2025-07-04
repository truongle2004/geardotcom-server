package web_ecommerce.user_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.user_service.dtos.ProvinceDto;
import web_ecommerce.user_service.entities.Province;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    @Query(value = "select new web_ecommerce.user_service.dtos.ProvinceDto(p.id, p.code, p.name, p.codename, p.divisionType, p.shortCodename) from Province p ")
    Page<ProvinceDto> getAll(Pageable pageable);
}
