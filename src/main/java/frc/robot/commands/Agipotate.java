/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Agipotato;

public class Agipotate extends CommandBase {

  private final Agipotato m_agipotato;
  private final double speed;

/**
 * This command runs the Agitator at a set speed while the command is executed
 *
 * @param  Agipotato the Agitator Subsystem
 * @param  speed the speed at which the motor is told to run
 * @see    Agipotato
 * 
 * @author Andrew Card
 */
  public Agipotate(Agipotato m_agipotato, double speed) {

    this.m_agipotato = m_agipotato;
    this.speed = speed;

    addRequirements(m_agipotato);

  }

  @Override
  public void execute() {
    m_agipotato.agipotate(speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_agipotato.agipotate(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
