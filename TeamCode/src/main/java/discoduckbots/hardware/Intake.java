package discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Intake {

    private static final double INTAKE_MOTOR_SPEED = 0.81;
    private DcMotor intakeMotor;

    public Intake(DcMotor intakeMotor) {
        this.intakeMotor = intakeMotor;
    }

    public void intake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setPower(INTAKE_MOTOR_SPEED);
    }

    public void outtake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setPower(INTAKE_MOTOR_SPEED);
    }

    public void stop() {
        intakeMotor.setPower(0.0);
    }
}