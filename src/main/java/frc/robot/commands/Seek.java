// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Seek extends CommandBase {

  Drivetrain m_drivetrain;


  /**
   * This command moves the robot to a desired firing angle and distance within the tollerance set in m_drivetrain.visionDrive()
   * <p>
   *
   * @param m_drivetrain the Drivetrain subsystem
   * @see Drivetrain
   * 
   * @author Andrew Card
   */
  public Seek(Drivetrain m_drivetrain) {
    
    this.m_drivetrain = m_drivetrain;
    addRequirements(m_drivetrain);

  }

  @Override
  public void initialize() {
    System.out.println("Aimbot Init");
  }

  @Override
  public void execute() {
    m_drivetrain.comboDrive(0, 0.3);
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.comboDrive(0, 0); //Stops the drivetrain
  }

  @Override
  public boolean isFinished() {
    return m_drivetrain.getLimelight().getIsTargetFound(); //Stops the aiming if it is within the tollerance
  }
}