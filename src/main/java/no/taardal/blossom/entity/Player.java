package no.taardal.blossom.entity;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.state.actorstate.ActorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Player extends Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public Player(AnimatedSprite animatedSprite, TiledEditorMap tiledEditorMap) {
        super(animatedSprite, tiledEditorMap);
        direction = Direction.EAST;

        speedX = 1;
        speedY = 1;

        x = 250;
        y = 100;

        Rectangle boundingBox = new Rectangle();
        int boundingBoxWidth = (animatedSprite.getWidth() / tiledEditorMap.getTileWidth()) * tiledEditorMap.getTileWidth();
        int boundingBoxHeight = (animatedSprite.getHeight() / tiledEditorMap.getTileHeight()) * tiledEditorMap.getTileHeight();
        LOGGER.debug("Bounding box: [{}w, {}h]", boundingBoxWidth, boundingBoxHeight);
        boundingBox.setBounds(getX(), getY(), boundingBoxWidth, boundingBoxHeight);
        setBoundingBox(boundingBox);
    }

    public void handleInput(Keyboard keyboard) {
        ActorState actorState = this.actorState.handleInput(keyboard);
        if (actorState != null) {
            LOGGER.debug("New actor state [{}]", actorState.toString());
            this.actorState = actorState;
        }
    }

}
