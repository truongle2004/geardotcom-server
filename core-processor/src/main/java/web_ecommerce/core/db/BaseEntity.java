package web_ecommerce.core.db;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import web_ecommerce.core.validation.annotation.ColumnComment;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ColumnComment("ID bảng")
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnComment("Ngày tạo")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @ColumnComment("Ngày cập nhật")
    private LocalDateTime updatedAt;
//
//    @CreatedBy
//    @Column(name = "created_by", updatable = false)
//    @ColumnComment("Người tạo")
//    private String createdBy;
//
//    @LastModifiedBy
//    @Column(name = "updated_by")
//    @ColumnComment("Người cập nhật")
//    private String updatedBy;
}
