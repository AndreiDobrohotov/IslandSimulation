package GUI.panels;

import controller.Controller;
import island.Service;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.ActionListener;

//главный фрейм, который содержит две панели: главное меню и игровое поле
@Getter
public class MainFrame extends JFrame {
    private final MainMenuPanel mainMenuPanel = new MainMenuPanel();
    private final IslandSimulationPanel islandSimulationPanel = new IslandSimulationPanel();
    private Controller controller;

    public MainFrame() {
        setTitle("Island Simulator");
        setSize(1266, 839);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    //установить в качестве контента главное меню
    public void setMainMenuPanel() {
        this.setContentPane(mainMenuPanel);
        mainMenuPanel.getCreateIslandButton().addActionListener(createIslandActionListener());
        this.repaint();
    }

    //установить в качестве контента игровое поле
    public void setIslandSimulationPanel() {
        this.setContentPane(islandSimulationPanel);
        islandSimulationPanel.getControlPanel().getStartButton().addActionListener(startSimulationActionListener());
        islandSimulationPanel.getControlPanel().getStopButton().addActionListener(stopSimulationActionListener());
        islandSimulationPanel.getControlPanel().getUpButton().addActionListener(mapMoveUpActionListener());
        islandSimulationPanel.getControlPanel().getDownButton().addActionListener(mapMoveDownActionListener());
        islandSimulationPanel.getControlPanel().getLeftButton().addActionListener(mapMoveLeftActionListener());
        islandSimulationPanel.getControlPanel().getRightButton().addActionListener(mapMoveRightActionListener());
        this.repaint();
    }

    //обработка нажатия кнопки в главном меню. Создаст остров с полученными параметрами.
    private ActionListener createIslandActionListener() {
        return e -> {
            int width = Integer.parseInt(mainMenuPanel.getWidthField().getText());
            int height = Integer.parseInt(mainMenuPanel.getHeightField().getText());
            int predatorCount = Integer.parseInt(mainMenuPanel.getPredatorCountField().getText());
            int herbivorousCount = Integer.parseInt(mainMenuPanel.getHerbivorousCountField().getText());
            controller = new Controller(new Service(width, height, predatorCount, herbivorousCount), this);
            this.setIslandSimulationPanel();
        };
    }

    //обработка нажатия кнопки в игровом поле. Запустит главный поток симуляции.
    private ActionListener startSimulationActionListener() {
        return e -> {
            int sliderValue = islandSimulationPanel.getControlPanel().getSlider().getValue();
            controller.getService().start(sliderValue);
        };
    }

    //обработка нажатия кнопки в игровом поле. Останавливает главный поток симуляции.
    private ActionListener stopSimulationActionListener() {
        return e -> {
            controller.getService().stop();
        };
    }

    //обработка нажатия кнопок передвижения в игровом поле. Обновляет информацию о новом поле.
    private ActionListener mapMoveUpActionListener() {
        return e -> {
            getIslandSimulationPanel().getMapPanel().decrementFieldY();
            controller.updateFieldUI();
        };
    }

    private ActionListener mapMoveDownActionListener() {
        return e -> {
            getIslandSimulationPanel().getMapPanel().incrementFieldY();
            controller.updateFieldUI();
        };
    }

    private ActionListener mapMoveLeftActionListener() {
        return e -> {
            getIslandSimulationPanel().getMapPanel().decrementFieldX();
            controller.updateFieldUI();
        };
    }

    private ActionListener mapMoveRightActionListener() {
        return e -> {
            getIslandSimulationPanel().getMapPanel().incrementFieldX();
            controller.updateFieldUI();
        };
}


}
