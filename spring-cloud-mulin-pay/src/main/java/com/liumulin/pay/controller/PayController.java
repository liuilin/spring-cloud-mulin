package com.liumulin.pay.controller;

import com.liumulin.pay.mapper.PayMapper;
import com.liumulin.pay.model.entity.PayDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  控制层
 * </p>
 *
 * @author Daniel Liu
 * @since 2022-06-14
 */
@RestController
public class PayController {
    @Resource
    private PayMapper payMapper;

    @GetMapping("all")
    public List<PayDO> test(){
        return payMapper.selectList(null);
    }

}

