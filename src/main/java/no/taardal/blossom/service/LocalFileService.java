package no.taardal.blossom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileService implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileService.class);

    @Override
    public BufferedImage getImage(String path) {
        return getBufferedImage(path);
    }

    @Override
    public String readFile(String path) {
        return readFile(Paths.get(getUri(path)));
    }

    @Override
    public String[] getFileNames(String path) {
        String[] fileNames = new File(getUri(path)).list();
        if (fileNames != null) {
            return fileNames;
        } else {
            LOGGER.warn("Could not find any files on path [{}].", path);
            return new String[]{};
        }
    }

    private BufferedImage getBufferedImage(String path) {
        try {
            return ImageIO.read(getResourceURL(path));
        } catch (IOException e) {
            LOGGER.error("Could not load image", e);
            throw new RuntimeException(e);
        }
    }

    private String readFile(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            LOGGER.error("Could not read file.", e);
            throw new RuntimeException(e);
        }
    }

    private URI getUri(String path) {
        try {
            return getResourceURL(path).toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Could not get resource URI.", e);
            throw new RuntimeException(e);
        }
    }

}
