package org.zmqy.erp.model.mis.entity.rpt;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tTemplatePrint")
public class TemplatePrint {
    private String id;

    public static final String REPORTID = "reportId";
    private String reportId;

    public static final String TEMPLATEFILENAME = "templateFileName";
    private String templateFileName;

    private String templateName;

    private String scaling;

    private String codePrintModel;

    private Integer codeNumber;

    private String codeModel;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private String filePath;

    private Integer width;

    private Integer height;
}