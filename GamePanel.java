import sun.net.www.content.text.Generic;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;// розмір екрану(ширина)
    static final int SCREEN_HEIGHT = 800;// розмір екрану(висота)
    static final int UNIT_SIZE = 20;// розмір однієї клітинки
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;// загальна кількість клітинок
    static final int DELAY = 20;// затримка для Timer
    static int applesEatenTotal;// кількість з'їдених яблук
    boolean running = false;// чи триває гра(початково ні, але в стартГейм методі змінимо)
    static final int SNAKES_COUNT = 100;
    Timer timer;// забезпечує можливість планування виконання коду через певний проміжок часу
    ArrayList<Object> list = new ArrayList<>();
    public static int aliveSnakes;
    public int generation = 1;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));//встановлює розмір панелі
        this.setBackground(Color.black);//змінює колір панелі
//        this.setFocusable(true);// щоб компонент міг реагувати на вхідні події, пов'язані з клавіатурою.
//        this.addKeyListener(new MyKeyAdapter());//  реагувати на натискання клавіш
        startGame();// починає гру
    }

    public void startGame() {
        list.clear();
        for(int i = 0; i < SNAKES_COUNT; i++){
            Snake snake = new Snake();
            Apple apple = new Apple(snake);
            ArrayList<Object> apple_snake = new ArrayList<>();
            apple_snake.add(snake);
            apple_snake.add(apple);
            list.add(apple_snake);
        }
        aliveSnakes = SNAKES_COUNT;
        running = true;// гра почалася
        timer = new Timer(DELAY, this);//приймає два параметри: затримку (delay) та об'єкт, який реалізує інтерфейс ActionListener
        timer.start();// починає таймер та викликає метод actionPerformed()
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // малює сітку для кращого розуміння(можна прибрати)
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            for(int i = 0; i < SNAKES_COUNT; i++){
                Snake snake = (Snake)(((ArrayList)list.get(i)).get(0));
                Apple apple = (Apple)(((ArrayList)list.get(i)).get(1));
                if(snake.isAlive) {
                    apple.drawApple(g);

                    snake.drawSnake(g);
                }
            }

            showScore(g);
            showGeneration(g);
        }
//        else {
//            gameOver(g);// закінчує гру, якщо running == false
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (aliveSnakes > 0) {
            for(int i = 0; i < SNAKES_COUNT; i++) {
                Snake snake = (Snake) (((ArrayList) list.get(i)).get(0));
                Apple apple = (Apple) (((ArrayList) list.get(i)).get(1));
                if (snake.isAlive) {
                    snake.move(apple);
                    if (snake.checkApple(apple)) {
//                        System.out.println(list.get(i) + " " + apple.x + " " + apple.y);
                        apple = new Apple(snake);
                        ((ArrayList)list.get(i)).set(1, apple);
                    }
                    snake.checkCollisions();
                }
            }

        }else{
            timer.stop();
            running = false;
            createNextGeneration();
        }
        repaint();// причиняє виклик методу paintComponent()
    }
    public void createNextGeneration(){
        float[][] childrensGenotype= NewGeneration.getChildrensGenotype(list);
        list.clear();
        for(int i = 0; i < SNAKES_COUNT; i++){
            Snake snake = new Snake(childrensGenotype[i]);
            Apple apple = new Apple(snake);
            ArrayList<Object> apple_snake = new ArrayList<>();
            apple_snake.add(snake);
            apple_snake.add(apple);
            list.add(apple_snake);
        }
        aliveSnakes = SNAKES_COUNT;
        applesEatenTotal = 0;
        generation++;
        running = true;// гра почалася
        timer = new Timer(DELAY, this);//приймає два параметри: затримку (delay) та об'єкт, який реалізує інтерфейс ActionListener
        timer.start();// починає таймер та викликає метод actionPerformed()
    }

    public void showScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEatenTotal, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEatenTotal)) / 2, g.getFont().getSize());
    }
    public void showGeneration(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Generation: " + generation, (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT - 50);
    }

//    public void gameOver(Graphics g) {
//        g.setColor(Color.red);
//        g.setFont(new Font("Ink Free", Font.BOLD, 75));
//        FontMetrics metrics1 = getFontMetrics(g.getFont());
//        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
//
//        showScore(g);
//    }

//    public class MyKeyAdapter extends KeyAdapter {
//        @Override
//        public void keyPressed(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_LEFT:
//                    if (snake.direction != 'R') {
//                        snake.direction = 'L';
//                    }
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    if (snake.direction != 'L') {
//                        snake.direction = 'R';
//                    }
//                    break;
//                case KeyEvent.VK_UP:
//                    if (snake.direction != 'D') {
//                        snake.direction = 'U';
//                    }
//                    break;
//                case KeyEvent.VK_DOWN:
//                    if (snake.direction != 'U') {
//                        snake.direction = 'D';
//                    }
//                    break;
//            }
//        }
//    }
    public static boolean Contains(int X, int Y){
        if((X >= 0 && X <= (SCREEN_WIDTH/UNIT_SIZE)-1) && (Y >= 0 && Y <= (SCREEN_WIDTH/UNIT_SIZE)-1)){
            return true;
        }
        return false;
    }
}
