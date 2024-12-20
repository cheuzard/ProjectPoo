package calculatorUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class CalculatorUI extends JFrame {
    private final JTextField inputField;
    private JTextField resultField;
    private final AtomicBoolean isResult;
    private final AtomicBoolean isError;
    private JButton[][] buttons;
    private boolean isScientificMode = false;

    // Define different sets of labels
    private final String[][] normalLabels = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "=", "+"},
            {"C", "(", ")", "⌫"}
    };

    private final String[][] scientificLabels = {
            {"sin", "cos", "tan", "/"},
            {"log", "ln", "^", "*"},
            {"π", "e", "√", "-"},
            {"0", ".", "=", "+"},
            {"C", "(", ")", "⌫"}
    };
    FocusListener redirect = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            // Redirect focus to the input field
            SwingUtilities.invokeLater(inputField::requestFocusInWindow);
        }

        @Override
        public void focusLost(FocusEvent e) {
            // Do nothing
        }
    };

    public CalculatorUI() {
        isResult = new AtomicBoolean(false);
        isError = new AtomicBoolean(false);

        // Set up the frame
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(360, 440);
        setLocationRelativeTo(null);

        // Create main container
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.setBackground(new Color(240, 240, 240));

        // Create display field
        inputField = new JTextField();
        inputField.setEditable(true);
        inputField.setHorizontalAlignment(JTextField.LEFT);
        inputField.setFont(new Font("Arial", Font.PLAIN, 20));
        inputField.setPreferredSize(new Dimension(300, 50));
        inputField.setBackground(Color.WHITE);

        // Add key listeners
        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && isResult.compareAndSet(true, false)) {
                    inputField.setText("");
                }else if (isError.compareAndSet(true, false)) {
                    inputField.setText("");
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculate();
                }else{
                    isError.set(false);
                    isResult.set(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Create mode toggle panel
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        CreateResultPanel(resultPanel);

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        createButtons(buttonPanel);

        // Add components to frame
        root.add(inputField, BorderLayout.NORTH);
        root.add(resultPanel, BorderLayout.CENTER);
        root.add(buttonPanel, BorderLayout.SOUTH);
        add(root);
    }

    private void createButtons(JPanel buttonPanel) {
        buttons = new JButton[5][4];
        for (int i = 0; i < normalLabels.length; i++) {
            for (int j = 0; j < normalLabels[i].length; j++) {
                buttons[i][j] = createStyledButton(normalLabels[i][j]);
                buttons[i][j].addFocusListener(redirect);


                // Style operator buttons
                if (j == 3 && i != 4) {
                    styleOperatorButton(buttons[i][j]);
                }
                // Style equals button
                if (i == 3 && j == 2) {
                    styleOperatorButton(buttons[i][j]);
                    buttons[i][j].addActionListener(_ -> calculate());
                }
                // Style utility buttons (last row)
                if (i == 4) {
                    styleUtilityButton(buttons[i][j]);
                }

                final int row = i;
                final int col = j;
                if (!(i == 3 && j == 2)) { // Skip equals button
                    buttons[i][j].addActionListener(_ -> handleButtonClick(row, col));
                }

                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    private void CreateResultPanel(JPanel resultPanel) {
        resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setPreferredSize(new Dimension(190, 30));
        resultField.setBackground(Color.WHITE);
        resultField.setHorizontalAlignment(JTextField.LEFT);
        resultField.setFont(new Font("Arial", Font.PLAIN, 20));
        resultField.addFocusListener(redirect);

        JButton modeButton = new JButton("Normal Mode");
        modeButton.addFocusListener(redirect);
        modeButton.setFocusPainted(false);
        modeButton.setContentAreaFilled(false); // Removes hover effect background
        modeButton.setPreferredSize(new Dimension(130, 30));
        modeButton.addActionListener(_ -> toggleMode(modeButton));

        resultPanel.add(resultField);
        resultPanel.add(modeButton);
    }

    private void handleButtonClick(int row, int col) {
        String buttonText = buttons[row][col].getText();
        if (buttonText.equals("C")) {
            inputField.setText("");
        } else if (buttonText.equals("⌫")) {
            String text = inputField.getText();
            if (!text.isEmpty()) {
                inputField.setText(text.substring(0, text.length() - 1));
            }
        } else {
            // Handle scientific mode special characters
            switch (buttonText) {
                case "sin":
                    inputField.setText(inputField.getText() + "sin(");
                    break;
                case "cos":
                    inputField.setText(inputField.getText() + "cos(");
                    break;
                case "tan":
                    inputField.setText(inputField.getText() + "tan(");
                    break;
                case "log":
                    inputField.setText(inputField.getText() + "log(");
                    break;
                case "ln":
                    inputField.setText(inputField.getText() + "ln(");
                    break;
                case "π":
                    inputField.setText(inputField.getText() + "π");
                    break;
                case "e":
                    inputField.setText(inputField.getText() + "exp");
                    break;
                case "√":
                    inputField.setText(inputField.getText() + "sqrt(");
                    break;
                default:
                    inputField.setText(inputField.getText() + buttonText);
            }
        }
    }

    private void toggleMode(JButton modeToggle ) {
        isScientificMode = !isScientificMode;
        modeToggle.setText(isScientificMode ? "Scientific Mode" : "Normal Mode");
        String[][] currentLabels = isScientificMode ? scientificLabels : normalLabels;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText(currentLabels[i][j]);
            }
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillOval(15, 0, getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(50, 50));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setBackground(new Color(230, 230, 230));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        return button;
    }

    private void styleOperatorButton(JButton button) {
        button.setBackground(new Color(255, 149, 0));
        button.setForeground(Color.WHITE);
    }

    private void styleUtilityButton(JButton button) {
        button.setBackground(new Color(165, 165, 165));
    }

    private void calculate() {
        inputField.setCaretPosition(inputField.getText().length());
        try {
            resultField.setText( Double.toString(calc.Calculatrice.calc(inputField.getText())));
            isResult.set(true);
        } catch (Exception e) {
            inputField.setText(e.getMessage());
            isError.set(true);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorUI().setVisible(true));
    }
}