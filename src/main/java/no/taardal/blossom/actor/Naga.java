package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.enemystate.NagaHurtState;
import no.taardal.blossom.actorstate.enemystate.NagaIdleState;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Naga extends Actor {

    public static final SpriteSheet SPRITE_SHEET = new SpriteSheetBuilder()
            .directory("naga")
            .fileName("naga-female-sheet-violet.png")
            .spriteWidth(60)
            .spriteHeight(60)
            .build();

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public Naga(World world) {
        super(world);
        velocity = Vector2d.zero();
        direction = Direction.WEST;
        position = new Vector2d(250, 133);
        health = 50;
        damage = 20;
        pushState(new NagaIdleState(this, world));
    }

    @Override
    public int getWidth() {
        return SPRITE_SHEET.getSpriteWidth();
    }

    @Override
    public int getHeight() {
        return SPRITE_SHEET.getSpriteHeight();
    }

    @Override
    public void onAttacked(Actor attacker) {
        pushState(new NagaHurtState(this, attacker));
    }

    @Override
    public void draw(Camera camera) {
        if (direction == Direction.EAST) {
            getAnimation().drawFlippedHorizontally(this, camera);
        } else {
            super.draw(camera);
        }
        if (getBounds() != null) {
            camera.drawRectangle(getBounds(), Color.RED);
        }
    }

    @Override
    public String toString() {
        return "Naga{" +
                "position=" + position +
                ", velocity=" + velocity +
                ", direction=" + direction +
                ", states=" + states +
                ", health=" + health +
                ", damage=" + damage +
                ", dead=" + dead +
                '}';
    }
}
