package nmquan.commonlib.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
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
    private Instant createdAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.updatedAt = null;
        this.updatedBy = null;
    }
}
