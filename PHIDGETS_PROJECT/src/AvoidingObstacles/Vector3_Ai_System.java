package AvoidingObstacles;

import com.phidget22.*;
import java.awt.EventQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Cursor;
import javax.swing.SwingConstants;

public class Vector3_Ai_System {
	public boolean ConnectionFailed = false;
	
	public static boolean InfrontObject = false;
	public static double DistanceFromObject = 0;
	public static double DistanceStrength = 200;
	public static boolean Ai_Active = false;
	public static boolean Last_Ai_Active = false;
	
	public static boolean Human_Control_Active = true;
	
	public static boolean Human_Forward = false;
	public static boolean Human_Left = false;
	public static boolean Human_Right = false;
	public static boolean Human_Backward = false;
	
	public static boolean Auto_Breaking = false;
	
	public static DistanceSensor sonar;
	public static DCMotor leftMotors;
	public static DCMotor rightMotors;
	
	public static double SetSpeed = 100;
	public static double SetRotationSpeed = 100;
	
	public static boolean VectorSetup = false;
	public static boolean Human_Breaks = false;
	
	public void MainRun() throws Exception 
	{
        //Connect to wireless rover
        Net.addServer("", "192.168.100.1", 5661, "", 0);

        leftMotors = new DCMotor();
        rightMotors = new DCMotor();
        sonar = new DistanceSensor();

        //Address
        leftMotors.setChannel(0);
        rightMotors.setChannel(1);
        
        leftMotors.open(5000);
        rightMotors.open(5000);
        sonar.open(5000);
        
        Thread.sleep(10);
        
        VectorSetup = true;
        
        boolean LastHuman_Control_Active = false;

        while (!ConnectionFailed) 
        {
        	if (!Ai_Active) 
        	{
        		if (Last_Ai_Active != Ai_Active) 
        		{
        			Last_Ai_Active = Ai_Active;
        			System.out.println("[Vector3]: Stopped.");
        			
        			leftMotors.setTargetVelocity(0);
                    rightMotors.setTargetVelocity(0);
        		}
        		
        		Thread.sleep(10);
        		continue;
        	}
        	
        	if (Last_Ai_Active != Ai_Active) 
    		{
    			Last_Ai_Active = Ai_Active;
    			System.out.println("[Vector3]: Running.");
    		}
        	
        	if (LastHuman_Control_Active != Human_Control_Active) 
        	{
        		LastHuman_Control_Active = Human_Control_Active;
        		
        		leftMotors.setTargetVelocity(0);
				rightMotors.setTargetVelocity(0);
        	}
        	
        	if (Human_Control_Active) 
        	{
        		if (Human_Backward && !Human_Forward) 
        		{
        			if (Auto_Breaking) 
        			{
        				leftMotors.setBrakingEnabled(false);
            			rightMotors.setBrakingEnabled(false);
        			}
        			
        			if (Human_Breaks) 
        			{
        				leftMotors.setBrakingEnabled(true);
            			rightMotors.setBrakingEnabled(true);
        			}
        			
        			if (Human_Left && Human_Right) 
        			{
        				leftMotors.setTargetVelocity(-1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity(-1 * (SetSpeed / 100));
        				continue;
        			}
        			else if (Human_Left) 
        			{
        				leftMotors.setTargetVelocity((-0.2 * (SetSpeed / 100)) * (1 - (SetRotationSpeed / 100)));
        				rightMotors.setTargetVelocity(-1 * (SetSpeed / 100));
        				continue;
        			}
        			else if (Human_Right) 
        			{
        				leftMotors.setTargetVelocity(-1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity((-0.2 * (SetSpeed / 100)) * (1 - (SetRotationSpeed / 100)));
        				continue;
        			}
        			else 
        			{
        				leftMotors.setTargetVelocity(-1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity(-1 * (SetSpeed / 100));
        				continue;
        			}
        		}
        		
        		if (!Human_Backward && Human_Forward) 
        		{
        			if (Auto_Breaking) 
        			{
        				leftMotors.setBrakingEnabled(false);
            			rightMotors.setBrakingEnabled(false);
        			}
        			
        			if (Human_Breaks) 
        			{
        				leftMotors.setBrakingEnabled(true);
            			rightMotors.setBrakingEnabled(true);
        			}
        			
        			if (Human_Left && Human_Right) 
        			{
        				leftMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				continue;
        			}
        			else if (Human_Left) 
        			{
        				leftMotors.setTargetVelocity((0.2 * (SetSpeed / 100)) * (1 - (SetRotationSpeed / 100)));
        				rightMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				continue;
        			}
        			else if (Human_Right) 
        			{
        				leftMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity((0.2 * (SetSpeed / 100)) * (1 - (SetRotationSpeed / 100)));
        				continue;
        			}
        			else 
        			{
        				leftMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				continue;
        			}
        		}
        		
        		if (!Human_Backward && !Human_Forward) 
        		{
        			if (Auto_Breaking) 
        			{
        				leftMotors.setBrakingEnabled(false);
            			rightMotors.setBrakingEnabled(false);
        			}
        			
        			if (Human_Breaks) 
        			{
        				leftMotors.setBrakingEnabled(true);
            			rightMotors.setBrakingEnabled(true);
        			}
        			
        			if (Human_Left && Human_Right) 
        			{
        				leftMotors.setBrakingEnabled(true);
            			rightMotors.setBrakingEnabled(true);
            			
        				leftMotors.setTargetVelocity(0);
        				rightMotors.setTargetVelocity(0);
        				continue;
        			}
        			else if (Human_Left) 
        			{
        				leftMotors.setTargetVelocity((-1 * (SetSpeed / 100)) * (SetRotationSpeed / 100));
        				rightMotors.setTargetVelocity((1 * (SetSpeed / 100)) * (SetRotationSpeed / 100));
        				continue;
        			}
        			else if (Human_Right) 
        			{
        				leftMotors.setTargetVelocity((1 * (SetSpeed / 100)) * (SetRotationSpeed / 100));
        				rightMotors.setTargetVelocity((-1 * (SetSpeed / 100)) * (SetRotationSpeed / 100));
        				continue;
        			}
        			else 
        			{
        				if (Auto_Breaking) 
            			{
        					leftMotors.setBrakingEnabled(true);
                			rightMotors.setBrakingEnabled(true);
            			}
        				
        				if (Human_Breaks) 
            			{
            				leftMotors.setBrakingEnabled(true);
                			rightMotors.setBrakingEnabled(true);
            			}
            			
        				leftMotors.setTargetVelocity(0);
        				rightMotors.setTargetVelocity(0);
        				continue;
        			}
        		}
        		else 
        		{
        			if (Auto_Breaking) 
        			{
        				leftMotors.setBrakingEnabled(false);
            			rightMotors.setBrakingEnabled(false);
        			}
        			
        			if (Human_Breaks) 
        			{
        				leftMotors.setBrakingEnabled(true);
            			rightMotors.setBrakingEnabled(true);
        			}
        			
        			if (Human_Left && Human_Right) 
        			{
        				leftMotors.setTargetVelocity(0);
        				rightMotors.setTargetVelocity(0);
        				continue;
        			}
        			else if (Human_Left) 
        			{
        				leftMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				continue;
        			}
        			else if (Human_Right) 
        			{
        				leftMotors.setTargetVelocity(-1 * (SetSpeed / 100));
        				rightMotors.setTargetVelocity(1 * (SetSpeed / 100));
        				continue;
        			}
        			else 
        			{
        				if (Auto_Breaking) 
            			{
        					leftMotors.setBrakingEnabled(true);
                			rightMotors.setBrakingEnabled(true);
            			}
        				
        				if (Human_Breaks) 
            			{
            				leftMotors.setBrakingEnabled(true);
                			rightMotors.setBrakingEnabled(true);
            			}

        				leftMotors.setTargetVelocity(0);
        				rightMotors.setTargetVelocity(0);
        				continue;
        			}
        		}
        	}
        	
        	DistanceFromObject = sonar.getDistance();
            
            if (sonar.getDistance() < DistanceStrength) 
            {
            	InfrontObject = true;
                //leftMotors.setTargetVelocity(0);
                //rightMotors.setTargetVelocity(0);
            } 
            else 
            {
            	InfrontObject = false;
                //leftMotors.setTargetVelocity(0.3);
                //rightMotors.setTargetVelocity(0.3);
            }

            Thread.sleep(10);
        }
    }
}
