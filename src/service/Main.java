package service;

import game.Arena;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        PlayersPreparer.getPreparer();
       /* Thread game=new Thread(new Game());
        game.start();*/

        Arena.getInstance().startGame();

    }
}
