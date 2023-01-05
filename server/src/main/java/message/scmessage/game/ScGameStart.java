package message.scmessage.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import entity.game.Game;
import entity.game.Location;
import entity.user.User;
import message.scmessage.ScMessage;
import properties.Config;

import java.util.ArrayList;
import java.util.List;

public class ScGameStart extends ScMessage {
    @JsonIgnore
    public Game game;
    public int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long gameTime = Config.GAME_LENGTH;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PlayerInitState> playerInitStateList = new ArrayList<PlayerInitState>();

    public ScGameStart() {
        this.statusCode = 403;
        this.gameTime = null;
        this.playerInitStateList = null;
    }

    public ScGameStart(Game game) {
        this.statusCode = 200;
        this.game = game;
        List<User> playerList = game.getPlayerList();
        for (User player : playerList) {
            Location location = game.getPlayerState().get(player);
            long initX = Math.round(location.getX());
            long initY = Math.round(location.getY());
            PlayerInitState playerState = new PlayerInitState(player.getUserID().toString(), initX, initY);
            playerInitStateList.add(playerState);
        }
    }

    public class PlayerInitState {
        public String id;
        public long locationX;
        public long locationY;

        PlayerInitState(String id, long x, long y) {
            this.id = id;
            this.locationX = x;
            this.locationY = y;
        }
    }
}
