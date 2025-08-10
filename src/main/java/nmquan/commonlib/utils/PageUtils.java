package nmquan.commonlib.utils;

import nmquan.commonlib.dto.PageInfo;
import nmquan.commonlib.dto.response.FilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PageUtils {

    public static <T> FilterResponse<T> manualPagination(List<T> data, Pageable pageable, Class<T> clazz) {
        if(data == null) {
            data = List.of();
        }
        if (pageable.getSort().isSorted()) {
            Comparator<T> comparator = pageable.getSort().stream()
                    .map(order -> {
                        Comparator<T> fieldComparator = Comparator.comparing(r -> {
                            try {
                                Field field = clazz.getDeclaredField(order.getProperty());
                                field.setAccessible(true);
                                return (Comparable) field.get(r);
                            } catch (Exception e) {
                                return null;
                            }
                        }, Comparator.nullsLast(Comparator.naturalOrder()));
                        return order.isDescending() ? fieldComparator.reversed() : fieldComparator;
                    })
                    .reduce(Comparator::thenComparing)
                    .orElse(null);

            if (comparator != null) {
                data = data.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
            }
        }
        List<T> pageData = data.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        Page<T> pageInfo = new PageImpl<>(pageData, pageable, data.size());

        return FilterResponse.<T>builder()
                .data(pageData)
                .pageInfo(PageInfo.builder()
                        .pageIndex(pageInfo.getNumber())
                        .pageSize(pageInfo.getSize())
                        .totalElements(pageInfo.getTotalElements())
                        .totalPages(pageInfo.getTotalPages())
                        .hasNextPage(pageInfo.hasNext())
                        .build())
                .build();
    }
}
