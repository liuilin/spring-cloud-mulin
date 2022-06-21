package service.impl;

import config.OssConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.IOssService;

import javax.annotation.Resource;
import java.io.File;

@Service
@Slf4j
@ConditionalOnProperty(name = "oss.uploadMethod", havingValue = "local")
public class LocalFileService implements IOssService {

    //    @Autowired
//    private ISysConfigService sysConfigService;
    @Resource
    private OssConfig ossConfig;

    @Override
    public String upload2PreviewUrl(MultipartFile multipartFile, String saveDirAndFileName) {
        try {
//            String savePath = ossSavePlaceEnum ==
//                    OssSavePlaceEnum.PUBLIC ? ossYmlConfig.getOss().getFilePublicPath() : ossYmlConfig.getOss().getFilePrivatePath();
//            String pathname = "C:\\" + savePath + File.separator + saveDirAndFileName;
//            pathname = pathname.replaceAll("/", "\\\\");

            File saveFile = new File("C:\\" + File.separator + saveDirAndFileName);

            //如果文件夹不存在则创建文件夹
            File dir = saveFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            multipartFile.transferTo(saveFile);
        } catch (Exception e) {
            log.error("", e);
        }

//        // 私有文件 不返回预览文件地址
//        if (ossSavePlaceEnum == OssSavePlaceEnum.PRIVATE) {
//            return saveDirAndFileName;
//        }

        return sysConfigService.getDBApplicationConfig().getOssPublicSiteUrl() + "/" + saveDirAndFileName;
    }

    @Override
    public boolean downloadFile(String source, String target) {
        return false;
    }
}