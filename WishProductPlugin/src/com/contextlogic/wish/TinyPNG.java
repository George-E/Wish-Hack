package com.contextlogic.wish;

import com.tinify.*;
import java.io.IOException;

public class TinyPNG {

    public static void main(String[] args) throws java.io.IOException {
        Tinify.setKey("MVGfmtzZA1YB-igVC9aJHFLO2GehQn_X");
        Tinify.fromFile("unoptimized.png").toFile("optimized.png");
    }
}
