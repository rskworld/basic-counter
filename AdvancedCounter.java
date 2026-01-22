import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;
import java.io.File;

/**
 * Advanced Counter Application
 * A feature-rich counter application with history, themes, shortcuts, and more.
 * 
 * @author RSK World
 * @website https://rskworld.in
 * @contact help@rskworld.in
 * @phone +91 93305 39277
 * @year 2026
 * @description Advanced counter application with multiple features including
 *              history tracking, themes, keyboard shortcuts, sound effects,
 *              and data persistence.
 */
public class AdvancedCounter extends JFrame implements ActionListener {
    // Core components
    private int counter = 0;
    private JLabel counterLabel;
    private JButton incrementButton;
    private JButton decrementButton;
    private JButton resetButton;
    
    // Advanced features
    private JTextArea historyArea;
    private JSlider stepSlider;
    private JLabel stepLabel;
    private JComboBox<String> themeComboBox;
    private JCheckBox soundCheckBox;
    private JButton saveButton;
    private JButton loadButton;
    private JButton clearHistoryButton;
    private JLabel statusLabel;
    
    // Data structures
    private List<String> history = new ArrayList<>();
    private Map<String, Color[]> themes = new HashMap<>();
    private String currentTheme = "Default";
    private int stepValue = 1;
    
    // Sound
    private Clip incrementSound;
    private Clip decrementSound;
    
    // File paths
    private static final String DATA_FILE = "counter_data.dat";
    private static final String HISTORY_FILE = "counter_history.txt";
    
    public AdvancedCounter() {
        initializeThemes();
        initializeSounds();
        setupUI();
        loadData();
        setupKeyboardShortcuts();
        
        // Add window listener for saving data on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                System.exit(0);
            }
        });
    }
    
    private void initializeThemes() {
        themes.put("Default", new Color[]{new Color(59, 130, 246), new Color(34, 197, 94), new Color(239, 68, 68)});
        themes.put("Dark", new Color[]{new Color(156, 163, 175), new Color(74, 222, 128), new Color(248, 113, 113)});
        themes.put("Ocean", new Color[]{new Color(6, 182, 212), new Color(34, 211, 238), new Color(251, 146, 60)});
        themes.put("Sunset", new Color[]{new Color(251, 191, 36), new Color(253, 224, 71), new Color(239, 68, 68)});
        themes.put("Forest", new Color[]{new Color(34, 197, 94), new Color(16, 185, 129), new Color(245, 158, 11)});
    }
    
    private void initializeSounds() {
        try {
            // Create simple beep sounds programmatically
            incrementSound = createBeepSound(800, 100);
            decrementSound = createBeepSound(400, 100);
        } catch (Exception e) {
            System.out.println("Sound initialization failed: " + e.getMessage());
        }
    }
    
    private Clip createBeepSound(int frequency, int duration) throws Exception {
        AudioFormat format = new AudioFormat(44100, 8, 1, true, false);
        byte[] buffer = new byte[44100 * duration / 1000];
        
        for (int i = 0; i < buffer.length; i++) {
            double angle = 2.0 * Math.PI * frequency * i / 44100;
            buffer[i] = (byte) (Math.sin(angle) * 127);
        }
        
        Clip clip = AudioSystem.getClip();
        clip.open(format, buffer, 0, buffer.length);
        return clip;
    }
    
    private void setupUI() {
        setTitle("Advanced Counter - RSK World Premium");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
        
        setLayout(new BorderLayout(10, 10));
        
        // Create main panels
        createTopPanel();
        createCenterPanel();
        createBottomPanel();
        createEastPanel();
        
        setVisible(true);
    }
    
    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Advanced Counter Application", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(31, 41, 55));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Status bar
        statusLabel = new JLabel("Ready | Theme: " + currentTheme + " | Step: " + stepValue, SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);
        topPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
    }
    
    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Counter display
        counterLabel = new JLabel("0", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 72));
        counterLabel.setForeground(themes.get(currentTheme)[0]);
        counterLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(counterLabel, BorderLayout.CENTER);
        
        // Control buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        
        decrementButton = createStyledButton("- " + stepValue, themes.get(currentTheme)[2]);
        resetButton = createStyledButton("Reset", new Color(107, 114, 128));
        incrementButton = createStyledButton("+ " + stepValue, themes.get(currentTheme)[1]);
        
        decrementButton.addActionListener(this);
        resetButton.addActionListener(this);
        incrementButton.addActionListener(this);
        
        buttonPanel.add(decrementButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(incrementButton);
        
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Step control
        JPanel stepPanel = new JPanel(new FlowLayout());
        stepPanel.add(new JLabel("Step Value:"));
        stepSlider = new JSlider(1, 10, stepValue);
        stepSlider.setMajorTickSpacing(1);
        stepSlider.setPaintTicks(true);
        stepSlider.setPaintLabels(true);
        stepSlider.addChangeListener(e -> updateStepValue());
        stepPanel.add(stepSlider);
        stepLabel = new JLabel("Step: " + stepValue);
        stepPanel.add(stepLabel);
        
        // Theme selector
        JPanel themePanel = new JPanel(new FlowLayout());
        themePanel.add(new JLabel("Theme:"));
        themeComboBox = new JComboBox<>(themes.keySet().toArray(new String[0]));
        themeComboBox.setSelectedItem(currentTheme);
        themeComboBox.addActionListener(e -> changeTheme());
        themePanel.add(themeComboBox);
        
        // Sound checkbox
        soundCheckBox = new JCheckBox("Sound Effects", true);
        themePanel.add(soundCheckBox);
        
        // Combine controls
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        controlPanel.add(stepPanel);
        controlPanel.add(themePanel);
        
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        
        // History area
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("History"));
        historyArea = new JTextArea(5, 30);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        // History buttons
        JPanel historyButtonPanel = new JPanel(new FlowLayout());
        clearHistoryButton = new JButton("Clear History");
        clearHistoryButton.addActionListener(this);
        historyButtonPanel.add(clearHistoryButton);
        historyPanel.add(historyButtonPanel, BorderLayout.SOUTH);
        
        bottomPanel.add(historyPanel, BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void createEastPanel() {
        JPanel eastPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        eastPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        saveButton = createStyledButton("Save Data", new Color(59, 130, 246));
        loadButton = createStyledButton("Load Data", new Color(34, 197, 94));
        JButton exportButton = createStyledButton("Export History", new Color(245, 158, 11));
        JButton aboutButton = createStyledButton("About", new Color(107, 114, 128));
        
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        exportButton.addActionListener(e -> exportHistory());
        aboutButton.addActionListener(e -> showAbout());
        
        eastPanel.add(saveButton);
        eastPanel.add(loadButton);
        eastPanel.add(exportButton);
        eastPanel.add(aboutButton);
        
        add(eastPanel, BorderLayout.EAST);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void setupKeyboardShortcuts() {
        JRootPane rootPane = getRootPane();
        
        // Ctrl+Plus for increment
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK), "increment");
        rootPane.getActionMap().put("increment", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { incrementCounter(); }
        });
        
        // Ctrl+Minus for decrement
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK), "decrement");
        rootPane.getActionMap().put("decrement", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { decrementCounter(); }
        });
        
        // Ctrl+R for reset
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), "reset");
        rootPane.getActionMap().put("reset", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { resetCounter(); }
        });
        
        // Ctrl+S for save
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "save");
        rootPane.getActionMap().put("save", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { saveData(); }
        });
        
        // Ctrl+L for load
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK), "load");
        rootPane.getActionMap().put("load", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { loadData(); }
        });
    }
    
    private void updateStepValue() {
        stepValue = stepSlider.getValue();
        stepLabel.setText("Step: " + stepValue);
        incrementButton.setText("+ " + stepValue);
        decrementButton.setText("- " + stepValue);
        updateStatus();
    }
    
    private void changeTheme() {
        currentTheme = (String) themeComboBox.getSelectedItem();
        Color[] colors = themes.get(currentTheme);
        counterLabel.setForeground(colors[0]);
        incrementButton.setBackground(colors[1]);
        decrementButton.setBackground(colors[2]);
        updateStatus();
    }
    
    private void incrementCounter() {
        counter += stepValue;
        updateCounterDisplay();
        addToHistory("Incremented by " + stepValue);
        playSound(incrementSound);
    }
    
    private void decrementCounter() {
        counter -= stepValue;
        updateCounterDisplay();
        addToHistory("Decremented by " + stepValue);
        playSound(decrementSound);
    }
    
    private void resetCounter() {
        int oldValue = counter;
        counter = 0;
        updateCounterDisplay();
        addToHistory("Reset from " + oldValue + " to 0");
        playSound(decrementSound);
    }
    
    private void updateCounterDisplay() {
        counterLabel.setText(String.valueOf(counter));
        
        Color[] colors = themes.get(currentTheme);
        if (counter > 0) {
            counterLabel.setForeground(colors[1]);
        } else if (counter < 0) {
            counterLabel.setForeground(colors[2]);
        } else {
            counterLabel.setForeground(colors[0]);
        }
    }
    
    private void addToHistory(String action) {
        String timestamp = new Date().toString();
        String entry = String.format("[%s] %s | Counter: %d%n", timestamp, action, counter);
        history.add(entry);
        historyArea.insert(entry, 0);
        
        // Limit history to 100 entries
        if (history.size() > 100) {
            history.remove(0);
        }
    }
    
    private void playSound(Clip sound) {
        if (soundCheckBox.isSelected() && sound != null) {
            sound.setFramePosition(0);
            sound.start();
        }
    }
    
    private void updateStatus() {
        statusLabel.setText("Ready | Theme: " + currentTheme + " | Step: " + stepValue + " | History: " + history.size());
    }
    
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeInt(counter);
            oos.writeObject(history);
            oos.writeObject(currentTheme);
            oos.writeInt(stepValue);
            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            counter = ois.readInt();
            history = (List<String>) ois.readObject();
            currentTheme = (String) ois.readObject();
            stepValue = ois.readInt();
            
            // Update UI
            updateCounterDisplay();
            stepSlider.setValue(stepValue);
            themeComboBox.setSelectedItem(currentTheme);
            updateStepValue();
            changeTheme();
            
            // Update history area
            historyArea.setText("");
            for (String entry : history) {
                historyArea.append(entry);
            }
            
            updateStatus();
            JOptionPane.showMessageDialog(this, "Data loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // File doesn't exist or is corrupted - that's okay for first run
        }
    }
    
    private void exportHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            writer.println("=== COUNTER HISTORY EXPORT ===");
            writer.println("Exported on: " + new Date());
            writer.println("Current Counter Value: " + counter);
            writer.println("Total History Entries: " + history.size());
            writer.println();
            
            for (String entry : history) {
                writer.print(entry);
            }
            
            JOptionPane.showMessageDialog(this, "History exported to " + HISTORY_FILE, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting history: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearHistory() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all history?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            history.clear();
            historyArea.setText("");
            updateStatus();
            JOptionPane.showMessageDialog(this, "History cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showAbout() {
        String aboutText = "Advanced Counter Application\n\n" +
                         "Version: 2.0 Premium\n" +
                         "Author: RSK World\n" +
                         "Website: https://rskworld.in\n" +
                         "Contact: help@rskworld.in\n" +
                         "Phone: +91 93305 39277\n\n" +
                         "Features:\n" +
                         "• Multiple themes\n" +
                         "• Adjustable step values\n" +
                         "• History tracking\n" +
                         "• Keyboard shortcuts\n" +
                         "• Sound effects\n" +
                         "• Data persistence\n" +
                         "• Export functionality\n\n" +
                         "© 2026 RSK World. All rights reserved.";
        
        JOptionPane.showMessageDialog(this, aboutText, "About Advanced Counter", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == incrementButton) {
            incrementCounter();
        } else if (e.getSource() == decrementButton) {
            decrementCounter();
        } else if (e.getSource() == resetButton) {
            resetCounter();
        } else if (e.getSource() == saveButton) {
            saveData();
        } else if (e.getSource() == loadButton) {
            loadData();
        } else if (e.getSource() == clearHistoryButton) {
            clearHistory();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdvancedCounter();
        });
    }
}
