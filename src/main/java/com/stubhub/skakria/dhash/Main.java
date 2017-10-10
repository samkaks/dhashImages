package com.stubhub.skakria.dhash;

import com.stubhub.skakria.dhash.DownloadImage;

public class Main {

    public static void main(String[] args) {
        String imageUrl = "https://pixel.nymag.com/imgs/fashion/daily/2016/02/29/29-liu-wen.w245.h368.2x.jpg";
        String filePath = "/Users/skakria/Projects/images/Urls";

        DownloadImage.findDuplicates(imageUrl, filePath); // returns a filePath to the
        // duplicate image
    }

}