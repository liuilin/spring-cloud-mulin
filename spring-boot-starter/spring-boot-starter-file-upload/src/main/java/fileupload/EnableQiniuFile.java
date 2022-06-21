package fileupload;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启文件
 *
 * @author wugang
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({QiniuConfiguration.class})
public @interface EnableQiniuFile {
}