package org.zmqy.erp.service.mis.pc.component.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zmqy.erp.mapper.mis.pc.component.sys.base.UserCpmMapper;

import java.util.List;

@Service
public class UserCpmService {

    @Autowired
    private UserCpmMapper mapper;

    public List<String> getUserResourceUrlList(String userId){
        return this.mapper.getUserResourceUrlList(userId);
    }
}
