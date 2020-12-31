package discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {

    private static final double MOTOR_POWER = 0.65;
    private static final double PUSHER_POWER = 1.0;
    private static final double PUSHER_TIME = 0.1;

    private DcMotor shooterMotor;
    private Servo pusherServo;

    public Shooter(DcMotor shooterMotor, Servo pusherServo) {
        this.shooterMotor = shooterMotor;
        this.pusherServo = pusherServo;

        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void shoot() {
        startMotor();
    }

    private void startMotor(){
        shooterMotor.setPower(MOTOR_POWER);
    }

    // switch to fixed rotation 110 degree
    public void pushRing(){
        pusherServo.setPosition(0.55);
    }

    public void resetPusher(){
        pusherServo.setPosition(0.85);
    }

    public void stop(){
        shooterMotor.setPower(0.0);
    }
}
