package com.sltas.jibx.v3;

import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class Main {

	public static void main(String[] args) throws JiBXException {
		
		Ufinterface ufinterface = new Ufinterface();
		
		Voucher voucher = new Voucher();
		ufinterface.setVoucher(voucher);
		
		VoucherHead head = new VoucherHead();
		head.setPrepay("n");
		head.setCorp("01");
		head.setBillTypeCode("D4");
		head.setBusinessType("D4");
		voucher.setVoucher_head(head);
		
		VoucherBody body = new VoucherBody();
//		Entry entry = new Entry();
//		entry.setSum_direction("1");
//		entry.setDigest("收款55");
//		body.setEntry(entry);
		
		body.setSum_direction("1");
		body.setDigest("收款55");
		
		voucher.setVoucher_body(body);

        IBindingFactory factory = BindingDirectory.getFactory(Ufinterface.class);
        IMarshallingContext mctx= factory.createMarshallingContext();
        StringWriter sw=new StringWriter();
        mctx.setIndent(2);
        mctx.marshalDocument(ufinterface, "UTF-8", null, sw);
        System.out.println(String.valueOf(sw));
        
        System.out.println("========================================================");
//        System.out.println("========================================================");
//        System.out.println("========================================================");
        
//        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
//        StringReader reader = new StringReader(String.valueOf(sw));
//        Ufinterface parseStudent = (Ufinterface)uctx.unmarshalDocument(reader);
        
//        System.out.println(parseStudent);
	}
	
}
