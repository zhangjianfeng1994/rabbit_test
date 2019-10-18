package com.sltas.example.amqp.rabbit;

public class Main {

	
	public static void main(String[] args) {
		
		System.out.println(false && true || true);    //true 因为 &&比||优先
		System.out.println(true || true && false);    //true 因为 &&比||优先
		System.out.println(false && true);
	}
	
}
