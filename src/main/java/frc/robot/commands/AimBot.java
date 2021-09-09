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
    double[] motorValues = m_drivetrain.getMotorValues();
    return (Math.abs(motorValues[0]) - Math.abs(motorValues[1]) == 0); //this should only trigger when both motor powers are 0
  }
}