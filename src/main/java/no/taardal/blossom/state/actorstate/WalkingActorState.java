package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.KeyBinding;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.Layer;
import no.taardal.blossom.layer.LayerType;
import no.taardal.blossom.world.World;
import no.taardal.blossom.tile.Tile;

public class WalkingActorState implements ActorState {

    private Actor actor;
    private World world;
    private Layer environmentLayer;

    public WalkingActorState(Actor actor, World world) {
        this.actor = actor;
        this.world = world;
        environmentLayer = getEnvironmentLayer(world);
    }

    @Override
    public String toString() {
        return "WalkingActorState{}";
    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT) || keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
            if (keyboard.isPressed(KeyBinding.LEFT_MOVEMENT)) {
                actor.setDirection(Direction.WEST);
            } else if (keyboard.isPressed(KeyBinding.RIGHT_MOVEMENT)) {
                actor.setDirection(Direction.EAST);
            }
            return null;
        } else {
            return new IdleActorState(actor, world);
        }
    }

    @Override
    public ActorState update() {
        moveX();
        moveY();
        return null;
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

    private void moveX() {
        if (actor.getDirection() == Direction.WEST) {
            actor.setX(actor.getX() - actor.getSpeedX());
            if (actor.getX() < 0) {
                actor.setX(0);
            }
        } else if (actor.getDirection() == Direction.EAST) {
            actor.setX(actor.getX() + actor.getSpeedX());
            if (isPassedMaxX()) {
                actor.setX(getMaxX());
            }
        }
    }

    private int getMaxX() {
        return world.getWidth() * world.getTileWidth() - actor.getWidth();
    }

    private boolean isPassedMaxX() {
        return actor.getX() + actor.getWidth() > world.getWidth() * world.getTileWidth();
    }

    private void moveY() {
        int slopeCollisionX = actor.getX() + (actor.getWidth() / 2);
        int column = slopeCollisionX / world.getTileWidth();
        int topRow = actor.getY() / world.getTileHeight();
        int bottomRow = (actor.getY() + actor.getHeight()) / world.getTileHeight();

        Tile tile = getTile(column, topRow);
        Tile tileBelowPlayer = getTile(column, bottomRow);

        if (isStandingInSlope(tile, tileBelowPlayer)) {
            Direction slopeMovementDirection = getSlopeMovementDirection(tile, tileBelowPlayer);
            if (slopeMovementDirection != null) {

                int tileX = column * world.getTileWidth();
                int tileY = topRow * world.getTileHeight();
                if (tile == null || !tile.isSlope()) {
                    tileY = bottomRow * world.getTileHeight();
                }

                /*
                When moving SOUTH in a slope, the new Y-coordinate will be at the players feet.
                This is compensated by subtracting the players height so that it will be at the players head.

                When moving EAST in a slope, the last step in X direction is equal to the X-coordinate for the next tile.
                This means the last step in the Y-direction is "missing" because when you step to that X-coordinate we get the next tile (next column), which is not a slope tile.
                Since the next tile is not a slope tile, we do not calculate a new Y, and we "lose" the step in the Y-direction.
                This is compensated by manually adding or subtracting the missing step, depending on the direction.
                */

                int missingSteps = 1;

                int y1 = 0;
                if (slopeMovementDirection == Direction.NORTH_EAST) {
                    y1 = tileY - (slopeCollisionX - tileX) - missingSteps;
                } else if (slopeMovementDirection == Direction.SOUTH_EAST) {
                    y1 = tileY + (slopeCollisionX - tileX) + missingSteps - actor.getHeight();
                } else if (slopeMovementDirection == Direction.NORTH_WEST) {
                    y1 = tileY - ((tileX + world.getTileWidth()) - slopeCollisionX);
                } else if (slopeMovementDirection == Direction.SOUTH_WEST) {
                    y1 = tileY + (world.getTileHeight() - (slopeCollisionX - tileX)) - actor.getHeight();
                }

                if (y1 != 0) {
                    actor.setY(y1);
                }
            }

        }

    }

    private Tile getTile(int column, int row) {
        int tileId = environmentLayer.getTileGrid()[column][row];
        return world.getTiles().get(tileId);
    }

    private boolean isStandingInSlope(Tile tile, Tile tileBelow) {
        return (tile != null && tile.isSlope()) || (tileBelow != null && tileBelow.isSlope());
    }

    private Direction getSlopeMovementDirection(Tile tile, Tile tileBelow) {
        if (actor.getDirection() == Direction.EAST && (isSlopeDirection(Direction.EAST, tile) || isSlopeDirection(Direction.EAST, tileBelow))) {
            return Direction.NORTH_EAST;
        } else if (actor.getDirection() == Direction.EAST && (isSlopeDirection(Direction.WEST, tile) || isSlopeDirection(Direction.WEST, tileBelow))) {
            return Direction.SOUTH_EAST;
        } else if (actor.getDirection() == Direction.WEST && (isSlopeDirection(Direction.EAST, tile) || isSlopeDirection(Direction.EAST, tileBelow))) {
            return Direction.SOUTH_WEST;
        } else if (actor.getDirection() == Direction.WEST && (isSlopeDirection(Direction.WEST, tile) || isSlopeDirection(Direction.WEST, tileBelow))) {
            return Direction.NORTH_WEST;
        } else {
            return null;
        }
    }

    private boolean isSlopeDirection(Direction direction, Tile tile) {
        return tile != null && tile.isSlope() && tile.getDirection() != null && tile.getDirection() == direction;
    }

}
