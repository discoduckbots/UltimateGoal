package discoduckbots.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleMover {

    private DcMotor wobbleMoverMotor;
    private Servo wobbleGrabber;

    public WobbleMover(DcMotor wobbleMoverMotor, Servo wobbleGrabber) {
        this.wobbleMoverMotor = wobbleMoverMotor;
        this.wobbleGrabber = wobbleGrabber;
    }

    public void drop(LinearOpMode opmode) {
        wobbleMoverMotor.setPower(.2);
        opmode.sleep(1000);
        wobbleMoverMotor.setPower(0);
        release();
        wobbleMoverMotor.setPower(-.2);
        opmode.sleep(1000);
        wobbleMoverMotor.setPower(0);
    }
    public void lower(double speed) {
        wobbleMoverMotor.setPower(speed);
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
        wobbleGrabber.setPosition(135);
    }
}
