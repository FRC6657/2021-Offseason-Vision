// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import frc.robot.commands.AimBot;
import frc.robot.commands.TeleOp;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  private final Drivetrain m_drivetrain = new Drivetrain();

  private final XboxController m_controller = new XboxController(0);

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    m_drivetrain.setDefaultCommand(new TeleOp(
      m_drivetrain,
      m_controller.getRawAxis(Axis.kRightTrigger.value) - m_controller.getRawAxis(Axis.kLeftTrigger.value),
      m_controller.getRawAxis(Axis.kRightX.value),
      m_controller.getRawButton(Button.kA.value)
    ));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
