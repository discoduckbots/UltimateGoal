package discoduckbots.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleMover {

    private DcMotor wobbleMoverMotor;
    private Servo wobbleGrabber;

    public WobbleMover(DcMotor wobbleMoverMotor, Servo wobbleGrabber) {
        this.wobbleMoverMotor = wobbleMoverMotor;
        this.wobbleGrabber = wobbleGrabber;

    }
    public void drop(LinearOpMode opmode) {
        wobbleMoverMotor.setPower(-1.0);
        opmode.sleep(2000);
        wobbleMoverMotor.setPower(0);
        release();
    }
    public void dropByEncoder(int revolutions){
        wobbleMoverMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        wobbleMoverMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wobbleMoverMotor.setTargetPosition(wobbleMoverMotor.getCurrentPosition() + revolutions);

        while (wobbleMoverMotor.getTargetPosition() > wobbleMoverMotor.getCurrentPosition()){
            wobbleMoverMotor.setPower(1.0);
        }
        wobbleMoverMotor.setPower(0.0);
        release();
    }

    public void lowerByEncoder(int revolutions){
        wobbleMoverMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        wobbleMoverMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wobbleMoverMotor.setTargetPosition(wobbleMoverMotor.getCurrentPosition() + revolutions);

        while (wobbleMoverMotor.getTargetPosition() > wobbleMoverMotor.getCurrentPosition()){
            wobbleMoverMotor.setPower(1.0);
        }
        wobbleMoverMotor.setPower(0.0);
    }

    public void grabAndLiftByEncoder(int revolutions, LinearOpMode opMode){
        grab();
        opMode.sleep(500);
        wobbleMoverMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        wobbleMoverMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wobbleMoverMotor.setTargetPosition(wobbleMoverMotor.getCurrentPosition() + revolutions);

        while (wobbleMoverMotor.getTargetPosition() > wobbleMoverMotor.getCurrentPosition()){
            wobbleMoverMotor.setPower(1.0);
        }

        wobbleMoverMotor.setPower(0.0);

    }

    public void liftByEncoder(int revolutions){
        wobbleMoverMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        wobbleMoverMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wobbleMoverMotor.setTargetPosition(wobbleMoverMotor.getCurrentPosition() + revolutions);

        while (wobbleMoverMotor.getTargetPosition() > wobbleMoverMotor.getCurrentPosition()){
            wobbleMoverMotor.setPower(1.0);
        }

        wobbleMoverMotor.setPower(0.0);

    }

    public void liftInch(LinearOpMode opmode) {
        wobbleMoverMotor.setPower(1.0);
        opmode.sleep(325);
        wobbleMoverMotor.setPower(0);
    }
    public void dropLift(LinearOpMode opmode) {
        wobbleMoverMotor.setPower(-1.0);
        opmode.sleep(2000);
        wobbleMoverMotor.setPower(0);
        release();
        wobbleMoverMotor.setPower(1.0);
        opmode.sleep(1000);
        wobbleMoverMotor.setPower(0);
    }

    public void lower(double speed) {
        wobbleMoverMotor.setPower(-1 * speed);
    }

    public void lift(double speed) {
        wobbleMoverMotor.setPower(speed);
    }

    public void stop() {
        wobbleMoverMotor.setPower(0);
    }

    public void grab() {
        wobbleGrabber.setPosition(0);
    }

    public void release() {
        wobbleGrabber.setPosition(0.5);
    }
}
