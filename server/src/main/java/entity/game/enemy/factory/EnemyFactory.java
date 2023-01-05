package entity.game.enemy.factory;

import entity.game.enemy.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EnemyFactory {

    private static final List<String> enemyTypes = new ArrayList<String>();

    static {
        try {
            File myObj = new File("D:\\JavaWorkspace\\NetworkProgramming\\src\\main\\java\\EnemyConfig.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                enemyTypes.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Enemy getEnemy(String enemyType) {
        switch (enemyType) {
            case "Rock1":
                return new Rock1();
            case "Rock2":
                return new Rock2();
            case "Rock3":
                return new Rock3();
            case "Mushroom":
                return new Mushroom();
            case "Slime":
                return new Slime();
        }
        return null;
    }

    public static Enemy getRandomEnemy() {
        Random random = new Random();
        int x = Math.abs(random.nextInt()) % enemyTypes.size();
        return getEnemy(enemyTypes.get(x));
    }
}
