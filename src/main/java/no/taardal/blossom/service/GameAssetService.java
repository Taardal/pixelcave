package no.taardal.blossom.service;

import no.taardal.blossom.ribbon.Ribbon;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.world.World;

import java.util.List;

public interface GameAssetService {

    World getWorld(String relativePath);

    List<Ribbon> getRibbons(String relativePath);

    SpriteSheet getSpriteSheet(String relativePath, int spriteWidth, int spriteHeight);

}
