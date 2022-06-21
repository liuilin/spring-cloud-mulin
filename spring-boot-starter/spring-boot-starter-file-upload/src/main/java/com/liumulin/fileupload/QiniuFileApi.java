package fileupload;

import com.gmrfid.starter.webflux.entity.ImageInfo;
import com.gmrfid.starter.webflux.exception.ErrorMessageException;
import com.gmrfid.starter.webflux.info.QhashInfo;
import com.gmrfid.utils.JsonUtils;
import com.liumulin.common.exception.BusinessException;
import com.liumulin.common.model.response.CommonResultCode;
import com.liumulin.common.util.UuidUtils;
import com.liumulin.common.util.string.StringUtils;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import com.qiniu.util.StringMap;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * 七牛云Api
 */
public class QiniuFileApi {
    /**
     * 过期时间
     */
    public static final int EXPIRES = 3600 * 24 * 30 * 12 * 10;
    private final String accessKey;
    private final String secretKey;
    private final String bucket;
    private final String token;
    private final String pathPrefix;
    private final String springApplicationName;
    private final String domain;
    private final UploadManager uploadManager;

    private QiniuFileApi() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    private QiniuFileApi(String accessKey,
                         String secretKey,
                         String bucket,
                         String pathPrefix,
                         String springApplicationName,
                         String domain) {
        if (StringUtils.isAnyEmpty(accessKey, secretKey, bucket)) {
            throw new BusinessException(CommonResultCode.STRING_NOT_EMPTY);
        }
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
        this.pathPrefix = pathPrefix;
        this.springApplicationName = springApplicationName;
        this.domain = domain;

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        this.uploadManager = new UploadManager(cfg);
        //获取凭证
        Auth auth = Auth.create(accessKey, secretKey);
        this.token = auth.uploadToken(bucket, null, EXPIRES, null, true);
    }

    public static QiniuFileApi getInstance(String accessKey,
                                           String secretKey,
                                           String bucket,
                                           String pathPrefix,
                                           String springApplicationName,
                                           String domain) {
        return LazyHolder.lazy(accessKey, secretKey, bucket, pathPrefix, springApplicationName, domain);
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy(accessKey, secretKey, bucket, pathPrefix, springApplicationName, domain);
    }

    public DefaultPutRet uploadImage(FilePart filePart) {
        return uploadFile(filePart, null);
    }

    /**
     * 七牛云上传图片文件
     */
    public DefaultPutRet uploadFile(FilePart filePart, String userId) {
        String id = UuidUtils.randomUuidToBase64();
        String key;
        if (userId != null) {
            key = UriComponentsBuilder.newInstance().pathSegment(pathPrefix, springApplicationName, "user", userId, id).build().toString().substring(1);
        } else {
            key = UriComponentsBuilder.newInstance().pathSegment(pathPrefix, springApplicationName, "service", id).build().toString().substring(1);
        }


        return filePart
                .flatMap(filePart -> Mono
                        .just(filePart)
                        .map(f -> {
                            Path temp;
                            try {
                                temp = Files.createTempFile(id, "temp");
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new BusinessException("临时文件创建失败");
                            }
                            return temp;
                        })
                        .flatMap(tempFile -> {
                                    String mime = Optional.ofNullable(filePart.headers().getContentType())
                                            .map(MimeType::toString)
                                            .orElse(null);

                                    String name = filePart.filename();
                                    return filePart
                                            .transferTo(tempFile)
                                            .thenReturn(tempFile)
                                            .flatMap(v -> Mono
                                                    .create(monoSink -> {
                                                        try {
                                                            System.out.println("token=:" + this.token);
                                                            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(tempFile.toFile()));
                                                            uploadManager.asyncPut(
                                                                    IOUtils.toByteArray(bufferedInputStream),
                                                                    key,
                                                                    token,
                                                                    new StringMap().put("name", name),
                                                                    mime,
                                                                    true,
                                                                    ((key1, response) -> {
                                                                        try {
                                                                            //解析上传成功的结果
                                                                            DefaultPutRet defaultPutRet = JsonUtils.deserialize(response.bodyString(), DefaultPutRet.class);
                                                                            monoSink.success(defaultPutRet);
                                                                        } catch (IOException e) {
                                                                            monoSink.error(ErrorMessageException.of(e.getMessage()));
                                                                        }
                                                                    }));
                                                        } catch (IOException e) {
                                                            monoSink.error(ErrorMessageException.of(e.getMessage()));
                                                        }
                                                    })
                                            )
                                            .cast(DefaultPutRet.class);
                                }
                        )
                );
    }



//    public Mono<ImageInfo> getImageInfo(String key) {
//        String url = UriComponentsBuilder.fromUriString(domain).path(key).query("imageInfo").build().toUriString();
//        return WebClient.builder().build().get()
//                .uri(url)
//                .retrieve()
//                .onStatus(HttpStatus::isError, r -> r.bodyToMono(QiniuError.class)
//                        .map(e -> ErrorMessageException.of(e.getError()))
//                )
//                .bodyToMono(ImageInfo.class);
//    }
//
//    public Mono<QhashInfo> getQhash(String key) {
//        String url = UriComponentsBuilder.fromUriString(domain).path(key).query("qhash/sha1").build().toUriString();
//        return WebClient.builder().build().get()
//                .uri(url)
//                .retrieve()
//                .onStatus(HttpStatus::isError, r -> r.bodyToMono(QiniuError.class)
//                        .map(e -> ErrorMessageException.of(e.getError()))
//                )
//                .bodyToMono(QhashInfo.class);
//    }

    private static class LazyHolder {
        private static QiniuFileApi lazy(String accessKey,
                                         String secretKey,
                                         String bucket,
                                         String pathPrefix,
                                         String springApplicationName,
                                         String domain) {
            return new QiniuFileApi(accessKey, secretKey, bucket, pathPrefix, springApplicationName, domain);
        }
    }

    @Data
    private static class QiniuError {
        private int code;
        private String error;
    }
}
