// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LimelightConstants;

import static frc.robot.Constants.LimelightConstants;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;

public class LimelightSubsystem extends SubsystemBase {

    private double lockedApriltag = -1;
    private double lastSeenApriltag = -1;

    public LimelightSubsystem() {

        HttpCamera httpCamera = new HttpCamera("sauron", "http://10.68.94.11:5801/");
        CameraServer.getVideo(httpCamera);
        Shuffleboard.getTab("LiveWindow").add(httpCamera);

        // CameraServer.getInstance().addCamera(httpCamera); Shuffleboard.getTab("Tab")
        // .add(httpCamera);

    }

    /**
     * @return The ID of the apriltag it can see
     */
    public double getTid() {
        return NetworkTableInstance.getDefault().getTable("limelight-sauron").getEntry("tid").getDouble(0);
    }

    /**
     * @return the distance from the center of the limelight view to the apriltag
     *         horizontally
     */
    public double getTx() {
        return NetworkTableInstance.getDefault().getTable("limelight-sauron").getEntry("tx").getDouble(0);
    }

    /**
     * @return the distance from the center of the limelight view to the apriltag
     *         vertically
     */
    public double getTy() {
        return NetworkTableInstance.getDefault().getTable("limelight-sauron").getEntry("ty").getDouble(0);
    }

    /**
     * 
     * @return the size of the apriltag seen as a square, which roughly translates
     *         to distance away from the apriltag
     */
    public double getTa() {
        return NetworkTableInstance.getDefault().getTable("limelight-sauron").getEntry("ta").getDouble(0);
    }

    /**
     * 
     * @param mode 1.0 for on, 0.0 for off
     */
    public void setFlasher(double mode) {
        NetworkTableInstance.getDefault().getTable("limelight-sauron").getEntry("ledMode").setDouble(mode);
    }

    /**
     * whether or not the limelight sees a apriltag
     * 
     * @return true if it sees an apriltag, false if it didnt detect a tag
     */
    public Boolean tagDetected() {

        if (NetworkTableInstance.getDefault().getTable("limelight-sauron").getEntry("tv").getDouble(0) == 1) {
            return true;
        }
        return false;
    }

    public void lockApriltag(){
        this.lockedApriltag = lastSeenApriltag;
    }

    /**
     * command for calculating limelight distance
     * 
     * @return horizontal floor distance from robot to apriltag
     */
    public double getDistance() {
        return 6894;
        // if(!tagDetected()){
        //     return 6894;
        // }
        // double targetOffsetAngle_Vertical = getTy();
        // double goalHeightInches = LimelightConstants.APRILTAG_HEIGHT;
        // double angleToGoalRadians = (LimelightConstants.LIMELIGHT_ANGLE + targetOffsetAngle_Vertical) * (3.14159 / 180);

        // // calculate distance
        // if (getTy() >= 0) {
        //     return ((goalHeightInches - LimelightConstants.LIMELIGHT_HEIGHT) / Math.tan(angleToGoalRadians) + LimelightConstants.HUB_RADIUS);
        // } else {
        //     return ((LimelightConstants.LIMELIGHT_HEIGHT) / Math.tan(angleToGoalRadians) + LimelightConstants.HUB_RADIUS);
        // }

    }



    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        if(getTid() != -1){
            lastSeenApriltag = this.getTid();
        }
        SmartDashboard.putNumber("apriltag", getTid());
        SmartDashboard.putNumber("Locked apriltag", lockedApriltag);
        SmartDashboard.putNumber("last apriltag", lastSeenApriltag);
        SmartDashboard.putNumber("distance", getDistance());


    }


}