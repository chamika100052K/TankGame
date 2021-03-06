package slicktest;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class HandleCoinsAndHealth {

    //C:9,0:14824:1102#
    public void handleCoins(String coin_update, int cell_size) {
        String tokens[] = coin_update.replaceAll("#", "").split(":");
        int x = (Integer.parseInt(tokens[1].split(",")[0])) * cell_size;
        int y = (Integer.parseInt(tokens[1].split(",")[1])) * cell_size;
        Game.map[x / cell_size][y / cell_size] = 5;
        int time = Integer.parseInt(tokens[2]);
        int value = Integer.parseInt(tokens[3]);
        //System.out.println("coin time = " + time);

        try {
            org.newdawn.slick.Image[] coin = {new org.newdawn.slick.Image("Images/coin.png"), new org.newdawn.slick.Image("Images/coin.png")};
            Animation anime = new Animation(coin, time, true);
            Game.coins.add(new Coins(x, y, value, time, anime));
        } catch (Exception e) {
        }
    }

    public void updateCoin(int delta) {
        for (int i = 0; i < Game.coins.size(); i++) {
            Game.coins.get(i).decayTime(delta);
            //System.out.println("now coin " + i + " time = " + Game.coins.get(i).getTime());
            if (Game.coins.get(i).getTime() <= 0) {
                Game.map[Game.coins.get(i).getX() / Game.SIZE][Game.coins.get(i).getY() / Game.SIZE] = 0;
                Game.coins.remove(i);
                i--;
                /////////////////////////////////////////////////////////
            }
        }
    }

    public void detectCoinCollisions() {
        for (int i = 0; i < Game.tank_positions.length; i++) {
            int x = Game.tank_positions[i][0];
            int y = Game.tank_positions[i][1];
            for (int j = 0; j < Game.coins.size(); j++) {
                int xx = Game.coins.get(j).getX();
                int yy = Game.coins.get(j).getY();
                if (x == xx && y == yy) {
                    Game.map[x / Game.SIZE][y / Game.SIZE] = 0;
                    Game.coins.remove(j);
                }
            }
        }
    }

    //L:2,5:65117#
    public void handleLives(String coin_update, int cell_size) {
        String tokens[] = coin_update.replaceAll("#", "").split(":");
        int x = (Integer.parseInt(tokens[1].split(",")[0])) * cell_size;
        int y = (Integer.parseInt(tokens[1].split(",")[1])) * cell_size;
        int time = Integer.parseInt(tokens[2]);
        Game.map[x / cell_size][y / cell_size] = 6;
        try {
            org.newdawn.slick.Image[] coin = {new org.newdawn.slick.Image("Images/health.png"), new org.newdawn.slick.Image("Images/health.png")};
            Animation anime = new Animation(coin, time, true);
            Game.lives.add(new Life(x, y, time, anime));
        } catch (Exception e) {
        }
    }

    public void updateLife(int delta) {
        for (int i = 0; i < Game.lives.size(); i++) {
            Game.lives.get(i).decayTime(delta);
            // System.out.println("now coin " + i + " time = " + Game.lives.get(i).getTime());
            if (Game.lives.get(i).getTime() <= 0) {
                Game.map[Game.lives.get(i).getX() / Game.SIZE][Game.lives.get(i).getY() / Game.SIZE] = 0;
                Game.lives.remove(i);
                i--;
            }
        }
    }

    public void detectLifeCollisions() {
        for (int i = 0; i < Game.tank_positions.length; i++) {
            int x = Game.tank_positions[i][0];
            int y = Game.tank_positions[i][1];
            for (int j = 0; j < Game.lives.size(); j++) {
                int xx = Game.lives.get(j).getX();
                int yy = Game.lives.get(j).getY();
                if (x == xx && y == yy) {
                    Game.map[Game.lives.get(j).getX() / Game.SIZE][Game.lives.get(j).getY() / Game.SIZE] = 0;
                    Game.lives.remove(j);
                }
            }
        }
    }
}
