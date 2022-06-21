package service;

import org.springframework.web.multipart.MultipartFile;

public interface IOssService {

    /**
     * 上传文件 & 生成下载/预览URL
     **/
    String upload2PreviewUrl(MultipartFile multipartFile, String saveDirAndFileName);

    /**
     * 将文件下载到本地
     * 返回是否 写入成功
     * false: 写入失败， 或者文件不存在
     **/
    boolean downloadFile(String source, String target);

}