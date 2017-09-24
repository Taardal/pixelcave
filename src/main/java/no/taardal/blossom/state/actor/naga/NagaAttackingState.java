package no.taardal.blossom.state.actor.naga;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.bounds.Bounds;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.state.actor.ActorAttackingState;
import no.taardal.blossom.state.actor.EnemyState;

public class NagaAttackingState extends ActorAttackingState<Naga> implements EnemyState {

    public NagaAttackingState(Naga actor) {
        super(actor);
    }

    @Override
    public void update(double secondsSinceLastUpdate) {
        getAnimation().update();
        if (getAnimation().isFinished()) {
            actor.popState();
        } else if (!enemiesAttacked && getAnimation().getFrame() == 3) {
            attackEnemiesInRange();
            enemiesAttacked = true;
        }
    }

    @Override
    public void nextMove(Player player) {
        if (actor.getPosition().getX() < player.getPosition().getX()) {
            actor.setDirection(Direction.EAST);
        } else if (actor.getPosition().getX() > player.getPosition().getX()) {
            actor.setDirection(Direction.WEST);
        }
    }

    protected Bounds getAttackBounds() {
        Bounds bounds = new Bounds();
        bounds.setWidth(20);
        bounds.setHeight(actor.getBounds().getHeight() / 2 + 5);
        bounds.setX(getAttackBoundsX());
        bounds.setY(actor.getBounds().getY());
        return bounds;
    }

    private int getAttackBoundsX() {
        if (actor.getDirection() == Direction.EAST) {
            return (actor.getBounds().getX() + (actor.getBounds().getWidth() / 2));
        } else {
            return actor.getBounds().getX() - 10;
        }
    }

}
