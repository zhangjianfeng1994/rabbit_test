package com.sltas.flow.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * <p>
 * Title: RequestParams
 * </p>
 * <p>
 * Description: 请求参数对象
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇
 * @date 2018年3月30日上午9:32:48
 *  
 */
public class ParamsParser implements Serializable{

	/**
	 * serialVersionUID
	 * @author ThinkPad
	 * @date 2018年3月30日上午9:35:59
	 */
	private static final long serialVersionUID = -5106820038897521018L;
	
	private transient Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private final LinkedHashMap<String, Object> documentAsMap;

	private boolean isCreate = false;
	private String serialNumber;
	private String callerInformation;
	
	public ParamsParser(){
		documentAsMap = new LinkedHashMap<String, Object>();
	}
	
	public ParamsParser(final String key, final Object value) {
        documentAsMap = new LinkedHashMap<String, Object>();
        documentAsMap.put(key, value);
    }
	
    public ParamsParser(final Map<String, Object> map) {
        documentAsMap = new LinkedHashMap<String, Object>(map);
    }
    
    /**
     * json 转换 ParamsParser 对象
     */
    public static ParamsParser parse(final String json) {
        return parse(json, new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(), new GsonTypeAdapter()).create());
    }
    
    private static ParamsParser parse(final String json, final Gson decoder) {
    	Assert.notNull(json, "json can not be null");
    	Assert.notNull(decoder, "decoder can not be null");
    	return new ParamsParser(decoder.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType()));
    }
    
    /**
     * key 新增key
     * value 新增key value值
     */
    public ParamsParser append(final String key, final Object value) {
        documentAsMap.put(key, value);
        return this;
    }
    
    /**
     * 注意事项：类型必须绝对匹配才能采用此种方法,否则异常
     * key 检索key
	 * clazz 返回类型泛型
     */
    public <T> T get(final Object key, final Class<T> clazz) {
    	Assert.notNull(clazz, "clazz can not be null");
        return clazz.cast(documentAsMap.get(key));
    }
    
    /**
     * 注意事项：类型必须绝对匹配才能采用此种方法,否则异常
     * key 检索key
	 * defaultValue 如果获取值不存在  使用默认值
     */
    @SuppressWarnings("unchecked")
	public <T> T get(final Object key, final T defaultValue) {
    	Assert.notNull(defaultValue, "defaultValue can not be null");
        Object value = documentAsMap.get(key);
        return value == null ? defaultValue : ((Class<T>) defaultValue.getClass()).cast(value);
    }
    
    public Integer getInteger(final Object key) {
    	Assert.notNull(get(key), key + " value can not be null");
        return Integer.valueOf(get(key).toString().trim()) ;
    }
    
    public Long getLong(final Object key) {
    	Assert.notNull(get(key), key + " value can not be null");
        return Long.valueOf(get(key).toString().trim()) ;
    }
    
    public Double getDouble(final Object key) {
    	Assert.notNull(get(key), key + " value can not be null");
        return Double.valueOf(get(key).toString().trim()) ;
    }
    
    public String getString(final Object key) {
    	Assert.notNull(get(key), key + " value can not be null");
    	String text = String.valueOf(get(key).toString().trim());
    	Assert.hasLength(text, key + " value size can not be null");
        return text;
    }
    
    public Boolean getBoolean(final Object key) {
        Assert.notNull(get(key), key + " value can not be null");
        return Boolean.valueOf(get(key).toString().trim()) ;
    }
    
    public Date getDate(final Object key) {
    	Assert.notNull(get(key), key + " value can not be null");
    	String dateString = get(key).toString().trim();
    	String format = ObjectUtil.getDateFormat(dateString);
    	Assert.notNull(format, key + " value type error");
        try {
			return new SimpleDateFormat(format).parse(dateString);
		} catch (ParseException e) {
			throw new IllegalArgumentException(key + " convert the type error");
		}  
    }
    
    public String toJson() {
        return new Gson().toJson(this);
    }
    
	public void create(){
		this.isCreate = true;
		this.serialNumber = UUID.randomUUID().toString().replaceAll("-", "");
		/**
		 * 获取方法的第三级调用这
		 * 1.StackTraceElement
		 * 2.QueryFilter
		 * 3.外部调用者 （第二级）
		 */
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		if(trace.length > 3){
			this.callerInformation = trace[2].toString();
		}
		
		logger.info("request body ： {}", this );
	}
	
	public boolean isCreate() {
		return isCreate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getCallerInformation() {
		return callerInformation;
	}

	public int size() {
		return documentAsMap.size();
	}

	public boolean isEmpty() {
		return documentAsMap.isEmpty();
	}

	public boolean containsKey(final Object key) {
		return documentAsMap.containsValue(key);
	}

	public boolean containsValue(final Object value) {
		return documentAsMap.containsValue(value);
	}

	public Object get(final Object key) {
		return documentAsMap.get(key);
	}

	public Object put(final String key, final Object value) {
		return documentAsMap.put(key, value);
	}

	public Object remove(final Object key) {
		return documentAsMap.remove(key);
	}

	public void putAll(final Map<? extends String, ? extends Object> map) {
		documentAsMap.putAll(map);
		
	}

	public void clear() {
		documentAsMap.clear();
		
	}

	public Set<String> keySet() {
		return documentAsMap.keySet();
	}

	public Collection<Object> values() {
		return documentAsMap.values();
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return documentAsMap.entrySet();
	}
	
	@Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParamsParser document = (ParamsParser) o;

        if (!documentAsMap.equals(document.documentAsMap)) {
            return false;
        }
        
        return true;
    }

	@Override
    public int hashCode() {
        return documentAsMap.hashCode();
    }

	
	
    @Override
	public String toString() {
		return "ParamsParser [ serialNumber=" + serialNumber
				+ ", isCreate=" + isCreate
				+ ", callerInformation=" + callerInformation
				+ ", documentAsMap=" + documentAsMap + " ]";
	}
	
}
