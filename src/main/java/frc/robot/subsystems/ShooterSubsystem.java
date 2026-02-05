// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.HardwareConstants;

public class ShooterSubsystem extends SubsystemBase {

  TalonFX ShooterMotor;
  TalonFX ShooterMotorSlave;


  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    this.ShooterMotor = new TalonFX(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, HardwareConstants.CANBUS);
    this.ShooterMotorSlave = new TalonFX(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, HardwareConstants.CANBUS);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
