package GUI.panels;

import controller.Controller;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class IslandPanel extends JPanel {
    private Image background = new ImageIcon("src/main/resources/images/islandPanelBg.png").getImage();
    @Getter private final ControlPanel controlPanel;
    @Getter private final MapPanel mapPanel;
    @Getter private final FullStatPanel fullStatPanel;
    @Getter private final FieldStatPanel fieldStatPanel;

    public IslandPanel() {
        setLayout(null);
        setBounds(0, 0, 1250, 800);
        mapPanel = new MapPanel();
        controlPanel = new ControlPanel();
        fieldStatPanel = new FieldStatPanel();
        fullStatPanel = new FullStatPanel();


        add(controlPanel);
        add(mapPanel);
        add(fullStatPanel);
        add(fieldStatPanel);

        setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}
