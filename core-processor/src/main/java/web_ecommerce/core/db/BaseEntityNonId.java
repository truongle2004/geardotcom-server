package web_ecommerce.core.db;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import web_ecommerce.core.validation.annotation.ColumnComment;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityNonId {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnComment("Ngày tạo")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @ColumnComment("Ngày cập nhật")
    private LocalDateTime updatedAt;
}
