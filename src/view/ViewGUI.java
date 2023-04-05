package view;

import javax.swing.*;
import java.io.IOException;

public class ViewGUI extends JFrame implements ViewI  {

    private JPanel errorPanel;

    JLabel label;

    public ViewGUI(JPanel errorPanel) {
        this.errorPanel = errorPanel;
        label = new JLabel();
        errorPanel.add(label);
    }
    @Override
    public void printWelcomeMessage() {

    }

    @Override
    public void printOutput(String output) throws IOException {
        label.setText(output);

    }
}
