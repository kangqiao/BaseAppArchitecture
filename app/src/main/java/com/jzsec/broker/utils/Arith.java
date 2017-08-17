package com.jzsec.broker.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by zhaopan on 15/8/12 16:45
 * e-mail: kangqiao610@gmail.com
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
 * 确的浮点数运算，包括加减乘除和四舍五入。
 */
public class Arith {
    @Deprecated
    public static final String CURRENCY_SYMBOL = "￥";
    public static final String UNIT_MONEY_CH = "￥";
    public static final String UNIT_MONEY_ZH = "元";
    public static final String UNIT_SCORE_ZH = "分";
    public static final String UNIT_RATE_SYMBOL = "%";
    public static final String UNIT_PERCENTAGE = "%";

    @Deprecated
    public static final String RATE_SYMBOL = "%";
    public static final int MONEY_DECIMAL_RETAIN = 2;
    public static final int RATE_DECIMAL_RETAIN = 2;
    public static final float A_HUNDRED_MILLION = 100000000f;

    private static DecimalFormat doubleFormat = new DecimalFormat("0.00");

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    //这个类不能实例化
    private Arith() {
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static long roundReturnLong(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * 提供精确的类型转换(Float)
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static float convertsToFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * 提供精确的类型转换(Int)不进行四舍五入
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static int convertsToInt(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * 提供精确的类型转换(Long)
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static long convertsToLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * 返回两个数中大的一个值
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public static double returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    /**
     * 返回两个数中小的一个值
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public static double returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    /**
     * 精确对比两个数字
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    /**
     * 佣金处理 => 万分之几, 保留一位小数.
     * @param d
     * @return
     */
    public static String valueOfCommissionZH(Object d) {
        return "万分之" + valueOfCommission(d);
    }

    /**
     * 对佣金的处理, 默认万分之几, 保留小数点后一位.
     * @param obj
     * @return
     */
    public static String valueOfCommission(Object obj) {
        return String.valueOf(round(toDouble(obj) * 10000, 1));
    }

    /**
     * 佣金处理 => 千分之几, 保留两位小数.
     * @param d
     * @return
     */
    public static String valueOfCommission2(double d) {
        return String.valueOf(round(d * 1000, 2));
    }

    /**
     * 对金额的处理, 默认保留小数点后两位.
     * @param str
     * @return
     */
    public static String valueOfMoney(Object str) {
        return keep2Decimal(str);
    }

    public static String valueOfPercentage(Object numDouble){
        return keep2Decimal(numDouble) + UNIT_PERCENTAGE;
    }

    public static String valueOf100Percentage(Object num){
        return valueOf100(num) + UNIT_PERCENTAGE;
    }

    public static String valueOf100(Object num){
        return keep2Decimal(toDouble(num) * 100);
    }

    public static double toDouble(Object value){
        return toDouble(value, Double.NaN);
    }

    public static double toDouble(Object value, double def){
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.valueOf((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return def;
    }

    public static boolean toBoolean(Object value, boolean def) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            String stringValue = (String) value;
            if ("true".equalsIgnoreCase(stringValue)) {
                return true;
            } else if ("false".equalsIgnoreCase(stringValue)) {
                return false;
            }
        }
        return def;
    }

    public static int toInteger(Object value, int def) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return (int) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return def;
    }

    public static long toLong(Object value, long def) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return (long) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return def;
    }

    public static String toString(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return String.valueOf(value);
        }
        return null;
    }


    /**
     * 保留小数点后两位.
     * @param numobj
     * @return
     */
    public static String keep2Decimal(Object numobj){
        double num = toDouble(numobj);
        if (isInvalidDouble(num)) {
            num = 0.00;
        }
        BigDecimal bigDecimal = new BigDecimal(num);
        return doubleFormat.format(bigDecimal);
    }

    /**
     * 格式化钱的输出.
     * @param moneyObj
     * @param scale 小数点位数
     * @return
     */
    public static String valueOfMoney(Object moneyObj, int scale) {
        double money = toDouble(moneyObj);
        if (isInvalidDouble(money)) {
            money = 0.00;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0.");
        for(int i=0;i<scale;i++){
            stringBuilder.append("0");
        }
        BigDecimal bigDecimal = new BigDecimal(money);
        DecimalFormat df = new DecimalFormat(stringBuilder.toString());
        return df.format(bigDecimal);
    }

    /**
     * 超过10000返回xx.xx万,否则返回原值
     *
     * @param num
     * @return
     */
    public static String valueOfNum(String num) {
        if (TextUtils.isEmpty(num)) {
            return "";
        }
        int length = num.length();
        if (length > 4) {

            String intValue = num.substring(0, length - 4);
            int start = length - 4;
            String floatValue = num.substring(start, start + 2);
            return intValue + "." + floatValue + "万";
        }
        return num + "";
    }

    public static boolean isInvalidDouble(double d) {
        return Double.isNaN(d) || Double.isInfinite(d);
    }

    public static String valueOfMoneyZH(double money) {
        return valueOfMoney(money) + UNIT_MONEY_ZH;
    }

    public static String valueOfMoneyZH(String str) {
        return valueOfMoney(str) + UNIT_MONEY_ZH;
    }

    public static String valueOfScoreZH(String score) {
        return score + UNIT_SCORE_ZH;
    }

    public static String valueOfMoneyRate(double d) {
        return round(d, 2) + RATE_SYMBOL;
    }

    public static boolean isFormatDouble(String commission) {
        try {
            Double.valueOf(commission);
            return true;
        } catch (NumberFormatException nfe) {
            Zlog.et("isFormatDouble", commission + " had not been format double type.");
        }
        return false;
    }

    /**
     * 判断length是否在[begin, end] 之间.
     *
     * @param length
     * @param begin
     * @param end
     * @return
     */
    public static boolean isInBound(int length, int begin, int end) {
        if (begin > end && (length == begin || length == end)) return true;
        return begin <= length && length <= end;
    }

    public static String formatToSepara(float data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }

    /**
     * 将字符串从右至左每三位加一逗号
     *
     * @param str 需要加逗号的字符串
     * @return 以从右至左每隔3位加一逗号显示
     */
    public static String displayWithComma(String str) {
        str = new StringBuffer(str).reverse().toString(); // 先将字符串颠倒顺序
        String str2 = "";
        int size = (str.length() % 3 == 0) ? (str.length() / 3) : (str.length() / 3 + 1); // 每三位取一长度
        /**
         * 比如把一段字符串分成n段,第n段可能不是三个数,有可能是一个或者两个,
         * 现将字符串分成两部分.一部分为前n-1段,第二部分为第n段.前n-1段，每一段加一",".而第n段直接取出即可
         */
        for (int i = 0; i < size - 1; i++) { // 前n-1段
            str2 += str.substring(i * 3, i * 3 + 3) + ",";
        }
        for (int i = size - 1; i < size; i++) { // 第n段
            str2 += str.substring(i * 3, str.length());
        }
        str2 = new StringBuffer(str2).reverse().toString();
        return str2;
    }



    public static String formatNumber(Object numobj, int x) {
        return formatNumber(numobj, x, null);
    }

    public static String formatNumber(Object numobj, int x, RoundingMode mode) {
        String formatStr = "";
        x = Math.max(0, Math.min(x, 10));
        if (x > 0) formatStr += ".";
        while (x-- > 0) {
            formatStr += "0";
        }
        double num = toDouble(numobj);
        if (isInvalidDouble(num) || num == 0) {
            return "0"+formatStr;
        }
        formatStr = ",##0"+formatStr;

        BigDecimal bigDecimal = new BigDecimal(num);
        if(null != mode){
            bigDecimal.setScale(x, mode);
        }
        return new DecimalFormat(formatStr).format(bigDecimal);
    }

    public static String keepXDecimal(Object numobj, int x) {
        return keepXDecimal(numobj, x, null);
    }

    public static String keepXDecimal(Object numobj, int x, RoundingMode mode) {
        String formatStr = "";
        x = Math.max(0, Math.min(x, 10));
        if (x > 0) formatStr += ".";
        while (x-- > 0) {
            formatStr += "0";
        }
        double num = toDouble(numobj);
        if (isInvalidDouble(num) || num == 0) {
            return "0"+formatStr;
        }
        formatStr = "0"+formatStr;

        BigDecimal bigDecimal = new BigDecimal(num);
        if(null != mode){
            bigDecimal.setScale(x, mode);
        }
        return new DecimalFormat(formatStr).format(bigDecimal);
    }

    public static String formatNumZH(Object in, int x) {
        return formatNumZH(in, x, null);
    }

    public static String formatNumZH(Object in, int x, RoundingMode mode) {
        final long wanyi = 1000000000000l;          //万亿
        final long yi = 100000000l;                 //亿
        final long oneHundredThousand = 100000l;    //十万

        double num = toDouble(in);
        if (isInvalidDouble(num) || num == 0) {
            return keepXDecimal(num, x);
        }
        String result = "";
        double number = 0;
        if (Math.abs((number = num / wanyi)) >= 1) {//单位:万亿
            result = keepXDecimal(number, x, mode) + "万亿";
        } else if (Math.abs((number = num / yi)) >= 1) {//单位:亿
            result = keepXDecimal(number, x, mode) + "亿";
        } else if (Math.abs((number = num / oneHundredThousand)) >= 1) {//单位:十万
            result = keepXDecimal(number*10, x, mode) + "万";
        } else {//单位:十万以下
            result = keepXDecimal(num, x, mode);
        }
        return result;
    }

    public static String formatNumZH2(Object in, int x) {
        return formatNumZH2(in, x, null);
    }

    public static String formatNumZH2(Object in, int x, RoundingMode mode) {
        int scale = x == 0? 2: x;
        return formatNumZH2(in, x, scale, mode);
    }

    public static String formatNumZH2(Object in, int x, int scale, RoundingMode mode) {
        final long wanyi = 1000000000000l;          //万亿
        final long yi = 100000000l;                 //亿
        final long oneHundredThousand = 100000l;    //十万

        double num = toDouble(in);
        if (isInvalidDouble(num) || num == 0) {
            return keepXDecimal(num, x);
        }
        String result = "";
        double number = 0;
        if (Math.abs((number = num / wanyi)) >= 1) {//单位:万亿
            result = keepXDecimal(number, scale, mode) + "万亿";
        } else if (Math.abs((number = num / yi)) >= 1) {//单位:亿
            result = keepXDecimal(number, scale, mode) + "亿";
        } else if (Math.abs((number = num / oneHundredThousand)) >= 1) {//单位:十万
            result = keepXDecimal(number*10, scale, mode) + "万";
        } else {//单位:十万以下
            result = keepXDecimal(num, x, mode);
        }
        return result;
    }
}