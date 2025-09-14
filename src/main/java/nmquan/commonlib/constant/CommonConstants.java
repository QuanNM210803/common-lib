package nmquan.commonlib.constant;

public class CommonConstants {

    public static final String FILE_XLSX = ".xlsx";
    public static final String FILE_ZIP = ".zip";
    public static final Integer DEFAULT_PAGE_INDEX = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_BY = "updatedAt";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";
    public static final String FALLBACK_SORT_BY = "createdAt";
    public static final String ROLE_INTERNAL = "INTERNAL";
    public static final String ROLE_ANONYMOUS = "ANONYMOUS";
    public static final String ROLE_SYSTEM = "SYSTEM";
    public static final String INTERNAL = "internal";
    public static final String EXTERNAL = "external";

    public static class ActionLog {
        private ActionLog() {
        }

        public static final String ADD = "Add";
        public static final String UPDATE = "Update";
        public static final String DELETE = "Delete";
        public static final String EXPORT = "Export";
    }

    public static class DATE_TIME {
        private DATE_TIME() {
        }
        public static final String DD_MM_YYYY = "dd/MM/yyyy";
        public static final String DD_MM_YYYY_HYPHEN = "dd-MM-yyyy";
        public static final String YYYY_MM_DD = "yyyy/MM/dd";
        public static final String YYYY_MM_DD_HYPHEN = "yyyy-MM-dd";
        public static final String HH_MM_SS = "HH:mm:ss";
        public static final String HH_MM = "HH:mm";

        public static final String DD_MM_YYYY_HH_MM_SS = DD_MM_YYYY + " " + HH_MM_SS;
        public static final String DD_MM_YYYY_HH_MM = DD_MM_YYYY + " " + HH_MM;
        public static final String DD_MM_YYYY_HH_MM_SS_HYPHEN = DD_MM_YYYY_HYPHEN + " " + HH_MM_SS;
        public static final String DD_MM_YYYY_HH_MM_HYPHEN = DD_MM_YYYY_HYPHEN + " " + HH_MM;

        public static final String YYYY_MM_DD_HH_MM_SS = YYYY_MM_DD + " " + HH_MM_SS;
        public static final String YYYY_MM_DD_HH_MM = YYYY_MM_DD + " " + HH_MM;
        public static final String YYYY_MM_DD_HH_MM_SS_HYPHEN = YYYY_MM_DD_HYPHEN + " " + HH_MM_SS;
        public static final String YYYY_MM_DD_HH_MM_HYPHEN = YYYY_MM_DD_HYPHEN + " " + HH_MM;

        public static final String TIME_ZONE_HCM = "Asia/Ho_Chi_Minh";
        public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    }

    public static class REGEX {
        private REGEX() {
        }
        public static final String TEXT = "^[a-zA-Z0-9]*";
        public static final String NUMBER = "^[0-9]*";
        public static final String TEXT_NUMBER = "^[a-zA-Z0-9_]*";
        public static final String EMAIL = "^(?=.{3,64}@)[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)*@[^-][A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    }
}
