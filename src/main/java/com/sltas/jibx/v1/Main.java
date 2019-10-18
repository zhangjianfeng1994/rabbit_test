package com.sltas.jibx.v1;

import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class Main {

	public static void main(String[] args) throws JiBXException {
		
		Student student=new Student();
        student.setAge("12");
        student.setClassNO("111");
        student.setName("jon");
        student.setSNO("NO_ewewewj3413");

        IBindingFactory factory = BindingDirectory.getFactory(Student.class);
        IMarshallingContext mctx= factory.createMarshallingContext();
        StringWriter sw=new StringWriter();
        mctx.setIndent(2);
        mctx.marshalDocument(student, "UTF-8", null, sw);
        System.out.println(String.valueOf(sw));
        
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        StringReader reader = new StringReader(String.valueOf(sw));
        Student parseStudent = (Student)uctx.unmarshalDocument(reader);
        
        System.out.println(parseStudent);
	}
	
}
