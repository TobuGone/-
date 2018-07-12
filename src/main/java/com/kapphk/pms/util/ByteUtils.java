package com.kapphk.pms.util;

import java.io.UnsupportedEncodingException;

/**
 * byte数组操作  通用工具类
 * 
 * 2018-01-31
 * @author 胡子
 *
 */
public class ByteUtils {
    /**
     * 在byte数组中截取指定长度数组
     * @param src   源数组
     * @param begin 源数组要复制的起始位置
     * @param count 要复制的长度
     * @return      需要的byte数组
     *
     * dest:目的数组
     * destPos:目的数组放置的起始位置
     */
    public static byte[] subBytes(byte[] src,int begin,int count) {
        byte[] dest = new byte[count];
        System.arraycopy(src, begin, dest, 0, count);//dest:目的数组/0:目的数组放置的起始位置
        return dest;
    }

    /**
     * byte数组转换成String
     * @param bytes     待转换byte数组
     * @param offset    指定开始位置（偏移）
     * @param count     解码的长度
     * @return          byte数组对应的字符串
     */
    public static String byte2String(byte[] bytes, int offset, int count) {
        if (bytes == null) return null;
        if (bytes.length >= count) {
            try {
                String strings = new String(bytes, offset, count, "gb2312");
                strings = strings.replace("\0", "");
                return strings;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * String转换成byte数组
     * @param strings   待转换字符串
     * @param count     解码后byte数组的长度
     * @return          字符串对应的byte数组
     */
    public static byte[] string2Byte(String strings, int count) {
        byte[] bytes = new byte[count];
        if (strings != null){
            try {
                byte[] bytesTemp = strings.getBytes("gb2312");
                for (int i = 0; i < (bytesTemp.length > count ? count : bytesTemp.length); i++) {
                    bytes[i] = bytesTemp[i];
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * int转换成byte数组（无符号n位int(无符号)转换成byte数组）
     * @param integer   待转换的int
     * @param capacity  byte容量
     * @return          int对应的byte数组
     */
    public static byte[] int2Byte(int integer, int capacity) {
        byte[] bytes = new byte[capacity];
        if (capacity == 1) {
            bytes[0] = (byte) (0xff & integer);
        } else if (capacity == 2) {
            bytes[0] = (byte) (0xff & integer);
            bytes[1] = (byte) ((0xff00 & integer) >> 8);
        } else if (capacity == 4) {
            bytes[0] = (byte) (0xff & integer);// "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
            bytes[1] = (byte) ((0xff00 & integer) >> 8);// ">>"右移位，若为正数则高位补0，若为负数则高位补1
            bytes[2] = (byte) ((0xff0000 & integer) >> 16);
            bytes[3] = (byte) ((0xff000000 & integer) >> 24);
        }
        return bytes;
    }

    /**
     * byte数组转换成int（从指定位置读取无符号16位）
     * @param bytes         byte数组
     * @param offset        偏移位
     * @param capacity      byte容量
     * @return              bytes对应的int值
     */
    public static int byte2Int(byte[] bytes, int offset,int capacity) {
        if (capacity == 1) {
            if ((bytes.length < offset)) return 0;
            return (int) ((bytes[offset] & 0xFF));

        } else if (capacity == 2) {
            if ((bytes.length < (offset + 1))) return 0;
            return (int) ((bytes[offset] & 0xFF) | ((bytes[offset + 1] & 0xFF) << 8)
            );

        } else if (capacity == 4) {
            if ((bytes.length < (offset + 3))) return 0;
            return (int) (
                    (bytes[offset] & 0xFF)
                            | ((bytes[offset + 1] & 0xFF) << 8)
                            | ((bytes[offset + 2] & 0xFF) << 16)
                            | ((bytes[offset + 3] & 0xFF) << 24)
            );
        }
        return 0;
    }
}
