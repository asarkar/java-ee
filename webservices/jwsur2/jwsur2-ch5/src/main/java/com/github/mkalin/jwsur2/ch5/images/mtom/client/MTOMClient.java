package com.github.mkalin.jwsur2.ch5.images.mtom.client;

import javax.xml.ws.soap.MTOMFeature;

import com.github.mkalin.jwsur2.ch5.images.mtom.client.generated.SkiImageService;
import com.github.mkalin.jwsur2.ch5.images.mtom.client.generated.SkiImageServiceService;

public class MTOMClient {
	public static void main(String[] args) {
		SkiImageServiceService webServiceClient = new SkiImageServiceService();

		MTOMFeature mtom = new MTOMFeature();
		SkiImageService service = webServiceClient.getPort(
				SkiImageService.class, mtom);

		service.getImages();

//		service = webServiceClient.getSkiImageServicePort();
//
//		service.getImages();
	}
}
