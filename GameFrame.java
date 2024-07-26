import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame() {
        GamePanel panel = new GamePanel();//створює головну панель гри
        this.add(panel);// додає панель до головного вікна
        this.setTitle("Snake");// додає назву вікна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//встановлює операцію на закраття головного вікна
        this.setResizable(false);//вимикає можливість зміни розміру головного екрану
        this.pack();// автоматично налаштовує розмір вікна відповідно до вмісту
        this.setVisible(true);// встановлює видимість вікна
        this.setLocationRelativeTo(null);// розміщує вікно по центу екрана
    }
}
