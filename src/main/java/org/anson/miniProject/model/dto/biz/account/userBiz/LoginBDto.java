package org.anson.miniProject.model.dto.biz.account.userBiz;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.model.bo.sys.log.BeginLoginDto;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class LoginBDto {
    private String no;
    private String encryptedPsd;
    private String loginTypeKey;
    private String ipv4;
    private Date operTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(LoginBDto.class, BeginLoginDto.class, false);

    public static BeginLoginDto toBeginLoginDto(LoginBDto dto) {
        if (dto == null) {
            return null;
        }

        BeginLoginDto target = new BeginLoginDto();

        bo2entityCopier.copy(dto, target, null);

        return target;
    }

    public List<BeginLoginDto> toBeginLoginDto(List<LoginBDto> dtoList) {
        if(IterUtil.isEmpty(dtoList)){
            return null;
        }

        List<BeginLoginDto> targetList = new ArrayList<>();

        for(LoginBDto dto : dtoList){
            targetList.add(toBeginLoginDto(dto));
        }

        return targetList;
    }
}
