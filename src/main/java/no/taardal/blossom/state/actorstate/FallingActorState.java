package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.tile.Tile;
import no.taardal.blossom.vector.Vector2i;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallingActorState implements ActorState {

    private static final Logger LOGGER = LoggerFactory.getLogger(FallingActorState.class);
    private static final Vector2i velocity = new Vector2i(0, 200);

    private Actor actor;
    private World world;
    private int speedY;

    public FallingActorState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
        speedY = 1;
    }

    @Override
    public String toString() {
        return "FallingActorState{}";
    }

    @Override
    public void onEntry() {
        actor.setFalling(true);
    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        return null;
    }

    @Override
    public ActorState update(double timeSinceLastUpdate) {
        if (actor.isFalling()) {
            fall(timeSinceLastUpdate);
            return null;
        } else {
            return new IdleActorState(actor, world);
        }
    }

    private void fall(double timeSinceLastUpdate) {
        int column = actor.getX() / world.getTileWidth();
        int row = (actor.getY() + actor.getHeight() + speedY) / world.getTileHeight();
        int tileId = world.getLayers().get("main").getTileGrid()[column][row];

        if (tileId != World.NO_TILE_ID) {
            Tile tile = world.getTiles().get(tileId);
            if (tile.isSlope()) {

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
                    int x = actor.getPosition().getX();
                    int y = slopeY - actor.getHeight();
                    actor.setPosition(new Vector2i(x, y));
                    actor.setFalling(false);
                } else {
                    Vector2i direction = velocity.multiply(timeSinceLastUpdate);
                    actor.getPosition().addEq(direction);
                }

            } else {
                int x = actor.getPosition().getX();
                int y = (row - 1) * world.getTileHeight();
                actor.setPosition(new Vector2i(x, y));
                actor.setFalling(false);
            }
        } else {
            Vector2i direction = velocity.multiply(timeSinceLastUpdate);
            actor.getPosition().addEq(direction);
        }
    }

}
