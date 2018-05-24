import gameLogic.Game;

public class GameLoop {

    public static void main(String[] args) throws InterruptedException{
       Game g = Game.getInstance();
       g.run();
    }
}
