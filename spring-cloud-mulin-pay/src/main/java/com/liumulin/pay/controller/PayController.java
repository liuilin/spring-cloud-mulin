//package com.liumulin.pay.controller;
//
//
//import com.liumulin.pay.convert.PayConvert;
//import com.liumulin.pay.model.dto.PayDTO;
//import com.liumulin.pay.model.vo.PayVO;
//import com.liumulin.pay.service.IPayService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 支付
// *
// * @author liuqiang
// * @since 2022-06-14
// */
//@RestController
//@RequestMapping("/pay")
//public class PayController {
//
//    @Autowired
//    private IPayService payService;
//
//    @PostMapping("/saveOrUpdate")
//    public CommonResult saveOrUpdate(@Validated @RequestBody PayDTO dto) {
//        return CommonResult.success(payService.saveOrUpdate(PayConvert.INSTANCE.payDTOtoPayDO(dto)));
//    }
//
//    @DeleteMapping("/{id}")
//    public CommonResult delete(@PathVariable("id") String id) {
//        return CommonResult.success(payService.removeById(id));
//    }
//
//    @GetMapping("/{id}")
//    public CommonResult<PayVO> getById(@PathVariable("id") String id) {
//        return CommonResult.success(PayConvert.INSTANCE.payDOToPayVO(payService.getById(id)));
//    }
//
//}