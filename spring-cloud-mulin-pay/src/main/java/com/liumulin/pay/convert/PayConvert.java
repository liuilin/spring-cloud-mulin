package com.liumulin.pay.convert;

import com.liumulin.pay.model.dto.PayDTO;
import com.liumulin.pay.model.entity.PayDO;
import com.liumulin.pay.model.vo.PayVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author liuqiang
 * @since 2022-06-14
 */
@Mapper
public interface PayConvert {
    PayConvert INSTANCE = Mappers.getMapper(PayConvert.class);

    PayDO payDTOtoPayDO(PayDTO payDTO);

    PayVO payDOToPayVO(PayDO payDO);
}