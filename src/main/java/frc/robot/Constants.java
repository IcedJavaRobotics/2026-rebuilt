// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {


  public static class DriverConstants {
    public static final int MAIN_DRIVER_PORT = 0;
    public static final int AUX_DRIVER_PORT = 1;
    public static final int DRIVER_STATION_PORT = 2;
    public static final double DEADBAND = 0;
    public static final double MAX_SPEED = Units.feetToMeters(9); //4.5

  }

  public static class HardwareConstants {
    public static final int PIGEON_ID = 7;
    
    public static final int INTAKE_JOINT_ID = 51;
    public static final int INTAKE_ROLLER_ID = 52;

    public static final int SHOOTER_LEFT_MOTOR_ID = 61;
    public static final int SHOOTER_RIGHT_MOTOR_ID = 62;

    public static final int CLIMBER_MOTOR_ID = 71;

    public static final int SPINDEXER_MOTOR_ID = 81;

    public static final String CANBUS = "rio";
    public static final String CANIVORE = "iceberg-canivore";
  }

  public static class ShooterConstants {
    public static final double GEAR_RATIO = 1;
    // Speed for functions check without a ball
    public static final double TESTING_SPEED = 0.15;

    // Manual Mode Shooting distances
    public static final double CLOSE_LAUNCH_SPEED = 0.4;
    public static final double MEDIUM_LAUNCH_SPEED = 0.6;
    public static final double FAR_LAUNCH_SPEED = 0.7;

  }

  public static class SpindexerConstants {
    // Speeds for spindexer
    public static final double TESTING_SPEED = 0.2;
    public static final double MANUAL_NORMAL_SPEED = 0.4;
  }

  public static class IntakeConstants {
    // Speeds for intake
    public static final double AUTOMATIC_INTAKE_SPEED = 0.3;
    public static final double MANUAL_INTAKE_SPEED = 0.5;
    public static final double MANUAL_SHUFFLE_SPEED = 0.1; // hold this when going over the bump to ensure balls dont fall out.
  }

  public static class ClimberConstants {
    public static final double STALL_TORQUE = 7.09;
    public static final double STALL_CURRENT = 370;
    public static final double TORQUE_CONSTANT = STALL_TORQUE/STALL_CURRENT;
    public static final double EFFICIENCY = 0.9;
    public static final double GEAR_RATIO = 12; // 12:1
  }

  public static class  IntakeConstants {
  
    public static final double ELEVATOR_SPEED = .1;
    public static final double ROLLER_SPEED = .1;
    public static final int ELEVATOR_MOTOR_ID = 51;
    public static final int ROLLER_MOTOR_ID = 52;
    
    
  }


  public static class VisionSubsystem {
    public static final String LIMELIGHT_NAME = "sauron";
    public static final String LIMELIGHT_IP = "http://10.68.94.11:5801/";

    
  }

  public static class PhysicsConstants {
    public static final double GRAVITY = 9.8;
    public static final double HUB_HEIGHT = 6; //Height in feet of hub
    
  }

  public static class Globals {
    //try not to use this but use robot container instead
  }




}
