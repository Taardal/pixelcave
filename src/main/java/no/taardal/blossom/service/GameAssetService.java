package no.taardal.blossom.service;

import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.world.World;

public interface GameAssetService {

    World getWorld(String relativePath);

    RibbonManager getRibbonManager(String relativePath);

    SpriteSheet getSpriteSheet(String relativePath, int spriteWidth, int spriteHeight);

}
