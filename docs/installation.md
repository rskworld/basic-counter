# Basic Counter - Installation Guide

## 📋 Prerequisites

Before installing and running the Basic Counter application, ensure you have the following requirements:

### System Requirements
- **Operating System**: Windows 7+, macOS 10.10+, or Linux (Ubuntu 16.04+)
- **Java Development Kit (JDK)**: Version 8 or higher
- **RAM**: Minimum 512MB (Recommended: 1GB or more)
- **Disk Space**: 10MB free space
- **Display**: 1024x768 resolution or higher

### Software Requirements
- **JDK Installation**: Java Development Kit must be properly installed
- **PATH Configuration**: Java bin directory should be in system PATH
- **GUI Support**: System must support graphical user interface

## 🔧 Installation Steps

### Step 1: Verify Java Installation

First, check if Java is installed on your system:

#### Windows
```cmd
java -version
javac -version
```

#### macOS/Linux
```bash
java -version
javac -version
```

**Expected Output**: You should see version information for both Java Runtime Environment (JRE) and Java Compiler (Javac).

### Step 2: Install Java (If Not Installed)

#### Windows
1. Download JDK from [Oracle Java Downloads](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer and follow the installation wizard
3. Set JAVA_HOME environment variable:
   ```cmd
   set JAVA_HOME=C:\Program Files\Java\jdk-xx
   set PATH=%PATH%;%JAVA_HOME%\bin
   ```

#### macOS
```bash
# Using Homebrew
brew install openjdk@11

# Or download from Oracle
# Follow the installation instructions
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install default-jdk
```

### Step 3: Download the Project

#### Option 1: Clone from Repository
```bash
git clone <repository-url>
cd basic-counter
```

#### Option 2: Download ZIP
1. Download the project ZIP file
2. Extract to your desired location
3. Navigate to the project directory

### Step 4: Compile the Application

Navigate to the project directory containing `BasicCounter.java`:

```bash
# Compile the Java file
javac BasicCounter.java
```

**Success**: If compilation succeeds, you'll see no output and a `BasicCounter.class` file will be created.

### Step 5: Run the Application

```bash
# Run the compiled application
java BasicCounter
```

The application window should appear with the counter interface.

## 🖥️ Running in Different Environments

### Using Command Line/Terminal

#### Windows Command Prompt
```cmd
cd path\to\basic-counter
javac BasicCounter.java
java BasicCounter
```

#### Windows PowerShell
```powershell
cd path\to\basic-counter
javac BasicCounter.java
java BasicCounter
```

#### macOS/Linux Terminal
```bash
cd path/to/basic-counter
javac BasicCounter.java
java BasicCounter
```

### Using IDEs

#### Eclipse
1. Open Eclipse IDE
2. Create a new Java Project
3. Copy `BasicCounter.java` to the src folder
4. Right-click on the file → Run As → Java Application

#### IntelliJ IDEA
1. Open IntelliJ IDEA
2. Create a new Java Project
3. Copy `BasicCounter.java` to the src folder
4. Right-click on the file → Run 'BasicCounter.main()'

#### NetBeans
1. Open NetBeans IDE
2. Create a new Java Project
3. Copy `BasicCounter.java` to the source package
4. Right-click on the file → Run File

## 🔍 Troubleshooting

### Common Issues and Solutions

#### Issue 1: 'javac' is not recognized
**Cause**: Java is not installed or PATH is not configured

**Solution**:
1. Verify Java installation
2. Add Java bin directory to system PATH
3. Restart command prompt/terminal

#### Issue 2: Class not found error
**Cause**: CLASSPATH issues or compilation problems

**Solution**:
```bash
# Compile with explicit classpath
javac -cp . BasicCounter.java

# Run with explicit classpath
java -cp . BasicCounter
```

#### Issue 3: GUI doesn't display
**Cause**: Headless environment or display issues

**Solution**:
1. Ensure you're running in a GUI environment
2. Check if display is available (Linux/macOS)
3. Try running with different Look and Feel

#### Issue 4: Permission denied
**Cause**: File permission issues

**Solution**:
```bash
# Linux/macOS
chmod +x BasicCounter.java
chmod 755 .

# Windows: Run as Administrator
```

#### Issue 5: Out of memory
**Cause**: Insufficient JVM memory allocation

**Solution**:
```bash
# Increase heap size
java -Xmx512m BasicCounter
```

## 📱 Verification

### Successful Installation Indicators

1. **Compilation Success**: No error messages during `javac`
2. **Class File Created**: `BasicCounter.class` file exists
3. **Application Launches**: GUI window appears
4. **Buttons Work**: All buttons respond to clicks
5. **Counter Updates**: Display shows correct values

### Test the Application

1. **Launch Test**: Application window opens centered
2. **Increment Test**: Click "+" button, counter increases
3. **Decrement Test**: Click "-" button, counter decreases
4. **Reset Test**: Click "Reset" button, counter returns to 0
5. **Color Test**: Verify color changes based on counter value

## 🛠️ Advanced Configuration

### Custom JVM Options

```bash
# Set minimum and maximum heap size
java -Xms256m -Xmx1024m BasicCounter

# Set Look and Feel
java -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel BasicCounter

# Enable debugging
java -Dswing.debug=true BasicCounter
```

### Environment Variables

#### Windows
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-11
set CLASSPATH=.
```

#### macOS/Linux
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export CLASSPATH=.
```

## 📞 Support

If you encounter any issues during installation:

**Contact Information**:
- **Email**: help@rskworld.in
- **Phone**: +91 93305 39277
- **Website**: https://rskworld.in
- **Location**: Nutanhat, Mongolkote, Purba Burdwan, West Bengal, India - 713147

**Additional Resources**:
- [Java Documentation](https://docs.oracle.com/javase/)
- [Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)

---

© 2026 RSK World. All rights reserved.  
Content used for educational purposes only.
