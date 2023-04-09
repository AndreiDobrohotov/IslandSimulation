package GUI.panels;

import GUI.constants.Constants;
import GUI.util.JTextFieldInputVerifier;
import GUI.util.JTextFieldLimit;
import lombok.Getter;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

//панель главного меню. Принимает параметры острова и передает сервису, для создания экземпляра.
@Getter
public class MainMenuPanel extends JPanel {
    private final JButton createIslandButton;
    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField predatorCountField;
    private final JTextField herbivorousCountField;

    public MainMenuPanel() {
        setLayout(null);
        setBounds(0, 0, 1250, 800);
        setVisible(true);

        widthField = createJTextField(525,281,10,100,3);
        heightField = createJTextField(705,281,10,100,3);
        predatorCountField = createJTextField(525,412,1,9999,4);
        herbivorousCountField = createJTextField(750,412,1,9999,4);

        createIslandButton = new JButton(Constants.CREATE_ISLAND_BUTTON_IMAGE);
        createIslandButton.setBounds(565,464,250,50);
        add(createIslandButton);
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
        textField.setBorder(new LineBorder(Constants.SPECIAL_RED, 2));
        textField.setForeground(Constants.SPECIAL_RED);
        textField.setFont(Constants.ARIAL_BOLD_20);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(textField);
        return textField;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Constants.MAIN_MENU_PANEL_BG, 0, 0, null);
    }
}
