package com.github.mkalin.jwsur2.ch5.predictions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

public class Predictions {
	private ConcurrentMap<Integer, Prediction> predictions;
	private ServletContext sctx;
	private AtomicInteger mapKey;

	public Predictions() {
		predictions = new ConcurrentHashMap<Integer, Prediction>();
		mapKey = new AtomicInteger();
	}

	public void setServletContext(ServletContext sctx) {
		this.sctx = sctx;
	}

	public ServletContext getServletContext() {
		return this.sctx;
	}

	public void setMap(ConcurrentMap<String, Prediction> predictions) {
	}

	public ConcurrentMap<Integer, Prediction> getMap() {
		if (good2Go())
			return this.predictions;
		else
			return null;
	}

	public int addPrediction(Prediction p) {
		int id = mapKey.incrementAndGet();
		p.setId(id);
		predictions.put(id, p);
		return id;
	}

	public Prediction getPrediction(int id) {
		return predictions.get(id);
	}

	public List<Prediction> getPredictions() {
		List<Prediction> list;
		if (good2Go()) {
			Object[] preds = predictions.values().toArray();
			Arrays.sort(preds);

			list = new ArrayList<Prediction>();
			for (Object obj : preds)
				list.add((Prediction) obj);
			return list;
		} else
			return null;
	}

	// ** utilities
	private boolean good2Go() {
		if (getServletContext() == null)
			return false;

		if (predictions.size() < 1)
			populate();
		return true;
	}

	private void populate() {
		String filename = "/WEB-INF/data/predictions.db";
		InputStream in = sctx.getResourceAsStream(filename);

		// Read the data into the array of Predictions.
		if (in != null) {
			try {
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(isr);

				String record = null;
				while ((record = reader.readLine()) != null) {
					String[] parts = record.split("!");
					Prediction p = new Prediction();
					p.setWho(parts[0]);
					p.setWhat(parts[1]);
					addPrediction(p);
				}
			} catch (IOException e) {
			}
		}
	}
}
