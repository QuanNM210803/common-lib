package nmquan.commonlib.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayInputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportResponse {
    private String filename;
    private InputStreamSource fileResource;
    private ByteArrayInputStream stream;
}
