/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Agitator;

public class cAgitator extends CommandBase {

  private final Agitator m_agitator;
  private final double speed;

/**
 * This command runs the Agitator at a set speed while the command is executed
 *
 * @param  Agitator the Agitator Subsystem
 * @param  speed the speed at which the motor is told to run
 * @see    Agipotato
 * 
 * @author Andrew Card
 */
  public cAgitator(Agitator m_agitator, double speed) {

    this.m_agitator = m_agitator;
    this.speed = speed;

    addRequirements(m_agitator);

  }

  @Override
  public void execute() {
    m_agitator.agipotate(speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_agitator.agipotate(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
