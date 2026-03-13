// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriverConstants;
import frc.robot.Constants.DriverStationConstants;
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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import swervelib.SwerveInputStream;

import java.io.ObjectInputFilter.Status;
import java.util.Map;
import java.util.function.BooleanSupplier;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.commands.autocommands.*;
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

        // initialize subsystems
        private final SwerveSubsystem driveSubsystem = new SwerveSubsystem();
        private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
        private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
        private final SpindexerSubsystem spindexerSubsystem = new SpindexerSubsystem();
        private final VisionSubsystem visionSubsystem = new VisionSubsystem();
        private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();
        private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();

        // Initialize Controllers
        private final XboxController driverController = new XboxController(DriverConstants.MAIN_DRIVER_PORT);
        private final XboxController auxController = new XboxController(DriverConstants.AUX_DRIVER_PORT);
        private final Joystick driverStation = new Joystick(DriverConstants.DRIVER_STATION_PORT);

        // Triggers for more control
        JoystickButton manualSwitch = new JoystickButton(driverStation, 7);
        Trigger shootcontrolSwitch = new Trigger( () -> getSwitch());
        Trigger shootingTrigger = new Trigger( () -> getRightAuxTriggerValue());
        Trigger intakeTrigger = new Trigger( () -> getLeftAuxTriggerValue());


        BooleanSupplier switchEnabled = ( () -> getSwitch());
        //private final SendableChooser<Command> autoChooser;

        PIDController headingController = new PIDController(0.015, 0, 0.001); // PID for making robot automatically face the hub


        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the trigger bindings
                headingController.enableContinuousInput(-180, 180);
                configureBindings();
                driveSubsystem.setDefaultCommand(driveFieldOrientedAngularVelocity); 
                intakeSubsystem.setDefaultCommand(resetIntake);     //.onlyIf(switchEnabled));
                DriverStation.silenceJoystickConnectionWarning(true);
                //autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be
                // `Commands.none()`
                //SmartDashboard.putData("AutoSelec", autoChooser);

                initializeDashboard();
                System.out.println("switch: " + getSwitch());
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

        SwerveInputStream driveAngularVelocity = SwerveInputStream.of(driveSubsystem.getSwerveDrive(),
                        () -> driverController.getLeftY() * getMultiplier(),
                        () -> driverController.getLeftX() * getMultiplier())
                        .withControllerRotationAxis(() -> getRightX())
                        .deadband(getDeadzone())
                        .scaleTranslation(1)// Can be changed to alter speed
                        .allianceRelativeControl(true);

        SwerveInputStream driveRobotOrientedVelocity = driveAngularVelocity.copy().robotRelative(true).allianceRelativeControl(false);

        Command driveFieldOrientedAngularVelocity = driveSubsystem.driveFieldOriented(driveAngularVelocity);
        Command driveRobotOriented = driveSubsystem.driveFieldOriented(driveRobotOrientedVelocity);
        Command resetIntake = intakeSubsystem.resetElevator(() -> getSwitch());

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
                //new Trigger(driverController::getRightBumperButton).whileTrue(driveRobotOriented);
                
                new JoystickButton(driverController, XboxController.Button.kB.value)
                        .whileTrue(new ZeroGyro(driveSubsystem));  //zero gyro on B



                new JoystickButton(driverController, XboxController.Button.kRightBumper.value)
                        .whileTrue(new IntakeHold(intakeSubsystem)); //right bumper = intake

                new JoystickButton(driverController, XboxController.Button.kLeftBumper.value)
                        .whileTrue(new ShooterFunctionsCheckCommand(shooterSubsystem));


                
                //manualSwitch.onTrue(new IntakeRollerTest(intakeSubsystem)); //while the switch is enabled, spin the spindexer at the given speed on smartdashboard
                
                new JoystickButton(driverStation, DriverStationConstants.BOTTOM_LEFT)
                        .whileTrue(new ShooterFunctionsCheckCommand(shooterSubsystem));

                new JoystickButton(driverStation, DriverStationConstants.TOP_LEFT)
                        .whileTrue(new IntakeElevatorTuner(intakeSubsystem, 0.3));
                new JoystickButton(driverStation, DriverStationConstants.MIDDLE_LEFT)
                        .whileTrue(new IntakeElevatorTuner(intakeSubsystem, -0.3));

                // new JoystickButton(driverStation, DriverStationConstants.TOP_RIGHT)
                //         .whileTrue(new IntakeRollerTest(intakeSubsystem));

                new JoystickButton(driverStation, DriverStationConstants.MIDDLE_RIGHT)
                        .whileTrue(new IntakeHold(intakeSubsystem));
              

                new JoystickButton(driverStation, DriverStationConstants.TOP_MIDDLE)
                        .whileTrue(new ClimberUp(climberSubsystem));
                
                new JoystickButton(driverStation, DriverStationConstants.MIDDLE_MIDDLE)
                        .whileTrue(new ClimberDown(climberSubsystem));

                new JoystickButton(driverStation, DriverStationConstants.BOTTOM_MIDDLE)
                        .whileTrue(new ZeroIntake(intakeSubsystem));

                new JoystickButton(driverStation, DriverStationConstants.TOP_RIGHT)
                        .whileTrue(new ShooterReverse(shooterSubsystem));


                new JoystickButton(auxController, XboxController.Button.kY.value)
                         .whileTrue(new IntakeElevatorTuner(intakeSubsystem, 0.3));
                new JoystickButton(auxController, XboxController.Button.kB.value)
                         .whileTrue(new IntakeElevatorTuner(intakeSubsystem, -0.3));
                new JoystickButton(auxController, XboxController.Button.kA.value)
                        .whileTrue(new TurnDownShooter(shooterSubsystem));
                new JoystickButton(auxController, XboxController.Button.kX.value)
                        .whileTrue(new TurnUpShooter(shooterSubsystem));
                
                new JoystickButton(auxController, XboxController.Button.kRightBumper.value)
                        .whileTrue(new ShooterReverse(shooterSubsystem));
                new JoystickButton(auxController, XboxController.Button.kStart.value)
                        .whileTrue(new ClimberUp(climberSubsystem));
                new JoystickButton(auxController, XboxController.Button.kBack.value)
                        .whileTrue(new ClimberDown(climberSubsystem));

                //manualSwitch.whileTrue(new ShooterReverse(shooterSubsystem));

                SmartDashboard.putBoolean("auto intake inward", true);
                shootingTrigger.whileTrue(new ShooterFunctionsCheckCommand(shooterSubsystem));
                intakeTrigger.whileTrue(new IntakeHold(intakeSubsystem));

                
        }

        private void initializeDashboard(){
              


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
         * @return True if up, false if down
         */
        private boolean getSwitch() {
                SmartDashboard.putBoolean("switch", !manualSwitch.getAsBoolean());
                return !manualSwitch.getAsBoolean();
        }

        /**
         * 
         * @return Will return the controller input either divided by two or not based on whether you hold the joystick button. If you hold left trigger, it will use the limelight to auto rotate
         */
        private double getRightX() {
                //SmartDashboard.putNumber("pos rot", driveSubsystem.getSwerveDrive().getPose().getRotation().getDegrees());
                if(getLeftDriverTriggerValue()){
                
                                return getControllerRotation();
                        
                        
                        
                }
                
                //System.out.println("switch: " + getSwitch());
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
                //return autoChooser.getSelected();
                 //return new ShooterFunctionsCheckCommand(shooterSubsystem);
                 return new SequentialCommandGroup(
                        new TurnOnShooter(shooterSubsystem),
                        new SetIntakeOut(intakeSubsystem),
                        new WaitCommand(1),
                        new SetIntakeIn(intakeSubsystem),
                        new WaitCommand(0.7),
                        new SetIntakeOut(intakeSubsystem),
                        new WaitCommand(0.7),
                        new SetIntakeIn(intakeSubsystem),
                        new WaitCommand(0.7),
                        new SetIntakeOut(intakeSubsystem),
                        new WaitCommand(0.7),
                        new SetIntakeIn(intakeSubsystem),
                        new WaitCommand(0.7),
                        new SetIntakeOff(intakeSubsystem),
                        new TurnOffShooter(shooterSubsystem)
                 );
        }
}
