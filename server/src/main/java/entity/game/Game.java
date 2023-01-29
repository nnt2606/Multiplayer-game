package entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.game.enemy.Enemy;
import entity.game.enemy.factory.EnemyFactory;
import entity.game.item.Item;
import entity.game.item.factory.ItemFactory;
import entity.room.Room;
import entity.user.User;
import exceptions.game.InvalidItemObtainedException;
import exceptions.game.NotEnoughPlayerException;
import lombok.Getter;
import message.csmessage.game.CsCharacterState;
import message.csmessage.game.CsUserObtainItem;
import message.scmessage.ScMessage;
import message.scmessage.game.*;
import properties.Config;
import utility.GameTimer;

import java.util.*;

@Getter
public class Game {
    private UUID gameID;
    private long gameLength = Config.GAME_LENGTH;
    @JsonIgnore
    private List<User> playerList = new ArrayList<User>();
    private Map<User, Location> playerState = new HashMap<User, Location>();
    private Map<Item, Location> itemLocationMap = new HashMap<Item, Location>();
    private Map<Enemy, Location> enemyLocationMap = new HashMap<Enemy, Location>();

    public Game(Room room) throws NotEnoughPlayerException {
//        if (room.getNumberOfMembers() < Config.MINIMUM_NUMBER_OF_PLAYERS) throw new NotEnoughPlayerException();
        gameID = UUID.randomUUID();
        playerList = room.getRoomMembers();
        assignStartPlace();
        assignInitialItems();
        assignInitialEnemies();
        room.forwardMessage(new ScGameStart(this));
        room.close();
        for (User player : playerList) {
            player.joinGame(this);
            player.getSession().leaveRoom();
        }
        GameTimer timer = new GameTimer(this);
        timer.start();
    }

    public void updatePlayerState(CsCharacterState characterState) {
        ScCharacterState scCharacterState = new ScCharacterState(characterState);
        forwardUDPMessage(scCharacterState);
    }

    public synchronized void userObtainItem(CsUserObtainItem userObtainItem, User user) throws InvalidItemObtainedException {
        String itemID = userObtainItem.getItemID();
        for (Map.Entry<Item, Location> entry : itemLocationMap.entrySet()) {
            if (entry.getKey().getItemID().equals(itemID)) {
                itemLocationMap.remove(entry.getKey());
                Random random = new Random();
                int x = Math.abs(random.nextInt()) % 17 + 2;
                int y = Math.abs(random.nextInt()) % 17 + 2;
                Location loc = new Location(x, y);
                while (enemyLocationMap.containsValue(loc)) {
                    x = Math.abs(random.nextInt()) % 17 + 2;
                    y = Math.abs(random.nextInt()) % 17 + 2;
                    loc = new Location(x, y);
                }
                Item item = ItemFactory.getItem(entry.getKey().getItemName());
                itemLocationMap.put(item, loc);
                ScUserObtainItem scUserObtainItem = new ScUserObtainItem(entry.getKey(), user.getUserID().toString());
                forwardTCPMessage(scUserObtainItem);
                ScSpawnItem scSpawnItem = new ScSpawnItem(item, (int) loc.getX(), (int) loc.getY());
                forwardTCPMessage(scSpawnItem);
                // forwardUDPMessage(scSpawnItem);
                return;
            }
        }

        throw new InvalidItemObtainedException();
    }

    public synchronized void endGame() {
        ScGameEnd scGameEnd = new ScGameEnd();
        for (User player : playerList) {
            player.forwardMessage(scGameEnd);
        }
    }

    private synchronized void assignInitialItems() {
        for (int i = 0; i < Config.NUMBER_OF_FRUITS; i++) {
            Random random = new Random();
            int x = Math.abs(random.nextInt()) % 17 + 2;
            int y = Math.abs(random.nextInt()) % 17 + 2;
            itemLocationMap.put(ItemFactory.getItem("Apple"), new Location(x, y));
        }
    }

    private synchronized void assignInitialEnemies() {
        for (int i = 0; i < Config.NUMBER_OF_ENEMIES; i++) {
            Random random = new Random();
            int x = Math.abs(random.nextInt()) % 17 + 2;
            int y = Math.abs(random.nextInt()) % 17 + 2;
            Location loc = new Location(x, y);
            while (itemLocationMap.containsValue(loc)) {
                x = Math.abs(random.nextInt()) % 17 + 2;
                y = Math.abs(random.nextInt()) % 17 + 2;
                loc = new Location(x, y);
            }
            enemyLocationMap.put(EnemyFactory.getRandomEnemy(), loc);
        }
    }

    private synchronized void assignStartPlace() {
        for (User player : playerList) {
            Random random = new Random();
            int x = Math.abs(random.nextInt()) % 17 + 2;
            int y = Math.abs(random.nextInt()) % 17 + 2;
            playerState.put(player, new Location(x, y));
        }
    }

    private void forwardTCPMessage(ScMessage message) {
        for (User player : playerList) {
            player.forwardMessage(message);
        }
    }

    private void forwardUDPMessage(ScMessage message) {
        for (User player : playerList) {
            player.forwardUDPMessage(message);
        }
    }

    public void sendInitialItemState() {
        for (Map.Entry<Item, Location> entry : itemLocationMap.entrySet()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int initX = (int) (Math.round(entry.getValue().getX()));
            int initY = (int) (Math.round(entry.getValue().getY()));
            ScSpawnItem scSpawnItem = new ScSpawnItem(entry.getKey(), initX, initY);
            forwardTCPMessage(scSpawnItem);
            // forwardUDPMessage(scSpawnItem);
        }
    }

    public void sendInitialEnemyState() {
        for (Map.Entry<Enemy, Location> entry : enemyLocationMap.entrySet()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int initX = (int) (Math.round(entry.getValue().getX()));
            int initY = (int) (Math.round(entry.getValue().getY()));
            ScSpawnEnemy scSpawnEnemy = new ScSpawnEnemy(entry.getKey(), initX, initY);
            forwardTCPMessage(scSpawnEnemy);
            // forwardUDPMessage(scSpawnEnemy);
        }
    }

    public void removeUser(User user) {
        this.playerList.remove(user);
        this.playerState.remove(user);
    }
}
