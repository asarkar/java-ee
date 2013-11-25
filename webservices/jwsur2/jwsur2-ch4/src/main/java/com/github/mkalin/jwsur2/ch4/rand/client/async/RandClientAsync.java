package com.github.mkalin.jwsur2.ch4.rand.client.async;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.NextN;
import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.RandService;
import com.github.mkalin.jwsur2.ch4.rand.client.async.generated.RandServiceService;

public class RandClientAsync {
	private final RandService port;

	public RandClientAsync() {
		RandServiceService service = new RandServiceService();
		port = service.getRandServicePort();
	}

	public static void main(String[] args) {
		RandClientAsync asyncClient = new RandClientAsync();

		asyncClient.requestAndPoll();
		asyncClient.requestAndRegister();
	}

	/*
	 * This is a request-and-poll style async invocation that polls the server
	 * in a separate thread
	 */
	private void requestAndPoll() {
		final Response<NextNResponse> future = port.nextNAsync(nextN(4));

		// Spawn a new thread to poll server for response
		new Thread(new RequestAndPollRunnable(future), "requestAndPoll")
				.start();

		System.out.println("requestAndPoll is back...\n");

		try {
			Thread.sleep(5000); // in production, do something useful!
		} catch (Exception e) {
		}

		System.out.println("\nrequestAndPoll is exiting...");
	}

	/*
	 * This is a request-and-register style async invocation that registers a
	 * callback handler
	 */
	private void requestAndRegister() {
		port.nextNAsync(nextN(4), new RequestAndRegisterHandler());

		System.out.println("requestAndRegister is back...\n");

		try {
			Thread.sleep(5000); // in production, do something useful!
		} catch (Exception e) {
		}

		System.out.println("\nrequestAndRegister is exiting...");
	}

	private NextN nextN(int i) {
		NextN nextN = new NextN();
		nextN.setArg0(i);

		return nextN;
	}

	private final class RequestAndRegisterHandler implements
			AsyncHandler<NextNResponse> {
		public void handleResponse(Response<NextNResponse> future) {
			System.out.println("Handler is called...");

			processResponse(future);
		}
	}

	private final class RequestAndPollRunnable implements Runnable {
		Response<NextNResponse> future;

		RequestAndPollRunnable(Response<NextNResponse> future) {
			this.future = future;
		}

		@Override
		public void run() {
			while (!future.isDone()) {
				// System.out.println("Polling server...");
			}

			System.out.println("Received server response...");

			processResponse(future);
		}
	}

	private void processResponse(Response<NextNResponse> future) {
		try {
			List<Integer> nums = future.get().getReturn();

			for (Integer num : nums) {
				System.out.println(num);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
