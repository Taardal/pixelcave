package no.taardal.blossom.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import no.taardal.blossom.resourceloader.BufferedImageResourceLoader;
import no.taardal.blossom.resourceloader.StringResourceLoader;
import no.taardal.blossom.resourceloader.ResourceLoader;

import java.awt.image.BufferedImage;

public class ResourceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(new StringResourceLoaderTypeLiteral()).to(StringResourceLoader.class);
        binder.bind(new BufferedImageResourceLoaderTypeLiteral()).to(BufferedImageResourceLoader.class);
    }

    private class BufferedImageResourceLoaderTypeLiteral extends TypeLiteral<ResourceLoader<BufferedImage>> {
    }

    private class StringResourceLoaderTypeLiteral extends TypeLiteral<ResourceLoader<String>> {
    }

}
