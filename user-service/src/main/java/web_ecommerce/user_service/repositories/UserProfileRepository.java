package web_ecommerce.user_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.user_service.dtos.UserProfileDto;
import web_ecommerce.user_service.entities.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    @Query(value = "select new web_ecommerce.user_service.dtos.UserProfileDto(u.id, u.userId, u.gender, u.phone, u.birthday) from UserProfile u where u.userId = :userId")
    UserProfileDto getByUserId(String userId);
}
