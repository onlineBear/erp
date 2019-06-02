package org.zmqy.erp.model.mis.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tLink")
public class Link {
    private String id;
    public static final String ID = "id";

    private String programDraId;
    public static final String PROGRAMDRAID = "programDraId";
    private String departure;

    private String direction;

    private String fromPort;

    private String toPort;

    private String points;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}