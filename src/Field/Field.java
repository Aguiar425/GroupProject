package Field;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Field {
    public static void main(String[] args) {

        // Setup terminal and screen layers
        Terminal terminal = null;
        Screen screen;
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create panel to hold components
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Forename"));
        panel.addComponent(new TextBox());

        panel.addComponent(new Label("Surname"));
        panel.addComponent(new TextBox());

        panel.addComponent(new EmptySpace(new TerminalSize(0, 0))); // Empty space underneath labels
        panel.addComponent(new Button("Submit"));

        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(panel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.RED));
        gui.addWindowAndWait(window);

    }


    public void Field() {
        TerminalSize size = new TerminalSize(80, 50); // set the desired size of the terminal
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(size);
        try {
            Terminal terminal = terminalFactory.createTerminal(); // create the terminal with the specified size
            // do something with the terminal
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }


}
