// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AimBot;
import frc.robot.commands.OuttakePowercells;
import frc.robot.commands.TeleOp;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Outtake;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  private final Drivetrain m_drivetrain = new Drivetrain(); //Drivetrain Subsystem
  private final Outtake m_outtake = new Outtake();
  private final Joystick m_nes = new Joystick(0); //Controller
  private final SendableChooser<Command> m_chooser = new SendableChooser<>(); //Auto Chooser
  private final AimBot m_aimbot = new AimBot(m_drivetrain); //Aimbot Command

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    
    CommandScheduler.getInstance().setDefaultCommand(m_drivetrain,
      new TeleOp(
      m_drivetrain, //Drivetrain Subsystem
      () -> m_nes.getY(),
      () -> m_nes.getX()
    ));

    final JoystickButton a = new JoystickButton(m_nes, 1);
    final JoystickButton b = new JoystickButton(m_nes, 2);
    final JoystickButton select = new JoystickButton(m_nes, 3);
    final JoystickButton start = new JoystickButton(m_nes, 4);

    a.whenHeld(new OuttakePowercells(m_outtake));

    m_chooser.setDefaultOption("Aim", m_aimbot); //Aim to target then end

    SmartDashboard.putData("auto-chooser", m_chooser); //Send the Auto Chooser
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected(); //Get the selected auto from the chooser
  }
}
