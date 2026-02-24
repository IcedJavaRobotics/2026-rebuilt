// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.SpindexerConstants;

public class SpindexerSubsystem extends SubsystemBase {


  TalonFX spindexerMotor;

  /** Creates a new SpindexerSubsystem. */
  public SpindexerSubsystem() {
    spindexerMotor = new TalonFX(HardwareConstants.SPINDEXER_MOTOR_ID);


  }

  public void set(double speed){
    spindexerMotor.set(speed);
  }

  public void start(){
    set(SpindexerConstants.MANUAL_NORMAL_SPEED);
  }
  public void startTest(){
    set(SpindexerConstants.TESTING_SPEED);
  }

  public void stop(){
    set(0);
  }

  public double getSpeed(){
    return spindexerMotor.get();
  }

    public void runSpindexer() {
      // TODO - add take-back-half method to accelerate faster
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("spindexer-speed", getSpeed());
    // This method will be called once per scheduler run
  }


}
