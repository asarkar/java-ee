package com.github.mkalin.jwsur2.ch5.predictions.client;

import java.util.List;

import com.github.mkalin.jwsur2.ch5.predictions.client.generated.Prediction;
import com.github.mkalin.jwsur2.ch5.predictions.client.generated.PredictionsSOAP;
import com.github.mkalin.jwsur2.ch5.predictions.client.generated.PredictionsSOAPService;
import com.github.mkalin.jwsur2.ch5.predictions.client.generated.VerbosityException_Exception;

public class PredictionsClient {
	public static void main(String[] args) throws VerbosityException_Exception {
		if (args.length < 2) {
			System.err.println("Usage: PredictionsClient <name> <key>");
			return;
		}
		new PredictionsClient().runTests(args[0], args[1]);
	}

	private void runTests(String name, String key)
			throws VerbosityException_Exception {
		PredictionsSOAPService service = new PredictionsSOAPService();
		service.setHandlerResolver(new ClientHandlerResolver(name, key));
		PredictionsSOAP port = service.getPredictionsSOAPPort();

		getTests(port);
		postTest(port);
		getAllTest(port); // confirm the POST
		deleteTest(port); // delete the just POSTed prediction
		getAllTest(port); // confirm the POST
		putTest(port);
	}

	private void getTests(PredictionsSOAP port) {
		getAllTest(port);
		getOneTest(port);
	}

	private void getAllTest(PredictionsSOAP port) {
		msg("getAll");
		List<Prediction> preds = port.getAll();
		for (Prediction pred : preds)
			System.out.println(String.format("%2d: ", pred.getId())
					+ pred.getWho() + " predicts: " + pred.getWhat());
	}

	private void getOneTest(PredictionsSOAP port) {
		msg("getOne (31)");
		System.out.println(port.getOne(31).getWhat());
	}

	private void postTest(PredictionsSOAP port)
			throws VerbosityException_Exception {
		msg("postTest");
		String who = "Freddy";
		String what = "Bad things may happen on Friday.";
		String res = port.create(who, what);
		System.out.println(res);
	}

	private void putTest(PredictionsSOAP port)
			throws VerbosityException_Exception {
		msg("putTest -- here's the record to be edited");
		getOneTest(port);
		msg("putTest results");
		String who = "FooBar";
		String what = null; // shouldn't change
		int id = 31;
		String res = port.edit(id, who, what);
		System.out.println(res);
		System.out.println("Confirming:");
		Prediction p = port.getOne(31);
		System.out.println(p.getWho());
		System.out.println(p.getWhat());
	}

	private void deleteTest(PredictionsSOAP port) {
		msg("deleteTest");
		String res = port.delete(33);
		System.out.println(res);
	}

	private void msg(String s) {
		System.out.println("\n" + s + "\n");
	}
}
