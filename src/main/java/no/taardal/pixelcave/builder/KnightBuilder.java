package no.taardal.pixelcave.builder;

import no.taardal.pixelcave.actor.Knight;
import no.taardal.pixelcave.direction.Direction;
import no.taardal.pixelcave.spritesheet.SpriteSheet;
import no.taardal.pixelcave.vector.Vector2f;
import no.taardal.pixelcave.world.World;

public class KnightBuilder implements Builder<Knight> {

    private SpriteSheet spriteSheet;
    private Direction direction;
    private Vector2f position;
    private Vector2f velocity;
    private World world;

    @Override
    public Knight build() {
        return new Knight(spriteSheet, direction, velocity, position);
    }

    public KnightBuilder setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        return this;
    }

    public KnightBuilder setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public KnightBuilder setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public KnightBuilder setPosition(float x, float y) {
        this.position = new Vector2f(x, y);
        return this;
    }

    public KnightBuilder setX(float x) {
        if (position != null) {
            position = position.withX(x);
        } else {
            position = new Vector2f(x, 0);
        }
        return this;
    }

    public KnightBuilder setY(float y) {
        if (position != null) {
            position = position.withY(y);
        } else {
            position = new Vector2f(0, y);
        }
        return this;
    }

    public KnightBuilder setVelocity(Vector2f velocity) {
        this.velocity = velocity;
        return this;
    }

    public KnightBuilder setWorld(World world) {
        this.world = world;
        return this;
    }

}
