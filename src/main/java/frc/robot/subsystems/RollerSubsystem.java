// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.data.Constants.HardwareConstants;
import frc.robot.data.Constants.IntakeRollerConstants;

public class RollerSubsystem extends SubsystemBase {

  TalonFX rollerMotor = new TalonFX(HardwareConstants.INTAKE_ROLLER_ID);

  VelocityVoltage maintainControl = new VelocityVoltage(IntakeRollerConstants.AUTOMATIC_INTAKE_SPEED);

  /** Creates a new RollerSubsystem. */
  public RollerSubsystem() {}


    /**
   * Use this method to maintain shooter speed while you are shooting, apparently lynk does it so its probably better? idk
   * @param velocity Velocity to maintain
   */
  public void setSpeed(double velocity){
    rollerMotor.setControl(maintainControl.withVelocity(velocity)); 
  }

  public void startIntakeRollers(){
    setSpeed(IntakeRollerConstants.AUTOMATIC_INTAKE_SPEED);
  }
  public void startPullbackRollers(){
    setSpeed(IntakeRollerConstants.PULLIN_INTAKE_SPEED);
  }
  public void startShooterRollers(){
    setSpeed(IntakeRollerConstants.SHOOTING_INTAKE_SPEED);
  }

  public void testForward(){
    setSpeed(IntakeRollerConstants.TEST_SPEED);
  }
  public void testBackwards(){
    setSpeed(-IntakeRollerConstants.TEST_SPEED);
  }

  public void stop(){
    setSpeed(0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
