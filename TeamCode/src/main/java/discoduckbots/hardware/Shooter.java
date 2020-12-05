package discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {

    private static final double MOTOR_POWER = 1.0;

    private DcMotor shooterMotor;
    private Servo pusherServo;

    public Shooter(DcMotor shooterMotor, Servo pusherServo) {
        this.shooterMotor = shooterMotor;
        this.pusherServo = pusherServo;

        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        pusherServo.setDirection(Servo.Direction.FORWARD);
    }

    public void shoot() {
        startMotor();
        pushRing();
        resetPusher();
    }

    private void startMotor(){
        shooterMotor.setPower(MOTOR_POWER);
    }

    private void pushRing(){
        pusherServo.setPosition(45.0);
    }

    private void resetPusher(){
        pusherServo.setPosition(0.0);
    }
}
