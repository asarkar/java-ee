package edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImageServiceTest {
	ImageService imageService;

	@Before
	public void setUp() {
		imageService = new ImageServiceImpl();
	}

	@Test
	public void testGetImages() {
		List<byte[]> images = imageService.getImages(null);
		Assert.assertNotNull(images);
		Assert.assertTrue(images.size() == 1);
	}

	@Test
	public void testGetImagesByName() {
		List<String> names = new ArrayList<String>();
		names.add("DukeWave.jpeg");
		List<byte[]> images = imageService.getImages(names);
		Assert.assertNotNull(images);
		Assert.assertTrue(images.size() == 1);
	}

	@Test
	public void testGetImagesByWrongName() {
		List<String> names = new ArrayList<String>();
		names.add("DukeWave.png");
		List<byte[]> images = imageService.getImages(names);
		Assert.assertNull(images);
	}
}
