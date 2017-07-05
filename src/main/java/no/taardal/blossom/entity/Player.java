package no.taardal.blossom.entity;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.AnimatedSprite;
import no.taardal.blossom.state.actorstate.ActorState;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Player extends Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public Player(AnimatedSprite animatedSprite, World world) {
        super(animatedSprite, world);
        direction = Direction.EAST;
        position = new Vector2d(250, 50);
    }

    public void handleInput(Keyboard keyboard) {
        ActorState actorState = this.actorState.handleInput(keyboard);
        if (actorState != null) {
            LOGGER.debug("New actor state [{}]", actorState.toString());
            actorState.onEntry();
            this.actorState = actorState;
        }
    }

}
