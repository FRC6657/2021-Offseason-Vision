// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AimBot;
import frc.robot.commands.TeleOp;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  private final Drivetrain m_drivetrain = new Drivetrain(); //Drivetrain Subsystem
  private final XboxController m_controller = new XboxController(0); //Controller
  private final SendableChooser<Command> m_chooser = new SendableChooser<>(); //Auto Chooser
  private final AimBot m_aimbot = new AimBot(m_drivetrain); //Aimbot Command

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    m_drivetrain.setDefaultCommand(new TeleOp(
      m_drivetrain, //Drivetrain Subsystem
      m_controller.getRawAxis(Axis.kRightTrigger.value) - m_controller.getRawAxis(Axis.kLeftTrigger.value), //Gas Brake Combo Driving
      m_controller.getRawAxis(Axis.kRightX.value), //Right Stick for rotation
      m_controller.getRawButton(Button.kA.value) //Aimbot Enable Button
    ));

    m_chooser.setDefaultOption("Aim", m_aimbot); //Aim to target then end

    SmartDashboard.putData("auto-chooser", m_chooser); //Send the Auto Chooser
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected(); //Get the selected auto from the chooser
  }
}
