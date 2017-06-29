package no.taardal.blossom.service;

import java.awt.image.BufferedImage;
import java.net.URL;

public interface ResourceService {

    default URL getResourceURL(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    BufferedImage getImage(String path);
    String readFile(String path);
    String[] getFileNames(String path);

}
