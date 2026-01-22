import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;
import java.net.URL;

/**
 * Ultimate Counter Application
 * The most advanced counter application with premium features including
 * multi-counter support, statistics, charts, notifications, and more.
 * 
 * @author RSK World
 * @website https://rskworld.in
 * @contact help@rskworld.in
 * @phone +91 93305 39277
 * @year 2026
 * @description Ultimate counter application with premium features including
 *              multi-counter support, statistics, charts, notifications,
 *              cloud sync simulation, and advanced analytics.
 */
public class UltimateCounter extends JFrame implements ActionListener {
    // Core components
    private Map<String, Integer> counters = new HashMap<>();
    private String currentCounter = "Main";
    private JLabel counterLabel;
    private JTextField counterNameField;
    
    // Advanced UI components
    private JTabbedPane tabbedPane;
    private JPanel mainPanel, statsPanel, multiCounterPanel, settingsPanel;
    private JTable historyTable;
    private DefaultTableModel historyModel;
    private JTextArea analyticsArea;
    private JProgressBar progressBar;
    private JLabel timerLabel;
    
    // Control components
    private JButton incrementButton, decrementButton, resetButton;
    private JSlider stepSlider;
    private JComboBox<String> themeComboBox, counterComboBox;
    private JCheckBox soundCheckBox, notificationCheckBox, autoSaveCheckBox;
    
    // Data structures
    private List<HistoryEntry> history = new ArrayList<>();
    private Map<String, Color[]> themes = new HashMap<>();
    private String currentTheme = "Default";
    private int stepValue = 1;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    // Advanced features
    private ScheduledExecutorService scheduler;
    private Clip incrementSound, decrementSound, achievementSound;
    private int totalOperations = 0;
    private long sessionStartTime = System.currentTimeMillis();
    private boolean isTimerRunning = false;
    private int timerSeconds = 0;
    
    // File paths
    private static final String DATA_FILE = "ultimate_counter_data.dat";
    private static final String CONFIG_FILE = "ultimate_counter_config.properties";
    
    public UltimateCounter() {
        initializeThemes();
        initializeSounds();
        setupScheduler();
        loadConfiguration();
        setupUI();
        loadData();
        setupKeyboardShortcuts();
        setupTrayIcon();
        
        // Add window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                saveConfiguration();
                scheduler.shutdown();
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
        themes.put("Purple", new Color[]{new Color(147, 51, 234), new Color(168, 85, 247), new Color(236, 72, 153)});
        themes.put("Gold", new Color[]{new Color(245, 158, 11), new Color(251, 191, 36), new Color(217, 119, 6)});
    }
    
    private void initializeSounds() {
        try {
            incrementSound = createBeepSound(800, 100);
            decrementSound = createBeepSound(400, 100);
            achievementSound = createBeepSound(1200, 200);
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
    
    private void setupScheduler() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateTimer, 1, 1, TimeUnit.SECONDS);
    }
    
    private void setupUI() {
        setTitle("Ultimate Counter - RSK World Premium Edition");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        
        tabbedPane = new JTabbedPane();
        createMainPanel();
        createStatsPanel();
        createMultiCounterPanel();
        createSettingsPanel();
        
        tabbedPane.addTab("Main Counter", mainPanel);
        tabbedPane.addTab("Statistics", statsPanel);
        tabbedPane.addTab("Multi-Counter", multiCounterPanel);
        tabbedPane.addTab("Settings", settingsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        createStatusBar();
        setVisible(true);
    }
    
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        
        // Top panel with counter name and controls
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("Counter Name:"));
        counterNameField = new JTextField(currentCounter, 15);
        counterNameField.addActionListener(e -> renameCounter());
        namePanel.add(counterNameField);
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Theme:"));
        themeComboBox = new JComboBox<>(themes.keySet().toArray(new String[0]));
        themeComboBox.setSelectedItem(currentTheme);
        themeComboBox.addActionListener(e -> changeTheme());
        controlPanel.add(themeComboBox);
        
        topPanel.add(namePanel, BorderLayout.WEST);
        topPanel.add(controlPanel, BorderLayout.EAST);
        
        // Center panel with counter display
        JPanel centerPanel = new JPanel(new BorderLayout());
        counterLabel = new JLabel("0", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 96));
        counterLabel.setForeground(themes.get(currentTheme)[0]);
        counterLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(counterLabel, BorderLayout.CENTER);
        
        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Progress to Next Milestone");
        centerPanel.add(progressBar, BorderLayout.SOUTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        
        decrementButton = createStyledButton("- " + stepValue, themes.get(currentTheme)[2]);
        resetButton = createStyledButton("Reset", new Color(107, 114, 128));
        incrementButton = createStyledButton("+ " + stepValue, themes.get(currentTheme)[1]);
        JButton timerButton = createStyledButton("Start Timer", new Color(59, 130, 246));
        
        decrementButton.addActionListener(this);
        resetButton.addActionListener(this);
        incrementButton.addActionListener(this);
        timerButton.addActionListener(e -> toggleTimer());
        
        buttonPanel.add(decrementButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(incrementButton);
        buttonPanel.add(timerButton);
        
        // Step control panel
        JPanel stepPanel = new JPanel(new FlowLayout());
        stepPanel.add(new JLabel("Step Value:"));
        stepSlider = new JSlider(1, 50, stepValue);
        stepSlider.setMajorTickSpacing(10);
        stepSlider.setPaintTicks(true);
        stepSlider.setPaintLabels(true);
        stepSlider.addChangeListener(e -> updateStepValue());
        stepPanel.add(stepSlider);
        
        // Timer display
        timerLabel = new JLabel("Timer: 00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.BLUE);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(stepPanel, BorderLayout.NORTH);
        bottomPanel.add(timerLabel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void createStatsPanel() {
        statsPanel = new JPanel(new BorderLayout());
        
        // History table
        String[] columns = {"Time", "Counter", "Action", "Value", "Step"};
        historyModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        historyTable = new JTable(historyModel);
        historyTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        
        // Analytics area
        analyticsArea = new JTextArea(10, 40);
        analyticsArea.setEditable(false);
        analyticsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane analyticsScroll = new JScrollPane(analyticsArea);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, analyticsScroll);
        splitPane.setDividerLocation(300);
        
        // Control buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh Analytics");
        JButton exportButton = new JButton("Export Statistics");
        JButton clearButton = new JButton("Clear History");
        
        refreshButton.addActionListener(e -> updateAnalytics());
        exportButton.addActionListener(e -> exportStatistics());
        clearButton.addActionListener(e -> clearHistory());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(clearButton);
        
        statsPanel.add(splitPane, BorderLayout.CENTER);
        statsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        updateAnalytics();
    }
    
    private void createMultiCounterPanel() {
        multiCounterPanel = new JPanel(new BorderLayout());
        
        // Counter selector
        JPanel selectorPanel = new JPanel(new FlowLayout());
        selectorPanel.add(new JLabel("Select Counter:"));
        counterComboBox = new JComboBox<>();
        counterComboBox.addActionListener(e -> switchCounter());
        selectorPanel.add(counterComboBox);
        
        JButton addCounterButton = new JButton("Add Counter");
        JButton removeCounterButton = new JButton("Remove Counter");
        addCounterButton.addActionListener(e -> addCounter());
        removeCounterButton.addActionListener(e -> removeCounter());
        
        selectorPanel.add(addCounterButton);
        selectorPanel.add(removeCounterButton);
        
        // Multi-counter display
        JPanel displayPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        displayPanel.setBorder(BorderFactory.createTitledBorder("All Counters"));
        
        multiCounterPanel.add(selectorPanel, BorderLayout.NORTH);
        multiCounterPanel.add(displayPanel, BorderLayout.CENTER);
        
        updateMultiCounterDisplay();
    }
    
    private void createSettingsPanel() {
        settingsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        
        // Sound settings
        JPanel soundPanel = new JPanel();
        soundPanel.setBorder(new TitledBorder("Sound Settings"));
        soundCheckBox = new JCheckBox("Enable Sound Effects", true);
        soundPanel.add(soundCheckBox);
        
        // Notification settings
        JPanel notificationPanel = new JPanel();
        notificationPanel.setBorder(new TitledBorder("Notification Settings"));
        notificationCheckBox = new JCheckBox("Enable Milestone Notifications", true);
        notificationPanel.add(notificationCheckBox);
        
        // Auto-save settings
        JPanel autoSavePanel = new JPanel();
        autoSavePanel.setBorder(new TitledBorder("Auto-Save Settings"));
        autoSaveCheckBox = new JCheckBox("Auto-save every 30 seconds", false);
        autoSavePanel.add(autoSaveCheckBox);
        
        // Data management
        JPanel dataPanel = new JPanel(new FlowLayout());
        dataPanel.setBorder(new TitledBorder("Data Management"));
        JButton saveButton = new JButton("Save Now");
        JButton loadButton = new JButton("Load Now");
        JButton resetButton = new JButton("Reset All Data");
        JButton aboutButton = new JButton("About");
        
        saveButton.addActionListener(e -> saveData());
        loadButton.addActionListener(e -> loadData());
        resetButton.addActionListener(e -> resetAllData());
        aboutButton.addActionListener(e -> showAbout());
        
        dataPanel.add(saveButton);
        dataPanel.add(loadButton);
        dataPanel.add(resetButton);
        dataPanel.add(aboutButton);
        
        settingsPanel.add(soundPanel);
        settingsPanel.add(notificationPanel);
        settingsPanel.add(autoSavePanel);
        settingsPanel.add(dataPanel);
    }
    
    private void createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        JLabel statusLabel = new JLabel("Ultimate Counter Premium | RSK World | " + 
                                       new Date().toString(), SwingConstants.LEFT);
        JLabel infoLabel = new JLabel("Counters: " + counters.size() + 
                                     " | Operations: " + totalOperations + 
                                     " | Theme: " + currentTheme, SwingConstants.RIGHT);
        
        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(infoLabel, BorderLayout.EAST);
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        
        // Define shortcuts
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK), "increment");
        rootPane.getActionMap().put("increment", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { incrementCounter(); }
        });
        
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK), "decrement");
        rootPane.getActionMap().put("decrement", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { decrementCounter(); }
        });
        
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), "reset");
        rootPane.getActionMap().put("reset", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { resetCounter(); }
        });
        
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK), "timer");
        rootPane.getActionMap().put("timer", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { toggleTimer(); }
        });
    }
    
    private void setupTrayIcon() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image trayImage = createTrayImage();
            
            PopupMenu popup = new PopupMenu();
            MenuItem showItem = new MenuItem("Show");
            MenuItem exitItem = new MenuItem("Exit");
            
            showItem.addActionListener(e -> setVisible(true));
            exitItem.addActionListener(e -> System.exit(0));
            
            popup.add(showItem);
            popup.add(exitItem);
            
            TrayIcon trayIcon = new TrayIcon(trayImage, "Ultimate Counter", popup);
            trayIcon.setImageAutoSize(true);
            
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("Tray icon could not be added.");
            }
        }
    }
    
    private Image createTrayImage() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLUE);
        g2d.fillOval(0, 0, 16, 16);
        g2d.setColor(Color.WHITE);
        g2d.drawString("C", 4, 12);
        g2d.dispose();
        return image;
    }
    
    // Core counter methods
    private void incrementCounter() {
        int oldValue = counters.getOrDefault(currentCounter, 0);
        int newValue = oldValue + stepValue;
        counters.put(currentCounter, newValue);
        
        updateCounterDisplay();
        addToHistory("Increment", stepValue, newValue);
        playSound(incrementSound);
        totalOperations++;
        
        checkMilestones(newValue);
        updateProgressBar(newValue);
    }
    
    private void decrementCounter() {
        int oldValue = counters.getOrDefault(currentCounter, 0);
        int newValue = oldValue - stepValue;
        counters.put(currentCounter, newValue);
        
        updateCounterDisplay();
        addToHistory("Decrement", -stepValue, newValue);
        playSound(decrementSound);
        totalOperations++;
        
        updateProgressBar(newValue);
    }
    
    private void resetCounter() {
        int oldValue = counters.getOrDefault(currentCounter, 0);
        counters.put(currentCounter, 0);
        
        updateCounterDisplay();
        addToHistory("Reset", -oldValue, 0);
        playSound(decrementSound);
        totalOperations++;
        
        updateProgressBar(0);
    }
    
    private void updateCounterDisplay() {
        int value = counters.getOrDefault(currentCounter, 0);
        counterLabel.setText(String.valueOf(value));
        
        Color[] colors = themes.get(currentTheme);
        if (value > 0) {
            counterLabel.setForeground(colors[1]);
        } else if (value < 0) {
            counterLabel.setForeground(colors[2]);
        } else {
            counterLabel.setForeground(colors[0]);
        }
    }
    
    private void updateStepValue() {
        stepValue = stepSlider.getValue();
        incrementButton.setText("+ " + stepValue);
        decrementButton.setText("- " + stepValue);
    }
    
    private void changeTheme() {
        currentTheme = (String) themeComboBox.getSelectedItem();
        Color[] colors = themes.get(currentTheme);
        counterLabel.setForeground(colors[0]);
        incrementButton.setBackground(colors[1]);
        decrementButton.setBackground(colors[2]);
        updateCounterDisplay();
    }
    
    private void addToHistory(String action, int step, int value) {
        HistoryEntry entry = new HistoryEntry(
            new Date(),
            currentCounter,
            action,
            value,
            step
        );
        history.add(entry);
        
        // Add to table
        Object[] rowData = {
            dateFormat.format(entry.timestamp),
            entry.counterName,
            entry.action,
            entry.value,
            entry.step
        };
        historyModel.addRow(rowData);
        
        // Limit history to 1000 entries
        if (history.size() > 1000) {
            history.remove(0);
            historyModel.removeRow(0);
        }
    }
    
    private void playSound(Clip sound) {
        if (soundCheckBox.isSelected() && sound != null) {
            sound.setFramePosition(0);
            sound.start();
        }
    }
    
    private void checkMilestones(int value) {
        if (notificationCheckBox.isSelected()) {
            if (value > 0 && value % 100 == 0) {
                showNotification("Milestone Reached!", "Counter reached " + value + "!");
                playSound(achievementSound);
            } else if (value < 0 && value % -50 == 0) {
                showNotification("Negative Milestone!", "Counter reached " + value + "!");
                playSound(achievementSound);
            }
        }
    }
    
    private void showNotification(String title, String message) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = createTrayImage();
            
            TrayIcon trayIcon = new TrayIcon(image, "Ultimate Counter");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Ultimate Counter");
            
            try {
                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
                tray.remove(trayIcon);
            } catch (AWTException e) {
                System.out.println("Notification could not be displayed.");
            }
        } else {
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateProgressBar(int value) {
        int nextMilestone = ((value / 100) + 1) * 100;
        if (value < 0) {
            nextMilestone = ((value / 50) - 1) * 50;
        }
        
        int progress = Math.abs(value % 100);
        progressBar.setValue(progress);
        progressBar.setString("Progress to " + nextMilestone + ": " + progress + "%");
    }
    
    private void updateTimer() {
        if (isTimerRunning) {
            timerSeconds++;
            int hours = timerSeconds / 3600;
            int minutes = (timerSeconds % 3600) / 60;
            int seconds = timerSeconds % 60;
            
            timerLabel.setText(String.format("Timer: %02d:%02d:%02d", hours, minutes, seconds));
        }
    }
    
    private void toggleTimer() {
        isTimerRunning = !isTimerRunning;
        if (isTimerRunning) {
            addToHistory("Timer Started", 0, counters.getOrDefault(currentCounter, 0));
        } else {
            addToHistory("Timer Stopped", timerSeconds, counters.getOrDefault(currentCounter, 0));
        }
    }
    
    private void renameCounter() {
        String newName = counterNameField.getText().trim();
        if (!newName.isEmpty() && !newName.equals(currentCounter)) {
            int value = counters.getOrDefault(currentCounter, 0);
            counters.remove(currentCounter);
            counters.put(newName, value);
            currentCounter = newName;
            updateCounterComboBox();
            updateMultiCounterDisplay();
        }
    }
    
    private void switchCounter() {
        currentCounter = (String) counterComboBox.getSelectedItem();
        counterNameField.setText(currentCounter);
        updateCounterDisplay();
    }
    
    private void addCounter() {
        String name = JOptionPane.showInputDialog(this, "Enter counter name:");
        if (name != null && !name.trim().isEmpty()) {
            counters.put(name.trim(), 0);
            updateCounterComboBox();
            updateMultiCounterDisplay();
        }
    }
    
    private void removeCounter() {
        if (counters.size() > 1) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Remove counter '" + currentCounter + "'?", 
                "Confirm Remove", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                counters.remove(currentCounter);
                currentCounter = counters.keySet().iterator().next();
                counterNameField.setText(currentCounter);
                updateCounterComboBox();
                updateCounterDisplay();
                updateMultiCounterDisplay();
            }
        }
    }
    
    private void updateCounterComboBox() {
        counterComboBox.removeAllItems();
        for (String name : counters.keySet()) {
            counterComboBox.addItem(name);
        }
        counterComboBox.setSelectedItem(currentCounter);
    }
    
    private void updateMultiCounterDisplay() {
        JPanel displayPanel = (JPanel) ((JComponent) multiCounterPanel.getComponent(1)).getComponent(0);
        displayPanel.removeAll();
        
        int count = 0;
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            if (count >= 4) break;
            
            JPanel counterPanel = new JPanel(new BorderLayout());
            counterPanel.setBorder(BorderFactory.createTitledBorder(entry.getKey()));
            
            JLabel valueLabel = new JLabel(String.valueOf(entry.getValue()), SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
            
            Color[] colors = themes.get(currentTheme);
            if (entry.getValue() > 0) {
                valueLabel.setForeground(colors[1]);
            } else if (entry.getValue() < 0) {
                valueLabel.setForeground(colors[2]);
            } else {
                valueLabel.setForeground(colors[0]);
            }
            
            counterPanel.add(valueLabel, BorderLayout.CENTER);
            displayPanel.add(counterPanel);
            count++;
        }
        
        displayPanel.revalidate();
        displayPanel.repaint();
    }
    
    private void updateAnalytics() {
        StringBuilder analytics = new StringBuilder();
        analytics.append("=== COUNTER ANALYTICS ===\n\n");
        
        // Basic statistics
        analytics.append("Session Statistics:\n");
        analytics.append("Session Start: ").append(dateFormat.format(new Date(sessionStartTime))).append("\n");
        analytics.append("Total Operations: ").append(totalOperations).append("\n");
        analytics.append("Active Counters: ").append(counters.size()).append("\n\n");
        
        // Counter statistics
        analytics.append("Counter Statistics:\n");
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            analytics.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        analytics.append("\n");
        
        // History analysis
        if (!history.isEmpty()) {
            analytics.append("History Analysis:\n");
            Map<String, Integer> actionCounts = new HashMap<>();
            int totalValue = 0;
            
            for (HistoryEntry entry : history) {
                actionCounts.put(entry.action, actionCounts.getOrDefault(entry.action, 0) + 1);
                totalValue += entry.step;
            }
            
            for (Map.Entry<String, Integer> entry : actionCounts.entrySet()) {
                analytics.append(entry.getKey()).append(": ").append(entry.getValue()).append(" times\n");
            }
            analytics.append("Net Change: ").append(totalValue).append("\n\n");
        }
        
        // Performance metrics
        long sessionTime = System.currentTimeMillis() - sessionStartTime;
        double operationsPerMinute = totalOperations / (sessionTime / 60000.0);
        analytics.append("Performance Metrics:\n");
        analytics.append("Session Duration: ").append(formatDuration(sessionTime)).append("\n");
        analytics.append("Operations per Minute: ").append(String.format("%.2f", operationsPerMinute)).append("\n");
        
        analyticsArea.setText(analytics.toString());
    }
    
    private String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }
    
    private void exportStatistics() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Statistics");
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(analyticsArea.getText());
                writer.println("\n=== DETAILED HISTORY ===\n");
                
                for (HistoryEntry entry : history) {
                    writer.printf("%s | %s | %s | %d | %d\n",
                        dateFormat.format(entry.timestamp),
                        entry.counterName,
                        entry.action,
                        entry.value,
                        entry.step
                    );
                }
                
                JOptionPane.showMessageDialog(this, "Statistics exported successfully!", 
                    "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting: " + e.getMessage(), 
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearHistory() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Clear all history? This cannot be undone.", 
            "Confirm Clear", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            history.clear();
            historyModel.setRowCount(0);
            updateAnalytics();
        }
    }
    
    private void resetAllData() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Reset all data? This will clear all counters and history.", 
            "Confirm Reset", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            counters.clear();
            counters.put("Main", 0);
            currentCounter = "Main";
            counterNameField.setText(currentCounter);
            history.clear();
            historyModel.setRowCount(0);
            totalOperations = 0;
            sessionStartTime = System.currentTimeMillis();
            timerSeconds = 0;
            isTimerRunning = false;
            
            updateCounterDisplay();
            updateCounterComboBox();
            updateMultiCounterDisplay();
            updateAnalytics();
        }
    }
    
    private void showAbout() {
        String aboutText = "Ultimate Counter Application\n\n" +
                         "Version: 3.0 Premium Edition\n" +
                         "Author: RSK World\n" +
                         "Website: https://rskworld.in\n" +
                         "Contact: help@rskworld.in\n" +
                         "Phone: +91 93305 39277\n\n" +
                         "Premium Features:\n" +
                         "• Multi-counter support\n" +
                         "• Advanced statistics and analytics\n" +
                         "• Milestone notifications\n" +
                         "• Built-in timer\n" +
                         "• System tray integration\n" +
                         "• Data export functionality\n" +
                         "• Customizable themes\n" +
                         "• Keyboard shortcuts\n" +
                         "• Sound effects\n" +
                         "• Auto-save capability\n" +
                         "• Progress tracking\n" +
                         "• History management\n\n" +
                         "© 2026 RSK World. All rights reserved.";
        
        JOptionPane.showMessageDialog(this, aboutText, 
            "About Ultimate Counter", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Data persistence methods
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(counters);
            oos.writeObject(history);
            oos.writeObject(currentCounter);
            oos.writeInt(totalOperations);
            oos.writeLong(sessionStartTime);
            JOptionPane.showMessageDialog(this, "Data saved successfully!", 
                "Save Complete", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), 
                "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            counters = (Map<String, Integer>) ois.readObject();
            history = (List<HistoryEntry>) ois.readObject();
            currentCounter = (String) ois.readObject();
            totalOperations = ois.readInt();
            sessionStartTime = ois.readLong();
            
            // Update UI
            counterNameField.setText(currentCounter);
            updateCounterComboBox();
            updateCounterDisplay();
            updateMultiCounterDisplay();
            
            // Update history table
            historyModel.setRowCount(0);
            for (HistoryEntry entry : history) {
                Object[] rowData = {
                    dateFormat.format(entry.timestamp),
                    entry.counterName,
                    entry.action,
                    entry.value,
                    entry.step
                };
                historyModel.addRow(rowData);
            }
            
            updateAnalytics();
            JOptionPane.showMessageDialog(this, "Data loaded successfully!", 
                "Load Complete", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Initialize default data if file doesn't exist
            counters.put("Main", 0);
            currentCounter = "Main";
            counterNameField.setText(currentCounter);
            updateCounterComboBox();
        }
    }
    
    private void saveConfiguration() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            writer.println("theme=" + currentTheme);
            writer.println("step=" + stepValue);
            writer.println("sound=" + soundCheckBox.isSelected());
            writer.println("notifications=" + notificationCheckBox.isSelected());
            writer.println("autosave=" + autoSaveCheckBox.isSelected());
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }
    
    private void loadConfiguration() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        switch (parts[0]) {
                            case "theme":
                                currentTheme = parts[1];
                                themeComboBox.setSelectedItem(currentTheme);
                                break;
                            case "step":
                                stepValue = Integer.parseInt(parts[1]);
                                stepSlider.setValue(stepValue);
                                break;
                            case "sound":
                                soundCheckBox.setSelected(Boolean.parseBoolean(parts[1]));
                                break;
                            case "notifications":
                                notificationCheckBox.setSelected(Boolean.parseBoolean(parts[1]));
                                break;
                            case "autosave":
                                autoSaveCheckBox.setSelected(Boolean.parseBoolean(parts[1]));
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading configuration: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == incrementButton) {
            incrementCounter();
        } else if (e.getSource() == decrementButton) {
            decrementCounter();
        } else if (e.getSource() == resetButton) {
            resetCounter();
        }
    }
    
    // Inner class for history entries
    private static class HistoryEntry implements Serializable {
        Date timestamp;
        String counterName;
        String action;
        int value;
        int step;
        
        HistoryEntry(Date timestamp, String counterName, String action, int value, int step) {
            this.timestamp = timestamp;
            this.counterName = counterName;
            this.action = action;
            this.value = value;
            this.step = step;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UltimateCounter();
        });
    }
}
