package config;


import com.liumulin.common.exception.BusinessException;
import com.liumulin.common.model.response.CommonResultCode;
import com.liumulin.common.util.object.ObjectUtils;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import fileupload.QiniuFileApi;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author Daniel Liu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss.qiniu")
public class QiniuConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String pathPrefix;
    private String domain;

    /**
     * 华东机房
     */
    @Resource
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Region.region2());
    }

    /**
     * 构建一个七牛云上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    /**
     * 认证信息实例
     */
    @Resource
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }

    /**
     * 构建七牛云空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }

    @Value("${spring.application.name}")
    private String springApplicationName;

    @Bean
    public QiniuFileApi qiniuFileApi() {
//        String accessKey = qiniuConfig.getAccessKey();
//        String secretKey = qiniuConfig.getSecretKey();
//        String bucket = qiniuConfig.getBucket();
//        String pathPrefix = qiniuConfig.getPathPrefix();
//        String domain = qiniuConfig.getDomain();

        if (!ObjectUtils.allNotNull(accessKey, secretKey, bucket)) {
            throw new BusinessException(CommonResultCode.CONFIG_NOT_CORRECT);
        }
        return QiniuFileApi.getInstance(accessKey, secretKey, bucket, pathPrefix, springApplicationName, domain);
    }
}
