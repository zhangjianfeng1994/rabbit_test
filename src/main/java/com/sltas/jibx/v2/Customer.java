package com.sltas.jibx.v2;

import java.util.List;

import lombok.Data;

@Data
public class Customer {

	private long customerNumber;
    /**
     * Personal name.
     */
    private String firstName;
    /**
     * Family name.
     */
    private String lastName;
    /**
     * Middle name(s), if any.
     */
    private List<String> middleNames;
	
}
