# Sample Data Generator - Instructions

## Overview
The `SampleDataGenerator.java` class creates diverse sample data for the Gym Management System application. This includes members, trainers, machines, and machine bookings.

## What Data is Generated

### Members (12 total)
- **Diverse demographics**: Different ages (23-59), genders, and join dates
- **Various fitness goals**: Weight Loss, Muscle Gain, Endurance, Flexibility, General Fitness
- **Payment statuses**: Mix of members with zero balance and outstanding balances ($50-$200)
- **Body statistics**: Various heights (1.64m - 1.82m) and weights (55kg - 95kg) for BMI calculations

### Trainers (6 total)
- **Specializations**: Yoga, Strength Training, Cardio, Pilates, CrossFit, General Fitness
- **Experience levels**: Intermediate, Advanced, Expert
- **Various rates**: $30-$50 per hour
- **Working hours**: 10-20 hours per week

### Machines (8 total)
- **Types**: Cardio (4), Strength (3), Functional (1)
- **Brands**: FitnessPro, IronGym, CardioMax, PowerHouse, AquaFit, SteelGym, CycleMax, FlexGym
- **Capacities**: 100kg - 300kg max weight capacity

### Bookings
- Sample bookings created for machines (only members with zero balance can book)

## How to Use

### Method 1: Run from IDE
1. Open the project in your IDE (Eclipse, IntelliJ, etc.)
2. Navigate to `SampleDataGenerator.java`
3. Right-click and select "Run" or "Run as Java Application"
4. The program will create three files in the project root:
   - `MemberFile`
   - `TrainerFile`
   - `MachineFile`

### Method 2: Run from Command Line
1. Navigate to the project directory:
   ```bash
   cd "Gym management system JAVA OOP GUI"
   ```
2. Compile the file:
   ```bash
   javac -d . SampleDataGenerator.java
   ```
3. Run the generator:
   ```bash
   java project_oop.SampleDataGenerator
   ```

## Output
After running, you should see:
```
=========================================
Gym Management System - Sample Data Generator
=========================================

✅ Generated 12 sample members
✅ Generated 6 sample trainers
✅ Generated 8 sample machines
✅ Created sample machine bookings

=========================================
Sample data generation completed successfully!
=========================================

Files created:
  - MemberFile
  - TrainerFile
  - MachineFile

You can now run the Gym Management System application.
```

## Important Notes

1. **File Location**: The generated files (`MemberFile`, `TrainerFile`, `MachineFile`) will be created in the same directory where you run the program. Make sure you run it from the project root or the directory where `Gym.java` is located.

2. **Overwriting**: If these files already exist, they will be **overwritten** with the new sample data. Make a backup if you have existing data you want to keep.

3. **Admin Info**: The `AdminInfo.txt` file is not modified. Default credentials remain:
   - Username: `admin`
   - Password: `anything`

4. **Member IDs**: Sample members have IDs 1001-1012
5. **Trainer IDs**: Sample trainers have IDs 2001-2006
6. **Machine IDs**: Sample machines have IDs 3001-3008

## Testing the Data

After generating the sample data:
1. Run the Gym Management System application
2. Login with admin credentials
3. Navigate to:
   - **View All Members** - Should show 12 members
   - **View All Trainers** - Should show 6 trainers
   - **View All Machines** - Should show 8 machines
   - **View All Bookings** - Should show several bookings
   - **Analytics Menu** - Should show diverse demographics and statistics
   - **Generate Reports** - Should generate reports with the sample data

## Customization

To modify the sample data:
1. Open `SampleDataGenerator.java`
2. Edit the methods:
   - `generateSampleMembers()` - Add/modify members
   - `generateSampleTrainers()` - Add/modify trainers
   - `generateSampleMachines()` - Add/modify machines
   - `createSampleBookings()` - Modify booking assignments
3. Re-run the generator

## Troubleshooting

**Problem**: Files not found error
- **Solution**: Make sure you're running from the correct directory where the Gym.java file is located

**Problem**: ClassNotFoundException
- **Solution**: Ensure all classes (Member, Trainer, Machine, Payment, Date) are compiled and in the classpath

**Problem**: Data not showing in application
- **Solution**: 
  1. Verify the files were created in the correct location
  2. Check that the application is reading from the same directory
  3. Try restarting the application
