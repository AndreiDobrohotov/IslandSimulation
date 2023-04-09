package GUI.constants;

import javax.swing.*;
import java.awt.*;

//содержит все картинки, шрифты и цвета для отображения
public class Constants {

    public static final Image MAIN_MENU_PANEL_BG = new ImageIcon("src/main/resources/images/backgrounds/mainMenuPanel.png").getImage();
    public static final Image ISLAND_SIMULATION_PANEL_BG = new ImageIcon("src/main/resources/images/backgrounds/islandSimulationPanel.png").getImage();
    public static final Image MAP_PANEL_BG = new ImageIcon("src/main/resources/images/backgrounds/mapPanel.png").getImage();
    public static final Image CONTROL_PANEL_BG = new ImageIcon("src/main/resources/images/backgrounds/controlPanel.png").getImage();
    public static final Image FIELD_STAT_PANEL_BG = new ImageIcon("src/main/resources/images/backgrounds/fieldStatPanel.png").getImage();
    public static final Image FULL_STAT_PANEL_BG = new ImageIcon("src/main/resources/images/backgrounds/fullStatPanel.png").getImage();

    public static final Image GRASS1 = new ImageIcon("src/main/resources/images/textures/grass1.png").getImage();
    public static final Image GRASS2 = new ImageIcon("src/main/resources/images/textures/grass2.png").getImage();
    public static final Image GRASS3 = new ImageIcon("src/main/resources/images/textures/grass3.png").getImage();
    public static final Image[] GRASS_TEXTURES = {GRASS1, GRASS2, GRASS3};
    public static final Image ROCKS1 = new ImageIcon("src/main/resources/images/textures/rocks1.png").getImage();
    public static final Image ROCKS2 = new ImageIcon("src/main/resources/images/textures/rocks2.png").getImage();
    public static final Image ROCKS3 = new ImageIcon("src/main/resources/images/textures/rocks3.png").getImage();
    public static final Image[] ROCK_TEXTURES = {ROCKS1, ROCKS2, ROCKS3};
    public static final Image SAND = new ImageIcon("src/main/resources/images/textures/sand.png").getImage();
    public static final Image WATER = new ImageIcon("src/main/resources/images/textures/water.png").getImage();
    public static final Image GROUND = new ImageIcon("src/main/resources/images/textures/ground.png").getImage();
    public static final Image SELECTION = new ImageIcon("src/main/resources/images/textures/selection.png").getImage();


    public static final ImageIcon UP_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/upButton.png");
    public static final ImageIcon DOWN_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/downButton.png");
    public static final ImageIcon LEFT_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/leftButton.png");
    public static final ImageIcon RIGHT_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/rightButton.png");
    public static final ImageIcon START_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/startButton.png");
    public static final ImageIcon STOP_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/stopButton.png");
    public static final ImageIcon CREATE_ISLAND_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/createIslandButton.png");
    public static final ImageIcon RENEW_BUTTON_IMAGE = new ImageIcon("src/main/resources/images/buttons/renewButton.png");

    public static final Color SPECIAL_RED = new Color(143, 9, 9);
    public static final Color SPECIAL_BROWN = new Color(150, 83, 65, 255);

    public static final Font ARIAL_BOLD_20 = new Font("Arial", Font.BOLD, 20);
    public static final Font ARIAL_BOLD_14 = new Font("Arial", Font.BOLD, 14);

}
