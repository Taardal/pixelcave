package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.keyboard.Key;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.layer.TiledEditorLayer;
import no.taardal.blossom.layer.TiledEditorLayerType;
import no.taardal.blossom.map.TiledEditorMap;
import no.taardal.blossom.tile.Tile;

public class WalkingActorState implements ActorState {

    private Actor actor;
    private TiledEditorMap tiledEditorMap;

    public WalkingActorState(Actor actor, TiledEditorMap tiledEditorMap) {
        this.actor = actor;
        this.tiledEditorMap = tiledEditorMap;
    }

    @Override
    public String toString() {
        return "WalkingActorState{}";
    }

    @Override
    public ActorState handleInput(Keyboard keyboard) {
        if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A) || keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
            if (keyboard.isPressed(Key.LEFT) || keyboard.isPressed(Key.A)) {
                actor.setDirection(Direction.WEST);
            } else if (keyboard.isPressed(Key.RIGHT) || keyboard.isPressed(Key.D)) {
                actor.setDirection(Direction.EAST);
            }
            return null;
        } else {
            return new IdleActorState(actor, tiledEditorMap);
        }
    }

    @Override
    public ActorState update() {
        move();
        return null;
    }

    private boolean isTileLayer(TiledEditorLayer tiledEditorLayer) {
        return tiledEditorLayer.getTiledEditorLayerType() == TiledEditorLayerType.TILELAYER;
    }

    public void move() {
        moveX();
        moveY();
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
        return tiledEditorMap.getWidth() * tiledEditorMap.getTileWidth() - actor.getWidth();
    }

    private boolean isPassedMaxX() {
        return actor.getX() + actor.getWidth() > tiledEditorMap.getWidth() * tiledEditorMap.getTileWidth();
    }

    private void moveY() {
        for (int i = 0; i < tiledEditorMap.getTiledEditorLayers().size(); i++) {
            TiledEditorLayer tiledEditorLayer = tiledEditorMap.getTiledEditorLayers().get(i);
            if (isTileLayer(tiledEditorLayer) && tiledEditorLayer.isVisible() && tiledEditorLayer.getName().equals("environment_layer")) {

                int slopeCollisionX = actor.getX() + (actor.getWidth() / 2);
                int column = slopeCollisionX / tiledEditorMap.getTileWidth();
                int topRow = actor.getY() / tiledEditorMap.getTileHeight();
                int bottomRow = (actor.getY() + actor.getHeight()) / tiledEditorMap.getTileHeight();

                Tile tile = getTile(column, topRow, tiledEditorLayer);
                Tile tileBelowPlayer = getTile(column, bottomRow, tiledEditorLayer);

                if (isStandingInSlope(tile, tileBelowPlayer)) {
                    Direction slopeMovementDirection = getSlopeMovementDirection(tile, tileBelowPlayer);
                    if (slopeMovementDirection != null) {

                        int tileX = column * tiledEditorMap.getTileWidth();
                        int tileY = topRow * tiledEditorMap.getTileHeight();
                        if (tile == null || !tile.isSlope()) {
                            tileY = bottomRow * tiledEditorMap.getTileHeight();
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
                            y1 = tileY - ((tileX + tiledEditorMap.getTileWidth()) - slopeCollisionX);
                        } else if (slopeMovementDirection == Direction.SOUTH_WEST) {
                            y1 = tileY + (tiledEditorMap.getTileHeight() - (slopeCollisionX - tileX)) - actor.getHeight();
                        }

                        if (y1 != 0) {
                            actor.setY(y1);
                        }
                    }

                }
            }
        }
    }

    private Tile getTile(int column, int row, TiledEditorLayer tiledEditorLayer) {
        int tileId = tiledEditorLayer.getData2D()[column][row];
        return tiledEditorMap.getTiles().get(tileId);
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
