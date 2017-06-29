package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.world.World;
import no.taardal.blossom.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallingActorState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(FallingActorState.class);

    private Actor actor;
    private World world;
    private Layer environmentLayer;
    private int speedY;

    public FallingActorState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
        environmentLayer = getEnvironmentLayer(world);
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
            return new IdleActorState(actor, world);
        } else {
            fall();
            return null;
        }
    }

    private Layer getEnvironmentLayer(World world) {
        return world.getLayers().stream()
                .filter(tiledEditorLayer -> isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer"))
                .findFirst()
                .orElse(null);
    }

    private boolean isTileLayer(Layer layer) {
        return layer.getLayerType() == LayerType.TILELAYER;
    }

    private void fall() {
        int column = actor.getX() / world.getTileWidth();
        int row = (actor.getY() + actor.getHeight() + speedY) / world.getTileHeight();
        int tileId = environmentLayer.getTileGrid()[column][row];

        if (tileId != World.NO_TILE_ID) {
            Tile tile = world.getTiles().get(tileId);
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
        int tileX = column * world.getTileWidth();
        int tileY = row * world.getTileHeight();

        int slopeY;
        if (tile.getDirection() == Direction.EAST) {
            slopeY = (tileY + world.getTileHeight()) - (slopeCollisionX - tileX);
        } else {
            slopeY = (tileY + world.getTileHeight()) - ((tileX + world.getTileWidth()) - slopeCollisionX);
        }

        if ((actor.getY() + actor.getHeight() + speedY) > slopeY) {
            actor.setY(slopeY - actor.getHeight());
        } else {
            actor.setY(actor.getY() + speedY);
        }
    }

    private void landOnFlatGround(int row) {
        actor.setY((row - 1) * world.getTileHeight());
    }

}
