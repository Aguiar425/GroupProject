//package Field;
//
//import com.googlecode.lanterna.screen.Screen;
//import com.googlecode.lanterna.terminal.Terminal;
//
//public final class FieldTest {
//
//    private static final String BORDER_STRING = "▒";
//    private static final String SNAKE_BODY_STRING = "#";
//    private static final String SNAKE_HEAD_STRING = "0";
//    private static final String FRUIT_STRING = "@";
//
//    private static int width;
//    private static int height;
//    private static Screen screen;
//    private static ScreenWriter screenWriter;
//
//    private FieldTest() {
//    }
//
//    public static void init(int width, int height) {
//
//        screen = TerminalFacade.createScreen();
//
//        FieldTest.width = width;
//        FieldTest.height = height;
//        screen.getTerminal().getTerminalSize().setColumns(width);
//        screen.getTerminal().getTerminalSize().setRows(height);
//
//        screenWriter = new ScreenWriter(screen);
//        screen.setCursorPosition(null);
//        screen.startScreen();
//
//        drawWalls();
//        screen.refresh();
//    }
//
//    private static void drawWalls() {
//        for (int i = 0; i < width; i++) {
//            screenWriter.drawString(i, 0, BORDER_STRING);
//            screenWriter.drawString(i, height - 1, BORDER_STRING);
//        }
//
//        for (int j = 0; j < height; j++) {
//            screenWriter.drawString(0, j, BORDER_STRING);
//            screenWriter.drawString(width - 1, j, BORDER_STRING);
//        }
//    }
//
//    public static Key readInput() {
//        return screen.readInput();
//    }
//
//
//    public static int getWidth() {
//        return width;
//    }
//
//    public static int getHeight() {
//        return height;
//    }
//}
