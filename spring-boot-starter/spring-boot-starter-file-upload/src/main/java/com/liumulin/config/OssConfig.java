package com.liumulin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OSS 公共配置
 *
 * @author Daniel Liu
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {

//    /**
//     * 指定该属性为嵌套值, 否则默认为简单值导致对象为空（外部类不存在该问题， 内部static需明确指定）
//     */
//    @NestedConfigurationProperty
//    private Oss oss;
//
//    /**
//     * 系统oss配置信息
//     **/
//    @Data
//    public static class Oss {
    /**
     * 存储根路径
     **/
    private String fileRootPath;
    /**
     * 公共读取块
     **/
    private String filePublicPath;
    /**
     * 私有读取块
     **/
    private String filePrivatePath;
    /**
     * OSS 类型
     **/
    private String serviceType;
//    }
}