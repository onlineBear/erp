package org.anson.miniProject.model.bo.sys.log;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BeginLoginDto {
    private String loginTypeKey;
    private Date operTime;
    private String loginUserNo;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(BeginLoginDto.class, LoginLog.class, false);

    public static LoginLog toEntity(BeginLoginDto bo) {
        if (bo == null) {
            return null;
        }

        LoginLog entity = new LoginLog();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public List<LoginLog> ToEntity(List<BeginLoginDto> dtoList) {
        if(IterUtil.isEmpty(dtoList)){
            return null;
        }

        List<LoginLog> entityList = new ArrayList<>();

        for(BeginLoginDto dto : dtoList){
            entityList.add(toEntity(dto));
        }

        return entityList;
    }
}
