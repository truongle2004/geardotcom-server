package web_ecommerce.user_service.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.user_service.dtos.UserAddressResponseDto;
import web_ecommerce.user_service.entities.UserAddress;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
    @Query(value = "select new web_ecommerce.user_service.dtos.UserAddressResponseDto " +
            "(u.id, u.userId, p.name, d.name, w.name, u.fullAddress, u.addressType, u.phoneNumber, u.receiverName)" +
            "from UserAddress u " +
            "join Province p on u.provinceCode = p.code " +
            "join District d on u.districtCode = d.code " +
            "join Ward w on u.wardCode = w.code " +
            "where u.userId = :userId")
    Page<UserAddressResponseDto> getByUserId(String userId, Pageable pageable);

    UserAddress getUserAddressById(Long userId);
}
