// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.primary;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.data.Constants.IntakeElevatorConstants;
import frc.robot.subsystems.*;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class FullIntakeCommand extends Command {
  ElevatorSubsystem elevatorSubsystem;
  RollerSubsystem rollerSubsystem;
  IndexerSubsystem indexerSubsystem;

  
  /** Creates a new FullIntakeCommand. */
  public FullIntakeCommand(ElevatorSubsystem elevatorSubsystem, RollerSubsystem rollerSubsystem, IndexerSubsystem indexerSubsystem) {
    this.elevatorSubsystem = elevatorSubsystem;
    this.rollerSubsystem = rollerSubsystem;
    this.indexerSubsystem = indexerSubsystem;
    addRequirements(elevatorSubsystem, rollerSubsystem, indexerSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elevatorSubsystem.goToIntakingPosition();
    if(elevatorSubsystem.getPosition() >= IntakeElevatorConstants.INTAKING_START_ROLLER_POSITION){
      rollerSubsystem.startIntakeRollers();
      indexerSubsystem.agitateLightly();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevatorSubsystem.stop();
    rollerSubsystem.stop();
    indexerSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
