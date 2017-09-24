package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.bounds.Bounds;
import no.taardal.blossom.sprite.Animation;

public abstract class ActorAttackingState<T extends Actor> implements ActorState {

    protected T actor;
    protected Bounds attackBounds;
    protected boolean enemiesAttacked;

    public ActorAttackingState(T actor) {
        this.actor = actor;
        attackBounds = getAttackBounds();
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("ATTACKING");
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void update(double secondsSinceLastUpdate) {

    }

    @Override
    public void onExit() {
        getAnimation().reset();
        enemiesAttacked = false;
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
