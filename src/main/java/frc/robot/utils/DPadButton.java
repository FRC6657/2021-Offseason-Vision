package frc.robot.utils;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class DPadButton extends Button {
    
    XboxController m_controller;
    Direction direction;

    public DPadButton(XboxController m_controller, Direction direction){
        this.m_controller = m_controller;
        this.direction = direction;
    }

    public enum Direction {
        UP(0), RIGHT(90), DOWN(180), LEFT(270);
        int direction;
        private Direction(int direction){
            this.direction = direction;
        }
    }

    public boolean get() {
        int dPadValue = m_controller.getPOV();
        return (dPadValue == direction.direction) || (dPadValue == (direction.direction + 45) % 360)
        || (dPadValue == (direction.direction + 315) % 360);
    }

}
