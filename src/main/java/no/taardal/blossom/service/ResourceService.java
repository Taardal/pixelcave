package no.taardal.blossom.service;

import no.taardal.blossom.ribbon.RibbonManager;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.world.World;

public interface ResourceService {

    World getWorld(String name);

    RibbonManager getRibbonManager(String name);

    SpriteSheetBuilder getSpriteSheet();

}
