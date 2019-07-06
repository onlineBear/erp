package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.OperLogBo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface IOperLogDomain {
    String beginLogin(OperLogBo bo, String operUserId, Date operTime);
}
