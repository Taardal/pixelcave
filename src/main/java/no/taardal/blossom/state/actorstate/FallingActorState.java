package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
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
    private TiledEditorLayer environmentLayer;
    private int speedY;

    public FallingActorState(Actor actor, TiledEditorMap tiledEditorMap) {
        this.actor = actor;
        this.tiledEditorMap = tiledEditorMap;
        environmentLayer = getEnvironmentLayer(tiledEditorMap);
        speedY = 1;
    }

    @Override
    public String toString() {
        return "FallingActorState{}";
    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        return null;
    }

    @Override
    public ActorState update() {
        if (actor.isOnGround()) {
            return new IdleActorState(actor, tiledEditorMap);
        } else {
            fall();
            return null;
        }
    }

    private TiledEditorLayer getEnvironmentLayer(TiledEditorMap tiledEditorMap) {
        return tiledEditorMap.getTiledEditorLayers().stream()
                .filter(tiledEditorLayer -> isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer"))
                .findFirst()
                .orElse(null);
    }

    private boolean isTileLayer(TiledEditorLayer tiledEditorLayer) {
        return tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER;
    }

    private void fall() {
        int column = actor.getX() / tiledEditorMap.getTileWidth();
        int row = (actor.getY() + actor.getHeight() + speedY) / tiledEditorMap.getTileHeight();
        int tileId = environmentLayer.getData2D()[column][row];

        if (tileId != TiledEditorMap.NO_TILE_ID) {
            Tile tile = tiledEditorMap.getTiles().get(tileId);
            if (tile.isSlope()) {
                landOnSlope(column, row, tile);
            } else {
                landOnFlatGround(row);
            }
        } else {
            actor.setY(actor.getY() + speedY);
        }
    }

    private void landOnSlope(int column, int row, Tile tile) {
        int slopeCollisionX = actor.getX() + (actor.getWidth() / 2);
        int tileX = column * tiledEditorMap.getTileWidth();
        int tileY = row * tiledEditorMap.getTileHeight();

        int slopeY;
        if (tile.getDirection() == Direction.EAST) {
            slopeY = (tileY + tiledEditorMap.getTileHeight()) - (slopeCollisionX - tileX);
        } else {
            slopeY = (tileY + tiledEditorMap.getTileHeight()) - ((tileX + tiledEditorMap.getTileWidth()) - slopeCollisionX);
        }

        if ((actor.getY() + actor.getHeight() + speedY) > slopeY) {
            actor.setY(slopeY - actor.getHeight());
        } else {
            actor.setY(actor.getY() + speedY);
        }
    }

    private void landOnFlatGround(int row) {
        actor.setY((row - 1) * tiledEditorMap.getTileHeight());
    }

}
