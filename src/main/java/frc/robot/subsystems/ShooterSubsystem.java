// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.data.LauncherPhysics;
import frc.robot.data.Constants.HardwareConstants;
import frc.robot.data.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

  private MotionMagicVelocityVoltage startupControl = new MotionMagicVelocityVoltage(0);  //use this mode when starting from 0
  private VelocityVoltage maintainControl = new VelocityVoltage(0); // use this mode when trying to maintain speed

  private TalonFX leadMotor = new TalonFX(HardwareConstants.SHOOTER_LEFT_MOTOR_ID); // Left motor is the leader
  private TalonFX followerMotor = new TalonFX(HardwareConstants.SHOOTER_RIGHT_MOTOR_ID); //right motor follows

  private LauncherPhysics launchTable;

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    syncMotors();
    invertMotors();
    launchTable = new LauncherPhysics();
  }

  /**
   * Use this method when starting the shooter from zero, uses MotionMagicVelocityVoltage for quick startup
   * @param velocity Velocity you are going to
   */
  public void startFromZero(double velocity){
    leadMotor.setControl(startupControl.withVelocity(-velocity)); //negative
  }

  /**
   * Use this method to maintain shooter speed while you are shooting, apparently lynk does it so its probably better? idk
   * @param velocity Velocity to maintain
   */
  public void maintainSpeed(double velocity){
    leadMotor.setControl(maintainControl.withVelocity(-velocity)); //negative
  }

  public double getVelocity(){
    return leadMotor.getVelocity().getValueAsDouble();
  }

  public void stop(){
    leadMotor.set(0);
  }


  /**
   * Uses lookup table to find velocity to feed into the other shoot methods
   * @param distance distance from center of robot to center of hub
   * @return velocity speed
   */
  public double getLaunchSpeed(double distance){
    return launchTable.getShootingVelocity(getLaunchSpeed(distance));
  }

  private void syncMotors(){
    System.out.println("STATUS OF SHOOTER MOTOR FOLLOWING: " + followerMotor.setControl(new Follower(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, MotorAlignmentValue.Opposed)).isOK()); 
  }

  private void invertMotors(){
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    System.out.println("STATUS OF SHOOTER MOTOR INVERSION: " + leadMotor.getConfigurator().apply(configs).isOK());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
