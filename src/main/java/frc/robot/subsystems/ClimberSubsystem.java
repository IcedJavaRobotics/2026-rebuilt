// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.HardwareConstants;

public class ClimberSubsystem extends SubsystemBase {

  TalonFX climberMotor;
  
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    climberMotor = new TalonFX(HardwareConstants.CLIMBER_MOTOR_ID, HardwareConstants.CANBUS);
  }

  public void functionsCheck(ShuffleboardTab statusCheckTab) {
    
  }

  public double getTorque(){
    double currentAmps = climberMotor.getStatorCurrent().getValueAsDouble();
    double torqueNm = currentAmps * (7.09 / 370.0); // Current * (Stall Torque / Stall Current)
    return torqueNm;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}
