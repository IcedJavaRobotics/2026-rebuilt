// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.IntakeConstants;


public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */

  PIDController elevatorPID = new PIDController(0.03, 0, 0);

  TalonFX intakeElevatorMotor;
  TalonFX intakeRollerMotor;

  public IntakeSubsystem() {

    this.intakeElevatorMotor = new TalonFX(HardwareConstants.INTAKE_ELEVATOR_ID, "rio");
    this.intakeRollerMotor = new TalonFX(HardwareConstants.INTAKE_ROLLER_ID, "rio");

    SmartDashboard.putNumber("roller-test-speed", -0.3); //negative is the right way
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
  public void setRollerToTestSpeed(){
    setRoller(SmartDashboard.getNumber("roller-test-speed", 0));
  }

  public void stopRoller(){
    intakeRollerMotor.set(0);
  }

  public void zeroMotor(){
    intakeElevatorMotor.setPosition(0);
  }

  public void goOut(){
    if((getPosition()<=(IntakeConstants.INTAKING_POSITION-22))){
      setIntake(0.2);
    } else if((getPosition()<=(IntakeConstants.INTAKING_POSITION-3))){
      setIntake(0.5);
    } else if((getPosition()<=(IntakeConstants.INTAKING_POSITION-1))){
      setIntake(0.2);
    }else {
      setIntake(0);
    }
    //setIntake(elevatorPID.calculate(getPosition(),IntakeConstants.INTAKING_POSITION));
  }

  public double getPosition(){
    return intakeElevatorMotor.getPosition().getValueAsDouble();
  }

  private void defaultReset(){
    if(getPosition() >= 8){
      intakeRollerMotor.setNeutralMode(NeutralModeValue.Brake);
    }
    if(getPosition() >= 2){
      intakeElevatorMotor.set(-0.3);
      intakeRollerMotor.setNeutralMode(NeutralModeValue.Coast);
    } else{
      stopIntake();
      intakeRollerMotor.setNeutralMode(NeutralModeValue.Brake);
    }
  }

  public Command resetElevator(){
      return run(() -> {
        this.defaultReset();
      });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("elevatorEncoder", intakeElevatorMotor.getPosition().getValueAsDouble());
    
  }
}
