package GUI.util;

import javax.swing.*;

public class ConfigInputVerifier extends InputVerifier {
    private int minValue;
    private int maxValue;
    private final String MESSAGE = "Значение должно быть в диапозоне от %d до %d";

    public ConfigInputVerifier(int minValue, int maxValue) {
        super();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        String text = textField.getText();

        try {
            int number = Integer.parseInt(text);
            return (number >= minValue && number <= maxValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        boolean valid = verify(input);

        if (!valid) {
            JOptionPane.showMessageDialog(null, String.format(MESSAGE, minValue, maxValue), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

        return valid;
    }
}
