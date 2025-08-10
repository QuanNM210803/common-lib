package nmquan.commonlib.utils;

import nmquan.commonlib.constant.CommonConstants;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    /**
     * Chuyển từ Instant về String theo format truyền vào (ví dụ: "yyyy-MM-dd HH:mm:ss")
     * @param instant Instant cần format
     * @param pattern Chuỗi định dạng (ví dụ "dd-MM-yyyy HH:mm:ss")
     * @return Chuỗi ngày giờ theo định dạng và múi giờ HCM
     */
    public static String instantToString_HCM(Instant instant, String pattern) {
        if (instant == null || pattern == null || pattern.isEmpty()) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
                .withZone(ZoneId.of(CommonConstants.DATE_TIME.TIME_ZONE_HCM));

        return formatter.format(instant);
    }

    /**
     * Chuyển từ Instant về String theo định dạng và múi giờ UTC (múi giờ 0)
     * @param instant Instant cần format
     * @param pattern Chuỗi định dạng (ví dụ: "yyyy-MM-dd HH:mm:ss")
     * @return Chuỗi định dạng theo UTC
     */
    public static String instantToString_UTC(Instant instant, String pattern) {
        if (instant == null || pattern == null || pattern.isEmpty()) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
                .withZone(ZoneOffset.UTC);

        return formatter.format(instant);
    }

    /**
     * Chuyển LocalDate (chỉ ngày) về Instant tại thời điểm bắt đầu ngày (00:00) theo timezone HCM
     */
    public static Instant localDateToInstant_StartOfDay(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
    }

    /**
     * Chuyển LocalDate (chỉ ngày) về Instant tại thời điểm kết thúc ngày (23:59:59.999...) theo timezone HCM
     */
    public static Instant localDateToInstant_EndOfDay(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusSeconds(1).toInstant();
    }

    /**
     * Chuyển LocalDateTime (ngày + giờ) về Instant theo timezone UTC
     */
    public static Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.atZone(ZoneOffset.UTC).toInstant();
    }

}
