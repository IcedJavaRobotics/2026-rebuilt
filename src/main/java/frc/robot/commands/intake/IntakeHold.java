// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class IntakeHold extends Command {

  IntakeSubsystem intakeSubsystem;

  /** Creates a new IntakeHold. */
  public IntakeHold(IntakeSubsystem intakeSubsystem) {
    this.intakeSubsystem = intakeSubsystem;

    addRequirements(intakeSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //intakeSubsystem.setRoller(IntakeConstants.AUTOMATIC_INTAKE_SPEED);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //intakeSubsystem.setRoller(IntakeConstants.AUTOMATIC_INTAKE_SPEED);
    intakeSubsystem.goOut();
    if(intakeSubsystem.getPosition() >= (IntakeConstants.INTAKING_POSITION-2)){
      intakeSubsystem.setRollerToTestSpeed();
    } else{
      intakeSubsystem.stopRoller();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stopIntake();
    intakeSubsystem.stopRoller();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
