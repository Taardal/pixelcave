package no.taardal.blossom.resourceloader;

import java.net.URL;

public interface ResourceLoader<T> {

    default URL getResourceURL(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    T loadResource(String path);

}
