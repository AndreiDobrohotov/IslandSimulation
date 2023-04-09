package GUI.panels;

import GUI.constants.Constants;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

//панель игрового поля. Содержит все остальные подпанели.
public class IslandSimulationPanel extends JPanel {
    @Getter private final ControlPanel controlPanel;
    @Getter private final MapPanel mapPanel;
    @Getter private final FullStatPanel fullStatPanel;
    @Getter private final FieldStatPanel fieldStatPanel;

    public IslandSimulationPanel() {
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
        g.drawImage(Constants.ISLAND_SIMULATION_PANEL_BG, 0, 0, null);
    }
}
