package no.taardal.blossom.resourceloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BufferedImageResourceLoader implements ResourceLoader<BufferedImage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BufferedImageResourceLoader.class);

    @Override
    public BufferedImage loadResource(String path) {
        return getBufferedImage(getResourceURL(path));
    }

    private BufferedImage getBufferedImage(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            LOGGER.error("Could not load sprite sheet image", e);
            throw new RuntimeException(e);
        }
    }

}
