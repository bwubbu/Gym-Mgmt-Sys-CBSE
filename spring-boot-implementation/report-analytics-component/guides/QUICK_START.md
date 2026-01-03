# Quick Start Guide - Report Analytics Component

## Prerequisites

Before running this component, make sure you have:

1. **Java 17 or higher** installed
   - Check version: `java -version`
   - Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/

2. **Maven 3.6+** installed
   - Check version: `mvn -version`
   - Download from: https://maven.apache.org/download.cgi

## Running the Application

### Option 1: Using Maven (Recommended)

1. **Open Terminal/Command Prompt** and navigate to the component directory:
   ```bash
   cd report-analytics-component
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```
   This will download all dependencies and compile the project.

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

   The application will start on **port 8085** by default.

4. **Verify it's running**:
   - You should see Spring Boot startup logs
   - Look for: `Started ReportAnalyticsApplication in X.XXX seconds`
   - Open browser: `http://localhost:8085`

### Option 2: Using IDE (IntelliJ IDEA / Eclipse / VS Code)

#### IntelliJ IDEA:
1. Open IntelliJ IDEA
2. **File → Open** → Select the `report-analytics-component` folder
3. Wait for Maven to download dependencies
4. Find `ReportAnalyticsApplication.java` in the project tree
5. Right-click → **Run 'ReportAnalyticsApplication'**

#### Eclipse:
1. Open Eclipse
2. **File → Import → Maven → Existing Maven Projects**
3. Select the `report-analytics-component` folder
4. Right-click on project → **Run As → Spring Boot App**

#### VS Code:
1. Install extensions: "Extension Pack for Java" and "Spring Boot Extension Pack"
2. Open the `report-analytics-component` folder
3. Open `ReportAnalyticsApplication.java`
4. Click "Run" button or press `F5`

### Option 3: Run as JAR file

1. **Build JAR**:
   ```bash
   mvn clean package
   ```

2. **Run JAR**:
   ```bash
   java -jar target/report-analytics-component-1.0.0.jar
   ```

## Testing the Application

### 1. Check if it's running

Open your browser and visit:
- **Health Check**: `http://localhost:8085/actuator/health` (if actuator is added)
- Or try any API endpoint below

### 2. Test API Endpoints

#### Using cURL:

```bash
# Get member demographics (will return empty data until other components are integrated)
curl http://localhost:8085/api/analytics/member/demographics

# Get equipment usage analytics
curl http://localhost:8085/api/analytics/equipment/usage

# Generate full report
curl http://localhost:8085/api/reports/full
```

#### Using Browser:

Simply open these URLs in your browser:
- `http://localhost:8085/api/reports/full`
- `http://localhost:8085/api/analytics/member/demographics`
- `http://localhost:8085/api/analytics/equipment/usage`

#### Using Postman:

1. Import the endpoints or manually test:
   - **GET** `http://localhost:8085/api/reports/full`
   - **GET** `http://localhost:8085/api/analytics/member/demographics`
   - **GET** `http://localhost:8085/api/analytics/equipment/usage`

## Expected Output

When you first run the application, you'll see:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-XX-XX INFO  --- [main] c.g.r.ReportAnalyticsApplication : Starting ReportAnalyticsApplication
2024-XX-XX INFO  --- [main] c.g.r.ReportAnalyticsApplication : Started ReportAnalyticsApplication in X.XXX seconds
```

## Troubleshooting

### Issue: "mvn: command not found"
**Solution**: Install Maven and add it to your PATH, or use the Maven wrapper if available.

### Issue: "Java version error"
**Solution**: Make sure you have Java 17 or higher:
```bash
java -version
```
If not, install Java 17+ and set JAVA_HOME.

### Issue: "Port 8085 already in use"
**Solution**: Either:
1. Stop the application using that port
2. Change the port in `application.yml`:
   ```yaml
   server:
     port: 8086  # Change to any available port
   ```

### Issue: "Connection refused" when calling other services
**Solution**: This is expected! The `DataService` currently returns empty data because other components aren't running yet. Once other team members' components are ready, update the service URLs in `application.yml`.

### Issue: "Failed to start application"
**Solution**: 
1. Check if port 8085 is available
2. Check Java version (must be 17+)
3. Check Maven dependencies: `mvn dependency:resolve`
4. Check logs for specific error messages

## Next Steps

1. **Once running**, the component will return empty data until other components are integrated
2. **Update DataService** to call actual REST APIs from other components
3. **Test with real data** once other components are available
4. **Configure service URLs** in `application.yml` for other components

## Configuration

Edit `src/main/resources/application.yml` to configure:
- Server port (default: 8085)
- Service URLs for other components
- Database settings

## Stopping the Application

- **In Terminal**: Press `Ctrl+C`
- **In IDE**: Click the stop button in the console/run panel

---

**Note**: This component is designed to work with other microservices. It will work standalone but will return empty data until integrated with:
- Member Management Component (port 8081)
- Trainer Management Component (port 8082)
- Equipment Booking Component (port 8083)
- Financial Management Component (port 8084)

