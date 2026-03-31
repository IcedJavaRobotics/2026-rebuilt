// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LauncherPhysics;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {


  LauncherPhysics launcherCalculator = new LauncherPhysics();

  TalonFX ShooterMotorLeader;
  TalonFX ShooterMotorFollower;

  
  

  double desiredSpeed = ShooterConstants.DEFAULT_SHOOTER_SPEED; 

  PIDController spinUpController = new PIDController(0.1, 0, 0);

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {

    // Setup motors
    this.ShooterMotorLeader = new TalonFX(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, HardwareConstants.CANBUS);
    this.ShooterMotorFollower = new TalonFX(HardwareConstants.SHOOTER_RIGHT_MOTOR_ID, HardwareConstants.CANBUS);
    System.out.println("STATUS OF MOTOR FOLLOWING: " + ShooterMotorFollower.setControl(new Follower(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, MotorAlignmentValue.Opposed))); 
    
    // Inialize smart dashboard
    updateDesiredSpeed();
  }

  /**
   * Base method, spins to the desired speed as fast as possible. Right now, it uses a PID loop but we will switch to eiher take-back-half or full-back acceleration methods
   * @param speed Desired speed to get to
   * @deprecated Right now this method should not be used, as it is non functional
   */
  public void spinUp(double speed){
    //ShooterMotorLeader.set(spinUpController.calculate(ShooterMotorLeader.getVelocity().getValueAsDouble() / HardwareConstants.KRAKEN_RPS, speed));
    ShooterMotorLeader.set(speed);
  }


  public void shootToDistance(double distance){
    spinUp(-launcherCalculator.getRPM(distance));
  }

  /**
   * Sets the desired speed in smartdashboard to a new number
   * @param speed the new desired speed
   */
  public void setDesiredSpeed(double speed){
    this.desiredSpeed = speed;
    SmartDashboard.putNumber("shootr spd desired", this.desiredSpeed);
  }

  /**
   * pushes the codes desiredspeed value with smartdashboards value
   */
  private void updateDesiredSpeed(){
    SmartDashboard.putNumber("shootr spd desired", this.desiredSpeed);
  }
  /**
   * Starts the shooter motor by setting it to the desired speed in the smartdashboard. 
   */
  public void startShooting(){
    ShooterMotorLeader.set(this.desiredSpeed);
  }

  /**
   * Stops the shooter motor completely by setting the speed to 0
   */
  public void stopShooting(){
    ShooterMotorLeader.set(0);
  }

  /**
   * Sets the shooter to spin in an inverted direction, this can be used to get balls out of being stuck in the shooter
   */
  public void reverseShooter(){
    ShooterMotorLeader.set(ShooterConstants.REVERSE_SHOOTER_SPEED);
  }

  public void checkShooter(){
    if (ShooterMotorLeader.getVelocity().getValueAsDouble() >= (this.desiredSpeed-ShooterConstants.SHOOTING_DEADZONE)) {

    }
    
  }

  public double getVelocity(){
    return ShooterMotorLeader.getVelocity().getValueAsDouble();
  }
  
  public boolean isAtFullSpeed(){
    if(this.getVelocity() <= (this.desiredSpeed*84)){
      return true;
    }
    return false;
    // if(this.getVelocity() >= launcherCalculator.getVelocity(this.desiredSpeed)){
    //   return true;
    // }
    // return false;
  }


  // These methods can be used to change the desired speed of the shooter through buttons, used primarily for tuning
  public void increaseDesiredSpeed(){
    this.desiredSpeed += ShooterConstants.DESIRED_SPEED_INTERVAL;
    updateDesiredSpeed();
  }
  public void decreaseDesiredSpeed(){
    this.desiredSpeed -= ShooterConstants.DESIRED_SPEED_INTERVAL;
    updateDesiredSpeed();
  }

  // A series of methods used to obtain information about the motors
  private double getLeaderTorque(){
    double currentAmps = ShooterMotorLeader.getStatorCurrent().getValueAsDouble();
    double motorTorque = currentAmps * HardwareConstants.TORQUE_CONSTANT;
    return motorTorque * HardwareConstants.EFFICIENCY;
  }
  private double getFollowerTorque(){
    double currentAmps = ShooterMotorFollower.getStatorCurrent().getValueAsDouble();
    double motorTorque = currentAmps * HardwareConstants.TORQUE_CONSTANT;
    return motorTorque * HardwareConstants.EFFICIENCY;
  }
  /**
   * Adds information about the shooter motors to smartdashboard
   * @apiNote Motor torque
   * @apiNote Motor speed
   * @apiNote Motor temperature
   */
  private void postMotorInformation(){
      SmartDashboard.putNumber("shooter lead Torque", getLeaderTorque());
      SmartDashboard.putNumber("shooter foll Torque", getFollowerTorque());
      SmartDashboard.putNumber("Shooter lead speed", ShooterMotorLeader.get());
      SmartDashboard.putNumber("Shooter foll speed", ShooterMotorFollower.get());
      SmartDashboard.putNumber("shooter lead temp", ShooterMotorLeader.getDeviceTemp().getValueAsDouble());
      SmartDashboard.putNumber("shooter foll temp", ShooterMotorFollower.getDeviceTemp().getValueAsDouble());

      SmartDashboard.putNumber("Shooter Velocity", this.getVelocity());
      SmartDashboard.putNumber("estimated shooter velocity", launcherCalculator.getVelocity(this.desiredSpeed));
      SmartDashboard.putBoolean("IsFullSpeed", isAtFullSpeed());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    postMotorInformation();
  }
}
