package no.taardal.blossom.resourceloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringResourceLoader implements ResourceLoader<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringResourceLoader.class);

    @Override
    public String loadResource(String path) {
        return readFile(getUri(path));
    }

    private URI getUri(String path) {
        try {
            return getResourceURL(path).toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Could not get resource URI.", e);
            throw new RuntimeException(e);
        }
    }

    private String readFile(URI uri) {
        try {
            return new String(Files.readAllBytes(Paths.get(uri)));
        } catch (IOException e) {
            LOGGER.error("Could not read file.", e);
            throw new RuntimeException(e);
        }
    }

}
