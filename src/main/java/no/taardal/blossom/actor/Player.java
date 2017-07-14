package no.taardal.blossom.actor;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.state.actorstate.PlayerFallingState;
import no.taardal.blossom.state.actorstate.PlayerState;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Player extends Actor {

    public static final SpriteSheet SPRITE_SHEET = new SpriteSheetBuilder().directory("scorpion").fileName("scorpion-black-sheet-x1.png").spriteWidth(16).spriteHeight(16).build();

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public Player(World world) {
        super(world);
        position = new Vector2d(250, 50);
        velocity = Vector2d.zero();
        direction = Direction.EAST;
        pushState(new PlayerFallingState(this, world));
    }

    public void handleInput(Keyboard keyboard) {
        if (!states.isEmpty()) {
            PlayerState playerState = (PlayerState) states.getFirst();
            playerState.handleInput(keyboard);
        }
    }

}
