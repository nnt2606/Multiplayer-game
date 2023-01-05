package message.scmessage.game;

import entity.game.enemy.Enemy;
import lombok.AllArgsConstructor;
import message.scmessage.ScMessage;

@AllArgsConstructor
public class ScSpawnEnemy extends ScMessage {
    public String enemyID;
    public String enemyName;
    public int positionX;
    public int positionY;

    public ScSpawnEnemy(Enemy enemy, int initX, int initY) {
        this.enemyID = enemy.getEnemyID();
        this.enemyName = enemy.getEnemyName();
        this.positionX = initX;
        this.positionY = initY;
    }
}
