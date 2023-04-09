package controller;

import GUI.panels.MainFrame;
import island.Service;
import lombok.Getter;

public class Controller {
    @Getter
    private final Service service;
    @Getter
    private final MainFrame mainFrame;

    public Controller(Service service, MainFrame mainFrame) {
        this.service = service;
        this.mainFrame = mainFrame;
        service.setController(this);
        mainFrame.getIslandSimulationPanel().getMapPanel().setFieldsTextures(service.getFields());
        updateAllUI();
    }

    //обновляет данные интерфейса всех панелей
    public synchronized void updateAllUI() {
        updateIslandUI();
        updateFieldUI();
    }

    //обновляет данные интерфейса для текущего поля
    public synchronized void updateFieldUI(){
        int x = mainFrame.getIslandSimulationPanel().getMapPanel().getCurrentFieldX();
        int y = mainFrame.getIslandSimulationPanel().getMapPanel().getCurrentFieldY();
        mainFrame.getIslandSimulationPanel().getFieldStatPanel().setFieldInfo(service.getStatisticCollector().getFieldsInfo()[x][y]);
        mainFrame.getIslandSimulationPanel().getFieldStatPanel().updateAllInformation(x,y);
    }

    //обновляет данные интерфейса для всего острова
    public synchronized void updateIslandUI(){
        mainFrame.getIslandSimulationPanel().getFullStatPanel().updateAllInformation(service.getStatisticCollector());
    }

    //в случае обнуления населения острова, прячет кнопки запуска и остановки симуляции
    public void gameOver() {
        mainFrame.getIslandSimulationPanel().getControlPanel().getStartButton().setVisible(false);
        mainFrame.getIslandSimulationPanel().getControlPanel().getStopButton().setVisible(false);
        updateAllUI();
    }


    public static void main(String[] args) {
        new MainFrame().setMainMenuPanel();
    }


}