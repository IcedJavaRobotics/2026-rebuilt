// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  /** Creates a new VisionSubsystem. */

  ShuffleboardTab visionTab = Shuffleboard.getTab("Vision");
  public VisionSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void functionsCheck(ShuffleboardTab functionsCheckTab) {
    ShuffleboardLayout cameraLayout = functionsCheckTab.getLayout("Cameras",BuiltInLayouts.kList).withSize(2,2);
    // Add booleans of if cameras are connected into this camera layout tab 
  }
}
