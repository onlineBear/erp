package org.zmqy.erp.framework.JasperReports;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.util.ClassUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-20 10:45
 */
public class JasperUtil {

    public PrintPageConfig genPrintHtmlFile(String tmplFileUrl, String targetUrl,
                                            Map<String, String> params) throws Exception {
        Connection connection = null;
        InputStream inputFile = null;
        try {
            Properties prop = new Properties();
            //读取配置文件 数据库账密
            InputStream is = new FileInputStream(ClassUtils.getDefaultClassLoader().getResource("").getPath() + "tool/mybatis/db.properties");
            prop.load(is);
            String driver = prop.getProperty("db.driver");
            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            //注册jdbc驱动
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            //传入模板.jasper文件内容
            inputFile = new FileInputStream(tmplFileUrl);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputFile);
            //指定数据源，并传入对应报表的执行参数
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, (Map) params,
                    connection);

            //生成最终结果的HTML文件，注意此处引用自己重写的HtmlExporter
            OverrideHtmlExporter exporter = new OverrideHtmlExporter(DefaultJasperReportsContext.getInstance());
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(targetUrl));
            exporter.exportReport();
            return new PrintPageConfig().setWidth(jasperPrint.getPageWidth())
                    .setHeight(jasperPrint.getPageHeight());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
            if(inputFile != null){
                inputFile.close();
            }


        }

    }
}
