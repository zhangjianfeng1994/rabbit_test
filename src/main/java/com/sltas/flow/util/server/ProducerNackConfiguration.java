package com.sltas.flow.util.server;

import java.io.Serializable;
import java.util.Arrays;

import com.sltas.flow.rabbit.dto.NackCause;

/**
 * <p>
 * Title: ProducerNackConfig.java
 * </p>
 * <p>
 * Description: 生产者应答失败配置
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月5日 下午2:40:56  
 */
public class ProducerNackConfiguration implements Serializable{
	
	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年11月5日 下午3:51:24 
	 */
	private static final long serialVersionUID = -5671817566530343291L;

	/**
	 * 生产者入队异常service名称
	 */
	private String nackServiceName;
	
	/**
	 * 方法名
	 */
	private String nackServiceMethod;

	/**
	 * 参数类型
	 * 指定返回信息均为 NackCause 为参数信息
	 */
	private final Class<?>[] paramTypes = {NackCause.class};

	private ProducerNackConfiguration(){
		super();
	}
	/**
	 * <p>
	 * Title: ProducerNackConfiguration.java
	 * </p>
	 * <p>
	 * Description: 初始化生产者入队异常处理配置
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @param nackServiceName		service名称
	 * @param nackServiceMethod 	service方法名
	 */
	private ProducerNackConfiguration(String nackServiceName,String nackServiceMethod){
		this.nackServiceName = nackServiceName;
		this.nackServiceMethod = nackServiceMethod;
	}
	
	
	public static ProducerNackConfiguration newInstance(String nackServiceName,String nackServiceMethod) {
		return new ProducerNackConfiguration(nackServiceName,nackServiceMethod);
	}
	
	public String getNackServiceName() {
		return nackServiceName;
	}

	public String getNackServiceMethod() {
		return nackServiceMethod;
	}

	public Class<?>[] getParamTypes() {
		return paramTypes;
	}

	@Override
	public String toString() {
		return "ProducerNackConfiguration [nackServiceName=" + nackServiceName + ", nackServiceMethod="
				+ nackServiceMethod + ", paramTypes=" + Arrays.toString(paramTypes) + "]";
	}
}
