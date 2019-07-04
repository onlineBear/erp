package org.anson.miniProject.domain.sys;

import org.anson.miniProject.model.bo.sys.OperLogBo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface IOperLogDomain {
    String beginLogin(OperLogBo bo, String operUserId, Date operTime);
}
