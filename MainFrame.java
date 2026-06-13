import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class MainFrame extends JFrame{
    static JFrame frame;
    private JButton generateButton;
    private JTextField lengthField;
    private JLabel resultPanel;
    private JLabel resultLabel;
    private JTextArea historyArea;
    private JLabel strengthLabel;
    JPanel pMain;
    private ArrayList<String> passwordHistory;
    public MainFrame(){
        passwordHistory = new ArrayList<>();
        frame = new JFrame("Приложение для паролей");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JScrollPane scrtable = new JScrollPane(VIS_TABLE);
        Container myC = frame.getContentPane();
        myC.setLayout(new BorderLayout(5,5));
        //MenuIS s = new MenuIS();
        //frame.setJMenuBar(s.mb1);
        frame.setSize(800, 400);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.add(new JLabel("Длина пароля:"));
        lengthField = new JTextField("12", 5);
        inputPanel.add(lengthField);
        inputPanel.add(new JLabel("символов"));
        generateButton = new JButton("Сгенерировать");
        generateButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        generateButton.addActionListener(e -> generatePassword());
        inputPanel.add(generateButton);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultLabel = new JLabel("Пароль появится здесь", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        resultLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        strengthLabel = new JLabel("", SwingConstants.CENTER);
        strengthLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        centerPanel.add(resultLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(strengthLabel);
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("История паролей"));
        historyArea = new JTextArea(5, 40);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        JButton copyButton = new JButton("Копировать последний пароль");
        copyButton.addActionListener(e -> {
            if(!passwordHistory.isEmpty()){
                String last = passwordHistory.get(passwordHistory.size() - 1);
                Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new java.awt.datatransfer.StringSelection(last), null);
                JOptionPane.showMessageDialog(this, "Пароль скопирован!", "Успех",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JButton clearButton = new JButton("Очистить историю");
        clearButton.addActionListener(e -> {
            passwordHistory.clear();
            historyArea.setText("");
        });
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(copyButton);
        btnPanel.add(clearButton);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        historyPanel.add(btnPanel, BorderLayout.SOUTH);
        myC.add(inputPanel, BorderLayout.NORTH);
        myC.add(centerPanel, BorderLayout.CENTER);
        myC.add(historyPanel, BorderLayout.SOUTH);
        lengthField.addActionListener(e -> generatePassword());
        
    }
    private void generatePassword(){
        try{
            int length = Integer.parseInt(lengthField.getText().trim());
            if (length < 4 || length > 50){
                JOptionPane.showMessageDialog(this,
                    "Длина пароля должна быть от 4 до 50 символов",
                    "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String password = generator.gen(length);
            String strength = generator.proverka(password);
            resultLabel.setText(password);
            strengthLabel.setText(strength);
            if (strength.contains("не")){
                strengthLabel.setForeground(Color.RED);
            } else if (strength.contains("средний")){
                strengthLabel.setForeground(new Color(255, 140, 0));
            }else{
                strengthLabel.setForeground(new Color(0, 128, 0));
            }
            passwordHistory.add(password);
            updateHistory();
            } catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this,
                    "Введите корректное число",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        
        }
    private void updateHistory(){
            StringBuilder sb = new StringBuilder();
            int start = Math.max(0, passwordHistory.size() - 10);
            for (int i = start; i < passwordHistory.size(); i++){
                sb.append(String.format("%2d. %s%n", i + 1, passwordHistory.get(i)));
            }
            historyArea.setText(sb.toString());
            historyArea.setCaretPosition(historyArea.getDocument().getLength());
        }
    }
    
