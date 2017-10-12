package com.stubhub.skakria.dhash;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.io.File.separator;

public class DownloadImage {

    private static final String FILE_EXT = ".jpeg";
    private static final String OUTPUT_DIR = "images";
    private static final String URL_FILE_NAME = "url.txt";

    /**
     * Finds the duplicates (if any) for an image at a provided URL.
     *
     * @param imageURL the URL of the image to look for duplicates of.
     * @param filePath the path of the directory to output image files to.
     * @return a list containing the file names of the duplicates found.
     * @throws IOException if an exception occurs during IO.
     */
    static List<String> saveAndFindDuplicates(String imageURL, String filePath)
            throws IOException {
        String imageFileName = generateRandomString() + FILE_EXT;
        String outputDirPath = filePath + separator + OUTPUT_DIR;

        Path originalImage = Paths.get(filePath, imageFileName);
        Path outputDir = Paths.get(outputDirPath);

        Files.createDirectories(outputDir);

        downloadImage(imageURL, originalImage);

        List<String> urls = Files.readAllLines(Paths
                .get(filePath, URL_FILE_NAME));

        for (String url : urls) {
            String filename = generateRandomString() + FILE_EXT;
            Path imageFile = Paths.get(outputDirPath, filename);
            downloadImage(url, imageFile);
        }

        List<String> results = new ArrayList<>();

        DirectoryStream<Path> stream = Files
                .newDirectoryStream(outputDir);
        for (Path entry : stream) {
            if (Files.isRegularFile(entry) &&
                    entry.getFileName().endsWith(FILE_EXT) &&
                    DHash.isIdentical(originalImage.toFile(), entry.toFile()))
                results.add(entry.getFileName().toString());
        }

        return results;
    }

    // download the image located at imageURL into outputFile
    private static void downloadImage(String imageURL, Path outputFile)
            throws IOException {
        BufferedImage image = ImageIO.read(new URL(imageURL));
        // TODO: Decide if "JPEG" needs refactoring into a constants container.
        ImageIO.write(image, "JPEG", Files.newOutputStream(outputFile));
    }

    private static String generateRandomString() {
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            char c = (char) ('a' + rand.nextInt(26));
            builder.append(c);
        }
        return builder.toString();
    }
}
