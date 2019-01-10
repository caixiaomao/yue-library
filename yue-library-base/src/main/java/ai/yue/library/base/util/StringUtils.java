package ai.yue.library.base.util;

import cn.hutool.core.util.ArrayUtil;

/**
 * String工具类
 * @author  孙金川
 * @version 创建时间：2017年10月28日
 */
public class StringUtils {

	/**
	 * 判断String类型的字符串是否为空<br>
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		return null == str || "".equals(str);
	}
	
	/**
	 * 确认String类型的字符串不为空<br>
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		return !isEmpty(str);
	}
	
	/**
	 * 判断String数组是否为空<br>
	 * <p>弱判断，只确定数组中第一个元素是否为空</p>
	 * @param array 要判断的String[]数组
	 * @return String数组长度==0或者第一个元素为空（true）
	 */
	public static boolean isEmptys(String[] array) {
		return (null == array || array.length == 0 || isEmpty(array[0])) ? true : false;
	}
	
	/**
	 * 确认String数组不为空<br>
	 * <p>弱判断，只确定数组中第一个元素是否为空</p>
	 * @param array 要判断的String[]数组
	 * @return String数组长度==0或者第一个元素为空（true）
	 */
	public static boolean isNotEmptys(String[] array) {
		return !isEmptys(array);
	}
	
	/**
	 * 字符串尾部值自动递增
	 * @param str 尾部值是Integer类型
	 * @return
	 */
	public static String autoIncrement(String str) {
		int maxIndex = str.length() - 1;
		Integer autoIncrementValue = Integer.valueOf(str.charAt(maxIndex)) + 1;
		return str.substring(0, maxIndex) + autoIncrementValue;
	}
	
	/**
	 * 删除开始相等的字符串
	 * @param sb 		需要处理的字符串
	 * @param condition 条件
	 * @return
	 */
	public static StringBuffer deleteFirstEqualString(StringBuffer sb, String condition) {
		if (sb.toString().startsWith(condition)) {
			return sb.delete(0, sb.length());
		}
		return sb;
	}
    
	/**
	 * 删除尾部相等的字符串
	 * @param sb 		需要处理的字符串
	 * @param condition 条件
	 * @return
	 */
	public static StringBuffer deleteLastEqualString(StringBuffer sb, String condition) {
		int end = sb.length();
		int start = end - condition.length();
		String str = sb.substring(start, end);
		if (condition.equals(str)) {
			return sb.delete(start, end);
		}
		return sb;
	}
	
    /**
     * 删除前后相等字符串
     * @param str		需要处理的字符串
     * @param firstStr	开始字符串
     * @param lastStr	末尾字符串
     * @return
     */
    public static String deleteFirstLastEqualString(String str, String firstStr, String lastStr) {
    	StringBuffer sb = new StringBuffer(str);
    	sb = deleteFirstEqualString(sb, firstStr);
    	sb = deleteLastEqualString(sb, lastStr);
    	return sb.toString();
    }
    
    /**
     * 删除前后字符串
     * @param str		需要处理的字符串
     * @param length	删除长度
     * @return
     */
    public static String deleteFirstLastString(String str, int length) {
    	return str.substring(length, str.length() - length);
    }
    
	/**
	 * <h1>判断字符串是否存在字符串数组中</h1>
	 * @deprecated {@linkplain ArrayUtil#contains(Object[], Object)}
	 * @param array
	 * @param str
	 * @return
	 */
    @Deprecated
	public static boolean isExistArrayInner(String[] array, String str) {
		for(String content : array) {
			if (content.equals(str)) {
				return true;
			}
		}
		return false;
	}

}
