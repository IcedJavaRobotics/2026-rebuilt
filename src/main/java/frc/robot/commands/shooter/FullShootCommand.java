// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.data.Constants.ShooterConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class FullShootCommand extends Command {
  ShooterSubsystem shooterSubsystem;
  IndexerSubsystem indexerSubsystem;
  
  /** Creates a new FullShootCommand. */
  public FullShootCommand(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {
    this.shooterSubsystem = shooterSubsystem;
    this.indexerSubsystem = indexerSubsystem;
    addRequirements(shooterSubsystem, indexerSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        shooterSubsystem.startFromZero(ShooterConstants.TEST_VELOCITY);
    if(shooterSubsystem.getVelocity() > ShooterConstants.TEST_VELOCITY_SWAP_SPEED){
      shooterSubsystem.maintainSpeed(ShooterConstants.TEST_VELOCITY);
    }
    if(shooterSubsystem.getVelocity() > ShooterConstants.SHOOTING_START_FEEDING){
      indexerSubsystem.feedShooter();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterSubsystem.stop();
    indexerSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
