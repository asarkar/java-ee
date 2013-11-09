package com.github.mkalin.jwsur2.ch5.images.base64;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class SkiImageService {
	private Map<String, Image> photos;

	public SkiImageService() {
		photos = new HashMap<String, Image>();

		photos.put("nordic.jpg", createImage("nordic.jpg"));
	}

	@WebMethod
	public Image getImage(String name) {
		return photos.get(name);
	}

	@WebMethod
	public List<Image> getImages() {
		List<Image> allImages = new ArrayList<Image>();
		allImages.addAll(photos.values());

		return allImages;
	}

	private Image createImage(String fileName) {
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return image;
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
