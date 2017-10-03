package no.taardal.pixelcave.command;

import no.taardal.pixelcave.actor.Actor;

public interface Command {

    void execute(Actor actor);

}
