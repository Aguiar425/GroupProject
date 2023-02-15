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
    public static void startScreen(int width, int height) {

        screen = TerminalFacade.createScreen();

        Field.width = width;
        Field.height = height;
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        screenWriter = new ScreenWriter(screen);
        screen.setCursorPosition(null);
        screen.startScreen();

        drawWalls();
        screen.refresh();
    }
}
