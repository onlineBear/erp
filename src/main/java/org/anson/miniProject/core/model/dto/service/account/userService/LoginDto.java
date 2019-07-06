package org.anson.miniProject.core.model.dto.service.account.userService;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.core.model.bo.account.userBiz.LoginBo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class LoginDto {
    @NotBlank(message = "请输入用户编码")
    private String no;
    @NotBlank(message = "请输入密码")
    private String psd;

    private static final BeanCopier toLoginBoCopier = BeanCopier.create(LoginDto.class, LoginBo.class, false);

    public static LoginBo toLoginBo(LoginDto dto) throws InstantiationException, IllegalAccessException {
        LoginBo bo = BeanHelper.beanToBean(dto, LoginBo.class, toLoginBoCopier);
        bo.setEncryptedPsd(dto.getPsd());
        return bo;
    }

    public List<LoginBo> toLoginBo(List<LoginDto> dtoList) throws IllegalAccessException, InstantiationException {
        if(IterUtil.isEmpty(dtoList)){
            return null;
        }

        List<LoginBo> targetList = new ArrayList<>();

        for(LoginDto dto : dtoList){
            targetList.add(toLoginBo(dto));
        }

        return targetList;
    }
}
