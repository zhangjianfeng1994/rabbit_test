package com.sltas.jibx.v3;

import lombok.Data;

@Data
public class Voucher {

	private String id;
	
	private VoucherHead voucher_head;

	private VoucherBody voucher_body;
}
