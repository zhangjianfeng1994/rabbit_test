package com.sltas.jibx.v3;

import lombok.Data;

@Data
public class Ufinterface {

	/**
	 * <ufinterface roottag="voucher" billtype="D4" subtype="run" replace="" receiver="" sender="sap" proc="add" isexchange="Y" filename="收款结算单模板demo.xml">
	 */
	
	private String roottag = "voucher";
	
	private String billtype = "D4";
	
	private String subtype = "run";
	
	private String replace;
	
	private String sender = "sap";
	
	private String proc = "add";
	
	private String isexchange = "Y";
	
	private String filename = "收款结算单模板demo.xml";
	
	private Voucher voucher;
	
}
