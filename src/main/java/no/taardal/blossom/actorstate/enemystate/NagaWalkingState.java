package no.taardal.blossom.actorstate.enemystate;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.actorstate.ActorWalkingState;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.Animation;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class NagaWalkingState extends ActorWalkingState<Naga> implements EnemyState {

    private static final Logger LOGGER = LoggerFactory.getLogger(NagaWalkingState.class);
    private static final Animation WALKING_ANIMATION = getWalkingAnimation();
    private static final Rectangle BOUNDS = new Rectangle(20, 34);
    private static final int MAX_DISTANCE_SAME_DIRECTION = 100;

    private Vector2d distanceWalkedInSameDirection;

    public NagaWalkingState(Naga actor, World world) {
        super(actor, world);
        distanceWalkedInSameDirection = Vector2d.zero();
    }

    @Override
    public Animation getAnimation() {
        return WALKING_ANIMATION;
    }

    @Override
    public Rectangle getBounds() {
        return BOUNDS;
    }

    @Override
    public void nextMove(Player player) {
        int radius = 50;
        double length = Vector2d.getLength(actor.getPosition(), player.getPosition());
        if (length <= radius) {
            actor.pushState(new NagaAttackingState(actor, world));
        } else if (Math.abs(distanceWalkedInSameDirection.getX()) > MAX_DISTANCE_SAME_DIRECTION) {
            if (actor.getDirection() == Direction.WEST) {
                actor.setDirection(Direction.EAST);
                actor.setVelocity(getVelocity());
            } else {
                actor.setDirection(Direction.WEST);
                actor.setVelocity(getVelocity());
            }
            distanceWalkedInSameDirection = Vector2d.zero();
        } else {
            if (actor.getDirection() == Direction.WEST && actor.getVelocity().getX() > 0) {
                actor.setDirection(Direction.EAST);
            } else if (actor.getDirection() == Direction.EAST && actor.getVelocity().getX() < 0) {
                actor.setDirection(Direction.WEST);
            }
            distanceWalkedInSameDirection = distanceWalkedInSameDirection.add(distanceWalked);
        }
    }

    @Override
    protected void updateBounds() {
        int boundsX = actor.getX() + 22;
        int boundsY = (actor.getY() + actor.getHeight()) - (int) BOUNDS.getHeight();
        BOUNDS.setLocation(boundsX, boundsY);
    }

    private static Animation getWalkingAnimation() {
        Sprite[] sprites = new Sprite[7];
        for (int i = 0; i < sprites.length; i++) {
            //sprites[i] = Naga.SPRITE_SHEET.getSprites()[i][1];
        }
        return new Animation(sprites);
    }

}
