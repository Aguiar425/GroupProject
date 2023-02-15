package Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Lift extends JPanel implements ActionListener, KeyListener {

    private static final int NUMBER_OF_FLOORS = 5, LIFT_W = 100, LIFT_H = 100, PAUSE = 2000;
    private final JLabel[] lifts = new JLabel[NUMBER_OF_FLOORS];
    private int liftPosition = 0;
    private boolean isMovingUp = true;
    private final Timer tm = new Timer(PAUSE, this);
    int x = 0, y = 0, velX = 0, velY = 0;


    public Lift() {
        setLayout(new GridLayout(0, 1));
        for (int floor = NUMBER_OF_FLOORS - 1; floor >= 0; floor--) {
            JLabel lift = getLift(floor);
            lifts[floor] = lift;
            add(lift);
        }

        addKeyListener(this);
        tm.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, 50, 30);

    }

    private JLabel getLift(int floor) {
        JLabel lift = new JLabel();
        lift.setPreferredSize(new Dimension(LIFT_W, LIFT_H));
        lift.setBackground(Color.RED);
        if (liftPosition == floor) {
            lift.setOpaque(true);
        }
        return lift;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        autoMove();
    }

    private void autoMove() {
        if (liftPosition == 0) {
            isMovingUp = true;
        } else if (liftPosition >= NUMBER_OF_FLOORS - 1) {
            isMovingUp = false;
        }
        if (isMovingUp) {
            moveUp();
        } else {
            moveDown();
        }
    }

    private void moveUp() {
        if (liftPosition >= NUMBER_OF_FLOORS - 1) return;
        lifts[liftPosition].setOpaque(false);
        liftPosition++;
        lifts[liftPosition].setOpaque(true);
        repaint();
    }

    private void moveDown() {
        if (liftPosition <= 0) return;
        lifts[liftPosition].setOpaque(false);
        liftPosition--;
        lifts[liftPosition].setOpaque(true);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_UP) {
            moveUp();
        }
        if (c == KeyEvent.VK_DOWN) {
            moveDown();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Lift t = new Lift();
        JFrame jf = new JFrame();
        jf.setTitle("Lift");
        jf.setSize(300, 400);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(t, BorderLayout.WEST);
    }
}