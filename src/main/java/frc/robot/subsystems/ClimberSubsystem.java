// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase {

  TalonFX climberMotor;
  
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    climberMotor = new TalonFX(HardwareConstants.CLIMBER_MOTOR_ID, HardwareConstants.CANBUS);
  }

  public void functionsCheck() {
    SmartDashboard.putNumber("climberTorque", getTorque());
  }

  public double getTorque(){
    double currentAmps = climberMotor.getStatorCurrent().getValueAsDouble();
    double motorTorque = currentAmps * ClimberConstants.TORQUE_CONSTANT;
    return motorTorque * ClimberConstants.GEAR_RATIO * ClimberConstants.EFFICIENCY;
  }

  public void moveUp(){
    //if(climberMotor.getPosition().getValueAsDouble() <= -380){
      climberMotor.set(-1);
    
  }
  public void moveDown(){
    //if(climberMotor.getPosition().getValueAsDouble() <= 0){
    climberMotor.set(1);
    //} else{
    //   climberMotor.set(0);
    // }
  }
  public void stopClimber(){
    climberMotor.set(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("climber encoder", climberMotor.getPosition().getValueAsDouble());
    // This method will be called once per scheduler run
    functionsCheck();
  }


}
