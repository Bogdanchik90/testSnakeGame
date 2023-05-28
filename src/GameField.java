import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 1600;
    private Image dot;
    private Image apple;
    private Image stone;
    private int appleX;
    private int appleY;
    private int stoneX;
    private int stoneY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean startStop = false;
    private int score = 0;
    private int t = 300;

    public int a = new Random().nextInt(40)*DOT_SIZE;
    public int b = new Random().nextInt(40)*DOT_SIZE;
    public int c = new Random().nextInt(40)*DOT_SIZE;
    public int d = new Random().nextInt(40)*DOT_SIZE;
    public int e = new Random().nextInt(40)*DOT_SIZE;


    public GameField() {
        setBackground(Color.gray);
        loadImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(t, this);
        createApple();
        createStone();
    }
    public void win(int scor){
        if (scor >= 30)
        inGame = false;
    }
    public void reStart() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        score = 0;
        t = 300;
        right = true;
        left = false;
        up = false;
        down = false;
        createApple();
        createStone();
    }

    public void createApple() {
        appleX = new Random().nextInt(40) * DOT_SIZE;
        appleY = new Random().nextInt(40) * DOT_SIZE;
    }
    public void createStone(){
        stoneX = new Random().nextInt(40) * DOT_SIZE;
        stoneY = new Random().nextInt(40) * DOT_SIZE;
    }

    public void loadImage() {
        ImageIcon iia = new ImageIcon("icons8-яблоко-16.png");
        apple = iia.getImage();
        ImageIcon iis = new ImageIcon("icons8-rgb-круг-2-16.png");
        dot = iis.getImage();
        ImageIcon ist = new ImageIcon("pngwing.com.png");
        stone = ist.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            g.drawImage(stone,stoneX,stoneY,this);
            g.drawImage(stone,d,a,this);
            g.drawImage(stone,a,b,this);
            g.drawImage(stone,a,e,this);
            g.drawImage(stone,e,d,this);
            g.drawImage(stone,b,e,this);
            g.drawImage(stone,c,c,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }

            String st = "Яблок съедено: " + score + " шт.";
            g.setColor(Color.BLACK);
            g.drawString(st,10,10);
        } else if (!inGame && score >= 30) {
            String str2 = "YOU WIN";
            g.setColor(Color.BLACK);
            g.drawString(str2,SIZE/3,SIZE/2);
        } else {
            String str = "GameOver " + score + " яблок";
            g.setColor(Color.BLACK);
            g.drawString(str, 110, SIZE / 2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left)
            x[0] -= DOT_SIZE;
        if (right)
            x[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            score++;
            createApple();
        }
    }
    public void checkStone(){
        if (x[0] == stoneX && y[0] == stoneY && dots > 12){
            dots /= 2;
        }
    }

    public void checkCollisions() {
        if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0)
            inGame = false;
        for (int i = dots; i > 0; i--) {
            if ((x[0] == x[i] && y[0] == y[i]) && dots > 3)
                inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            checkStone();
            move();
            win(score);
            if (t > 35 && score % 2 == 0)
                t--;
            timer.setDelay(t);
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_ENTER && timer.isRunning())
                    timer.stop();
            if (key == KeyEvent.VK_ENTER)
                timer.start();
            if (key == KeyEvent.VK_SPACE && !inGame){
                inGame = true;
                reStart();
            }
        }
    }
}
