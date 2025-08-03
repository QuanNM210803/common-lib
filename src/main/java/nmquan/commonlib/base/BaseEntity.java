package nmquan.commonlib.base;

import jakarta.persistence.*;
import lombok.Data;
import nmquan.commonlib.model.UserCustom;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(columnDefinition = "Boolean default true")
    private Boolean isActive = true;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isDeleted = false;


    @Column(updatable = false, name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(updatable = false, name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.updatedAt = null;
        this.updatedBy = null;
    }
}
