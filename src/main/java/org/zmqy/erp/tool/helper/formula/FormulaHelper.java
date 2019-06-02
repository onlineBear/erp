package org.zmqy.erp.tool.helper.formula;

import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.MapUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 计算公式接口
 * @Auther: lly
 * @Date: 2018-12-24 11:34
 */
public class FormulaHelper {

    private static final String FORMULA = "formula";

    private static final String COLUMN_NO = "columnNo";

    /**
     * @param data        需要添加公式列的数据集
     * @param formulaList 公式列集合,map格式:(公式)formula:"(a+b)/3"   (公式列编码)columnNo: goodPrice
     * @return
     */
    public static List<Map> setListFormula(List<Map> data, List<Map<String, String>> formulaList) {
        if (data == null || data.size() <= 0) {
            throw new RuntimeException("参数为空");
        }
        if (formulaList == null || formulaList.size() <= 0) {
            throw new RuntimeException("参数为空");
        }

        for (Map dataMap : data) {
            for (Map formulaMap : formulaList) {
                String formula = (String) formulaMap.get(FORMULA);
                String[] columnNoArray = getFormulColumn((formula));
                for (int i = 0; i < columnNoArray.length; i++) {
                    String columnNo = columnNoArray[i];
                    if (!StringUtil.isEmpty(columnNo)) {
                        formula = formula.replaceAll(columnNo, dataMap.get(columnNo).toString());
                    }
                }

                Object value = computeFormula(formula);
                dataMap.put((String) formulaMap.get(COLUMN_NO), value);

            }
        }

        return data;
    }

    /**
     * @param data        需要添加公式列的数据集
     * @param formulaList 公式列集合,map格式:(公式)formula:"(a+b)/3"   (公式列编码)columnNo: goodPrice
     * @return
     */
    public static Map setListFormula(Map data, List<Map<String, String>> formulaList) {
        if (MapUtil.isEmpty(data)) {
            throw new RuntimeException("参数为空");
        }
        if (formulaList == null || formulaList.size() <= 0) {
            throw new RuntimeException("参数为空");
        }


        for (Map formulaMap : formulaList) {
            String formula = (String) formulaMap.get(FORMULA);
            String[] columnNoArray = getFormulColumn((formula));
            for (int i = 0; i < columnNoArray.length; i++) {
                String columnNo = columnNoArray[i];
                if (!StringUtil.isEmpty(columnNo)) {
                    formula = formula.replaceAll(columnNo, data.get(columnNo).toString());
                }
            }

            Object value = computeFormula(formula);
            data.put((String) formulaMap.get(COLUMN_NO), value);

        }


        return data;
    }


    /**
     * 将字符串公式中的列编码给提出来放到一个数组
     *
     * @param formula
     * @return
     */
    private static String[] getFormulColumn(String formula) {
        String string = formula.replaceAll("[^a-z^A-Z]", ",");
        return string.split(",");
    }

    /**
     * 计算一个字符串公式
     *
     * @param formula
     * @return
     */
    public static Object computeFormula(String formula) {
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        Object data = null;

        try {
            data = jse.eval(formula);
        } catch (ScriptException e) {
            data = null;
        }
        return data;

    }

}
