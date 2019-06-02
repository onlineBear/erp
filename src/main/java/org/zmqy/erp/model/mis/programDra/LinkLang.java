package org.zmqy.erp.model.mis.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tLinkLang")
public class LinkLang {
    private String id;

    private String linkId;
    public static final String LINKID = "linkId";

    private String content;

    private String langId;
    public static final String LANGID = "langId";

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}