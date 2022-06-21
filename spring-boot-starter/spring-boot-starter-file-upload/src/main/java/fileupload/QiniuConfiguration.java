package fileupload;

import com.liumulin.common.exception.BusinessException;
import com.liumulin.common.model.response.CommonResultCode;
import com.liumulin.common.util.object.ObjectUtils;
import config.QiniuConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * 七牛配置
 *
 * @author happy
 */
@Configuration
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Import(QiniuConfig.class)
public class QiniuConfiguration {
    @Resource
    private QiniuConfig qiniuConfig;
    @Value("${spring.application.name}")
    private String springApplicationName;

    @Bean
    public QiniuFileApi qiniuFileApi() {
        String accessKey = qiniuConfig.getAccessKey();
        String secretKey = qiniuConfig.getSecretKey();
        String bucket = qiniuConfig.getBucket();
        String pathPrefix = qiniuConfig.getPathPrefix();
        String domain = qiniuConfig.getDomain();

        if (!ObjectUtils.allNotNull(accessKey, secretKey, bucket)) {
            throw new BusinessException(CommonResultCode.CONFIG_NOT_CORRECT);
        }
        return QiniuFileApi.getInstance(accessKey, secretKey, bucket, pathPrefix, springApplicationName, domain);
    }
}
