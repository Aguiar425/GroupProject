package Field;

import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    static final int numberRows = 25;
    static final int numberCols = 25;
    static final int originX = 0;
    static final int originY = 20;
    static final int squareSize = 50;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < numberRows + 1; i++) {

            g.drawLine(originX, originY + i * squareSize,originX + numberCols * squareSize, originY + i * squareSize);
            g.drawLine(originX + i * squareSize, originY, originX + i * squareSize, originY + numberRows * squareSize);
        }

    }
}
