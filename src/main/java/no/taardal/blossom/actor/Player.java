package no.taardal.blossom.actor;

import no.taardal.blossom.actorstate.playerstate.PlayerFallingState;
import no.taardal.blossom.actorstate.playerstate.PlayerHurtState;
import no.taardal.blossom.actorstate.playerstate.PlayerState;
import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

public class Player extends Actor {

    public static final SpriteSheet SPRITE_SHEET = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    private List<Enemy> enemies;

    public Player(World world) {
        super(world, null);
        position = new Vector2d(0, 133);
        velocity = Vector2d.zero();
        direction = Direction.EAST;
        health = 100;
        damage = 50;
        attackRange = 20;
        movementSpeed = 100;
        pushState(new PlayerFallingState(this, world));
    }

    public Player(World world, List<Enemy> enemies) {
        this(world);
        this.enemies = enemies;
    }

    @Override
    public int getWidth() {
        return SPRITE_SHEET.getSpriteWidth();
    }

    @Override
    public int getHeight() {
        return SPRITE_SHEET.getSpriteHeight();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void onAttacked(Actor attacker) {
        pushState(new PlayerHurtState(this, attacker));
    }

    @Override
    public void draw(Camera camera) {
        if (direction == Direction.WEST) {
            getCurrentAnimation().drawFlippedHorizontally(this, camera);
        } else {
            super.draw(camera);
        }
        if (getBounds() != null) {
            camera.drawRectangle(getBounds(), Color.RED);
        }
    }

    public void handleInput(Keyboard keyboard) {
        if (!states.isEmpty()) {
            PlayerState playerState = (PlayerState) states.getFirst();
            playerState.handleInput(keyboard);
        }
    }

}
