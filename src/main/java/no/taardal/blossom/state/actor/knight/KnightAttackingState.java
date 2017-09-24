package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.bounds.Bounds;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.state.actor.ActorAttackingState;
import no.taardal.blossom.state.actor.PlayerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightAttackingState extends ActorAttackingState<Knight> implements PlayerState {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightAttackingState.class);

    public KnightAttackingState(Knight actor) {
        super(actor);
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            actor.popState();
        } else if (!enemiesAttacked && getAnimation().getFrame() == 4) {
            attackEnemiesInRange();
            enemiesAttacked = true;
        }
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    protected Bounds getAttackBounds() {
        Bounds bounds = new Bounds();
        bounds.setWidth(actor.getAttackRange());
        bounds.setHeight(actor.getBounds().getHeight() / 2 + 5);
        bounds.setX(getAttackBoundsX());
        bounds.setY(getAttackBoundsY());
        return bounds;
    }

    private int getAttackBoundsX() {
        if (actor.getDirection() == Direction.EAST) {
            return actor.getBounds().getX() + (actor.getBounds().getWidth() / 2);
        } else {
            return actor.getBounds().getX() + (actor.getBounds().getWidth() / 2) - actor.getAttackRange();
        }
    }

    private int getAttackBoundsY() {
        return actor.getBounds().getY();
    }

}
