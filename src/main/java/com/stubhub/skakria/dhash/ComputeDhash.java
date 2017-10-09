package com.stubhub.skakria.dhash;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;


import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


public class ComputeDhash {

	public static BufferedImage findResizeAndGray(File f) throws IOException {

		// Find a suitable ImageReader
		Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
		ImageReader reader = null;
		while (readers.hasNext()) {
			reader = (ImageReader) readers.next();
			if (reader.canReadRaster()) {
				break;
			}
		}

		ImageInputStream input = ImageIO.createImageInputStream(f);
		reader.setInput(input);

		// Read the image raster
		Raster raster = reader.readRaster(0, null);

		// Create a new RGB image
		BufferedImage bi = new BufferedImage(raster.getWidth(), raster.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		// Fill the new image with the old raster
		bi.getRaster().setRect(raster);
		greyScaleImage(bi);

		// resize to a fixed width (not proportional)
		int scaledWidth = 9;
		int scaledHeight = 8;

		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, bi.getType());
		// System.out.println(outputImage);
		// System.out.println(outputImage.getAlphaRaster()!=null);
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(bi, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();
		input.close();
		return outputImage;
	}

//	public static void writeImage(BufferedImage out, String outputPathName) throws IOException {
//
//		File file = new File(outputPathName);
//		if (!file.exists() && !file.mkdirs() && !file.createNewFile()) {
//			System.out.println("Had some weird issue creating a file");
//		}
//
//		String formatName = outputPathName.substring(outputPathName.lastIndexOf(".") + 1);
//		// writes to output file
//		ImageIO.write(out, formatName, file);
//
//	}

	public static void greyScaleImage(BufferedImage img) { // this is bad i'll
															// come fix it later
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int p = img.getRGB(x, y); // this is very slow

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// calculate average
				int avg = (r + g + b) / 3;

				// replace RGB value with avg
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;

				img.setRGB(x, y, p);
			}
		}

	}

	public static String dhash(BufferedImage img) {
		//long startTime = System.currentTimeMillis();
		StringBuilder res = new StringBuilder();

		for (int y = 0; y < img.getHeight(); y++) {

			StringBuilder bit = new StringBuilder();

			for (int x = 0; x < img.getWidth() - 1; x++) {

				if ((img.getRGB(x, y) - img.getRGB(x + 1, y)) < 0) {
					bit.append("1");
					// System.out.print(1 + " ");
				} else {
					bit.append("0");
					// System.out.print(0 + " ");
				}
				if (bit.length() == 4) {
					int decimal = Integer.parseInt(bit.toString(), 2);
					String hexStr = Integer.toString(decimal, 16);
					bit = new StringBuilder();
					res.append(hexStr);
				}
			}
			// System.out.println();
		}
		// System.out.println();
		return res.toString();
	}

	public static double hammingDistance(String a, String b) {
		int dist = 0;
		if (a.length() != b.length())
			return -1; // have to come back and put a check here
		for (int i = 0; i < a.length(); i++) {
			int diff = a.charAt(i) - b.charAt(i);
			if (a.charAt(i) == b.charAt(i) || Math.abs(diff) <= 2)
				dist += 0;
			else
				dist += 1;
		}
		return (1 - dist / ((double) a.length()));
	}

	public static boolean isIdentical(File a, File b) throws IOException {
		//System.out.println(b.getName());
		BufferedImage imageA = findResizeAndGray(a);
		BufferedImage imageB = findResizeAndGray(b);
		String ahash = dhash(imageA);
		String bhash = dhash(imageB);
		Double dist = hammingDistance(ahash, bhash);
		if (dist > 0.9)
			return true;
		else
			return false;

	}
	

}
