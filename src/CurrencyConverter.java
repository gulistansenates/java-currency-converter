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
        // UI Teması ve renk ayarları
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        getContentPane().setBackground(new Color(245, 245, 245));
        setTitle("💱 Currency Converter");
        setSize(420, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Üst başlık
        JLabel titleLabel = new JLabel("Currency Converter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Ana panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        add(panel, BorderLayout.CENTER);

        amountField = new JTextField();
        fromCurrency = new JComboBox<>(fakeRates.keySet().toArray(new String[0]));
        toCurrency = new JComboBox<>(fakeRates.keySet().toArray(new String[0]));

        panel.add(new LabeledPanel("Amount:", amountField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(new LabeledPanel("From Currency:", fromCurrency));
        panel.add(Box.createVerticalStrut(10));
        panel.add(new LabeledPanel("To Currency:", toCurrency));
        panel.add(Box.createVerticalStrut(15));

        // Buton
        JButton convertButton = new JButton("Convert");
        convertButton.setBackground(new Color(30, 144, 255));
        convertButton.setForeground(Color.WHITE);
        convertButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        convertButton.setFocusPainted(false);
        convertButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        convertButton.addActionListener(new ConvertAction());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(convertButton);
        panel.add(buttonPanel);

        // Sonuç etiketi
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(resultLabel, BorderLayout.SOUTH);

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
            setBackground(new Color(245, 245, 245));
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            add(jLabel, BorderLayout.WEST);
            add(component, BorderLayout.CENTER);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}
