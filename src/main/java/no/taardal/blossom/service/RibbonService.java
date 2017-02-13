package no.taardal.blossom.service;

import com.google.inject.Inject;
import no.taardal.blossom.resourceloader.ResourceLoader;
import no.taardal.blossom.ribbon.RibbonItem;
import no.taardal.blossom.ribbon.Ribbon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RibbonService implements Service<Ribbon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RibbonService.class);
    private static final String RIBBONS_RESOURCE_FOLDER = "ribbons";

    private ResourceLoader<BufferedImage> bufferedImageResourceLoader;

    @Inject
    public RibbonService(ResourceLoader<BufferedImage> bufferedImageResourceLoader) {
        this.bufferedImageResourceLoader = bufferedImageResourceLoader;
    }

    @Override
    public Ribbon get(String name) {
        String path = RIBBONS_RESOURCE_FOLDER + "/" + name;
        URI ribbonsResourceDirectoryURI = getRibbonsResourceFolderURI(path);
        File ribbonsResourceDirectory = new File(ribbonsResourceDirectoryURI);
        String[] ribbonImagesFileNames = ribbonsResourceDirectory.list();
        List<RibbonItem> bufferedImages = new ArrayList<>();
        if (ribbonImagesFileNames != null) {
            for (String ribbonImageFileName : ribbonImagesFileNames) {
                String fullPath = path + "/" + ribbonImageFileName;
                BufferedImage bufferedImage = bufferedImageResourceLoader.loadResource(fullPath);
                bufferedImages.add(new RibbonItem(bufferedImage));
            }
        } else {
            LOGGER.warn("Could not find any files in ribbon resource directory [{}].", ribbonsResourceDirectory.getName());
        }
        return new Ribbon(bufferedImages);
    }

    private URI getRibbonsResourceFolderURI(String path) {
        try {
            return bufferedImageResourceLoader.getResourceURL(path).toURI();
        } catch (URISyntaxException e) {
            LOGGER.error("Could not get ribbons resource folder URI", e);
            throw new RuntimeException(e);
        }
    }

}
