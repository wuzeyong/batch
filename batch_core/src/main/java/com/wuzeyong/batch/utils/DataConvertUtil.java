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
     * ȫ��ת���
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
     * ���ַ���sΪ����ת���ɿ��ַ��������򷵻ر���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String s=new String();<br>
     * s=DataConvert.toString(s);<br>
     * s+="dfgdg";<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���dfgdg��<br>
     *
     * @param s  ԭ�ַ���
     * @return   ���ַ���sΪ����ת���ɿ��ַ��������򷵻ر���
     */
    public static String toString(String s)
    {
        if(s == null)
            return "";
        else
            return s;
    }
    /**
     * �� s Ϊ����ת����Ĭ���ַ��� sDefault<p>
     * @author CJ(jbye)
     * ʾ����<p>
     * ------------------------------------------<br>
     * String s=null;<br>
     * String s=DataConvert.toString(s,"yes");<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���yes��<br>
     *
     * @param s  �ַ��� sDefault �ַ���
     * @return  ���ַ��� sΪ����ת����sDefault�����򷵻ر�����ַ���
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
     * ����������short1Ϊ����ת���ɿ��ַ��������򷵻ر�����ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * short b=5;<br>
     * String s=DataConvert.toString(b);<br>
     * s+="dfgdg";<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���5dfgdg��<br>
     *
     * @param short1  ��������
     * @return  ����������short1Ϊ����ת���ɿ��ַ��������򷵻ر�����ַ���
     */
    public static String toString(Short short1)
    {
        if(short1 == null)
            return "";
        else
            return String.valueOf(short1);
    }

    /**
     * ��˫����ʵ��double1Ϊ����ת���ɿ��ַ��������򷵻ر�����ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * Double b=1.5;<br>
     * String s=DataConvert.toString(b);<br>
     * s+="dfgdg";<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���1.5dfgdg��<br>
     *
     * @param double1 ˫����ʵ��
     * @return  ��˫����ʵ��double1Ϊ����ת���ɿ��ַ��������򷵻ر�����ַ���
     */
    public static String toString(Double double1)
    {
        if(double1 == null)
            return "";
        else
            return String.valueOf(double1);
    }

    /**
     * ��BigDecimal���͵�bigdecimalΪ����ת���ɿ��ַ��������򷵻ر�����ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * BigDecimal s=new BigDecimal(1.23011);
     * String b=DataConvert.toString(s);
     * b+="fdgdf";
     * System.out.print(b);<br>
     * ------------------------------------------<br>
     * ���õ���1.2301100000000000367350594387971796095371246337890625fdgdf��<br>
     *
     * @param bigdecimal BigDecimal������
     * @return  ��BigDecimal���͵�bigdecimalΪ����ת���ɿ��ַ��������򷵻ر�����ַ���
     */
    public static String toString(BigDecimal bigdecimal)
    {
        if(bigdecimal == null)
            return "";
        else
            return String.valueOf(bigdecimal);
    }

    /**
     * ��Object����ת�����ַ���
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
     * ���ַ���s��Ǯ����ʽ��ʾ������������λһ��,Ϊ�ջ�null���򡱡��򷵻ؿ��ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String d="10000454.283";<br>
     * String a=DataConvert.toMoney(d);<br>
     * System.out.print(a);<br>
     * ------------------------------------------<br>
     * ���õ���10,000,454.28��<br>
     *
     * @param s   ԭ�ַ���
     * @return   ���ַ���s��Ǯ����ʽ��ʾ������������λһ��
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
     * ��˫����ʵ��double1��Ǯ����ʽ��ʾ������������λһ��,Ϊ���򷵻ؿ��ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * double d=10000454.283;<br>
     * String a=DataConvert.toMoney(d);<br>
     * System.out.print(a);<br>
     * ------------------------------------------<br>
     * ���õ���10,000,454.28��<br>
     *
     * @param double1 ˫����ʵ��
     * @return   ��˫����ʵ��double1��Ǯ����ʽ��ʾ������������λһ�����ַ���
     */
    public static String toMoney(Double double1)
    {
        if(double1 == null)
            return "";
        else
            return toMoney(double1.doubleValue());
    }

    /**
     * ��˫����ʵ��d��Ǯ����ʽ��ʾ������������λһ��<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * double d=10000454.283;<br>
     * String a=DataConvert.toMoney(d);<br>
     * System.out.print(a);<br>
     * ------------------------------------------<br>
     * ���õ���10,000,454.28��<br>
     *
     * @param d ˫����ʵ��
     * @return   ��˫����ʵ��d��Ǯ����ʽ��ʾ������������λһ�����ַ���
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
     * �������ַ���sת����ĳ��ĳ��ĳ���ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="20050818";<br>
     * s=DataConvert.toDate_YMD(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���2005��08��18�ա�<br>
     *
     * @param s �����ַ���
     * @return �������ַ���sת����ĳ��ĳ��ĳ���ַ���
     */
    public static String toDate_YMD(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s.substring(4, 6);
        String s3 = s.substring(6, 8);
        String s4 = s1 + "��" + s2 + "��" + s3 + "��";
        return s4;
    }

    /**
     * ��ʾ�����ַ���sת����ĳ��ĳ��ĳ���������ַ���s1ת����ĳ��ĳ��ĳ�յ��ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * String  s1="20050818";<br>
     * s=DataConvert.toDate_YMD2(s,s1);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���2005��07��18����2005��08��18�ա�<br>
     *
     * @param s   ��ʼ�ַ���
     * @param s1  �����ַ���
     * @return  ��ʾ�����ַ���sת����ĳ��ĳ��ĳ���������ַ���s1ת����ĳ��ĳ��ĳ�յ��ַ���
     */
    public static String toDate_YMD2(String s, String s1)
    {
        String s2 = s.substring(0, 4);
        String s3 = s1.substring(0, 4);
        String s4 = s.substring(4, 6);
        String s5 = s1.substring(4, 6);
        String s6 = s.substring(6, 8);
        String s7 = s1.substring(6, 8);
        String s8 = s2 + "��" + s4 + "��" + s6 + "����" + s3 + "��" + s5 + "��" + s7 + "��";
        return s8;
    }

    /**
     * ȡ�����ַ���s������<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_YM(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���2005��07�¡�<br>
     *
     * @param s �����ַ���s
     * @return  ȡ�����ַ���s������
     */
    public static String toDate_YM(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s.substring(4, 6);
        String s3 = s1 + "��" + s2 + "��";
        return s3;
    }

    /**
     * ȡ�����ַ���s�����<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_Y(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���2005�ꡱ<br>
     *
     * @param s �����ַ���
     * @return  ȡ�����ַ���s�����
     */
    public static String toDate_Y(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s1 + "��";
        return s2;
    }

    /**
     * �������ַ���sת��Ϊ�ԡ��������ָ�������ַ���<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_YMD0(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���2005-07-18��<br>
     *
     * @param s �����ַ���
     * @return  �������ַ���sת��Ϊ�ԡ��������ָ�������ַ���
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
     * ȡ�����ַ���s������<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="20050718";<br>
     * s=DataConvert.toDate_YM2(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���2005��07�¡�<br>
     *
     * @param s �����ַ���s
     * @return  ȡ�����ַ���s������
     */
    public static String toDate_YM2(String s)
    {
        String s1 = s.substring(0, 4);
        String s2 = s.substring(4, 6);
        String s3 = s1 + "��" + s2 + "��";
        return s3;
    }

    /**
     * ���ַ���s����"ISO8859_1"ת��<p>
     *
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="20050sa718";<br>
     * s=DataConvert.toRealString_old(s);<br>
     * System.out.print(s);<br>
     * ------------------------------------------<br>
     *  ���õ���20050sa718��<br>
     *
     * @param s ԭ�ַ���
     * @return  ���ַ���s����"ISO8859_1"ת��
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
     * ���ַ���sת����˫����ʵ��<p>
     *  @author William
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="12";<br>
     * double b=DataConvert.toDouble(s);<br>
     * System.out.print(b);<br>
     * ------------------------------------------<br>
     *  ���õ���12.0��<br>
     *
     * @param s ԭ�ַ���
     * @return  ���ַ���sת����˫����ʵ��
     */
    public static double toDouble(String s)
    {
        if(s==null || s.equals("")) return 0;
        return Double.parseDouble(s);
    }

    /**
     * ��ObjectNoת����Double
     * @param o
     * @return
     */
    public static double toDouble(Object o)
    {
        if(o==null) return 0;
        return Double.parseDouble(o.toString());
    }

    /**
     * ��ObjectNoת����Double,���������뱣��nλС��
     * @param o
     * @param n ����С��λ��
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
     * ��ObjectNoת����Double,���������뱣��nλС��
     * @param o
     * @param n ����С��λ��
     * @param method ����С����ʽ
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
     * ���ַ���sת��������<p>
     * @author William
     * ʾ����<p>
     * ------------------------------------------<br>
     * String  s="12";<br>
     * int b=DataConvert.toInt(s);<br>
     * System.out.print(b);<br>
     * ------------------------------------------<br>
     *  ���õ���12��<br>
     *
     * @param s ԭ�ַ���
     * @return  ���ַ���sת��������
     */
    public static int toInt(String s)
    {
        if(s==null || s.equals("")) return 0;
        return Integer.parseInt(s);
    }
}