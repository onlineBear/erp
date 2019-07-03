package org.anson.miniProject.domain.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.model.bo.sys.DictTypeBo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Date;

@Validated
public interface IDictTypeDomain {
    @Validated(DictTypeBo.add.class)
    String add(@Valid DictTypeBo bo, String operUserId, Date operTime);
    @Validated(DictTypeBo.mdf.class)
    void mdf(@Valid DictTypeBo bo, String operUserId, Date operTime);
    void del(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException;
}
