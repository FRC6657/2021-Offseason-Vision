/**
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class cOuttake extends CommandBase {

  private final Outtake m_outtake;

  /**
   * This command runs the Outtake when the command is executing
   * <p>
   * This command raises the Hopper Gate when Initialized, and Lowers it when Ended
   *
   * @param m_outtake the Outtake Subsystem
   * @see Outtake
   * 
   * @author Andrew Card
   */

  public cOuttake(Outtake m_outtake) {

    this.m_outtake = m_outtake;

    addRequirements(m_outtake);

  }
  @Override
  public void initialize() {
    m_outtake.setServoAngle(115); //Sets the servo gate to open
  }

  @Override
  public void execute() {
    m_outtake.outtake(0.9); //Runs the outtake
  }

  @Override
  public void end(boolean interrupted) {
    
    m_outtake.outtake(0); //Stops the outtake

    m_outtake.setServoAngle(167); // Closes the servo gate

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
