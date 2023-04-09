package GUI.panels;

import GUI.constants.Constants;
import animals.enums.AnimalType;
import lombok.Setter;
import statictic.EventLog;
import statictic.StatisticCollector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.DecimalFormat;

//панель отвечает за вывод информации о конкретном поле выбранном на карте
public class FieldStatPanel extends JPanel {
    private final JLabel[] animalLabels = new JLabel[15];
    private final JLabel[] additionalLabels = new JLabel[3];
    private JLabel grassLabel;
    private JLabel mainStatusJLabel;
    private JSpinner numberOfIteration;
    private JTextArea logPanel;
    @Setter
    private StatisticCollector.FieldInfo fieldInfo;


    public FieldStatPanel() {
        setLayout(null);
        setBounds(620, 320, 620, 470);
        initUI();
        setVisible(true);
    }


    public void updateAllInformation(int x, int y) {
        if (fieldInfo == null) {
            return;
        }
        String status = String.format("Поле [%d,%d]", x,y);
        mainStatusJLabel.setText(status);

        ((SpinnerNumberModel) numberOfIteration.getModel()).setMaximum(EventLog.getNumberOfIteration());
        for (int i = 0; i < animalLabels.length; i++) {
            AnimalType type = AnimalType.values()[i];
            int count = fieldInfo.getAnimalsCountOnField(type);
            animalLabels[i].setText(String.valueOf(count));
        }
        grassLabel.setText(new DecimalFormat("#.##").format(fieldInfo.getGrass()) + " кг");
        additionalLabels[0].setText(String.valueOf(fieldInfo.getAnimalsBorn()));
        additionalLabels[1].setText(String.valueOf(fieldInfo.getAnimalsEaten()));
        additionalLabels[2].setText(String.valueOf(fieldInfo.getAnimalsDiedOfHunger()));
    }

    private void initUI() {
        numberOfIteration = createJSpinner();
        logPanel = createJTextArea();
        mainStatusJLabel = createStatusJLabel();
        createJButton();
        createLifeCycleJLabel();

        for (int i = 0; i < animalLabels.length; i++) {
            int x = i < 5 ? 95 : i >= 10 ? 375 : 235;
            int y = (i % 5 + 1) * 50;
            animalLabels[i] = createCountJLabel(x, y);
        }
        grassLabel = createCountJLabel(510, 50);
        for (int i = 0; i < additionalLabels.length; i++) {
            int x = 510;
            int y = (i % 5 + 3) * 50;
            additionalLabels[i] = createCountJLabel(x, y);
        }
    }

    public void setTextArea() {
        logPanel.setText("");
        int number = (Integer) numberOfIteration.getModel().getValue();
        if (fieldInfo.getEventLog().getLogs().get(number) != null) {
            logPanel.append(number+ "-й жизненный цикл:\n");
            for (String string : fieldInfo.getEventLog().getLogs().get(number)) {
                logPanel.append(string + "\n");
            }
        }
        else {
            logPanel.append("Нет данных для отображения.");
        }
    }


    private JLabel createCountJLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setFont(Constants.ARIAL_BOLD_20);
        label.setForeground(Color.BLACK);
        label.setBounds(x, y, 120, 40);
        add(label);
        return label;
    }

    private void createLifeCycleJLabel() {
        JLabel label = new JLabel();
        label.setFont(Constants.ARIAL_BOLD_14);
        label.setForeground(Color.BLACK);
        label.setBounds(18, 305, 120, 100);
        label.setText("Жизненный цикл");
        add(label);
    }

    private JLabel createStatusJLabel() {
        JLabel label = new JLabel();
        label.setFont(Constants.ARIAL_BOLD_20);
        label.setForeground(Color.BLACK);
        label.setBounds(20, 12, 500, 30);
        label.setText("ОСТРОВ 100х100. Количество жителей");
        add(label);
        return label;
    }

    private JSpinner createJSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, EventLog.getNumberOfIteration(), 1));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setEditable(false);
        editor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        editor.getTextField().setBackground(Constants.SPECIAL_BROWN);
        editor.getTextField().setForeground(Color.BLACK);
        editor.getTextField().setFont(Constants.ARIAL_BOLD_20);
        spinner.setBorder(new LineBorder(Color.BLACK, 2));
        spinner.setBounds(45, 375, 70, 30);
        this.add(spinner);
        return spinner;
    }

    private JTextArea createJTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(Constants.ARIAL_BOLD_14);
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(Constants.SPECIAL_BROWN);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
        scrollPane.setBounds(150, 300, 450, 150);
        add(scrollPane);
        return textArea;
    }

    private void createJButton() {
        JButton button = new JButton(Constants.RENEW_BUTTON_IMAGE);
        button.setBounds(18, 420, 120, 30);
        button.addActionListener(e -> {
            setTextArea();
        });
        add(button);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Constants.FIELD_STAT_PANEL_BG, 0, 0, null);
    }
}
