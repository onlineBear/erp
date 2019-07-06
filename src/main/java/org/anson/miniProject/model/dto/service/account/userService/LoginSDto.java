package org.anson.miniProject.model.dto.service.account.userService;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.model.dto.biz.account.userBiz.LoginBDto;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class LoginSDto {
    @NotBlank(message = "请输入用户编码")
    private String no;
    @NotBlank(message = "请输入密码")
    private String psd;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(LoginSDto.class, LoginBDto.class, false);

    public static LoginBDto toLoginBDto(LoginSDto dto) {
        if (dto == null) {
            return null;
        }

        LoginBDto target = new LoginBDto();

        bo2entityCopier.copy(dto, target, null);

        target.setEncryptedPsd(dto.getPsd());

        return target;
    }

    public List<LoginBDto> toLoginBDto(List<LoginSDto> dtoList) {
        if(IterUtil.isEmpty(dtoList)){
            return null;
        }

        List<LoginBDto> targetList = new ArrayList<>();

        for(LoginSDto dto : dtoList){
            targetList.add(toLoginBDto(dto));
        }

        return targetList;
    }
}
