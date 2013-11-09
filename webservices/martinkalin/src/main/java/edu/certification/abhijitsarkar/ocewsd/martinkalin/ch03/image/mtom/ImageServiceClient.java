package edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image.mtom;

import java.util.List;

import javax.activation.DataHandler;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class ImageServiceClient {
	public static void main(String[] args) throws Exception {
		Logger logger = AppLogger.getInstance(ImageServiceClient.class);
		logger.debug("Starting ImageServiceClient");

		ImageService eif = new ImageServiceImplService()
				.getImageServiceImplPort();
		List<DataHandler> images = eif.getImages(null);

		for (DataHandler dataHandler : images) {
			logger.debug("MIME type: " + dataHandler.getContentType());
			logger.debug("Content: " + dataHandler.getContent());
		}
	}
}
