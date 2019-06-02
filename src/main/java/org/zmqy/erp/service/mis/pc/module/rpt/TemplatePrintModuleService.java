package org.zmqy.erp.service.mis.pc.module.rpt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zmqy.erp.constract.mis.constract.GeneralParam;
import org.zmqy.erp.domain.mis.rpt.ITemplatePrintDomain;
import org.zmqy.erp.framework.JasperReports.JasperUtil;
import org.zmqy.erp.framework.JasperReports.PrintPageConfig;
import org.zmqy.erp.mapper.mis.pc.module.rpt.TemplatePrintModuleMapper;
import org.zmqy.erp.model.mis.entity.rpt.TemplatePrint;
import org.zmqy.erp.tool.helper.Ip.IpHelper;
import org.zmqy.erp.tool.helper.zmqy.param.BizParamHelper;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.file.FileUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-19 12:17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TemplatePrintModuleService {
    @Autowired
    private TemplatePrintModuleMapper mapper;
    @Autowired
    private ITemplatePrintDomain domain;
    @Autowired
    private HttpServletRequest request;


    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Map> getReportTemplate(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        String reportId = (String) paramsMap.get("reportId");
        return mapper.getReportTemplate(reportId);
    }

    public void mdfTemplateConfig(Map<String, Object> paramsMap) throws Exception {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        String[] strKey = {"id", "scaling", "codePrintModel", "codeModel"};
        String[] intKey = {"codeNumber"};

        TemplatePrint templatePrint = BizParamHelper.map2Bean(paramsMap, strKey, null, null, intKey, null, TemplatePrint.class);

        Date date = new Date();
        domain.updateById(templatePrint, date);
    }


    public void uploadTemplate(MultipartFile file, String reportId, String loginUserId) throws Exception {
        if (file == null) {
            log.error("不为空");
            throw new RuntimeException("文件为空!");
        }
        if (StringUtil.isEmpty(reportId)) {
            throw new RuntimeException("报表id为空");
        }
        String fileName = file.getOriginalFilename();
        if (StringUtil.isEmpty(fileName)) {
            throw new RuntimeException("文件为空!");
        }
        if (!fileName.endsWith(".zip")) {
            throw new RuntimeException("不支持的文件类型:" + fileName);
        }
        //  /D:/java/apache-tomcat-8.5.32/webapps/erp-1.0/WEB-INF/classes/
        String filePath = this.getClass().getResource("/").getPath();
        if (filePath != null) {
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1, filePath.length() - 1);
            }
            if (filePath.indexOf("webapps") != -1) {
                filePath = filePath.substring(0, filePath.indexOf("webapps"));
            }
        }

        File dest = new File(filePath + "/webapps/reportTemplate/" + reportId);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        log.error("开始上传");
        File dest1 = new File(filePath + "/webapps/reportTemplate/" + reportId + "/" + fileName);
        file.transferTo(dest1);
        log.error("上传路径为:"+filePath + "/webapps/reportTemplate/" + reportId + "/" + fileName);
        //上传完文件之后保存
        Date date = new Date();
        TemplatePrint templatePrint = new TemplatePrint();
        templatePrint.setReportId(reportId);
        templatePrint.setTemplateFileName(fileName);
        templatePrint.setTemplateName(fileName.substring(0, fileName.lastIndexOf(".")));
        templatePrint.setScaling("整宽不变形");
        templatePrint.setCodePrintModel("批量打印");
        templatePrint.setCodeNumber(1);
        templatePrint.setCodeModel("客户端构造");
        templatePrint.setFilePath(filePath + "/webapps/reportTemplate/" + reportId + "/" + fileName);

        domain.add(templatePrint, loginUserId, date);


    }


    public PrintPageConfig TemplatePrint(Map<String, Object> paramsMap) throws Exception {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        Date date = new Date();

        //报表id
        String reportId = (String) paramsMap.get("reportId");
        //模板id
        String templateId = (String) paramsMap.get("templateId");
        //模板所需参数
        Map<String, String> map = (Map<String, String>) paramsMap.get("param");
        map.put("param", "1073471954843422721");//测试
        TemplatePrint templatePrint = domain.selectById(templateId);
        if (templatePrint == null) {
            throw new RuntimeException("不存在该模板");
        }

        //获取模板文件路径
        String filePath = templatePrint.getFilePath();
        //解压目录
        String directory = filePath.substring(0, filePath.lastIndexOf(".zip"));
        File upTemplate = new File(directory);
        //判断zip文件有没有解压,没有就解压
        if (!upTemplate.exists()) {
            String outPath = filePath.substring(0, filePath.lastIndexOf(templatePrint.getTemplateFileName()));
            FileUtil.unZipFiles(filePath, outPath, false);
        }

        //获取生成的模板html
        String htmlFilePath = directory + "/" + templatePrint.getTemplateName() + ".html";
        File htmlFile = new File(htmlFilePath);
        PrintPageConfig printPageConfig = new PrintPageConfig();
        //假如模板文件没有生成,则生成
        if (!htmlFile.exists()) {
            JasperUtil jasperUtil = new JasperUtil();
            //获取模板文件jasper
            String jasperPath = directory + "/" + templatePrint.getTemplateName() + ".jasper";
            //生成html文件并返回对象
            printPageConfig = jasperUtil.genPrintHtmlFile(jasperPath, directory + "/" + templatePrint.getTemplateName() + ".html", map);
            //将宽高更新到数据库中
            TemplatePrint print = new TemplatePrint();
            print.setId(templatePrint.getId());
            print.setHeight(printPageConfig.getHeight());
            print.setWidth(printPageConfig.getWidth());
            domain.updateById(print, date);
        }
        //将生成的html的url返回给前端打印
        printPageConfig.setUrl(IpHelper.getIpAndPort(request) + "/reportTemplate/" + reportId + "/" + templatePrint.getTemplateName() + "/" + templatePrint.getTemplateName() + ".html");
        if (printPageConfig.getHeight() == 0) {
            printPageConfig.setHeight(templatePrint.getHeight());
        }
        if (printPageConfig.getWidth() == 0) {
            printPageConfig.setWidth(templatePrint.getWidth());
        }

        return printPageConfig;
    }

    public void delTemplate(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        String id = (String) paramsMap.get("id");
        TemplatePrint templatePrint = domain.selectById(id);
        if (templatePrint == null) {
            return;
        }

        try {
            String zipPath = templatePrint.getFilePath();
            FileUtil.deleteFile(zipPath);
            String directory = zipPath.substring(0, zipPath.lastIndexOf(".zip")) + "/";
            FileUtil.delDirectory(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }

        domain.del(id);
    }
}
