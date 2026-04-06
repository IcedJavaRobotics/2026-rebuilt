// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.data.Constants.HardwareConstants;
import frc.robot.data.Constants.IntakeElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {

  PIDController elevatorPID = new PIDController(0.02, 0, 0.001);
  PIDController panicPID = new PIDController(0.03, 0, 0.001);

  TalonFX leadMotor = new TalonFX(HardwareConstants.INTAKE_LEFT_LEADER_ELEVATOR_ID);
  TalonFX followerMotor = new TalonFX(HardwareConstants.INTAKE_RIGHT_FOLLOWER__ELEVATOR_ID);

  /** Creates a new ElevatorSubsystem. */
  public ElevatorSubsystem() {
    syncMotors();
  }

  private void setSpeed(double speed){
    leadMotor.set(speed);
  }

  public void manualElevatorIn(){
    setSpeed(IntakeElevatorConstants.MANUAL_ELEVATOR_IN);
  }
  public void manualElevatorOut(){
    setSpeed(IntakeElevatorConstants.MANUAL_ELEVATOR_OUT);
  }
  public void stop(){
    setSpeed(0);
  }
  public void zeroMotor(){
    leadMotor.setPosition(0);
  }
  public double getPosition(){
    return leadMotor.getPosition().getValueAsDouble();
  }
  public void goToIntakingPosition(){
    setSpeed(elevatorPID.calculate(getPosition(), IntakeElevatorConstants.INTAKING_POSITION));
  }
  public void goToHoldingPosition(){
    setSpeed(elevatorPID.calculate(getPosition(), IntakeElevatorConstants.HOLDING_POSITION));
  }
  public void goToPanicPosition(){
    setSpeed(panicPID.calculate(getPosition(), IntakeElevatorConstants.PANIC_POSITION));
  }

  private void syncMotors(){
    System.out.println("STATUS OF ELEVATOR MOTOR FOLLOWING: " + followerMotor.setControl(new Follower(HardwareConstants.SHOOTER_LEFT_MOTOR_ID, MotorAlignmentValue.Opposed))); 
  }
 

  
  private double getLeaderTorque(){
    double currentAmps = leadMotor.getStatorCurrent().getValueAsDouble();
    double motorTorque = currentAmps * HardwareConstants.TORQUE_CONSTANT;
    return motorTorque * HardwareConstants.KRAKEN_EFFICIENCY;
  }
  private double getFollowerTorque(){
    double currentAmps = followerMotor.getStatorCurrent().getValueAsDouble();
    double motorTorque = currentAmps * HardwareConstants.TORQUE_CONSTANT;
    return motorTorque * HardwareConstants.KRAKEN_EFFICIENCY;
  }
  private double getTorque(){
    return (0.5 * (getLeaderTorque() + getFollowerTorque()));
  }
 
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Intake Torque", getTorque());
    SmartDashboard.putNumber("Intake Position", getPosition());
  }
}
