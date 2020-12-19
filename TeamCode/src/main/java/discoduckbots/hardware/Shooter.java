package discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {

    private static final double MOTOR_POWER = 0.65;
    private static final double PUSHER_POWER = 1.0;
    private static final double PUSHER_TIME = 5.0;

    private DcMotor shooterMotor;
    private CRServo pusherServo;

    public Shooter(DcMotor shooterMotor, CRServo pusherServo) {
        this.shooterMotor = shooterMotor;
        this.pusherServo = pusherServo;

        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        pusherServo.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void shoot() {
        startMotor();
        pushRing();
    }

    private void startMotor(){
        shooterMotor.setPower(MOTOR_POWER);
    }

    private void pushRing(){

        long millis = System.currentTimeMillis();
        long currentTime = millis;

        while (PUSHER_TIME > (currentTime - millis) / 1000){
            pusherServo.setPower(PUSHER_POWER);
            currentTime = System.currentTimeMillis();
        }

        pusherServo.setPower(0);
    }

    public void stop(){
        shooterMotor.setPower(0.0);
        pusherServo.setPower(0.0);
    }
}
