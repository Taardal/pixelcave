package no.taardal.blossom.service;

import com.google.inject.Inject;
import no.taardal.blossom.manager.RibbonsManager;
import no.taardal.blossom.resourceloader.ResourceLoader;
import no.taardal.blossom.ribbon.Ribbon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RibbonManagerService implements Service<RibbonsManager> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RibbonManagerService.class);
    private static final String RIBBONS_RESOURCE_FOLDER = "ribbons";

    private ResourceLoader<BufferedImage> bufferedImageResourceLoader;
    private List<Ribbon> ribbons;

    @Inject
    public RibbonManagerService(ResourceLoader<BufferedImage> bufferedImageResourceLoader) {
        this.bufferedImageResourceLoader = bufferedImageResourceLoader;
        ribbons = new ArrayList<>();
    }

    @Override
    public RibbonsManager get(String ribbonsResourceDirectoryName) {
        String ribbonsResourceDirectoryPath = getPath(ribbonsResourceDirectoryName);
        for (String fileName : getFileNamesInDirectory(ribbonsResourceDirectoryPath)) {
            String ribbonImageFilePath = ribbonsResourceDirectoryPath + "/" + fileName;
            BufferedImage bufferedImage = bufferedImageResourceLoader.loadResource(ribbonImageFilePath);
            ribbons.add(new Ribbon(bufferedImage));
        }
        return new RibbonsManager(ribbons);
    }

    private String getPath(String name) {
        if (!name.startsWith(RIBBONS_RESOURCE_FOLDER)) {
            name = RIBBONS_RESOURCE_FOLDER + "/" + name;
        }
        return name;
    }

    private String[] getFileNamesInDirectory(String path) {
        File ribbonsResourceDirectoryFile = new File(getAsURI(path));
        String[] ribbonImagesFileNames = ribbonsResourceDirectoryFile.list();
        if (ribbonImagesFileNames != null) {
            return ribbonImagesFileNames;
        } else {
            LOGGER.warn("Could not find any files in ribbon resource directory [{}].", ribbonsResourceDirectoryFile.getName());
            return new String[]{};
        }
    }

    private URI getAsURI(String path) {
        try {
            return bufferedImageResourceLoader.getResourceURL(path).toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Could not get ribbons resource folder URI", e);
            throw new RuntimeException(e);
        }
    }

}

