// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AimBot extends CommandBase {

  Drivetrain m_drivetrain;

  public AimBot(Drivetrain m_drivetrain) {
    
    this.m_drivetrain = m_drivetrain;

    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {

    System.out.println("Aimbot Init");

  }

  @Override
  public void execute() {

    m_drivetrain.visionDrive();

  }

  @Override
  public void end(boolean interrupted) {

    m_drivetrain.comboDrive(0, 0);

  }

  @Override
  public boolean isFinished() {
    // Stops the command when the target is within 1 degree of the target on each axis.
    double xError = m_drivetrain.getLimelight().getdegRotationToTarget();
    double yError = m_drivetrain.getLimelight().getdegVerticalToTarget();
    return ((Math.abs(xError) < 1) && (Math.abs(yError) < 1));
  }
}