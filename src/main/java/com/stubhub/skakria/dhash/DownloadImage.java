package com.stubhub.skakria.dhash;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DownloadImage {

    public static void saveImage(String imageUrl, String filePath) {
        BufferedImage image = null;
        try {
            File file = new File(filePath);
            URL url = new URL(imageUrl);
            image = ImageIO.read(url);

            ImageIO.write(image, "JPEG", file);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<String> readURLTextFile(String path) {
        List<String> result = new ArrayList<>();
        try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr)) {
            String line;

            while ((line = br.readLine()) != null) {
                result.add(line.trim());
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        return result;
    }

    private static String generateRandomString() {
        Random rand = new Random();
        String randomString = "";
        for (int i = 0; i < 10; i++) {
            char c = (char) ('a' + rand.nextInt(26));
            randomString += c;
        }
        return randomString;
    }

    /**
     * Finds the duplicates (if any) for an image at a provided URL.
     * TODO: overload method in case similarity coefficient is required.
     *
     * @param imageUrl
     * @param filePath
     * @throws IOException
     */
    public static void findDuplicates(String imageUrl, String filePath) throws IOException {
        String imagePath = filePath + "/" + generateRandomString() + ".jpeg";

        String folderPath = filePath + "/images";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
        }
        saveImage(imageUrl, imagePath);
        List<String> urlArray = readURLTextFile(filePath + "/url.txt");

        for (String s : urlArray) {
            String fileName = generateRandomString() + ".jpeg";
            saveImage(s, folderPath + "/" + fileName);
        }

        File[] listOfFiles = folder.listFiles();
        File ogImage = new File(imagePath);
        List<String> results = new ArrayList<>();

        for (File file : listOfFiles) {
            String normalizedPath = file.getAbsolutePath().toLowerCase();

            if (file.isFile() && normalizedPath.endsWith(".jpeg")) { // kinda hackey way to just get jpegs :D
                if (ComputeDhash.isIdentical(ogImage, file)) {
                    results.add(file.getName());
                }
            }
        }
        System.out.println(results);

    }

    public static void main(String[] args) throws IOException {

    }

}
