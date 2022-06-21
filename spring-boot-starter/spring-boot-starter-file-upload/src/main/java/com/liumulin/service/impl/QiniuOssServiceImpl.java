package service.impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.IOssService;

/**
 * 七牛云文件上传
 *
 * @author liuqiang
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "oss.uploadMethod", havingValue = "qiniu")
public class QiniuOssServiceImpl implements IOssService {

    @Override
    public String upload2PreviewUrl(MultipartFile multipartFile, String saveDirAndFileName) {
        return null;
    }

    @Override
    public boolean downloadFile(String source, String target) {
        return false;
    }
}
