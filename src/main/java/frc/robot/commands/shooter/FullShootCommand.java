// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SpindexerSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class FullShootCommand extends Command {
  ShooterSubsystem shooterSubsystem;
  SpindexerSubsystem spindexerSubsystem;
  LimelightSubsystem limelightSubsystem;
  /** Creates a new FullShootCommand. */
  public FullShootCommand(  ShooterSubsystem shooterSubsystem,SpindexerSubsystem spindexerSubsystem,LimelightSubsystem limelightSubsystem) {
    this.shooterSubsystem = shooterSubsystem;
    this.spindexerSubsystem = spindexerSubsystem;
    this.limelightSubsystem = limelightSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooterSubsystem,limelightSubsystem,spindexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if(limelightSubsystem.getDistance() == 6894){
      shooterSubsystem.startShooting(); //shoot at default speed if no apriltag detected
      //TODO make it remember the distance so that way if it gets interupted the speed doesnt change
    } else{
      shooterSubsystem.shootToDistance(limelightSubsystem.getDistance());
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(shooterSubsystem.isAtFullSpeed()){
      spindexerSubsystem.start();
      //intakeSubsystem.agitate();
      
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    spindexerSubsystem.stop();
    shooterSubsystem.stopShooting();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
