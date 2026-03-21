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

  public static class DriverStationConstants {
    public static final int TOP_LEFT = 1; // ELEV OUT
    public static final int TOP_MIDDLE = 9; //CLIMBER UP
    public static final int TOP_RIGHT = 2; // 

    public static final int MIDDLE_LEFT = 6; // ELEV IN
    public static final int MIDDLE_MIDDLE = 8; // CLIMB DOWN
    public static final int MIDDLE_RIGHT = 3; // SELF DESTRUCT

    public static final int BOTTOM_LEFT = 5; //SHOOT
    public static final int BOTTOM_MIDDLE = 4; // ZERO DRIVE
    public static final int BOTTOM_RIGHT = 10; //

    public static final int SWITCH = 7; // True when switch is DOWN ------- SPINDEXER
  }

  public static class HardwareConstants {
    public static final int PIGEON_ID = 7;
    
    public static final int INTAKE_ELEVATOR_ID = 51;
    public static final int INTAKE_ROLLER_ID = 52;
    public static final int INTAKE_ELEVATOR_FOLLOWER_ID = 53;

    public static final int SHOOTER_LEFT_MOTOR_ID = 61;
    public static final int SHOOTER_RIGHT_MOTOR_ID = 62;

    public static final int SPINDEXER_MOTOR_ID = 56;

    public static final String CANBUS = "rio";
    public static final String CANIVORE = "CANivore";

    public static final int KRAKEN_RPS = 520; // max rps
   public static final double STALL_TORQUE = 7.09;
    public static final double STALL_CURRENT = 370;
    public static final double TORQUE_CONSTANT = STALL_TORQUE/STALL_CURRENT;
    public static final double EFFICIENCY = 0.9;
  }

  public static class ShooterConstants {

    public static final double GEAR_RATIO = 1;
    public static final double DEFAULT_SHOOTER_SPEED = -0.65; //made negative after cabarrus change
    public static final double REVERSE_SHOOTER_SPEED = 0.6;
    public static final double DESIRED_SPEED_INTERVAL = 0.01;
    public static final double SHOOTING_DEADZONE = 0.05;
    // Manual Mode Shooting distances



  }

  public static class SpindexerConstants {
    // Speeds for spindexer
    public static final double TESTING_SPEED = -0.2;
    public static final double MANUAL_NORMAL_SPEED = -0.75;
  }

  public static class IntakeConstants {
    // Speeds for intake
    public static final double AUTOMATIC_INTAKE_SPEED = 0.3;
    public static final double MANUAL_INTAKE_SPEED = 0.5;
    public static final double MANUAL_SHUFFLE_SPEED = 0.1; // hold this when going over the bump to ensure balls dont fall out.
    public static final double INTAKE_ELEVATOR_IN = -0.3; // IN IS NEGATIVE FOR SHOOTER
    public static final double INTAKE_ELEVATOR_OUT = 0.3;
    public static final double INTAKE_ROLLER_OUT = -0.05; // OUT IS NEGATIVE FOR ROLLER

    public static final double INTAKING_POSITION = 32; //34
    public static final double MARGIN = 0.5;
  }

  public static class VisionSubsystem {
    public static final String LIMELIGHT_NAME = "sauron";
    public static final String LIMELIGHT_IP = "http://10.68.94.11:5801/";

    
  }

    public static final class LimelightConstants {
    /** upward angle of limelight camera [degrees] */
    public static final double LIMELIGHT_ANGLE = 22.0;
    /** distance from limelight lens from floor [inches] */
    public static final double LIMELIGHT_HEIGHT = 18.5;
    /** distance from hub apriltag to floor(bottom of tag) [inches] */
    public static final double APRILTAG_HEIGHT = 44.25;
    /** distance from the edge of the hub where the apriltag is located to the center of the hub [inches] */
    public static final double HUB_RADIUS = 23.5;
  }

  public static class PhysicsConstants {
    public static final double GRAVITY = 9.8;
    public static final double HUB_HEIGHT = 6; //Height in feet of hub
    
  }

  public static class Globals {
    //try not to use this but use robot container instead
  }


  public static class Credits {
    public static final String DRIVER = "Luke";
    public static final String AUX_DRIVER = "Jackson";
    public static final String DRIVE_COACH = "Savannah";

    public static final String[] Programmers = {"Luke", "Alec"};
  }




}
