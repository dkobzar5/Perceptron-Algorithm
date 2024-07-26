import com.sun.javaws.IconUtil;

import java.awt.*;
import java.util.Random;

public class Apple {
    public int x;// координати яблука по Х
    public int y;// координати яблука по У

    public void drawApple(Graphics g) {
        g.setColor(Color.red);// встановлює колір на червоний
        g.fillOval(x, y, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);// малює яблуко
    }

    public Apple(Snake snake) {
        while(true) {
            int[] coorditates = generateCoordinates();
            boolean regenerate = false;
            for (int i = 0; i < snake.bodyParts; i++) {
                if (coorditates[0] == snake.x[i] && coorditates[1] == snake.y[i]) {
                    System.out.println(Integer.toString(snake.x[i]) + " " + Integer.toString(snake.y[i])+ " " + Integer.toString(coorditates[0]) + " " + Integer.toString(coorditates[1]));
                    regenerate = true;
                    break;
                }
            }
            if(!regenerate){
                this.x = coorditates[0];
                this.y = coorditates[1];
                break;
            }
        }
    }
    public int[] generateCoordinates(){
        Random random = new Random();
        int tX = random.nextInt((int) (GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
        int tY = random.nextInt((int) (GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
        int[] result =  {tX, tY};
        return result;
    }

}
