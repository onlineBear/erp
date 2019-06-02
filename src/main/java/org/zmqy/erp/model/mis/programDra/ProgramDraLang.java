package org.zmqy.erp.model.mis.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tProgramDraLang")
public class ProgramDraLang {
    private String id;

    private String langId;
    public static final String LANGID = "langId";

    private String programDraId;
    public static final String PROGRAMDRAID = "programDraId";

    private String programDraName;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}