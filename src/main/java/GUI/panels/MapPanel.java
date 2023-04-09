package GUI.panels;

import GUI.constants.Constants;
import island.Field;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

//панель отвечает за отображение острова
public class MapPanel extends JPanel {
    private Image[][] fieldsTextures;
    private int width = 0;
    private int height = 0;
    @Getter
    private int currentFieldX = 0;
    @Getter
    private int currentFieldY = 0;


    public MapPanel() {
        setLayout(null);
        setBounds(10, 10, 600, 600);
        setVisible(true);
    }

    public void incrementFieldX() {
        if (currentFieldX != width - 1) {
            currentFieldX++;
        }
        repaint();
    }

    public void incrementFieldY() {
        if (currentFieldY != height - 1) {
            currentFieldY++;
        }
        repaint();
    }

    public void decrementFieldX() {
        if (currentFieldX != 0) {
            currentFieldX--;
        }
        repaint();
    }

    public void decrementFieldY() {
        if (currentFieldY != 0) {
            currentFieldY--;
        }
        repaint();
    }

    //заполняет массив текстур для отрисовки
    public void setFieldsTextures(Field[][] fields) {
        width = fields.length;
        height = fields[0].length;
        fieldsTextures = new Image[width][height];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fieldsTextures[i][j] = switch (fields[i][j].getType()) {
                    case GRASS -> Constants.GRASS_TEXTURES[ThreadLocalRandom.current().nextInt(Constants.GRASS_TEXTURES.length)];
                    case ROCK -> Constants.ROCK_TEXTURES[ThreadLocalRandom.current().nextInt(Constants.ROCK_TEXTURES.length)];
                    case GROUND -> Constants.GROUND;
                    case SAND -> Constants.SAND;
                };
            }
        }
    }


    //рисует клетки острова
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Constants.MAP_PANEL_BG, 0, 0, null);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = currentFieldX - 2 + i;
                int y = currentFieldY - 2 + j;
                try {
                    g.drawImage(fieldsTextures[x][y], i * 117 + 10, j * 117 + 10, 114, 114, null);
                } catch (Exception ignore) {
                    g.drawImage(Constants.WATER, i * 117 + 10, j * 117 + 10, 114, 114, null);
                }
            }
        }
        g.drawImage(Constants.SELECTION, 241, 241, 120,120,null);
    }


}

