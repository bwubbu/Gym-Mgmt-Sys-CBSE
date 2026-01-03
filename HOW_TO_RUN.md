# How to Run the Gym Management System

This is a Java Swing GUI application built with NetBeans. Here are several ways to run it:

## Prerequisites

1. **Install Java JDK** (if not already installed):
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Or use OpenJDK: https://adoptium.net/
   - Make sure Java is added to your system PATH

2. **Verify Java Installation**:
   ```powershell
   java -version
   javac -version
   ```

## Method 1: Using Command Line

### Step 1: Navigate to the project directory
```powershell
cd "Gym management system JAVA OOP GUI"
```

### Step 2: Compile all Java files
```powershell
javac -d . *.java
```
This will create a `project_oop` folder with compiled `.class` files.

### Step 3: Run the application
```powershell
java project_oop.Gym
```
OR
```powershell
java project_oop.JFrameAdmin
```

Both commands will start the login screen.

## Method 2: Using NetBeans IDE (Recommended)

Since this project was built with NetBeans:

1. **Install NetBeans IDE** (if not installed):
   - Download from: https://netbeans.apache.org/

2. **Open the project**:
   - Open NetBeans
   - File → Open Project
   - Navigate to `Gym management system JAVA OOP GUI` folder
   - Select the folder and click "Open Project"

3. **Run the project**:
   - Right-click on `Gym.java` in the Projects panel
   - Select "Run File"
   - OR click the green "Run Project" button (▶️)

## Method 3: Using IntelliJ IDEA

1. **Open IntelliJ IDEA**
2. **File → Open** → Select the `Gym management system JAVA OOP GUI` folder
3. **Set up the project**:
   - IntelliJ should auto-detect Java files
   - If prompted, set the JDK version
4. **Run**:
   - Right-click on `Gym.java`
   - Select "Run 'Gym.main()'"

## Method 4: Using Eclipse

1. **Open Eclipse**
2. **File → Import → General → Existing Projects into Workspace**
3. **Select the `Gym management system JAVA OOP GUI` folder**
4. **Run**:
   - Right-click on `Gym.java`
   - Run As → Java Application

## Default Login Credentials

Based on the `AdminInfo.txt` file:
- **Username**: `admin`
- **Password**: `anything`

## Application Flow

1. **Login Screen** (`JFrameAdmin`) - Enter credentials
2. **Main Menu** (`JFrameMainMenu`) - Choose from:
   - 1. Accounts
   - 2. Member
   - 3. Trainer
   - 4. Machine
   - 5. Booking
   - 6. Report

## Troubleshooting

### "javac is not recognized"
- Java JDK is not installed or not in PATH
- Install JDK and add it to system PATH
- Restart your terminal/IDE after installation

### "Package project_oop does not exist"
- Make sure you're compiling from the correct directory
- The package structure should be: `project_oop/ClassName.class`

### "ClassNotFoundException"
- Ensure all `.java` files are compiled
- Run `javac -d . *.java` from the `Gym management system JAVA OOP GUI` directory

### File I/O Errors
- The application creates data files (`MemberFile`, `TrainerFile`, `MachineFile`) in the same directory
- Make sure the application has write permissions in that directory

## Notes

- The application uses Java Swing for the GUI
- Data is stored in binary files using Java Serialization
- All classes are in the `project_oop` package
- The main entry point is `Gym.java` which launches the login screen

