package com.github.mkalin.jwsur2.ch4.rand.client.async;

import java.util.List;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.NextN;
import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.RandService;
import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.RandServiceService;

public class RandClientAsync {
	public static void main(String[] args) {
		RandServiceService service = new RandServiceService();
		RandService port = service.getRandServicePort();
		NextN nextN = new NextN();
		nextN.setArg0(4);
		port.nextNAsync(nextN, new MyHandler());
		System.out.println("main is back while handler is busy...\n");
		try {
			Thread.sleep(5000); // in production, do something useful!
		} catch (Exception e) {
		}

		System.out.println("\nmain is exiting...");
	}

	static class MyHandler implements AsyncHandler<NextNResponse> {
		public void handleResponse(Response<NextNResponse> future) {
			try {
				System.out.println("\nHandler is called...");

				NextNResponse response = future.get();
				List<Integer> nums = response.getReturn();
				for (Integer num : nums)
					System.out.println(num);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}
