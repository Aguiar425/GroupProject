package Field;

import javax.swing.*;

public class Field {
    public static void main(String[] args) {
        JFrame frame = new JFrame("frame");
        frame.setSize(1200,800);

        Grid grid = new Grid();
        frame.add(grid);
        frame.setVisible(true);
    }
}
