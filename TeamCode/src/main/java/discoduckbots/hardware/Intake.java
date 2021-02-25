package discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    private static final double INTAKE_MOTOR_SPEED = 0.81;
    private DcMotor intakeMotor;
    private Servo intakePusher;

    public Intake(DcMotor intakeMotor, Servo intakePusher) {
        this.intakeMotor = intakeMotor;
        this.intakePusher = intakePusher;
    }

    public void intake(double speed) {
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setPower(speed);
}
    public void intake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setPower(INTAKE_MOTOR_SPEED);
    }

    public void outtake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setPower(INTAKE_MOTOR_SPEED);
    }

    public void pushRing(){
        intakePusher.setPosition(1.0);
    }

    public void resetPusher(){
        intakePusher.setPosition(0);
    }

    public void stop() {
        intakeMotor.setPower(0.0);
    }
}