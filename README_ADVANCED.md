# Advanced Counter Application

A feature-rich counter application with advanced functionality including history tracking, multiple themes, keyboard shortcuts, sound effects, and data persistence.

## 📋 Project Details

- **Title**: Advanced Counter Premium
- **Version**: 2.0
- **Category**: Java Projects
- **Difficulty**: Intermediate
- **Year**: 2026
- **Author**: RSK World
- **Website**: https://rskworld.in
- **Contact**: help@rskworld.in | +91 93305 39277

## 🛠️ Technologies Used

- **Java** - Core programming language
- **Swing** - Advanced GUI framework
- **Event Handling** - Complex event processing
- **File I/O** - Data persistence and export
- **Audio System** - Sound effects generation
- **Serialization** - Object data storage

## ✨ Advanced Features

### 🎯 Core Functionality
- ✅ **Dynamic Step Control** - Adjustable increment/decrement values (1-10)
- ✅ **Smart Counter Display** - Large, color-coded display
- ✅ **Advanced Buttons** - Enhanced styling with hover effects
- ✅ **Real-time Status Bar** - Live application state information

### 🎨 Themes & Customization
- ✅ **5 Beautiful Themes**:
  - Default (Blue/Green/Red)
  - Dark (Gray tones)
  - Ocean (Cyan/Orange)
  - Sunset (Yellow/Orange/Red)
  - Forest (Green/Yellow)
- ✅ **Dynamic Color Coding** - Counter changes color based on value and theme
- ✅ **Theme Persistence** - Selected theme saved between sessions

### 📊 History & Tracking
- ✅ **Complete History Log** - Tracks all counter operations with timestamps
- ✅ **Scrollable History Panel** - Easy navigation through past operations
- ✅ **History Export** - Export history to text file
- ✅ **Clear History** - Option to clear history with confirmation
- ✅ **Auto-limit** - Maintains last 100 entries for performance

### ⌨️ Keyboard Shortcuts
- ✅ **Ctrl + Plus** - Increment counter
- ✅ **Ctrl + Minus** - Decrement counter
- ✅ **Ctrl + R** - Reset counter
- ✅ **Ctrl + S** - Save data
- ✅ **Ctrl + L** - Load data

### 🔊 Audio Features
- ✅ **Sound Effects** - Programmatically generated beep sounds
- ✅ **Toggle Sounds** - Option to enable/disable sound effects
- ✅ **Different Tones** - Unique sounds for increment/decrement

### 💾 Data Management
- ✅ **Auto-Save** - Data saved on application close
- ✅ **Manual Save/Load** - Explicit save and load buttons
- ✅ **Data Persistence** - Counter value, history, theme, and step saved
- ✅ **Export Functionality** - Export history to external file

### 🎯 User Experience
- ✅ **Professional UI** - Clean, modern interface with proper spacing
- ✅ **Responsive Design** - Adaptable window with minimum size
- ✅ **Status Information** - Real-time display of current settings
- ✅ **About Dialog** - Comprehensive application information
- ✅ **Confirmation Dialogs** - Safe operations with user confirmation

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE or command line access

### Steps

1. **Navigate** to the project directory
2. **Compile** the Java file:
   ```bash
   javac AdvancedCounter.java
   ```
3. **Run** the application:
   ```bash
   java AdvancedCounter
   ```

## 📱 Application Layout

```
┌─────────────────────────────────────────────────────────────┐
│                    Advanced Counter Application             │
│  Ready | Theme: Default | Step: 1 | History: 0            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                        0                                    │
│                                                             │
│                 [- 1]  [Reset]  [+ 1]                      │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│ Step Value: [====|====] Step: 1    Theme: [Default ▼] ☑   │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │                 History                                 │ │
│ │ ┌─────────────────────────────────────────────────────┐ │ │
│ │ │                                                   │ │ │
│ │ │                                                   │ │ │
│ │ └─────────────────────────────────────────────────────┘ │ │
│ │                                    [Clear History]    │ │
│ └─────────────────────────────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│ [Save Data]  [Load Data]  [Export History]  [About]        │
└─────────────────────────────────────────────────────────────┘
```

## 🎮 Usage Instructions

### Basic Operations
1. **Increment**: Click "+ [step]" button or press Ctrl+Plus
2. **Decrement**: Click "- [step]" button or press Ctrl+Minus
3. **Reset**: Click "Reset" button or press Ctrl+R

### Advanced Features
1. **Change Step**: Use the slider to adjust increment/decrement value (1-10)
2. **Switch Theme**: Select from dropdown menu (Default, Dark, Ocean, Sunset, Forest)
3. **Toggle Sound**: Check/uncheck "Sound Effects" checkbox
4. **Save Data**: Click "Save Data" or press Ctrl+S
5. **Load Data**: Click "Load Data" or press Ctrl+L
6. **Export History**: Click "Export History" to save history to file
7. **Clear History**: Click "Clear History" (with confirmation)

### File Operations
- **Auto-save**: Data automatically saved when closing application
- **Manual save**: Explicitly save current state
- **Load data**: Restore previously saved state
- **Export history**: Save history log to text file

## 🏗️ Project Structure

```
basic-counter/
├── AdvancedCounter.java         # Main advanced application
├── SimpleCounter.java           # Original simple version
├── README_ADVANCED.md           # Advanced features documentation
├── README.md                    # Original documentation
├── index.html                   # Demo page
├── docs/                        # Documentation
│   ├── features.md             # Feature details
│   └── installation.md         # Installation guide
├── images/                      # Project images
│   └── basic-counter.png       # Application screenshot
├── counter_data.dat            # Saved application data (auto-generated)
└── counter_history.txt         # Exported history (auto-generated)
```

## 💡 Learning Outcomes

This advanced project helps you learn:
- **Advanced Swing Components** - JSlider, JComboBox, JTextArea
- **Event Handling** - Multiple event types and listeners
- **Data Persistence** - Serialization and file I/O
- **Audio Programming** - Java Sound API usage
- **Keyboard Shortcuts** - Input mapping and actions
- **Theme System** - Dynamic UI customization
- **History Management** - Data tracking and display
- **Professional UI Design** - Layout management and styling

## 🔧 Customization Options

### Adding New Themes
```java
themes.put("ThemeName", new Color[]{
    new Color(R, G, B),    // Zero/Default color
    new Color(R, G, B),    // Positive color  
    new Color(R, G, B)     // Negative color
});
```

### Modifying Step Range
```java
stepSlider = new JSlider(minValue, maxValue, defaultValue);
```

### Custom Sound Effects
Replace the `createBeepSound()` method with custom audio file loading.

## 🐛 Troubleshooting

### Common Issues

1. **Compilation Error**: Ensure JDK 8+ is installed
2. **No Sound**: Check system audio settings and Java sound support
3. **File Not Found**: Ensure write permissions in application directory
4. **Theme Not Saving**: Check if application has write permissions

### Solutions

- Verify Java installation: `java -version`
- Check compiler: `javac -version`
- Ensure directory permissions: Check read/write access
- Test sound system: Try other Java applications with sound

## 📞 Support

For any queries or support:
- **Email**: help@rskworld.in
- **Phone**: +91 93305 39277
- **Website**: https://rskworld.in
- **Location**: Nutanhat, Mongolkote, Purba Burdwan, West Bengal, India - 713147

## 📄 License

This project is created and maintained by RSK World for educational purposes. 

© 2026 RSK World. All rights reserved.

---

**Founded by Molla Samser, with Designer & Tester Rima Khatun**

RSK World is your one-stop destination for free programming resources, source code, and development tools.

Content used for educational purposes only. [View Disclaimer](https://rskworld.in/disclaimer.php)
