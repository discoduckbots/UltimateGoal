package discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    private static final double INTAKE_MOTOR_SPEED = .5;
    private DcMotorEx intakeMotor;
    private Servo intakePusher;
    private static final double MAX_ROTATIONS_PER_SECOND = 100;
    private static final double ENCODER_CYCLES_PER_ROTATION = 28;


    private double getVelocity(double power) {
        return MAX_ROTATIONS_PER_SECOND * ENCODER_CYCLES_PER_ROTATION * power;
    }

    public Intake(DcMotorEx intakeMotor, Servo intakePusher) {
        this.intakeMotor = intakeMotor;
        this.intakePusher = intakePusher;
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void intake(double speed) {
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setVelocity(getVelocity(speed));
}
    public void intake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setVelocity(getVelocity(INTAKE_MOTOR_SPEED));
    }

    public void outtake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setVelocity(getVelocity(INTAKE_MOTOR_SPEED));
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