package com.liumulin.pay.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Daniel Liu
 * @since 2022-06-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PayDTO{
    private Long id;

    private String serial;
}
