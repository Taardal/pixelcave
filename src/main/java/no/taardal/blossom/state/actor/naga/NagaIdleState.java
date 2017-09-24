package no.taardal.blossom.state.actor.naga;

import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.state.actor.ActorIdleState;
import no.taardal.blossom.state.actor.EnemyState;
import no.taardal.blossom.vector.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NagaIdleState extends ActorIdleState<Naga> implements EnemyState {

    private static final Logger LOGGER = LoggerFactory.getLogger(NagaIdleState.class);

    public NagaIdleState(Naga actor) {
        super(actor);
    }

    @Override
    public void nextMove(Player player) {
        int radius = 20;
        double length = Vector2d.getLength(actor.getPosition(), actor.getPosition());
        if (length <= radius) {
            actor.pushState(new NagaAttackingState(actor));
        } else {
            actor.changeState(new NagaRunningState(actor));
        }
    }

    @Override
    protected void updateBounds() {

    }
}
