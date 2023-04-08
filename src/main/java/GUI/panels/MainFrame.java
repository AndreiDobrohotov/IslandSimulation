package GUI;

import GUI.panels.IslandSimulationPanel;
import GUI.panels.MainMenuPanel;
import controller.Controller;
import island.Service;
import lombok.Getter;
import javax.swing.*;

@Getter
public class MainFrame extends JFrame {
    private final MainMenuPanel mainMenuPanel = new MainMenuPanel();
    private final IslandSimulationPanel islandSimulationPanel = new IslandSimulationPanel();
    private Controller controller;

    public void setMainMenuPanel() {
        this.setContentPane(mainMenuPanel);
        mainMenuPanel.getCreateIslandButton().addActionListener(e -> {
            int width = Integer.parseInt(mainMenuPanel.getWidthField().getText());
            int height = Integer.parseInt(mainMenuPanel.getHeightField().getText());
            int predatorCount = Integer.parseInt(mainMenuPanel.getPredatorCountField().getText());
            int herbivorousCount = Integer.parseInt(mainMenuPanel.getHerbivorousCountField().getText());
            controller = new Controller(new Service(width, height, predatorCount, herbivorousCount), this);
            this.setIslandSimulationPanel();
        });
        this.repaint();
    }

    public void setIslandSimulationPanel(){
        this.setContentPane(islandSimulationPanel);
        this.repaint();
    }

    public MainFrame() {
        setTitle("Island Simulator");
        setSize(1266, 839);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
