// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.IntakeConstants;


public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */

  PIDController elevatorPID = new PIDController(0.03, 0, 0);

  TalonFX intakeElevatorMotor;
  TalonFX intakeElevatorFollower;

  TalonFX intakeRollerMotor;


  public IntakeSubsystem() {

    this.intakeElevatorMotor = new TalonFX(HardwareConstants.INTAKE_ELEVATOR_ID, "rio");
    this.intakeRollerMotor = new TalonFX(HardwareConstants.INTAKE_ROLLER_ID, "rio");
    this.intakeElevatorFollower = new TalonFX(HardwareConstants.INTAKE_ELEVATOR_FOLLOWER_ID, "rio");

     System.out.println("STATUS OF INTAKE MOTOR FOLLOWING: " + intakeElevatorFollower.setControl(new Follower(HardwareConstants.INTAKE_ELEVATOR_ID, MotorAlignmentValue.Aligned))); 
    

    SmartDashboard.putNumber("roller-test-speed", -0.35); //negative is the right way
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

  private void defaultReset(boolean runing){
  //   if(switchStatus.getAsBoolean() == false){ //if the intake back switch is off
  //     intakeRollerMotor.set(0);
  //     System.out.println("AUTO INTAKE OFF");
  //   }else{
  //   if(getPosition() >= 12){
  //     intakeRollerMotor.setNeutralMode(NeutralModeValue.Brake);
  //     intakeElevatorMotor.set(-0.4);
  //   }
  //   if(getPosition() >= 1){
  //     intakeElevatorMotor.set(0);
  //     intakeRollerMotor.setNeutralMode(NeutralModeValue.Coast);
  //   } else{
  //     stopIntake();
  //     intakeRollerMotor.setNeutralMode(NeutralModeValue.Brake);
  //   }
  // }
      if(SmartDashboard.getBoolean("auto intake inward", false)){
      System.out.println("running default command as intended master");
      } 
      System.out.println(SmartDashboard.getBoolean("auto intake inward", false));
  
  }

  public void panicReset(){
    intakeElevatorMotor.set(elevatorPID.calculate(intakeElevatorMotor.getPosition().getValueAsDouble(), 3));
  }

  public Command resetElevator(BooleanSupplier supplier){
      return run(() -> {
          this.defaultReset(supplier.getAsBoolean());
      });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("elevatorEncoder", intakeElevatorMotor.getPosition().getValueAsDouble());
    
  }

}
