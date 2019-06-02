package org.zmqy.erp.model.mis.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tProgramDra")
public class ProgramDra {
    private String id;
    public static final String ID = "id";

    private String classType;

    private Integer seq;

    private String linkFromPortIdProperty;

    private String linkToPortIdProperty;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}