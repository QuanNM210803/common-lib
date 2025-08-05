package nmquan.commonlib.dto;

import java.time.Instant;

public interface BaseRepositoryDto {
    Long getId();
    Boolean getIsActive();
    Boolean getIsDeleted();
    String getCreatedBy();
    String getUpdatedBy();
    Instant getCreatedAt();
    Instant getUpdatedAt();
}
