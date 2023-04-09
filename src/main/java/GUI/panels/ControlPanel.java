package GUI.panels;

import GUI.constants.Constants;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

//панель отвечает за перемещение по карте.
//позволяет выбрать частоту с которой будут повторяться жизненные циклы.
@Getter
public class ControlPanel extends JPanel {
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton startButton;
    private JButton stopButton;
    private JSlider slider;

    public ControlPanel() {
        setLayout(null);
        setBounds(10, 620, 600, 170);
        iniButtons();
        iniSlider();
        setVisible(true);
    }

    public void iniSlider(){
        slider = new JSlider(1, 9, 5);
        slider.setBounds(237, 95, 346, 45);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setBackground(new Color(0,0,0,0));
        slider.setForeground(Color.black);
        slider.setOpaque(false);
        add(slider);
    }

    public void iniButtons(){
        upButton = new JButton(Constants.UP_BUTTON_IMAGE);
        downButton = new JButton(Constants.DOWN_BUTTON_IMAGE);
        leftButton = new JButton(Constants.LEFT_BUTTON_IMAGE);
        rightButton = new JButton(Constants.RIGHT_BUTTON_IMAGE);
        startButton = new JButton(Constants.START_BUTTON_IMAGE);
        stopButton = new JButton(Constants.STOP_BUTTON_IMAGE);

        upButton.setBounds(90, 20, 60, 60);
        downButton.setBounds(90, 90, 60, 60);
        leftButton.setBounds(20, 90, 60, 60);
        rightButton.setBounds(160, 90, 60, 60);
        startButton.setBounds(235, 20, 165, 40);
        stopButton.setBounds(415, 20, 165, 40);

        add(upButton);
        add(downButton);
        add(leftButton);
        add(rightButton);
        add(startButton);
        add(stopButton);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Constants.CONTROL_PANEL_BG, 0, 0, null);
    }
}
