package org.zmqy.erp.model.mis.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tNodeLang")
public class NodeLang {
    private String id;

    private String nodeId;
    public static final String NODEID = "nodeId";

    private String content;

    private String langId;
    public static final String LANGID = "langId";

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}