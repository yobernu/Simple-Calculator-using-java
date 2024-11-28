import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    // Declare components
    private JTextField textField;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton equalsButton, clearButton, multiplyButton;
    private String currentOperator = "";
    private double num1, num2, result;

    public Calculator() {
        // Set the title and layout
        setTitle("Calculator");
        setLayout(new BorderLayout());

        // Initialize the text field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);

        // Create number buttons (0-9)
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            numberButtons[i].addActionListener(this);
        }

        // Create operator buttons
        String[] operators = {"+", "-", "*", "/", "%", "="};
        operatorButtons = new JButton[operators.length];
        for (int i = 0; i < operators.length; i++) {
            operatorButtons[i] = new JButton(operators[i]);
            operatorButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            operatorButtons[i].addActionListener(this);
        }

        // Clear button
        clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 18));
        clearButton.addActionListener(this);

        // Multiply button (separate, since it's on a different place)
        multiplyButton = new JButton("*");
        multiplyButton.setFont(new Font("Arial", Font.PLAIN, 18));
        multiplyButton.addActionListener(this);

        // Panel for buttons (4x4 layout)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));  // 4x4 grid

        // Add buttons to the grid layout
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(operatorButtons[0]); // +

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(operatorButtons[1]); // -

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(operatorButtons[3]); // /

        panel.add(operatorButtons[4]); // %
        panel.add(numberButtons[0]);
        panel.add(operatorButtons[5]); // =
        panel.add(clearButton); // C

        // Add panel to frame
        add(panel, BorderLayout.CENTER);

        // Set frame properties
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9')) {
            // Append the clicked number to the text field
            textField.setText(textField.getText() + command);
        } else if (command.equals("C")) {
            // Clear the text field
            textField.setText("");
            num1 = num2 = result = 0;
            currentOperator = "";
        } else if (command.equals("=")) {
            // Perform the calculation
            num2 = Double.parseDouble(textField.getText());
            try {
                switch (currentOperator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            textField.setText("Error");
                            return;
                        }
                        break;
                    case "%":
                        result = num1 % num2;
                        break;
                    default:
                        textField.setText("Error");
                        return;
                }
                textField.setText(String.valueOf(result));
                num1 = result;
                currentOperator = "";
            } catch (Exception ex) {
                textField.setText("Error");
                ex.printStackTrace();
            }
        } else {
            // Save the operator
            if (!currentOperator.isEmpty()) {
                return; // Prevent operator chaining
            }
            currentOperator = command;
            num1 = Double.parseDouble(textField.getText());
            textField.setText("");
        }
    }

    public static void main(String[] args) {
        // Create and display the calculator
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }
}
