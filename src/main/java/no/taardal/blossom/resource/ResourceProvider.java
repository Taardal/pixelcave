package no.taardal.blossom.resource;

import java.net.URL;

public interface ResourceProvider<T> {

    default URL getResourceURL(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    T getResource(String name);

}
