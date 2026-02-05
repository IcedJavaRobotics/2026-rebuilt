// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */

  TalonFX intakeElevatorMotor;
  TalonFX intakeRollerMotor;

  public IntakeSubsystem() {

    this.intakeElevatorMotor = new TalonFX(Constants.IntakeConstants.ELEVATOR_MOTOR_ID, "rio");
    this.intakeRollerMotor = new TalonFX(Constants.IntakeConstants.ROLLER_MOTOR_ID, "rio");

  } 

  public void extendIntake (){

    intakeElevatorMotor.set(Constants.IntakeConstants.ELEVATOR_SPEED);

  }
  public void retractIntake (){

    intakeElevatorMotor.set(-Constants.IntakeConstants.ELEVATOR_SPEED);

  }
  public void stopIntake (){

    intakeElevatorMotor.set(0);

  }

  public void rollerIn (){

    intakeRollerMotor.set(Constants.IntakeConstants.ROLLER_SPEED);

  }
  public void rollerOut (){

    intakeRollerMotor.set(-Constants.IntakeConstants.ROLLER_SPEED);

  }
  public void rollerStop (){

    intakeRollerMotor.set(0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
