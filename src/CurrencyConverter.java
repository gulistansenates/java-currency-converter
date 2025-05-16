import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverter extends JFrame {

    private final JSONObject fakeRates = new JSONObject(
            """
            {
              "USD": 1.0,
              "EUR": 0.92,
              "TRY": 32.5,
              "GBP": 0.78,
              "JPY": 155.3
            }
            """
    );

    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titleLabel = new JLabel("Currency Converter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        amountField = new JTextField();
        add(new LabeledPanel("Amount:", amountField));

        fromCurrency = new JComboBox<>(fakeRates.keySet().toArray(new String[0]));
        toCurrency = new JComboBox<>(fakeRates.keySet().toArray(new String[0]));
        add(new LabeledPanel("From:", fromCurrency));
        add(new LabeledPanel("To:", toCurrency));

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertAction());
        add(convertButton);

        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(resultLabel);

        setVisible(true);
    }

    private class ConvertAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String from = (String) fromCurrency.getSelectedItem();
                String to = (String) toCurrency.getSelectedItem();

                double fromRate = fakeRates.getDouble(from);
                double toRate = fakeRates.getDouble(to);

                double result = (amount / fromRate) * toRate;

                resultLabel.setText(String.format("Result: %.2f %s", result, to));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    }

    private static class LabeledPanel extends JPanel {
        public LabeledPanel(String label, JComponent component) {
            setLayout(new BorderLayout(5, 5));
            add(new JLabel(label), BorderLayout.WEST);
            add(component, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}
