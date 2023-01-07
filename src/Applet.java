import java.awt.*;
import javax.swing.*;

public class Applet extends JApplet {
    public void init() {
        // Create a panel to hold the content
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());

        // Add some content to the panel
        for (int i = 0; i < 100; i++) {
            content.add(new JButton("Button " + i));
        }

        // Create a scroll pane to hold the content panel
        JScrollPane scrollPane = new JScrollPane(content);

        // Add the scroll pane to the applet
        add(scrollPane);
    }
}
