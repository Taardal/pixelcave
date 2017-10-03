package no.taardal.pixelcave.state.actor;

import no.taardal.pixelcave.actor.Actor;
import no.taardal.pixelcave.bounds.Bounds;
import no.taardal.pixelcave.keyboard.Keyboard;
import no.taardal.pixelcave.statemachine.StateMachine;

public abstract class ActorAttackingState<T extends Actor> extends ActorState<T> {

    protected Bounds attackBounds;
    protected boolean enemiesAttacked;

    public ActorAttackingState(T actor, StateMachine stateMachine) {
        super(actor, stateMachine);
        attackBounds = getAttackBounds();
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void nextMove(Keyboard keyboard) {

    }

    @Override
    protected void updateBounds() {

    }

    protected abstract Bounds getAttackBounds();

    protected void attackEnemiesInRange() {
        for (int i = 0; i < actor.getEnemies().size(); i++) {
            Actor enemy = actor.getEnemies().get(i);
            if (attackBounds.intersects(enemy.getBounds())) {
                enemy.onAttacked(actor);
            }
        }
    }

}
