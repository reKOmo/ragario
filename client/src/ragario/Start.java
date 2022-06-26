package ragario;

public class Start {
    public static void main(String[] args) {
        MainGame game = new MainGame();
        game.init();
        try {
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
