// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class PullIntakeInCommand extends Command {

  IntakeSubsystem intakeSubsystem;
  /** Creates a new PullIntakeInCommand. */
  public PullIntakeInCommand(IntakeSubsystem intakeSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(intakeSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeSubsystem.IntakeElevatorIn(0.2);
    intakeSubsystem.agitate();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stopIntake();
    intakeSubsystem.stopRoller();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(intakeSubsystem.getPosition() <= IntakeConstants.HOLDING_POSITION){
      return true;
    }
    return false;
  }
}
