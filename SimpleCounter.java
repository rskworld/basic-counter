import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCounter extends JFrame implements ActionListener {
    private int counter = 0;
    private JLabel counterLabel;
    private JButton incrementButton;
    private JButton decrementButton;
    private JButton resetButton;
    
    public SimpleCounter() {
        setTitle("Basic Counter - RSK World");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
        addComponentsToFrame();
        setVisible(true);
    }
    
    private void initComponents() {
        counterLabel = new JLabel("0", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 48));
        counterLabel.setForeground(new Color(59, 130, 246));
        
        incrementButton = new JButton("+ Increment");
        decrementButton = new JButton("- Decrement");
        resetButton = new JButton("Reset");
        
        incrementButton.setFont(new Font("Arial", Font.PLAIN, 14));
        decrementButton.setFont(new Font("Arial", Font.PLAIN, 14));
        resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        
        incrementButton.setBackground(new Color(34, 197, 94));
        incrementButton.setForeground(Color.WHITE);
        incrementButton.setFocusPainted(false);
        
        decrementButton.setBackground(new Color(239, 68, 68));
        decrementButton.setForeground(Color.WHITE);
        decrementButton.setFocusPainted(false);
        
        resetButton.setBackground(new Color(107, 114, 128));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        
        incrementButton.addActionListener(this);
        decrementButton.addActionListener(this);
        resetButton.addActionListener(this);
        
        addButtonHoverEffect(incrementButton);
        addButtonHoverEffect(decrementButton);
        addButtonHoverEffect(resetButton);
    }
    
    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }
        });
    }
    
    private void addComponentsToFrame() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Basic Counter Application", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(31, 41, 55));
        titlePanel.add(titleLabel);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(counterLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        buttonPanel.add(decrementButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(incrementButton);
        
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("© 2026 RSK World | https://rskworld.in", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(footerPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == incrementButton) {
            counter++;
            updateCounterDisplay();
        } else if (e.getSource() == decrementButton) {
            counter--;
            updateCounterDisplay();
        } else if (e.getSource() == resetButton) {
            counter = 0;
            updateCounterDisplay();
        }
    }
    
    private void updateCounterDisplay() {
        counterLabel.setText(String.valueOf(counter));
        
        if (counter > 0) {
            counterLabel.setForeground(new Color(34, 197, 94));
        } else if (counter < 0) {
            counterLabel.setForeground(new Color(239, 68, 68));
        } else {
            counterLabel.setForeground(new Color(59, 130, 246));
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleCounter();
            }
        });
    }
}
