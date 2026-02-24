// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.HardwareConstants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */

  TalonFX intakeElevatorMotor;
  TalonFX intakeRollerMotor;

  public IntakeSubsystem() {

    this.intakeElevatorMotor = new TalonFX(HardwareConstants.INTAKE_ELEVATOR_ID, "rio");
    this.intakeRollerMotor = new TalonFX(HardwareConstants.INTAKE_ROLLER_ID, "rio");

    SmartDashboard.putNumber("roller-test-speed", 0.05);
  } 

  public void setIntake(double speed){
    intakeElevatorMotor.set(speed);
  }
  public void stopIntake(){
    intakeElevatorMotor.set(0);
  }

  public void setRoller(double speed){
    intakeRollerMotor.set(speed);
  }
  public void stopRoller(){
    intakeRollerMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("elevatorEncoder", intakeElevatorMotor.getPosition().getValueAsDouble());
    
  }
}
