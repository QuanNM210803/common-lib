package nmquan.commonlib.utils;

import nmquan.commonlib.constant.CommonConstants;
import nmquan.commonlib.exception.AppException;
import nmquan.commonlib.exception.CommonErrorCode;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExportUtils {

    public static ByteArrayOutputStream genXlsxFromMap(Map<String, Object> data, String pathName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathName)) {
            if (in == null) {
                throw new AppException(CommonErrorCode.FILE_TEMPLATE_NOT_FOUND);
            }
            Context context = PoiTransformer.createInitialContext();
            for (Map.Entry<String, Object> d : data.entrySet()) {
                if (d.getKey() != null && d.getValue() != null) {
                    context.putVar(d.getKey(), d.getValue());
                }
            }
            JxlsHelper.getInstance().processTemplate(in, out, context);
        } catch (Exception e) {
            throw new AppException(CommonErrorCode.EXPORT_FAIL);
        }
        return out;
    }

    public static ByteArrayOutputStream genXlsxFromList(List<?> dataList, String pathName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathName)) {
            if (in == null) {
                throw new AppException(CommonErrorCode.FILE_TEMPLATE_NOT_FOUND);
            }
            Context context = PoiTransformer.createInitialContext();
            context.putVar(CommonConstants.MAP_DATA_KEY, dataList);
            JxlsHelper.getInstance().processTemplate(in, out, context).setEvaluateFormulas(true);
        } catch (Exception e) {
            throw new AppException(CommonErrorCode.EXPORT_FAIL);
        }
        return out;
    }

    public static InputStreamResource toInputStreamResource(ByteArrayOutputStream outputStream) {
        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    public static String getXlsxFileName(String pathName) {
        String[] parts = pathName.split("/");
        String fileName = parts[parts.length - 1];
        if (fileName.contains(CommonConstants.EXTENSION_XLSX)) {
            return fileName;
        }
        return fileName + CommonConstants.EXTENSION_XLSX;
    }
}
