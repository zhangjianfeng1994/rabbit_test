package com.sltas.flow.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class ObjectUtil {

	
	private final static Object[][] patterns = {  
		{Pattern.compile("^\\d+-\\d+-\\d+$"),"yyyy-MM-dd"},  
		{Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+$"),"yyyy-MM-dd HH:mm"},  
		{Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+:\\d+$"),"yyyy-MM-dd HH:mm:ss"},  
		{Pattern.compile("^\\d+/\\d+/\\d+$"),"yyyy/MM/dd"},  
		{Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+$"),"yyyy/MM/dd HH:mm"},  
		{Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+:\\d+$"),"yyyy/MM/dd HH:mm:ss"}  
	}; 
	
	public static String getDateFormat(String dateString){
		String format = null;  
        Pattern p;  
        for(Object[] item:patterns){  
            p = (Pattern)item[0];  
            if(p.matcher(dateString).matches()){  
                format = item[1].toString();  
                break;  
            }  
        }
        return format;
	}
	
	
    /**
     * <p>
     * Title: isNullOrEmpty
     * </p>
     * <p>
     * Description: 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty 
     * </p>
     * <p>
     * 
     * </p>
     * @tags @param obj
     * @tags @return 
     * 
     * @author 周顺宇
     * @date 2018年1月23日下午2:23:43
     */
    public static boolean isNullOrEmpty(Object obj) {  
        if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
          
        return false;  
    }  
    
    
	/**
	 * <p>
	 * Title: deeplyCopy
	 * </p>
	 * <p>
	 * Description: 序列化
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param src
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2017年9月24日下午1:50:15
	 */
	public static <T> T deeplyCopy(T src){
		try {
			//深度复制--序列化串行化  jvm中序列化指向初始为空 close是销毁相关资料
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.close();
			baos.close();
			//恢复（反序列化-反串行）
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			T copy =  (T) ois.readObject();
			ois.close();
			bais.close();
			
			return copy;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
