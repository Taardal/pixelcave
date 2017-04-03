package no.taardal.blossom.entity;

import no.taardal.blossom.resourceloader.BufferedImageResourceLoader;
import no.taardal.blossom.service.SpriteSheetService;
import no.taardal.blossom.sprite.Sprite;
import no.taardal.blossom.sprite.SpriteSheet;

public class Scorpion {

//    private AnimatedSprite idle;
//    private AnimatedSprite takingDamage;
//    private AnimatedSprite walk;
//    private AnimatedSprite attack;
//    private AnimatedSprite death;
    private String spriteSheetPath;

    public Scorpion() {
        SpriteSheetService spriteSheetService = new SpriteSheetService(new BufferedImageResourceLoader());
        SpriteSheet scorpionSpriteSheet = spriteSheetService.get("scorpion/scorpion-black-sheet-x1.png");
        Sprite[][] sprites2D = scorpionSpriteSheet.getSprites2D();

//        idle = new AnimatedSprite(sprites2D[0], 6);
//        walk = new AnimatedSprite(sprites2D[1], 4);
//        attack = new AnimatedSprite(sprites2D[2], 5);
//        takingDamage = new AnimatedSprite(sprites2D[3], 3);
//        death = new AnimatedSprite(sprites2D[4], 7);
    }
}
