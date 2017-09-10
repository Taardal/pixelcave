package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.enemystate.NagaHurtState;
import no.taardal.blossom.actorstate.enemystate.NagaIdleState;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Naga extends Enemy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public enum Variation {
        VIOLET,
        MAGMA
    }

    public Naga(SpriteSheet spriteSheet) {
        super(spriteSheet);
    }

    public Naga(World world, SpriteSheet spriteSheet) {
        super(world, spriteSheet);
        pushState(new NagaIdleState(this, world));
    }

    @Override
    public void onAttacked(Actor attacker) {
        pushState(new NagaHurtState(this, attacker));
    }

    @Override
    public void draw(Camera camera) {
        if (direction == Direction.EAST) {
            getCurrentAnimation().drawFlippedHorizontally(this, camera);
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
