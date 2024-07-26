import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.util.Random;

public class Snake {
    public final int x[] = new int[GamePanel.GAME_UNITS];// позиція х
    public final int y[] = new int[GamePanel.GAME_UNITS];// позиція у
    public int bodyParts = 6;// початкова кількість частин змійки
    public char direction = 'R';
    public boolean isAlive;
    public int applesEaten = 0;
    public int liveTime = 0;
    public int health = 250;
    Scaner scaner = new Scaner();
    Brain2 brain;

    public Snake(){
        Random random = new Random();
        x[0] = random.nextInt((GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE)+1)*GamePanel.UNIT_SIZE;
        y[0] = random.nextInt((GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE)+1)*GamePanel.UNIT_SIZE;
        brain = new Brain2();
        this.isAlive = true;
    }
    public Snake(float[] genotype){
        Random random = new Random();
        x[0] = random.nextInt((GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE)+1)*GamePanel.UNIT_SIZE;
        y[0] = random.nextInt((GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE)+1)*GamePanel.UNIT_SIZE;
        brain = new Brain2(genotype);
        this.isAlive = true;
    }
    public void move(Apple apple) {
        float[] scanData = scaner.scan(apple);
//        scaner.showData(scanData);

        direction = brain.chooseDirection(scanData, direction);
//        for(int i = 0; i < result.length; i++){
//            System.out.print(result[i] + "\t");
//        }
//        System.out.println(direction);
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - GamePanel.UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + GamePanel.UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - GamePanel.UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + GamePanel.UNIT_SIZE;
                break;
        }
        liveTime++;
        health--;
    }

    public void drawSnake(Graphics g) {
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
            } else {
                g.setColor(new Color(45, 180, 0));
                g.fillRect(x[i], y[i], GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
            }
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                isAlive = false;
            }
        }
        if (x[0] < 0) {
            isAlive = false;
        }
        if (x[0] >= GamePanel.SCREEN_WIDTH) {
            isAlive = false;
        }
        if (y[0] < 0) {
            isAlive = false;
        }
        if (y[0] >= GamePanel.SCREEN_HEIGHT) {
            isAlive = false;
        }
        if (health==0){
            isAlive = false;
        }
        if(!isAlive){
            GamePanel.aliveSnakes--;
        }
    }

    public boolean checkApple(Apple apple) {
        if ((x[0] == apple.x) && (y[0] == apple.y)) {
            bodyParts++;
            applesEaten++;
            health+=150;
            GamePanel.applesEatenTotal++;
            return true;
        }
        return false;
    }


    public boolean bodyContains(int X, int Y) {
        for (int i = 1; i < bodyParts; i++) {
            if ((x[i] / GamePanel.UNIT_SIZE == X) && (y[i] / GamePanel.UNIT_SIZE == Y)) {
                return true;
            }
        }
        return false;
    }

    public class Scaner {
        public float[] scan(Apple apple) {
            float[] scanData = {};
            String[] allDirections = {"Up", "UpRight", "Right", "DownRight", "Down", "DownLeft", "Left", "UpLeft"};
            for (String dirName : allDirections) {
                Directions direction = Directions.valueOf(dirName);
                scanData = ArrayUtils.addAll(scanData, scanDirection(direction.getShiftX(), direction.getShiftY(), apple));
            }
            return scanData;
        }

        public void showData(float[] array) {
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.print("{" + array.length + "}");
            System.out.println();
        }

        public float[] scanDirection(int shiftX, int shiftY, Apple apple) {
            float[] array = {scanWall(shiftX, shiftY), scanApple(shiftX, shiftY, apple), scanTail(shiftX, shiftY)};
            return array;
        }

        public float scanWall(int shiftX, int shiftY) {
            int headX = x[0] / GamePanel.UNIT_SIZE;
            int headY = y[0] / GamePanel.UNIT_SIZE;
            for (int i = 1; true; i++) {
                if (!GamePanel.Contains(headX + shiftX * i, headY + shiftY * i)) {
                    return 1f / i;
                }
            }
        }

        public float scanApple(int shiftX, int shiftY, Apple apple) {
            int headX = x[0] / GamePanel.UNIT_SIZE;
            int headY = y[0] / GamePanel.UNIT_SIZE;
            int appleX = apple.x / GamePanel.UNIT_SIZE;
            int appleY = apple.y / GamePanel.UNIT_SIZE;
            for (int i = 1; true; i++) {
                if (!GamePanel.Contains(headX + shiftX * i, headY + shiftY * i)) {
                    return 0;
                } else if ((appleX == headX + shiftX * i) && (appleY == headY + shiftY * i)) {
                    return 1f / i;
                }
            }
        }

        public float scanTail(int shiftX, int shiftY) {
            int headX = x[0] / GamePanel.UNIT_SIZE; // 4
            int headY = y[0] / GamePanel.UNIT_SIZE;// 3
            for (int i = 1; true; i++) {
                if (!GamePanel.Contains(headX + shiftX * i, headY + shiftY * i)) {
                    return 0;
                } else if (bodyContains(headX + shiftX * i, headY + shiftY * i)) {
                    return 1f / i;
                }
            }
        }
    }
}
