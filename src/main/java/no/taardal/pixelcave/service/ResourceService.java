package no.taardal.pixelcave.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceService.class);

    public String readFile(String path) {
        return readFile(Paths.get(getUri(path)));
    }

    public BufferedImage getBufferedImage(String path) {
        return getBufferedImage(getResourceURL(path));
    }

    public String[] getFileNames(String path) {
        String[] fileNames = new File(getUri(path)).list();
        if (fileNames != null) {
            return fileNames;
        } else {
            LOGGER.warn("Could not find any files on path [{}].", path);
            return new String[]{};
        }
    }

    private String readFile(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            LOGGER.error("Could not read file from path [{}].", path, e);
            throw new RuntimeException(e);
        }
    }

    private BufferedImage getBufferedImage(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.error("Could not read image from URL [{}].", url, e);
            throw new RuntimeException(e);
        }
    }

    private URI getUri(String path) {
        try {
            return getResourceURL(path).toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Could not get resource URI from path [{}].", path, e);
            throw new RuntimeException(e);
        }
    }

    private URL getResourceURL(String path) {
        return getClass().getClassLoader().getResource(path);
    }

}
