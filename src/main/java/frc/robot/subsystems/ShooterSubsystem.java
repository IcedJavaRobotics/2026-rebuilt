// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.HardwareConstants;

public class ShooterSubsystem extends SubsystemBase {

  TalonFX shooterMotorMaster;
  TalonFX shooterMotorSlave;

  ShuffleboardTab functionsCheckTab = Shuffleboard.getTab("Functions Check");
  

  PIDController spinUpController = new PIDController(0.1, 0, 0);

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    this.shooterMotorMaster = new TalonFX(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, HardwareConstants.CANBUS);
    this.shooterMotorSlave = new TalonFX(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, HardwareConstants.CANBUS);

  }

  public void spinUp(double speed){
    shooterMotorMaster.set(spinUpController.calculate(shooterMotorMaster.getVelocity().getValueAsDouble() / HardwareConstants.KRAKEN_RPS, speed));
  }

  


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
