package edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image.mtom;

import java.awt.Image;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;

@WebService(endpointInterface = "edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image.mtom.ImageService")
@MTOM(enabled = true)
//@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
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

	public List<DataHandler> getImages(final List<String> names) {
		List<DataHandler> images = null;

		if (imageFiles == null || imageFiles.length == 0) {
			return null;
		}

		images = new ArrayList<DataHandler>();
		DataHandler dataHandler = null;
		Image img = null;

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
				img = reader.read(reader.getMinIndex());
				dataHandler = new DataHandler(img, "image/jpeg");
				images.add(dataHandler);
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
