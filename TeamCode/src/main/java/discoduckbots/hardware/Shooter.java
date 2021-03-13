package discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {

    private final DcMotorEx shooterMotor;
    private final Servo pusherServo;

    private static final double MAX_ROTATIONS_PER_SECOND = 100;
    private static final double ENCODER_CYCLES_PER_ROTATION = 28;

    private static final double HIGH_GOAL_POWER = .9;
    private static final double POWER_SHOT_POWER = 0.75;
    private static final double AUTO_GOAL_POWER = .85;

    public Servo getPusherServo(){
        return pusherServo;
    }

    public Shooter(DcMotorEx shooterMotor, Servo pusherServo) {
        this.shooterMotor = shooterMotor;
        this.pusherServo = pusherServo;

        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shooterMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    private double getVelocity(double power) {
        return MAX_ROTATIONS_PER_SECOND * ENCODER_CYCLES_PER_ROTATION * power;
    }

    public void setPowerForHighGoal(){
        shooterMotor.setVelocity(getVelocity(HIGH_GOAL_POWER));
    }

    public void setPowerForAutonomous(){
        shooterMotor.setVelocity(getVelocity(AUTO_GOAL_POWER));
    }

    public void setPowerForPowerShot(){
        shooterMotor.setVelocity(getVelocity(POWER_SHOT_POWER));
    }

    public void pushRing(){
        pusherServo.setPosition(0.25);
    }

    public void resetPusher(){
        pusherServo.setPosition(0.0);
    }

    public void stop(){
        shooterMotor.setVelocity(0);
    }
}