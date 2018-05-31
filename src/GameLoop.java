import gameLogic.Game;

public class GameLoop {

    public static void main(String[] args) {
       Game g = Game.getInstance();
       g.run();
    }
}
