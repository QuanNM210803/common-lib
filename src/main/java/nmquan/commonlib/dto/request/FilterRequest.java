package nmquan.commonlib.dto.request;

import lombok.Data;
import nmquan.commonlib.constant.CommonConstants;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class FilterRequest {
    private Integer pageIndex = CommonConstants.DEFAULT_PAGE_INDEX;
    private Integer pageSize = CommonConstants.DEFAULT_PAGE_SIZE;
    private String sortBy = CommonConstants.DEFAULT_SORT_BY;
    private String sortDirection = "DESC";

    public Pageable getPageable() {
        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.DESC);
        return org.springframework.data.domain.PageRequest.of(pageIndex, pageSize, Sort.by(direction, sortBy));
    }
}
