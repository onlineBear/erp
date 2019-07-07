package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.OperLogDmo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface IOperLogDomain {
    String beginLogin(OperLogDmo bo, String operUserId, Date operTime);
}
