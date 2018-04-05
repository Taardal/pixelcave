package no.taardal.pixelcave.service;

import no.taardal.pixelcave.ribbon.Ribbon;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.world.World;

import java.util.List;

public interface GameAssetService {

    World getWorld(String relativePath);

    List<Ribbon> getRibbons();

    SpriteSheet getSpriteSheet(String relativePath, int spriteWidth, int spriteHeight);

}
