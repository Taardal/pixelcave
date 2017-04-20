package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.gameloop.GameLoop;
import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.layer.TiledEditorLayerType;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallingActorState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(FallingActorState.class);

    private Actor actor;
    private TiledEditorMap tiledEditorMap;
    private int speedY;
    private boolean falling;

    private double gravity = 9.81f;
    private double g = 0.5f;
    private double time = GameLoop.getSecondsPerUpdate();

    float positionX = 0;
    float positionY = 0;
    float velocityX = 0;
    float velocityY = 0;

    double vy = 0;
    double maxVy = 10;

    public FallingActorState(Actor actor, TiledEditorMap tiledEditorMap) {
        this.actor = actor;
        this.tiledEditorMap = tiledEditorMap;
        speedY = 1;
        falling = true;
    }

    @Override
    public String toString() {
        return "FallingActorState{}";
    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(Key.UP)) {
            if (velocityY == 0) {
                velocityY = -20f;
            }
        }
        return null;
    }

    @Override
    public ActorState update() {
        if (falling) {
            fall();
        } else {
            if (velocityY != 0) {

                double time = this.time + 0.2;
//                double gravity = 10;

                //velocityY
                double v = actor.getY() + velocityY * time;
                actor.setY((int) v);

                velocityY += gravity * time;
                LOGGER.debug("Velocity Y [{}]", velocityY);

                if (actor.getY() >= 175) {
                    velocityY = 0;
                }
            }
        }
        return null;
    }

    private void fall() {
        int x = actor.getX();
        int y = actor.getY();

        for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
            TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
            if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer")) {

                int column = x / tiledEditorMap.getTileWidth();
                int row = (y + actor.getHeight() + speedY) / tiledEditorMap.getTileHeight();
                int tileId = tiledEditorLayer.getData2D()[column][row];
                if (tileId != TiledEditorMap.NO_TILE_ID) {

                    Tile tile = tiledEditorMap.getTiles().get(tileId);
                    if (tile.isSlope()) {

                        int slopeCollisionX = x + (actor.getWidth() / 2);
                        int tileX = column * tiledEditorMap.getTileWidth();
                        int tileY = row * tiledEditorMap.getTileHeight();

                        int slopeY;
                        if (tile.getDirection() == Direction.EAST) {
                            slopeY = (tileY + tiledEditorMap.getTileHeight()) - (slopeCollisionX - tileX);
                        } else {
                            slopeY = (tileY + tiledEditorMap.getTileHeight()) - ((tileX + tiledEditorMap.getTileWidth()) - slopeCollisionX);
                        }

                        if ((y + actor.getHeight() + speedY) > slopeY) {
                            falling = false;
                            y = slopeY - actor.getHeight();
                        } else {
                            y += speedY;
                        }

                    } else {
                        falling = false;
                        vy = 0;
//                        y = (row - 1) * tiledEditorMap.getTileHeight();
                    }

                } else {
//                    y += speedY;

                    double v0y = 0;

                    double sy = v0y * time + 0.5 * gravity * Math.pow(time, 2);
//                    LOGGER.debug("[{}] sy", sy);

                    double vy = v0y + gravity * time;

                    this.vy += vy;
                    LOGGER.debug("[{}] vy", this.vy);

                    if (this.vy > maxVy) {
                        this.vy = maxVy;
                    }

                    y += this.vy;

                }
            }
        }

        actor.setY(y);
    }

    private boolean isTileLayer(TiledEditorLayer tiledEditorLayer) {
        return tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER;
    }

}
