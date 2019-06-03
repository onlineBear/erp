package org.anson.miniProject.tool.util.common;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.anson.miniProject.constract.enums.ExceptionMsgEnum;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * @Description: 国际化资源获取
 * @Auther: lly
 * @Date: 2018-11-29 14:02
 */
public class I18nMessageUtil {
    private static final MessageSource messageSource = (MessageSource) SpringTool.getBean("messageSource");

    /**
     * 根据语言类型和资源的id读取资源
     *
     * @param langNO
     * @param langNO
     * @return
     */
    public static String getMessageSource(String langNO, String msgID) {
        MessageSource messageSource = (MessageSource) SpringTool.getBean("messageSource");
        String value = "";
        switch (langNO) {
            case "en":
                value = getMessageSource(Locale.US, msgID);
                break;
            case "zh-CN":
                value = getMessageSource(Locale.SIMPLIFIED_CHINESE, msgID);
                break;
            default:
                value = getMessageSource(msgID);
                break;
        }
        return value;
    }


    /**
     * 根据语言类型和资源名称读取资源
     *
     * @param lang
     * @param msgName
     * @return
     */
    public static String getMessageSource(Locale lang, String msgName) {
        MessageSource messageSource = (MessageSource) SpringTool.getBean("messageSource");
        return messageSource.getMessage(msgName, null, lang);
    }

    /**
     * 根据语言名称和资源名称和变量读取资源类型
     *
     * @param lang
     * @param msgName
     * @param orgMsg
     * @return
     */
    public static String getMessageSource(Locale lang, String msgName, String orgMsg) {
        MessageSource messageSource = (MessageSource) SpringTool.getBean("messageSource");
        String msg = messageSource.getMessage(msgName, null, lang);
        MessageFormat format = new MessageFormat(msg, Locale.CHINA);
        Object[] params = {orgMsg};
        String message = format.format(params);
        return message;
    }

    /**
     * 根据语言名称和资源名称和变量读取资源类型
     *
     * @param msgName
     * @return
     */
    public static String getMessageSource(String msgName) {
        Locale locale = LocaleContextHolder.getLocale();
        MessageSource messageSource = (MessageSource) SpringTool.getBean("messageSource");
        String msg = messageSource.getMessage(msgName, null, locale);
        return msg;
    }

    public static String getMsg(String langNo, ExceptionMsgEnum msgEnum, String... strings){
        return getMsg(langNo, msgEnum.getErrMsg(), strings);
    }

    private static String getMsg(String langNo, String code, String... strings){
        String msg = null;

        switch (langNo){
            case "zh-CN":
                msg = messageSource.getMessage(code, strings, Locale.SIMPLIFIED_CHINESE);
            case "en":
                msg = messageSource.getMessage(code, strings, Locale.US);
                break;
        }

        return msg;
    }
}
