package com.liumulin.pay.controller;


import com.liumulin.common.model.response.CommonResult;
import com.liumulin.pay.convert.PayConvert;
import com.liumulin.pay.model.dto.PayDTO;
import com.liumulin.pay.model.vo.PayVO;
import com.liumulin.pay.service.IPayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 支付
 *
 * @author Daniel Liu
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private IPayService payService;

    @PostMapping("/saveOrUpdate")
    public CommonResult<Boolean> saveOrUpdate(@Validated @RequestBody PayDTO dto) {
        return CommonResult.success(payService.saveOrUpdate(PayConvert.INSTANCE.payDTOtoPayDO(dto)));
    }

    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@PathVariable("id") String id) {
        return CommonResult.success(payService.removeById(id));
    }

    @GetMapping("/{id}")
    public CommonResult<PayVO> getById(@PathVariable("id") String id) {
        return CommonResult.success(PayConvert.INSTANCE.payDOtoPayVO(payService.getById(id)));
    }

    @GetMapping("/list")
    public CommonResult<List<PayVO>> list() {
        return CommonResult.success(PayConvert.INSTANCE.payDOListToPayVOList(payService.list(null)));
    }

}