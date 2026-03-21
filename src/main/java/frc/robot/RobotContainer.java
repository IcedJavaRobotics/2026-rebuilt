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
// import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.*;
import frc.robot.commands.autocommands.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.spindexer.*;
import frc.robot.commands.swerve.*;

import java.util.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

        // Initialize Controllers
        private final XboxController driverController = new XboxController(DriverConstants.MAIN_DRIVER_PORT);
        private final XboxController auxController = new XboxController(DriverConstants.AUX_DRIVER_PORT);
        private final Joystick driverStation = new Joystick(DriverConstants.DRIVER_STATION_PORT);

        // Triggers for more control
        JoystickButton manualSwitch = new JoystickButton(driverStation, 7);
        Trigger shootcontrolSwitch = new Trigger( () -> getSwitch());
        Trigger shootingTrigger = new Trigger( () -> getRightAuxTriggerValue());
        Trigger intakeTrigger = new Trigger( () -> getLeftAuxTriggerValue());

        Trigger lockTrigger = new Trigger( () -> getRightDriverTriggerValue());


        BooleanSupplier switchEnabled = ( () -> getSwitch());
        //private final SendableChooser<Command> autoChooser;

        PIDController headingController = new PIDController(0.015, 0, 0.001); // PID for making robot automatically face the hub

        private String formattedTime = "hi";

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {

                // Configure the trigger bindings
                headingController.enableContinuousInput(-180, 180); //Causes the pid for heading to loop along with the gyro
                configureBindings();

                // Setup default commands
                driveSubsystem.setDefaultCommand(driveFieldOrientedAngularVelocity); 
                //intakeSubsystem.setDefaultCommand(resetIntake);     //.onlyIf(switchEnabled));
     
                //autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be
                // `Commands.none()`
                //SmartDashboard.putData("AutoSelec", autoChooser);

                initializeDashboard();
                System.out.println("switch: " + getSwitch());
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

                // ----------------------- MAIN DRIVER CONTROLS ----------------------------------------------------------------------

                // Left bumper causes straightforward driving
                new Trigger(driverController::getRightBumperButton).whileTrue(driveRobotOriented);
                
                // B button causes the driver to zero their controller
                new JoystickButton(driverController, XboxController.Button.kB.value)
                        .whileTrue(new ZeroGyro(driveSubsystem));  //zero gyro on B
                
                new JoystickButton(driverController, XboxController.Button.kA.value)
                        .whileTrue(new SpindexerCommand(spindexerSubsystem));
                
                new JoystickButton(driverController, XboxController.Button.kB.value)
                        .whileTrue(new StartShooter(shooterSubsystem));

                // lockTrigger.whileTrue(new LockUpWheels(driveSubsystem));

                // ----------------------- AUX DRIVER CONTROLS -----------------------------------------------------------------------

                // Elevator Out
                new JoystickButton(auxController, XboxController.Button.kY.value)
                         .whileTrue(new IntakeElevatorOut(intakeSubsystem));
                // Elevator In
                new JoystickButton(auxController, XboxController.Button.kB.value)
                         .whileTrue(new IntakeElevatorIn(intakeSubsystem));
                // Shooter Power down
                new JoystickButton(auxController, XboxController.Button.kA.value)
                        .whileTrue(new TurnDownShooter(shooterSubsystem));
                // Shooter Power up
                new JoystickButton(auxController, XboxController.Button.kX.value)
                        .whileTrue(new TurnUpShooter(shooterSubsystem));
                // Shooter Reverse
                new JoystickButton(auxController, XboxController.Button.kRightBumper.value)
                        .whileTrue(new ShooterReverse(shooterSubsystem));

                //print "shut up you chud" - Antony

                // ----------------------- BUTTON BOARD CONTROLS ----------------------------------------------------------------------
                
                // Shooter Functions Check (Starts shooter)
               new JoystickButton(driverStation, DriverStationConstants.BOTTOM_LEFT)
                        .whileTrue(new StartShooter(shooterSubsystem));
                // Elevator Out
                new JoystickButton(driverStation, DriverStationConstants.TOP_LEFT)
                        .whileTrue(new IntakeElevatorOut(intakeSubsystem));
                // Elevator In
                new JoystickButton(driverStation, DriverStationConstants.MIDDLE_LEFT)
                        .whileTrue(new IntakeElevatorIn(intakeSubsystem));
                // Full intake command/Hold Intake (Holding makes intake elevator and rollers start, letting go resets)
                new JoystickButton(driverStation, DriverStationConstants.MIDDLE_RIGHT)
                        .whileTrue(new IntakeHold(intakeSubsystem));
                // Zeroes the intake
                new JoystickButton(driverStation, DriverStationConstants.BOTTOM_MIDDLE)
                        .whileTrue(new ZeroIntake(intakeSubsystem));
                // Shooter Reverse
                new JoystickButton(driverStation, DriverStationConstants.TOP_RIGHT)
                        .whileTrue(new IntakeRollerTest(intakeSubsystem));
                // Starts shooter
                shootingTrigger.whileTrue(new StartShooter(shooterSubsystem));
                // Full intake command/Hold Intake (Holding makes intake elevator and rollers start, letting go resets)
                intakeTrigger.whileTrue(new IntakeHold(intakeSubsystem));

                
        }

        /**
         * Sets up the Smartdashboard by adding all the values we will monitor on there
         */
        private void initializeDashboard(){
                setupDriverstation();
                
                // Switches
                SmartDashboard.putBoolean("auto intake inward", true);
                SmartDashboard.putBoolean("leds on", false);
        }

        /**
         * Sets up various things of the driver station
         * - Silences joystick warnings
         * - 
         */
        private void setupDriverstation(){
                DriverStation.silenceJoystickConnectionWarning(true);
                SmartDashboard.putNumber("Match Number", DriverStation.getMatchNumber());
                SmartDashboard.putString("Match type", DriverStation.getMatchType().toString());
                SmartDashboard.putString("Alliance", DriverStation.getAlliance().get().toString());
                SmartDashboard.putString("Competition", DriverStation.getEventName());
                SmartDashboard.putNumber("Time left", DriverStation.getMatchTime());

                // Adds time booted to the dashboard
                Date currentDate = new Date();
                LocalTime currentTime = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                formattedTime = currentTime.format(formatter);
        }

        /**
         * runs periodically to update the dashboards info with info that continuously updates
         */
        private void updateDashboard(){
                SmartDashboard.putNumber("Time left", DriverStation.getMatchTime());
        }


        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() { 
                //return autoChooser.getSelected(); 
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

















        // ----------------------------------------------------------------------------------------------------------------------------------------
        // --------------------------UTILITY METHODS FOR THE CONTROLLERS---------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------------------------------







                
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
}
