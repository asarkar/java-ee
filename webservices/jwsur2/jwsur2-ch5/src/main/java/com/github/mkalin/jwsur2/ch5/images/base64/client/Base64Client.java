package com.github.mkalin.jwsur2.ch5.images.base64.client;

import com.github.mkalin.jwsur2.ch5.images.base64.client.generated.SkiImageService;
import com.github.mkalin.jwsur2.ch5.images.base64.client.generated.SkiImageServiceService;

public class Base64Client {

	public static void main(String[] args) {
		SkiImageServiceService webServiceClient = new SkiImageServiceService();
		SkiImageService service = webServiceClient.getSkiImageServicePort();

		service.getImages();
	}
}
