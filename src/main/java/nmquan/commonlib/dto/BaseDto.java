package nmquan.commonlib.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDto<T> {
    public Long id;
    public Boolean isActive;
    public Boolean isDeleted;
    public String createdBy;
    public String updatedBy;
    public T createdAt;
    public T updatedAt;
}
