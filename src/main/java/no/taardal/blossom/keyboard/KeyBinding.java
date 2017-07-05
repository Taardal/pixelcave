package no.taardal.blossom.keyboard;

public enum KeyBinding {

    LEFT_MOVEMENT(Key.LEFT, Key.A),
    RIGHT_MOVEMENT(Key.RIGHT, Key.D),
    UP_MOVEMENT(Key.SPACE, Key.W),
    DOWN_MOVEMENT(Key.DOWN, Key.S);

    private Key primaryKey;
    private Key secondaryKey;

    KeyBinding(Key primaryKey, Key secondaryKey) {
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
    }

    public Key getPrimaryKey() {
        return primaryKey;
    }

    public Key getSecondaryKey() {
        return secondaryKey;
    }

}
