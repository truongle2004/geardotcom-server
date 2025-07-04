package web_ecommerce.user_service.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.user_service.dtos.UserAddressDto;
import web_ecommerce.user_service.entities.UserAddress;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
    @Query(value = "select new web_ecommerce.user_service.dtos.UserAddressDto " +
            "(u.id, u.userId, u.provinceCode, u.districtCode, u.wardCode, u.fullAddress, u.addressType, u.phoneNumber, u.receiverName)" +
            " from UserAddress u where u.userId = :userId")
    UserAddressDto getByUserId(String userId);

    UserAddress getUserAddressByUserId(String userId);
}
