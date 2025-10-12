package nmquan.commonlib.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nmquan.commonlib.constant.MessageConstants;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeStatusActiveRequest {
    @NotNull(message = MessageConstants.IDS_REQUIRE)
    private List<Long> ids;
    @NotNull(message = MessageConstants.STATUS_ACTIVE_REQUIRE)
    private Boolean active;
}