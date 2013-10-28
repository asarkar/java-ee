package com.github.mkalin.jwsur2.ch4.rand.client;

import java.util.List;

public class RandClient {
	public static void main(String[] args) {
		// setup
		RandServiceService service = new RandServiceService();
		RandService port = service.getRandServicePort();

		// sample calls
		System.out.println(port.next1());
		System.out.println();
		List<Integer> nums = port.nextN(4);
		for (Integer num : nums)
			System.out.println(num);
	}
}
