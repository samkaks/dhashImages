package com.stubhub.skakria.dhash;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please enter correct values; usage for this " +
                    "program is as follows: java -jar <this jar> " +
                    "<absolute_path_to_url_file> <absolute_path_to_output_dir");
            System.exit(1);
        }

        String urlsFilePath = args[0];
        String outputDirPath = args[1];

        String imageUrl = "https://pixel.nymag.com/imgs/fashion/daily/2016/02/29/29-liu-wen.w245.h368.2x.jpg";
        String filePath = "/Users/skakria/Projects/images/Urls";

        try {
            DownloadImage.saveAndFindDuplicates(imageUrl, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}