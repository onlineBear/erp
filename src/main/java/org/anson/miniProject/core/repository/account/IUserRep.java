package org.anson.miniProject.core.repository.account;

import org.anson.miniProject.core.mapper.account.UserMapper;
import org.anson.miniProject.core.model.po.account.User;
import org.anson.miniProject.core.repository.IBaseRep;

public interface IUserRep extends IBaseRep<User, UserMapper> {
    String authentication(String userNo, String encryptedPsd);
}
