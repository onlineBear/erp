package org.zmqy.erp.tool.helper.pinyin;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.zmqy.erp.tool.util.common.StringUtil;

/**
 * 汉语拼音助手类
 */
@Slf4j
public class HanyuPinyinHelper {

    /**
     * 中文字符正则表达式
     */
    private static final String CHINESE_CHAR_REGEX = "[\u4e00-\u9fa5]+";
    /**
     * 将 字符串中的中文 转换成 汉语拼音, 非中文字符保持不变
     * @param str 串
     * @param caseType 是否大小写, null: 大写
     * @param toneType 是否带声调, 以及带声调的方式, null: 不带声调
     * @param vCharType 'ü' 的显示格式: u: / v / ü, null: 以 v 显示
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toFullHanyuPinyin(String str, HanyuPinyinCaseType caseType,
                                           HanyuPinyinToneType toneType, HanyuPinyinVCharType vCharType)
                                            throws BadHanyuPinyinOutputFormatCombination{
        if(StringUtil.isEmpty(str)){
            return "";
        }

        char[] cl_chars = str.trim().toCharArray();

        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

        // 拼音大小写
        if(caseType == null || caseType.equals(HanyuPinyinCaseType.UPPERCASE)){
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }else{
            defaultFormat.setCaseType(caseType);
        }

        // 是否带声调
        if(toneType == null || toneType.equals(HanyuPinyinToneType.WITHOUT_TONE)){
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }else {
            defaultFormat.setToneType(toneType);
        }

        // 'ü' 的显示格式: u: / v / ü
        if(vCharType == null || vCharType.equals(HanyuPinyinVCharType.WITH_V)){
            defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        }else {
            defaultFormat.setVCharType(vCharType);
        }

        StringBuffer hanyupinyinSb = new StringBuffer();

        for (int i=0; i<cl_chars.length; i++){
            if (String.valueOf(cl_chars[i]).matches(CHINESE_CHAR_REGEX)){   // 如果字符是中文,则将中文转为汉语拼音
                hanyupinyinSb.append(PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0]);
            } else {    // 如果字符不是中文,则不转换
                hanyupinyinSb.append(cl_chars[i]);
            }
        }

        return hanyupinyinSb.toString();
    }

    /**
     * 将 字符串中的中文 转换成 汉语拼音, 非中文字符保持不变
     * @param str 串
     * @param caseType 是否大小写, null: 大写
     * @param toneType 是否带声调, 以及带声调的方式, null: 不带声调
     * @param vCharType 'ü' 的显示格式: u: / v / ü, null: 以 v 显示
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toHanyuPinyinWithFirstLetter(String str, HanyuPinyinCaseType caseType,
                                                       HanyuPinyinToneType toneType, HanyuPinyinVCharType vCharType)
                                                        throws BadHanyuPinyinOutputFormatCombination{
        if(StringUtil.isEmpty(str)){
            return "";
        }

        char[] cl_chars = str.trim().toCharArray();

        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

        // 拼音大小写
        if(caseType == null || caseType.equals(HanyuPinyinCaseType.UPPERCASE)){
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }else{
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }

        // 是否带声调
        if(toneType == null || toneType.equals(HanyuPinyinToneType.WITHOUT_TONE)){
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }else {
            defaultFormat.setToneType(toneType);
        }

        // 'ü' 的显示格式: u: / v / ü
        if(vCharType == null || vCharType.equals(HanyuPinyinVCharType.WITH_V)){
            defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        }else {
            defaultFormat.setVCharType(vCharType);
        }

        StringBuffer hanyupinyinSb = new StringBuffer();

        for (int i = 0; i < cl_chars.length; i++) {
            if (String.valueOf(cl_chars[i]).matches(CHINESE_CHAR_REGEX)) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                hanyupinyinSb.append(PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0].substring(0, 1));
            } else {    // 如果字符不是中文,则不转换
                hanyupinyinSb.append(cl_chars[i]);
            }
        }

        return hanyupinyinSb.toString();
    }

    public static void main(String[] args) throws Exception{
        String chineseStr = "可口可乐1000ml - 供应商0-1";
        log.info(HanyuPinyinHelper.toFullHanyuPinyin(chineseStr, null, null, null));
        log.info(HanyuPinyinHelper.toHanyuPinyinWithFirstLetter(chineseStr, null, null, null));
    }
}
