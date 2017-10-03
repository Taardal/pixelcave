package no.taardal.pixelcave.service;

import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.sprite.SpriteSheet;
import no.taardal.pixelcave.world.World;

import java.util.List;

public interface GameAssetService {

    World getWorld(String relativePath);

    List<Ribbon> getRibbons(String relativePath);

    SpriteSheet getSpriteSheet(String relativePath, int spriteWidth, int spriteHeight);

}
