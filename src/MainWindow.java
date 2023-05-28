import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("SnakeGame   съешь 30шт.");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(668,700);
        setLocation(200,40);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
