// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.data.Constants.HardwareConstants;
import frc.robot.data.Constants.IndexerConstants;

public class IndexerSubsystem extends SubsystemBase {

  TalonFX indexerMotor = new TalonFX(HardwareConstants.INDEXER_MOTOR_ID);
  
  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {}

  public void setSpeed(double speed){
    indexerMotor.set(speed);
  }

  public void feedShooter(){
    setSpeed(IndexerConstants.MANUAL_NORMAL_SPEED);
  }
  public void reverseIndex(){
    setSpeed(IndexerConstants.MANUAL_REVERSE_SPEED);
  }
  public void agitateLightly(){
    setSpeed(IndexerConstants.LIGHT_AGITATION_SPEED);
  }
  public void testForward(){
    setSpeed(IndexerConstants.TESTING_SPEED);
  }
  public void testBackwards(){
    setSpeed(-IndexerConstants.TESTING_SPEED);
  }
  public void selfFeed(){
    setSpeed(IndexerConstants.SELF_FEED_SPEED);
  }
  public void stop(){
    setSpeed(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
