package edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

@WebService(endpointInterface = "edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image.ImageService")
public class ImageServiceImpl implements ImageService {

	public ImageServiceImpl() {
		try {
			imageFiles = new File(location.toURI())
					.listFiles(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String filename) {
							return filename.endsWith(IMAGE_SUFFIX);
						}

					});
		} catch (URISyntaxException e) {
			e.printStackTrace();

			throw new WebServiceException("Invalid image location", e);
		}
	}

	public List<byte[]> getImages(final List<String> names) {
		List<byte[]> images = null;

		if (imageFiles == null || imageFiles.length == 0) {
			return null;
		}

		images = new ArrayList<byte[]>();

		// get the first ImageReader that can handle this suffix
		ImageReader reader = ImageIO.getImageReadersBySuffix(IMAGE_SUFFIX)
				.next();

		try {
			for (File imageFile : imageFiles) {
				if (names != null && names.size() > 0
						&& !names.contains(imageFile.getName())) {
					continue;
				}
				reader.setInput(ImageIO.createImageInputStream(imageFile));
				BufferedImage image = reader.read(reader.getMinIndex());
				WritableRaster raster = image.getRaster();
				DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
				images.add(data.getData());
				reader.reset();
			}
		} catch (IOException e) {
			e.printStackTrace();

			throw new WebServiceException("Error reading images from disk", e);
		}

		return (images.size() == 0 ? null : images);
	}

	private static final String IMAGE_LOCATION = "/ch03/image";
	private static final String IMAGE_SUFFIX = "jpeg";
	private static final URL location = ImageServiceImpl.class
			.getResource(IMAGE_LOCATION);
	private File[] imageFiles;
}
