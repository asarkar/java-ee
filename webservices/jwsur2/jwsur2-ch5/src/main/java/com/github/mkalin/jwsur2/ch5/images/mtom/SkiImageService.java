package com.github.mkalin.jwsur2.ch5.images.mtom;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

@WebService
// MTOM overrides SOAPBinding
@MTOM(enabled = true, threshold = 1024)
//@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public class SkiImageService {
	private Map<String, DataHandler> photos;

	public SkiImageService() {
		photos = new HashMap<String, DataHandler>();

		photos.put("nordic.jpg", createImage("nordic.jpg"));
	}

	@WebMethod
	public DataHandler getImage(String name) {
		return photos.get(name);
	}

	@WebMethod
	public List<DataHandler> getImages() {
		List<DataHandler> allImages = new ArrayList<DataHandler>();
		allImages.addAll(photos.values());

		return allImages;
	}

	private DataHandler createImage(String fileName) {
		byte[] bytes = getRawBytes(fileName);
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		Iterator<ImageReader> iterators = ImageIO
				.getImageReadersByFormatName("jpeg");
		ImageReader iterator = (ImageReader) iterators.next();
		Image image = null;
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(in);
			iterator.setInput(iis, true);
			image = iterator.read(0);
			return new DataHandler(image, "image/jpeg");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] getRawBytes(String fileName) {
		if (fileName == null)
			fileName = "nordic.jpg";

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			FileInputStream in = new FileInputStream(getClass().getResource(
					fileName).getPath());

			byte[] buffer = new byte[2048];
			int n = 0;
			while ((n = in.read(buffer)) != -1)
				out.write(buffer, 0, n); // append to array
			in.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}
}
