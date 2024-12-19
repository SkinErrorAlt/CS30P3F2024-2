package AvoidingObstacles;

import com.phidget22.*;
import java.util.*;

public class Main {
    // Sensor and motor declarations
    public static DistanceSensor sonar;
    
    // Enhanced memory and mapping
    public static Map<String, String> environmentGrid = new HashMap<>();
    public static final int GRID_SIZE = 10; // 10x10 grid around the rover
    public static final int GRID_RADIUS = GRID_SIZE / 2;
    
    // Memory and mapping
    public static Set<String> exploredLocations = new HashSet<>();
    public static Map<String, Integer> obstacleMap = new HashMap<>();
    
    // Status tracking variables
    public static boolean objectDetected = false;
    public static int objectDetectStrength = 150;
    public static boolean isStuck = false;
    
    // Motor control
    public static DCMotor leftMotors;
    public static DCMotor rightMotors;
    
    // Navigation states
    public static enum RoverState {
        MOVING_FORWARD,
        BACKING_UP,
        TURNING,
        STOPPED,
        EXPLORING,
        LOOKING_AROUND,
        ESCAPING_TIGHT_SPACE,
        AVOIDING_OBSTACLE
    }
    public static RoverState currentState = RoverState.MOVING_FORWARD;
    
    // Childlike behavior and exploration parameters
    public static Random random = new Random();
    public static long lastMovementTime = 0;
    public static long explorationInterval = 30000; // 30 seconds
    public static long lookAroundInterval = 15000; // 15 seconds
    public static int stuckCounter = 0;
    public static final int MAX_STUCK_ATTEMPTS = 5;
    
    // Turning and navigation parameters
    public static int turningDuration = 1000; // 1 second turn
    public static int backupDuration = 500;  // 0.5 seconds backup
    public static int turnAttempts = 0;
    public static final int MAX_TURN_ATTEMPTS = 3;
    
    // Current location tracking
    public static double currentX = 0;
    public static double currentY = 0;
    public static double currentHeading = 0;
    
    public static void main(String[] args) throws Exception {
        // Connect to wireless rover
        Net.addServer("", "192.168.100.1", 5661, "", 0);
        
        // Initialize motors and sensor
        leftMotors = new DCMotor();
        rightMotors = new DCMotor();
        sonar = new DistanceSensor();
        
        // Set channels
        leftMotors.setChannel(0);
        rightMotors.setChannel(1);
        
        // Open devices
        leftMotors.open(5000);
        rightMotors.open(5000);
        sonar.open(5000);
        
        // Record initial time
        lastMovementTime = System.currentTimeMillis();
        
        // Initialize environment grid
        initializeEnvironmentGrid();
        
        sonar.setDataInterval(100);
		leftMotors.setDataInterval(100);
		rightMotors.setDataInterval(100);
		leftMotors.setAcceleration(leftMotors.getMaxAcceleration());
		rightMotors.setAcceleration(rightMotors.getMaxAcceleration());
        
        // Main navigation loop
        while (true) {
            clear();
            ShowStatus();
            updateSensorStatus();
            checkStuckStatus();
            navigateRover();
            updateLocation();
            
            clear();
            ShowStatus();
            
            Thread.sleep(10); // Reduced sleep for more responsive navigation
        }
    }
    
    public static void initializeEnvironmentGrid() {
        // Initialize the grid with unknown locations
        for (int x = -GRID_RADIUS; x <= GRID_RADIUS; x++) {
            for (int y = -GRID_RADIUS; y <= GRID_RADIUS; y++) {
                environmentGrid.put(x + "," + y, "[?]"); // All locations start unknown
            }
        }
    }
    
    public static void updateSensorStatus() throws PhidgetException {
        // Check sensor for object detection
        objectDetected = sonar.getDistance() <= objectDetectStrength;
        
        // If an object is detected, record its location
        if (objectDetected) {
            String obstacleKey = getCurrentLocationKey();
            obstacleMap.put(obstacleKey, obstacleMap.getOrDefault(obstacleKey, 0) + 1);
            
            // Update environment grid with obstacle
            updateEnvironmentGrid(obstacleKey, "[x]");
        } else {
            // Update environment grid with free space
            String currentKey = getCurrentLocationKey();
            updateEnvironmentGrid(currentKey, "[ ]");
        }
    }
    
    public static void checkStuckStatus() {
        // Enhanced stuck detection
        if (currentState == RoverState.MOVING_FORWARD && objectDetected) {
            stuckCounter++;
            
            if (stuckCounter >= MAX_STUCK_ATTEMPTS) {
                isStuck = true;
                currentState = RoverState.ESCAPING_TIGHT_SPACE;
            }
        } else {
            stuckCounter = 0;
            isStuck = false;
        }
    }
    
    public static void escapeFromTightSpace() throws Exception {
        // More advanced escape mechanism
        stopMotors();
        
        // Try different escape strategies
        for (int i = 0; i < 3; i++) {
            // Backup and turn at different angles
            backUpRover();
            
            // Turn at random angle
            double turnAngle = Math.PI * random.nextDouble() - Math.PI / 2;
            leftMotors.setTargetVelocity(-0.25 * Math.signum(turnAngle));
            rightMotors.setTargetVelocity(0.25 * Math.signum(turnAngle));
            Thread.sleep(random.nextInt(1500) + 500);
            
            stopMotors();
            
            // Check if we've cleared the obstacle
            if (!objectDetected) {
                currentState = RoverState.MOVING_FORWARD;
                return;
            }
        }
        
        // If still stuck, go to stopped state
        currentState = RoverState.STOPPED;
        stopMotors();
    }
    
    public static void navigateRover() throws Exception {
        long currentTime = System.currentTimeMillis();
        
        // Handle tight space escape first
        if (currentState == RoverState.ESCAPING_TIGHT_SPACE) {
            escapeFromTightSpace();
            return;
        }
        
        // Childlike exploration - sometimes stop and look around
        if (currentTime - lastMovementTime > lookAroundInterval && 
            currentState != RoverState.LOOKING_AROUND) {
            currentState = RoverState.LOOKING_AROUND;
            lookAround();
            lastMovementTime = currentTime;
            return;
        }
        
        // Random exploration intervals
        if (currentTime - lastMovementTime > explorationInterval) {
            currentState = RoverState.EXPLORING;
            exploreUnknownAreas();
            lastMovementTime = currentTime;
            return;
        }
        
        switch (currentState) {
            case MOVING_FORWARD:
                if (objectDetected) {
                    // Stop and prepare to avoid obstacle
                    stopMotors();
                    
                    // Try backing up first
                    currentState = RoverState.BACKING_UP;
                    backUpRover();
                } else {
                    // Continue moving forward
                    moveForward();
                }
                break;
            
            case BACKING_UP:
                if (!objectDetected) {
                    // If obstacle is no longer detected, attempt to turn
                    currentState = RoverState.TURNING;
                    turnRover();
                } else {
                    // If still detecting obstacle, keep backing up
                    backUpRover();
                }
                break;
            
            case TURNING:
                if (!objectDetected) {
                    // If front is now clear, resume forward movement
                    currentState = RoverState.MOVING_FORWARD;
                    turnAttempts = 0;
                    moveForward();
                } else {
                    // Increment turn attempts
                    turnAttempts++;
                    
                    if (turnAttempts >= MAX_TURN_ATTEMPTS) {
                        // If multiple turn attempts fail, stop
                        currentState = RoverState.STOPPED;
                        stopMotors();
                    } else {
                        // Try turning again
                        turnRover();
                    }
                }
                break;
            
            case STOPPED:
                // Remain stopped until manually restarted or conditions change
                stopMotors();
                break;
            
            case AVOIDING_OBSTACLE:
                // Move away from obstacle in random direction
                escapeFromObstacle();
                break;
            
            default:
                // Default to moving forward if in an undefined state
                currentState = RoverState.MOVING_FORWARD;
                moveForward();
        }
    }
    
    public static void escapeFromObstacle() throws Exception {
        // Randomly back up and turn to avoid the obstacle
        backUpRover();
        turnRover();
        
        // Try moving in the new direction
        moveForward();
        
        currentState = RoverState.MOVING_FORWARD;
    }
    
    public static void exploreUnknownAreas() throws Exception {
        // Find an unknown location to explore
        List<String> unknownLocations = new ArrayList<>();
        for (Map.Entry<String, String> entry : environmentGrid.entrySet()) {
            if (entry.getValue().equals("[?]")) {
                unknownLocations.add(entry.getKey());
            }
        }
        
        if (!unknownLocations.isEmpty()) {
            // Choose a random unknown location
            String targetLocation = unknownLocations.get(random.nextInt(unknownLocations.size()));
            
            // Playful exploration towards the unknown location
            double targetX = Double.parseDouble(targetLocation.split(",")[0]);
            double targetY = Double.parseDouble(targetLocation.split(",")[1]);
            
            // Random zigzag movement towards the target
            for (int i = 0; i < 3; i++) {
                // Move forward with slight random deviation
                moveForward();
                Thread.sleep(random.nextInt(1000) + 500);
                
                // Slight random turn
                leftMotors.setTargetVelocity(random.nextDouble() * 0.25);
                rightMotors.setTargetVelocity(random.nextDouble() * 0.25);
                Thread.sleep(random.nextInt(500) + 250);
            }
            
            stopMotors();
        } else {
            // If all areas are known, do a playful random exploration
            exploreRandomly();
        }
    }
    
    public static void lookAround() throws Exception {
        // Mimic a child-like curiosity of looking around
        stopMotors();
        
        // Turn in different directions with random delays
        for (int i = 0; i < 3; i++) {
            leftMotors.setTargetVelocity(-0.25);
            rightMotors.setTargetVelocity(0.25);
            Thread.sleep(random.nextInt(1000) + 500); // Random turn duration
            
            stopMotors();
            Thread.sleep(1000); // Pause and "observe"
            
            // Playful head tilt simulation
            if (random.nextBoolean()) {
                Thread.sleep(random.nextInt(500) + 250);
            }
        }
        
        // Resume normal navigation
        currentState = RoverState.MOVING_FORWARD;
    }
    
    public static void exploreRandomly() throws Exception {
        // Add some randomness to movement
        double randomDirection = random.nextDouble() * Math.PI * 2;
        
        // Turn to a random direction
        leftMotors.setTargetVelocity(-0.25);
        rightMotors.setTargetVelocity(0.25);
        Thread.sleep(random.nextInt(1000) + 500);
        
        stopMotors();
        Thread.sleep(500);
        
        // Move forward for a random duration
        moveForward();
        Thread.sleep(random.nextInt(3000) + 1000);
        
        stopMotors();
        
        // Return to normal state
        currentState = RoverState.BACKING_UP;
    }
    
    public static void updateLocation() throws PhidgetException {
        double velocity = 0.25; // matching motor velocity
        currentHeading += (leftMotors.getTargetVelocity() - rightMotors.getTargetVelocity()) * 0.1;
        
        // Update the rover's location
        currentX += velocity * Math.cos(currentHeading);
        currentY += velocity * Math.sin(currentHeading);
        
        // Record explored locations around the rover
        exploredLocations.add(getCurrentLocationKey());
    }
    
    public static String getCurrentLocationKey() {
        return String.format("%d,%d", (int)(currentX), (int)(currentY));
    }
    
    public static void moveForward() throws PhidgetException {
        leftMotors.setTargetVelocity(0.6);//
        rightMotors.setTargetVelocity(0.6);
    }
    
    public static void stopMotors() throws PhidgetException {
        leftMotors.setTargetVelocity(0);
        rightMotors.setTargetVelocity(0);
    }
    
    public static void backUpRover() throws Exception {
        // Back up for a short duration
        leftMotors.setTargetVelocity(-0.5);
        rightMotors.setTargetVelocity(-0.5);
        Thread.sleep(backupDuration);
        stopMotors();
    }
    
    public static void turnRover() throws Exception {
        // Turn in place for a specified duration
        leftMotors.setTargetVelocity(-0.5);
        rightMotors.setTargetVelocity(0.5);
        Thread.sleep(turningDuration);
        stopMotors();
    }
    
    public static void updateEnvironmentGrid(String location, String status) {
        environmentGrid.put(location, status);
    }
    
    // Display 10x10 grid environment centered around the rover
    public static void ShowStatus() throws PhidgetException {
        System.out.println("--- Rover Status ---");
        System.out.println("Current State: " + currentState);
        System.out.println("Sonar Distance: " + sonar.getDistance() + " mm");
        System.out.println("Object Detected: " + objectDetected);
        System.out.println("Turn Attempts: " + turnAttempts);
        System.out.println("Current Location: (" + currentX + ", " + currentY + ")");
        System.out.println("Heading: " + Math.toDegrees(currentHeading) + " degrees");
        System.out.println("Explored Locations: " + exploredLocations.size());
        System.out.println("Obstacle Map Size: " + obstacleMap.size());
        System.out.println("Stuck Status: " + (isStuck ? "STUCK" : "OK"));
    }
    
    public static void clear() {
        for (int i = 0; i < 30; i++) {
            System.out.println("");
        }
    }
}
