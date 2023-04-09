package GUI.panels;

import GUI.constants.Constants;
import animals.enums.AnimalType;
import statictic.EventLog;
import statictic.StatisticCollector;

import javax.swing.*;
import java.awt.*;

//Панель отвечает за вывод информации обо всем острове.
public class FullStatPanel extends JPanel {
    private final JLabel[] animalLabels = new JLabel[15];
    private final JLabel[] additionalLabels = new JLabel[3];
    private JLabel grassLabel;
    private JLabel mainStatusJLabel;

    public FullStatPanel() {
        setLayout(null);
        setBounds(620, 10, 620, 300);
        initUI();
        setVisible(true);
    }

    public JLabel createNewLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setFont(Constants.ARIAL_BOLD_20);
        label.setForeground(Color.BLACK);
        label.setBounds(x, y, 120, 40);
        add(label);
        return label;
    }

    public String convertNumberToString(Number number) {
        long l = number.longValue();
        String text = String.valueOf(l);
        if (l>=1_000_000) text = text.substring(0,text.length()-6)+"млн.";
        else if (l>=1000) text = text.substring(0,text.length()-3)+"т.";
        return text;
    }

    public void initUI(){
        mainStatusJLabel = createStatusJLabel();
        for(int i = 0; i < animalLabels.length; i++) {
            int x = i<5 ? 95 : i>=10 ? 375 : 235;
            int y = (i%5+1)*49;
            animalLabels[i] = createNewLabel(x,y);
        }
        grassLabel = createNewLabel(510,49);
        for(int i = 0; i < additionalLabels.length; i++) {
            int x = 510;
            int y = (i%5+3)*49;
            additionalLabels[i] = createNewLabel(x,y);
        }
    }

    public void updateAllInformation(StatisticCollector collector) {
        String status;
        if(collector.getTotalAnimalsCount() != 0) {
            status = String.format("Остров %dx%d. Население: %d. Жизненный цикл: %d. ",
                    collector.getWidth(), collector.getHeight(), collector.getTotalAnimalsCount(), EventLog.getNumberOfIteration() );
        }
        else{
            status = String.format("Население полность вымерло на %d-м жизненном цикле.", EventLog.getNumberOfIteration() );
        }
        mainStatusJLabel.setText(status);
        for(int i = 0; i < animalLabels.length; i++) {
            AnimalType type = AnimalType.values()[i];
            int count = collector.getTotalAnimalsCountByType(type);
            animalLabels[i].setText(convertNumberToString(count));
        }
        grassLabel.setText(convertNumberToString(collector.getTotalGrass()) + " кг");
        additionalLabels[0].setText(String.valueOf(convertNumberToString(collector.getTotalAnimalsBorn())));
        additionalLabels[1].setText(String.valueOf(convertNumberToString(collector.getTotalAnimalsEaten())));
        additionalLabels[2].setText(String.valueOf(convertNumberToString(collector.getTotalAnimalsDiedOfHunger())));
    }

    public void gameOver(StatisticCollector collector) {

        for(int i = 0; i < animalLabels.length; i++) {
            AnimalType type = AnimalType.values()[i];
            int count = collector.getTotalAnimalsCountByType(type);
            animalLabels[i].setText(convertNumberToString(count));
        }
        grassLabel.setText(convertNumberToString(collector.getTotalGrass()) + " кг");
        additionalLabels[0].setText(String.valueOf(convertNumberToString(collector.getTotalAnimalsBorn())));
        additionalLabels[1].setText(String.valueOf(convertNumberToString(collector.getTotalAnimalsEaten())));
        additionalLabels[2].setText(String.valueOf(convertNumberToString(collector.getTotalAnimalsDiedOfHunger())));
    }



    private JLabel createStatusJLabel() {
        JLabel label = new JLabel();
        label.setFont(Constants.ARIAL_BOLD_20);
        label.setForeground(Color.BLACK);
        label.setBounds(20, 12, 600, 30);
        add(label);
        return label;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Constants.FULL_STAT_PANEL_BG, 0, 0, null);
    }
}
