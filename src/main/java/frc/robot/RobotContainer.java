// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriverConstants;
// import frc.robot.commands.RollerInCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import swervelib.SwerveInputStream;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import swervelib.SwerveInputStream;

import java.io.ObjectInputFilter.Status;
import java.util.Map;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.subsystems.*;
import frc.robot.commands.functionchecks.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
        // The robot's subsystems and commands are defined here...

        private final SwerveSubsystem drivebase = new SwerveSubsystem();
        // private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
        //private final SelectorSubsystem selectorSubsystem = new SelectorSubsystem(shoulderSubsystem, elevatorSubsystem,wristSubsystem);
        private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
        private final SpindexerSubsystem spindexerSubsystem = new SpindexerSubsystem();
        private final VisionSubsystem visionSubsystem = new VisionSubsystem();
        private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();

        private final SendableChooser<Command> autoChooser;

        XboxController driverController = new XboxController(DriverConstants.MAIN_DRIVER_PORT);
        XboxController auxController = new XboxController(DriverConstants.AUX_DRIVER_PORT);
        private final Joystick driverStation = new Joystick(DriverConstants.DRIVER_STATION_PORT);

        PIDController headingController = new PIDController(0.015, 0, 0.001);

        ShuffleboardTab statusCheckTab = Shuffleboard.getTab("Status");
        ShuffleboardTab functionsCheckTab = Shuffleboard.getTab("Functions Check");

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the trigger bindings
                headingController.enableContinuousInput(-180, 180);
                configureBindings();
                drivebase.setDefaultCommand(driveFieldOrientedAngularVelocity); 

                DriverStation.silenceJoystickConnectionWarning(true);
                autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be
                // `Commands.none()`
                SmartDashboard.putData("AutoSelec", autoChooser);

                initializeDashboard();

        }

        private double getDeadzone() {
                return DriverConstants.DEADBAND;
        }

        private double getLeftX() {
                return -driverController.getLeftX();
        }

        private double getLeftY() {
                return -driverController.getLeftY();
        }
        private double getMultiplier(){
                if(driverController.getLeftStickButton()){
                        return 1;
                } else if(getLeftDriverTriggerValue()){
                        return 0.4;
                }
                return 0.5;
        }

        private double getTurnMultiplier(){
                if(driverController.getRightStickButton()){
                        return 1;
                } else{
                        return 0.5;
                }
        }

        SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                        () -> driverController.getLeftY() * getMultiplier(),
                        () -> driverController.getLeftX() * getMultiplier())
                        .withControllerRotationAxis(() -> getRightX())
                        .deadband(getDeadzone())
                        .scaleTranslation(1)// Can be changed to alter speed
                        .allianceRelativeControl(true);

        SwerveInputStream driveRobotOrientedVelocity = driveAngularVelocity.copy().robotRelative(true).allianceRelativeControl(false);

        Command driveFieldOrientedAngularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
        Command driveRobotOriented = drivebase.driveFieldOriented(driveRobotOrientedVelocity);

        /**
         * Use this method to define your trigger->command mappings. Triggers can be
         * created via the
         * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
         * an arbitrary
         * predicate, or via the named factories in {@link
         * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
         * {@link
         * CommandXboxController
         * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
         * PS4} controllers or
         * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
         * joysticks}.
         */
        private void configureBindings() {
                new Trigger(driverController::getRightBumperButton).whileTrue(driveRobotOriented);
                new JoystickButton(driverController, XboxController.Button.kA.value)
                        .whileTrue(new ShooterFunctionsCheckCommand(shooterSubsystem));

                //Intake bindings
                // new JoystickButton (auxController, XboxController.Button.kA.value)
                //         .whileTrue(new RollerInCommand(intakeSubsystem));
        }

        private void initializeDashboard(){
                SmartDashboard.putNumber("Gyro", drivebase.getSwerveDrive().getGyro().getRotation3d().getZ() * (180/Math.PI));
                SmartDashboard.putNumber("odometry angle", drivebase.getPose().getRotation().getDegrees());

                // Checking conections
                ShuffleboardLayout swerveLayout = statusCheckTab.getLayout("Swerve", BuiltInLayouts.kList).withSize(2,4);
                ShuffleboardLayout motorLayout = statusCheckTab.getLayout("Motors",BuiltInLayouts.kList).withSize(2,4);
                ShuffleboardLayout miscLayout = statusCheckTab.getLayout("Misc",BuiltInLayouts.kList).withSize(2,4);
        
                visionSubsystem.functionsCheck(statusCheckTab);
                drivebase.functionsCheck(statusCheckTab);
                climberSubsystem.functionsCheck(statusCheckTab);
                //TODO Add motor torque

                // Functions Checks
                ShuffleboardLayout swerveTests = functionsCheckTab.getLayout("Swerve", BuiltInLayouts.kList).withSize(2,4).withProperties(Map.of("Label position", "HIDDEN")); 

                functionsCheckTab.add(new ShooterFunctionsCheckCommand(shooterSubsystem));

        }

        private boolean isRobotRelative(){
                if(driverController.getRightBumperButton()){
                        return true;
                }
                return false;
        }

        private boolean auxRightstickLeft() {
                if (auxController != null) {
                        if (auxController.getRightX() <= -0.5) {
                                return true;
                        }
                        return false;
                }
                return false;
        }

        private boolean getRightDriverTriggerValue() {
                if (driverController != null) {
                        if (driverController.getRightTriggerAxis() >= 0.5) {
                                return true;
                        }
                        return false;
                } else {
                        return false;
                }
        }

        private boolean getLeftDriverTriggerValue() {
                if (driverController != null) {
                        if (driverController.getLeftTriggerAxis() >= 0.5) {
                                return true;
                        }
                        return false;
                } else {
                        return false;
                }
        }

        private boolean getRightAuxTriggerValue() {
                if (auxController != null) {
                        if (auxController.getRightTriggerAxis() >= 0.5) {
                                return true;
                        }
                        return false;
                } else {
                        return false;
                }
        }
        private boolean getLeftAuxTriggerValue() {
                if (auxController != null) {
                        if (auxController.getLeftTriggerAxis() >= 0.5) {
                                return true;
                        }
                        return false;
                } else {
                        return false;
                }
        }

        private boolean getLeftTriggerValue() {
                if (driverController != null) {
                        if (driverController.getLeftTriggerAxis() >= 0.5) {
                                return true;
                        }
                        return false;
                } else {
                        return false;
                }
        }

        /**
         * 
         * @return True if manual, false if automatic
         */
        private boolean getManualSwitch() {
                return driverStation.getRawButtonPressed(7);
        }

        /**
         * 
         * @return Will return the controller input either divided by two or not based on whether you hold the joystick button. If you hold left trigger, it will use the limelight to auto rotate
         */
        private double getRightX() {
                SmartDashboard.putNumber("pos rot", drivebase.getSwerveDrive().getPose().getRotation().getDegrees());
                if(getLeftDriverTriggerValue()){
                
                                return getControllerRotation();
                        
                        
                        
                }
                
                return getControllerRotation();
        }

        private double getControllerRotation() {
                return driverController.getRightX() * getTurnMultiplier();     
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                return autoChooser.getSelected();
                // return null;
        }
}
