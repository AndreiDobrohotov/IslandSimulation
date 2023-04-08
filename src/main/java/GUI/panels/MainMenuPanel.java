package GUI.panels;

import GUI.util.JTextFieldInputVerifier;
import GUI.util.JTextFieldLimit;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StartMenuPanel extends JPanel {
    private final ImageIcon backGround = new ImageIcon("src/main/resources/images/startMenuBG.png");;
    private final ImageIcon buttonImage = new ImageIcon("src/main/resources/images/createIslandButton.png");;
    private final Color mainColor = new Color(143, 9, 9);
    private final Font mainFont = new Font("Arial", Font.BOLD, 20);
    private JButton createIslandButton = new JButton();
    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField predatorCountField;
    private final JTextField herbivorousCountField;

    public StartMenuPanel() {
        setLayout(null);
        setBounds(0, 0, 1250, 800);
        setVisible(true);

        widthField = createJTextField(525,281,10,100,3);
        heightField = createJTextField(705,281,10,100,3);
        predatorCountField = createJTextField(525,412,0,50,2);
        herbivorousCountField = createJTextField(750,412,0,50,2);

        createIslandButton = new JButton(buttonImage);
        createIslandButton.setBounds(565,464,250,50);
        add(createIslandButton);
        createIslandButton.addActionListener(e -> {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int predatorCount = Integer.parseInt(predatorCountField.getText());
            int herbivorousCount = Integer.parseInt(herbivorousCountField.getText());
            Controller.createService(width, height, predatorCount, herbivorousCount);
            Controller.getMainFrame().setIslandPanel();
        });
    }

    public JTextField createJTextField(int x, int y, int minValue, int maxValue, int length) {
        JTextField textField = new JTextField();

        textField.setBounds(x, y, 70, 30);
        //устанавливаем прозрачный фон
        textField.setOpaque(false);
        //устанавливаем лимит для значений поля
        textField.setInputVerifier(new JTextFieldInputVerifier(minValue,maxValue));
        //устанавливаем лимит на длину вводимого текста
        textField.setDocument(new JTextFieldLimit(length));
        textField.setText(String.valueOf(minValue));
        textField.setBorder(new LineBorder(mainColor, 2));
        textField.setForeground(mainColor);
        textField.setFont(mainFont);
        textField.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(textField);
        return textField;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround.getImage(), 0, 0, null);
    }
}
