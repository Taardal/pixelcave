package no.taardal.blossom.service;

import no.taardal.blossom.level.Level;

public interface Service<T extends Level> {

    T getLevel(String path);

}
