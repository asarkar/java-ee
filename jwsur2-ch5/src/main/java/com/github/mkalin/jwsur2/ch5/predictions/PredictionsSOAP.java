package com.github.mkalin.jwsur2.ch5.predictions;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService
@HandlerChain(file = "serviceHandler.xml")
public class PredictionsSOAP {
	@Resource
	private WebServiceContext wsCtx;
	private ServletContext sCtx;
	private static final Predictions predictions = new Predictions();
	private static final int maxLength = 16;

	@WebMethod
	public List<Prediction> getAll() {
		init();
		return predictions.getPredictions();
	}

	@WebMethod
	public Prediction getOne(int id) {
		init();
		return predictions.getPrediction(id);
	}

	@WebMethod
	public String create(String who, String what) throws VerbosityException {
		int count = wordCount(what);
		if (count > maxLength)
			throw new VerbosityException(count + " is too verbose!",
					"Max words: " + maxLength);
		init();
		Prediction p = new Prediction();
		p.setWho(who);
		p.setWhat(what);
		int id = predictions.addPrediction(p);
		String msg = "Prediction " + id + " created.";
		return msg;
	}

	@WebMethod
	public String edit(int id, String who, String what)
			throws VerbosityException {
		int count = wordCount(what);
		if (count > maxLength)
			throw new VerbosityException(count + " is too verbose!",
					"Max words: " + maxLength);
		init();
		String msg = "Prediction " + id + " not found.";
		Prediction p = predictions.getPrediction(id);
		if (p != null) {
			if (who != null)
				p.setWho(who);
			if (what != null)
				p.setWhat(what);
			msg = "Prediction " + id + " updated.";
		}
		return msg;
	}

	@WebMethod
	public String delete(int id) {
		init();
		String msg = "Prediction " + id + " not found.";
		Prediction p = predictions.getPrediction(id);
		if (p != null) {
			predictions.getMap().remove(id);
			msg = "Prediction " + id + " removed.";
		}
		return msg;
	}

	private void init() {
		if (wsCtx == null)
			throw new RuntimeException("DI failed on wsCtx!");
		if (sCtx == null) { // ServletContext not yet set?
			MessageContext mCtx = wsCtx.getMessageContext();
			sCtx = (ServletContext) mCtx.get(MessageContext.SERVLET_CONTEXT);
			predictions.setServletContext(sCtx);
		}
	}

	private int wordCount(String words) {
		if (words == null)
			return -1;
		return words.trim().split("\\s+").length;
	}
}