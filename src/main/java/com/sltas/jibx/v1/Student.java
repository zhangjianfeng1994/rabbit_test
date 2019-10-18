package com.sltas.jibx.v1;

import lombok.Data;

@Data
public class Student {

	private String name;
    private String SNO;
    private String classNO;
    private String age;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", SNO='" + SNO + '\'' +
                ", classNO=" + classNO +
                ", age=" + age +
                '}';
    }
	 
}
