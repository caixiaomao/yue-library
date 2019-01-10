package ai.yue.library.base.util;

import static com.alibaba.fastjson.JSON.toJSON;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Map工具类
 * @author  孙金川
 * @version 创建时间：2018年1月26日
 */
public class MapUtils {

	/**
	 * 不可变的空Map常量
	 */
	public final static Map<String, Object> FINAL_EMPTY_MAP = new HashMap<>();
	/**
	 * @deprecated {@link #FINAL_EMPTY_MAP}
	 */
	@Deprecated
	public final static Map<String, Object> newHashMap = new HashMap<>();
	
	/**
	 * 判断Map数据结构key的一致性
	 * @param paramMap			参数
	 * @param mustContainKeys	必须包含的key（必传）
	 * @param canContainKeys	可包含的key（非必传）
	 * @return
	 */
	public static boolean isKeys(Map<String, Object> paramMap, String[] mustContainKeys, String... canContainKeys) {
		for (String key : mustContainKeys) {
			if (!paramMap.containsKey(key)) {
				return false;
			}
		}
		
		// 无可包含key
		if (0 == canContainKeys.length) {
			return true;
		}
		
		int keySize = mustContainKeys.length + canContainKeys.length;
		if (paramMap.size() > keySize) {
			return false;
		}
		
		int paramMapCanContainKeysLength = 0;
		for (String key : canContainKeys) {
			if (paramMap.containsKey(key)) {
				paramMapCanContainKeysLength++;
			}
		}
		
		if (paramMapCanContainKeysLength + mustContainKeys.length != paramMap.size()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 判断Map数据结构所有的key是否与数组完全匹配
	 * @param paramMap
	 * @param keys
	 * @return 匹配所有的key且大小一致（true）
	 */
	public static boolean isKeysEqual(Map<String, Object> paramMap, String[] keys) {
		if (paramMap.size() != keys.length) {
			return false;
		}
		for(String key : keys) {
			if(!paramMap.containsKey(key)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断Map数据结构是否包含{@linkplain keys}之一
	 * @param paramMap
	 * @param keys
	 * @return 只要包含一个key（true）
	 */
	public static boolean isContainsOneOfKey(Map<String, Object> paramMap, String[] keys) {
		for(String key : keys) {
			if(paramMap.containsKey(key)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断Map数组第一个元素，是否包含所有的key<br>
	 * <p>弱比较，只判断数组中第一个元素是否包含所有的key</p>
	 * @param paramMaps
	 * @param keys
	 * @return Map数组元素0包含所有的key（true）
	 */
	public static boolean isMapsKeys(Map<String, Object>[] paramMaps, String[] keys) {
		return isKeys(paramMaps[0], keys);
	}
	
    /**
     * Null-安全检查指定的Map是否为空。
     * <p>
     * Null returns true.
     * 
     * @param map  the map to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Map<String, Object> map) {
        return (map == null || map.isEmpty());
    }
    
	/**
	 * 判断Map数组是否为空<br>
	 * <p>弱判断，只确定数组中第一个元素是否为空</p>
	 * @param paramMaps 要判断的Map[]数组
	 * @return Map数组==null或长度==0或第一个元素为空（true）
	 */
	public static boolean isEmptys(Map<String, Object>[] paramMaps) {
		return (null == paramMaps || paramMaps.length == 0 || paramMaps[0].isEmpty()) ? true : false;
	}
	
	/**
	 * 判断Map是否为空，或者Map中String类型的value值是否为空<br>
	 * @param paramMap 要判断的Map
	 * @return
	 */
	public static boolean isStringValueEmpty(Map<String, Object> paramMap) {
		if (paramMap.isEmpty()) {
			return true;
		}
		for (Object value : paramMap.values()) {
			if (null == value || "".equals(value))  {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 删除Value字符串前后空格
	 * @param paramMap
	 */
	public static void trimStringValues(Map<String, Object> paramMap) {
		for (String key : paramMap.keySet()) {
			String str = getString(paramMap, key);
			String value = str.trim();
			if (!str.equals(value)) {
				paramMap.replace(key, value);
			}
		}
	}
	
	/**
	 * 批量移除
	 * @param paramMap 要操作的Map
	 */
	public static void remove(Map<String, Object> paramMap, String[] keys) {
		for (String key : keys) {
			paramMap.remove(key);
		}
	}
	
	/**
	 * 移除空Map对象
	 * @param paramMap 要操作的Map
	 */
	public static void removeEmptyMap(Map<String, Object> paramMap) {
		Iterator<Entry<String, Object>> iter = paramMap.entrySet().iterator();
	    while (iter.hasNext()) {
	        Entry<String, Object> entry = iter.next();
	        Object value = entry.getValue();
	        if (StringUtils.isEmpty(value)) {
	        	iter.remove();
	        }
	    }
	}
	
	/**
	 * 替换key
	 * @param paramMap 		要操作的Map
	 * @param key 			被替换的key
	 * @param replaceKey	替换的key
	 */
	public static void replaceKey(Map<String, Object> paramMap, String key, String replaceKey) {
		var value = paramMap.get(key);
		paramMap.put(replaceKey, value);
		paramMap.remove(key);
	}
	
    /**
     * 获取所有的key
     * @param paramMap
     * @return
     */
    public static List<String> keyList(Map<String, Object> paramMap) {
		List<String> list = new ArrayList<>();
		paramMap.keySet().forEach(action -> {
			list.add(action);
		});
		return list;
    }
    
    /**
     * 以安全的方式从Map中获取一组数据，组合成一个新的Map
     * @param paramMap
     * @param keys
     * @return
     */
	public static JSONObject getJSONObject(Map<String, Object> paramMap, String... keys) {
		JSONObject paramJSON = new JSONObject(paramMap);
		if (!isContainsOneOfKey(paramJSON, keys)) {
			return null;
		}

		JSONObject resultJSON = new JSONObject();
		for (String key : keys) {
			Object value = paramJSON.get(key);
			if (value != null) {
				resultJSON.put(key, value);
			}
		}

		return resultJSON;
	}
	
	/**
	 * 以安全的方式从Map中获取对象
	 * @param <T>
	 * @param paramMap
	 * @param key
	 * @param clazz
	 * @return
	 */
    public static <T> T getObject(final Map<?, ?> paramMap, final Object key, Class<T> clazz) {
        if (paramMap != null) {
            Object answer = paramMap.get(key);
            if (answer != null) {
            	return ObjectUtils.toObject(answer, clazz);
            }
        }
        
        return null;
    }
    
	/**
	 * 以安全的方式从Map中获取Number
	 * @param paramMap
	 * @param key
	 * @return
	 */
    public static Number getNumber(final Map<?, ?> paramMap, final Object key) {
        if (paramMap != null) {
            Object answer = paramMap.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;
                    
                } else if (answer instanceof String) {
                    try {
                        String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);
                        
                    } catch (ParseException e) {
                        // failure means null is returned
                    }
                }
            }
        }
        return null;
    }
	
	/**
	 * 以安全的方式从Map中获取字符串
	 * @param paramMap
	 * @param key
	 * @return
	 */
    public static String getString(final Map<?, ?> paramMap, final Object key) {
        if (paramMap != null) {
            Object answer = paramMap.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }
    
    /**
     * 以安全的方式从Map中获取Boolean
     * @param paramMap
	 * @param key
	 * @return
     */
    public static Boolean getBoolean(final Map<?, ?> paramMap, final Object key) {
        if (paramMap != null) {
            Object answer = paramMap.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;
                    
                } else if (answer instanceof String) {
                    return Boolean.valueOf((String) answer);
                    
                } else if (answer instanceof Number) {
                    Number n = (Number) answer;
                    return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return null;
    }
    
    /**
     * 以安全的方式从Map中获取Integer
     * @param paramMap
	 * @param key
	 * @return
     */
    public static Integer getInteger(final Map<?, ?> paramMap, final Object key) {
        Number answer = getNumber(paramMap, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return answer.intValue();
    }
	
    /**
     * 以安全的方式从Map中获取Long
     * @param paramMap
	 * @param key
	 * @return
     */
    public static Long getLong(final Map<?, ?> paramMap, final Object key) {
        Number answer = getNumber(paramMap, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return answer.longValue();
    }
    
    /**
     * 以安全的方式从Map中获取Double
     * @param paramMap
	 * @param key
	 * @return
     */
    public static Double getDouble(final Map<?, ?> paramMap, final Object key) {
        Number answer = getNumber(paramMap, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return answer.doubleValue();
    }
    
    /**
     * 以安全的方式从Map中获取BigDecimal
     * @param paramMap
	 * @param key
	 * @return
     */
    public static BigDecimal getBigDecimal(final Map<?, ?> paramMap, final Object key) {
        Number answer = getNumber(paramMap, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
        	return new BigDecimal((Double) answer);
        }
        return new BigDecimal(answer.doubleValue());
    }
    
    /**
     * 以安全的方式从Map中获取JSONObject
     * @param paramMap
	 * @param key
	 * @return
     */
    public static JSONObject getJSONObject(final Map<?, ?> paramMap, String key) {
        Object value = paramMap.get(key);

        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }

        if (value instanceof String) {
            return JSON.parseObject((String) value);
        }

        return (JSONObject) toJSON(value);
    }
	
    /**
     * 以安全的方式从Map中获取JSONArray
     * @param paramMap
	 * @param key
	 * @return
     */
    public static JSONArray getJSONArray(final Map<?, ?> paramMap, String key) {
        Object value = paramMap.get(key);

        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }

        if (value instanceof String) {
            return (JSONArray) JSON.parse((String) value);
        }

        return (JSONArray) toJSON(value);
    }
    
}
