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

  

  // things I copied------------
  private double targetVelocity;
  private double gain;
  private double motorOutput = 0;
  private double lastError = 0;
  private double tbhValue = 0;
  //end-------------------------
  /** Creates a new SpindexerSubsystem. */
  public SpindexerSubsystem() {
    spindexerMotor = new TalonFX(HardwareConstants.SPINDEXER_MOTOR_ID);
    SmartDashboard.putNumber("desired indexer speed", SpindexerConstants.MANUAL_NORMAL_SPEED);

  }

  public void set(double speed){
    spindexerMotor.set(speed);
  }

  public void start(){
    set(SmartDashboard.getNumber("desired indexer speed", 0));
  }
  public void startReverse(){
    set(-SmartDashboard.getNumber("desired indexer speed", 0));
  }
  public void startTest(){
    set(SmartDashboard.getNumber("spindexer-speed", 0));
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
  
  // Everything below is copied---------------------------
  public void tbhController (double gain) {
    this.gain = gain;
  }

  public void setTarget(double target) {
    this.targetVelocity = target;

    this.tbhValue = (2 * target) - 1;
  }

  public double update(double currentVelocity) {
    double error = targetVelocity - currentVelocity;

    motorOutput += error * gain;

    if (motorOutput > 1.0) {
      motorOutput = 1.0;
    } else if (motorOutput < -1.0){
    motorOutput = -1.0;}

    if (Math.signum(error) != Math.signum(lastError)) {
      motorOutput = 0.5 * (motorOutput + tbhValue);

      tbhValue = motorOutput;
    }

    lastError = error;
    return motorOutput;
  }
  // copy stops here---------------------------------------

  @Override
  public void periodic() {
    SmartDashboard.putNumber("spindexer-speed", 0.2);
    // This method will be called once per scheduler run
  }


}
