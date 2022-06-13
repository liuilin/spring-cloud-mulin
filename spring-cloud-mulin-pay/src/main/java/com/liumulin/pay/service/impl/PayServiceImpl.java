package com.liumulin.pay.service.impl;

import com.liumulin.pay.model.entity.PayDO;
import com.liumulin.pay.mapper.PayMapper;
import com.liumulin.pay.service.IPayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Daniel Liu
 * @since 2022-06-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl extends ServiceImpl<PayMapper, PayDO> implements IPayService {

}
