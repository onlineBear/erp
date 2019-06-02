package org.zmqy.erp.model.mis.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tNode")
public class Node {
    private String id;
    public static final String ID = "id";

    private String programDraId;
    public static final String PROGRAMDRAID = "programDraId";

    private String width;

    private String height;

    private String nodeIdentity;

    private String category;

    private String loc;

    private String link;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}