package com.wuzeyong.batch.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DataConvertUtil
{

    public DataConvertUtil()
    {
    }

    /**
     * 全角转半角
     * @param input
     * @return
     */
	public static String ToDBC(String input) {
	    char[] c = input.toCharArray();
	    for (int i = 0; i< c.length; i++) {
	    if (c[i] == 12288) {
	    c[i] = (char) 32;
	    continue;
	    }
	    if (c[i]> 65280&& c[i]< 65375)
	    c[i] = (char) (c[i] - 65248);
	    }
	    String sReturn = new String(c);
	    if(sReturn!=null)sReturn=sReturn.trim();
	    if(sReturn!=null)sReturn = sReturn.toUpperCase();
	    return sReturn;
	}
    
    /**
     * 若字符串s为空则转换成空字符串，否则返回本身<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String s=new String();<br>
     * s=DataConvert.toString(s);<br>
     * s+="dfgdg";<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“dfgdg”<br>
     *
     * @param s  原字符串
     * @return   若字符串s为空则转换成空字符串，否则返回本身
     */
    public static String toString(String s)
    {
        if(s == null)
            return "";
        else
            return s;
    }
    /**
     * 若 s 为空则转换成默认字符串 sDefault<p>
     * @author CJ(jbye)
     * 示例：<p>
     * ------------------------------------------<br>
     * String s=null;<br>
     * String s=DataConvert.toString(s,"yes");<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“yes”<br>
     *
     * @param s  字符串 sDefault 字符串
     * @return  若字符串 s为空则转换成sDefault，否则返回本身的字符串
     */
    public static String toString(String s,String sDefault)
    {
        if(s == null)
            return sDefault;
        else
            return s;
    }

    public static String toHTMLString(String s)
    {
        if(s == null || s.equals(""))
            return "&nbsp;";
        else

            return s;
    }

    public static String toHTMLMoney(String s)
    {
        if(s == null || s.equals(""))
            return "&nbsp;";
        else

            return toMoney(s);
    }

    /**
     * 若短整型数short1为空则转换成空字符串，否则返回本身的字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * short b=5;<br>
     * String s=DataConvert.toString(b);<br>
     * s+="dfgdg";<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“5dfgdg”<br>
     *
     * @param short1  短整型数
     * @return  若短整型数short1为空则转换成空字符串，否则返回本身的字符串
     */
    public static String toString(Short short1)
    {
        if(short1 == null)
            return "";
        else
            return String.valueOf(short1);
    }

    /**
     * 若双精度实数double1为空则转换成空字符串，否则返回本身的字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * Double b=1.5;<br>
     * String s=DataConvert.toString(b);<br>
     * s+="dfgdg";<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“1.5dfgdg”<br>
     *
     * @param double1 双精度实数
     * @return  若双精度实数double1为空则转换成空字符串，否则返回本身的字符串
     */
    public static String toString(Double double1)
    {
        if(double1 == null)
            return "";
        else
            return String.valueOf(double1);
    }

    /**
     * 若BigDecimal类型的bigdecimal为空则转换成空字符串，否则返回本身的字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * BigDecimal s=new BigDecimal(1.23011);
     * String b=DataConvert.toString(s);
     * b+="fdgdf";
     * System.out.print(b);<br>
     * ------------------------------------------<br>
     * 将得到“1.2301100000000000367350594387971796095371246337890625fdgdf”<br>
     *
     * @param bigdecimal BigDecimal类型数
     * @return  若BigDecimal类型的bigdecimal为空则转换成空字符串，否则返回本身的字符串
     */
    public static String toString(BigDecimal bigdecimal)
    {
        if(bigdecimal == null)
            return "";
        else
            return String.valueOf(bigdecimal);
    }

    /**
     * 将Object类型转换成字符串
     * @param ob
     * @return
     */
    public static String toString(Object ob){
        if (ob==null) return "";
        if (ob instanceof BigDecimal||ob instanceof Double){
            DecimalFormat a = new DecimalFormat("0.#############");
            return a.format(ob);
        }
        else return ob.toString();
    }

    /**
     * 将字符串s以钱的形式显示，整数部分三位一豆,为空或”null“或”“则返回空字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String d="10000454.283";<br>
     * String a=DataConvert.toMoney(d);<br>
     * System.out.print(a);<br>
     * ------------------------------------------<br>
     * 将得到“10,000,454.28”<br>
     *
     * @param s   原字符串
     * @return   将字符串s以钱的形式显示，整数部分三位一豆
     */
    public static String toMoney(String s)
    {
        try
        {
            if(s == null || s == "" || s.equalsIgnoreCase("Null"))
                return "";
            else
                return toMoney(Double.valueOf(s).doubleValue());
        }
        catch(Exception e)
        {
            return s;
        }

    }

    /**
     * 将双精度实数double1以钱的形式显示，整数部分三位一豆,为空则返回空字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * double d=10000454.283;<br>
     * String a=DataConvert.toMoney(d);<br>
     * System.out.print(a);<br>
     * ------------------------------------------<br>
     * 将得到“10,000,454.28”<br>
     *
     * @param double1 双精度实数
     * @return   将双精度实数double1以钱的形式显示，整数部分三位一豆的字符串
     */
    public static String toMoney(Double double1)
    {
        if(double1 == null)
            return "";
        else
            return toMoney(double1.doubleValue());
    }

    /**
     * 将双精度实数d以钱的形式显示，整数部分三位一豆<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * double d=10000454.283;<br>
     * String a=DataConvert.toMoney(d);<br>
     * System.out.print(a);<br>
     * ------------------------------------------<br>
     * 将得到“10,000,454.28”<br>
     *
     * @param d 双精度实数
     * @return   将双精度实数d以钱的形式显示，整数部分三位一豆的字符串
     */
    public static String toMoney(double d)
    {
    	/*
        String s = NumberFormat.getInstance().format(d);
        String s1 = NumberFormat.getInstance().format(d * 10D);
        if(d == 0.0D)
            s = "";
        else
        if(s.indexOf(".") < 0)
            s = s + ".00";
        else
        if(s1.indexOf(".") < 0)
            s = s + "0";

        return s;
        */

        java.text.NumberFormat nf=java.text.NumberFormat.getInstance();
        //nf.setMinimumIntegerDigits(3);
        nf.setMinimumFractionDigits(2)  ;
        nf.setMaximumFractionDigits(2)  ;

        return nf.format(d);

    }

    public static String toMoney(BigDecimal bigdecimal)
    {
        return toMoney(String.valueOf(bigdecimal));
    }

    /**
     * 将日期字符串s转换成某年某月某日字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="20050818";<br>
     * s=DataConvert.toDate_YMD(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“2005年08月18日”<br>
     *
     * @param s 日期字符串
     * @return 将日期字符串s转换成某年某月某日字符串
     */
    public static String toDate_YMD(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s.substring(4, 6);
        String s3 = s.substring(6, 8);
        String s4 = s1 + "年" + s2 + "月" + s3 + "日";
        return s4;
    }

    /**
     * 显示日期字符串s转换成某年某月某日至日期字符串s1转换成某年某月某日的字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * String  s1="20050818";<br>
     * s=DataConvert.toDate_YMD2(s,s1);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“2005年07月18日至2005年08月18日”<br>
     *
     * @param s   开始字符串
     * @param s1  结束字符串
     * @return  显示日期字符串s转换成某年某月某日至日期字符串s1转换成某年某月某日的字符串
     */
    public static String toDate_YMD2(String s, String s1)
    {
        String s2 = s.substring(0, 4);
        String s3 = s1.substring(0, 4);
        String s4 = s.substring(4, 6);
        String s5 = s1.substring(4, 6);
        String s6 = s.substring(6, 8);
        String s7 = s1.substring(6, 8);
        String s8 = s2 + "年" + s4 + "月" + s6 + "日至" + s3 + "年" + s5 + "月" + s7 + "日";
        return s8;
    }

    /**
     * 取日期字符串s的年月<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_YM(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“2005年07月”<br>
     *
     * @param s 日期字符串s
     * @return  取日期字符串s的年月
     */
    public static String toDate_YM(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s.substring(4, 6);
        String s3 = s1 + "年" + s2 + "月";
        return s3;
    }

    /**
     * 取日期字符串s的年份<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_Y(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“2005年”<br>
     *
     * @param s 日期字符串
     * @return  取日期字符串s的年份
     */
    public static String toDate_Y(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s1 + "年";
        return s2;
    }

    /**
     * 将日期字符串s转换为以“——“分割的日期字符串<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_YMD0(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“2005-07-18”<br>
     *
     * @param s 日期字符串
     * @return  将日期字符串s转换为以“——“分割的日期字符串
     */
    public static String toDate_YMD0(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s.substring(4, 6);
        String s3 = s.substring(6, 8);
        String s4 = "-";
        String s5 = s1 + s4 + s2 + s4 + s3;
        return s5;
    }

    /**
     * 取日期字符串s的年月<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_YM2(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“2005年07月”<br>
     *
     * @param s 日期字符串s
     * @return  取日期字符串s的年月
     */
    public static String toDate_YM2(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s.substring(4, 6);
        String s3 = s1 + "年" + s2 + "月";
        return s3;
    }

    /**
     * 将字符串s进行"ISO8859_1"转换<p>
     *
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="20050sa718";<br>
     * s=DataConvert.toRealString_old(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  将得到“20050sa718”<br>
     *
     * @param s 原字符串
     * @return  将字符串s进行"ISO8859_1"转换
     */
    public static String toRealString_old(String s)
    {
        try
        {
            String s1 = s;
            if(s1 != null)
                s1 = new String(s1.getBytes(), "ISO8859_1");
            return s1;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            return s;
        }
    }



    public static int iChange = 1;

    /**
     * 将字符串s转换成双精度实数<p>
     *  @author William
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="12";<br>
     * double b=DataConvert.toDouble(s);<br>
     * System.out.print(b);<br>
     * ------------------------------------------<br>
     *  将得到“12.0”<br>
     *
     * @param s 原字符串
     * @return  将字符串s转换成双精度实数
     */
    public static double toDouble(String s)
    {
        if(s==null || s.equals("")) return 0;
        return Double.parseDouble(s);
    }

    /**
     * 将ObjectNo转换成Double
     * @param o
     * @return
     */
    public static double toDouble(Object o)
    {
        if(o==null) return 0;
        return Double.parseDouble(o.toString());
    }

    /**
     * 将ObjectNo转换成Double,并四舍五入保留n位小数
     * @param o
     * @param n 保留小数位数
     * @return
     */
    public static double toDouble(Object o,int n)
    {
        if(o==null) return 0;
        double res = Double.parseDouble(o.toString());
        BigDecimal b= new BigDecimal(res);
        res=b.setScale(n,BigDecimal.ROUND_HALF_UP).doubleValue();
        return res;
    }

    /**
     * 将ObjectNo转换成Double,并四舍五入保留n位小数
     * @param o
     * @param n 保留小数位数
     * @param method 保留小数方式
     * @return
     */
    public static double toDouble(Object o,int n,int method)
    {
        if(o==null) return 0;
        double res = Double.parseDouble(o.toString());
        BigDecimal b= new BigDecimal(res);
        res=b.setScale(n,method).doubleValue();
        return res;
    }


    /**
     * 将字符串s转换成整数<p>
     * @author William
     * 示例：<p>
     * ------------------------------------------<br>
     * String  s="12";<br>
     * int b=DataConvert.toInt(s);<br>
     * System.out.print(b);<br>
     * ------------------------------------------<br>
     *  将得到“12”<br>
     *
     * @param s 原字符串
     * @return  将字符串s转换成整数
     */
    public static int toInt(String s)
    {
        if(s==null || s.equals("")) return 0;
        return Integer.parseInt(s);
    }
}