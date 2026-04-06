// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.data;

import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
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
    public static final double DEFAULT_SPEED = 0.5;
    public static final double TURBO_SPEED = 0.8;
  }

  public static class DriverStationConstants {
    public static final int TOP_LEFT = 1; 
    public static final int TOP_MIDDLE = 9; 
    public static final int TOP_RIGHT = 2; 

    public static final int MIDDLE_LEFT = 6; 
    public static final int MIDDLE_MIDDLE = 8; 
    public static final int MIDDLE_RIGHT = 3; 

    public static final int BOTTOM_LEFT = 5; 
    public static final int BOTTOM_MIDDLE = 4; 
    public static final int BOTTOM_RIGHT = 10; 

    public static final int SWITCH = 7; // True when switch is DOWN 
  }

  public static class HardwareConstants {
    public static final int PIGEON_ID = 7;
    
    public static final int INTAKE_LEFT_LEADER_ELEVATOR_ID = 21;
    public static final int INTAKE_RIGHT_FOLLOWER__ELEVATOR_ID = 22;
    public static final int INTAKE_ROLLER_ID = 23;
  
    public static final int SHOOTER_LEFT_MOTOR_ID = 31;
    public static final int SHOOTER_RIGHT_MOTOR_ID = 32;

    public static final int INDEXER_MOTOR_ID = 41;

    public static final String RIO_CANBUS = "rio";
    public static final String CANIVORE_CANBUS = "CANivore";

    public static final int KRAKEN_RPS = 520; 
    public static final double STALL_TORQUE = 7.09;
    public static final double STALL_CURRENT = 370;
    public static final double TORQUE_CONSTANT = STALL_TORQUE/STALL_CURRENT;
    public static final double KRAKEN_EFFICIENCY = 0.9;
  }

  public static class ShooterConstants {

    public static final double GEAR_RATIO = 1;
    // All shooter speeds are in RPS/Velocity
    public static final double DEFAULT_SHOOTER_SPEED = 55; 
    public static final double REVERSE_SHOOTER_SPEED = 40;
    public static final double SHOOTING_START_FEEDING = 50;
    public static final double INIITAL_TEST_VELOCITY = 10;
    public static final double TEST_VELOCITY = 30;
    public static final double TEST_VELOCITY_SWAP_SPEED = 25;
    public static final double SELF_FEED_VELOCITY = 20;
  }

  public static class IndexerConstants { 
    public static final double TESTING_SPEED = 0.20;
    public static final double MANUAL_NORMAL_SPEED = 0.70;
    public static final double MANUAL_REVERSE_SPEED = -0.70;
    public static final double LIGHT_AGITATION_SPEED = 0.1;
    public static final double SELF_FEED_SPEED = 0.3;
  }

  public static class IntakeElevatorConstants {
    public static final double MANUAL_ELEVATOR_IN = -0.3; // IN IS NEGATIVE FOR SHOOTER
    public static final double MANUAL_ELEVATOR_OUT = 0.3;

    public static final double INTAKING_POSITION = 31.5;
    public static final double INTAKING_START_ROLLER_POSITION = 6;
    public static final double HOLDING_POSITION = 24;
    public static final double PANIC_POSITION = 3;
  }
  public static class IntakeRollerConstants {
    public static final double AUTOMATIC_INTAKE_SPEED = 1.00;
    public static final double SHOOTING_INTAKE_SPEED = 0.30;
    public static final double PULLIN_INTAKE_SPEED = 0.40;
    public static final double TEST_SPEED = 0.15;
  }

  public static class VisionSubsystem {
    public static final String LIMELIGHT_NAME = "sauron";
    public static final String LIMELIGHT_IP = "http://10.68.94.11:5801/";
    public static final Pose2d hubPos = new Pose2d(Units.inchesToMeters(182.11), Units.inchesToMeters(158.84), new Rotation2d());
    public static final Pose2d redHubPos = FlippingUtil.flipFieldPose(hubPos);    
  }

  public static class PhysicsConstants {
    public static final double GRAVITY = 9.8;
    public static final double HUB_HEIGHT = 6; //Height in feet of hub
  }

  public static class Credits {
    public static final String DRIVER = "Luke";
    public static final String AUX_DRIVER = "Jackson";
    public static final String DRIVE_COACH = "Savannah";

    public static final String[] Programmers = {"Luke", "Alec"};

    public static final String UselessPerson = "Safy";

  }




}
