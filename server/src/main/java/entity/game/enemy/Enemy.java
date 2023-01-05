package entity.game.enemy;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Enemy {
    protected String enemyID;
    protected String enemyName;

    protected Enemy() {
        this.enemyID = UUID.randomUUID().toString();
    }
}
