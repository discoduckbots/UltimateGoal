package discoduckbots.opmode.poc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.sensors.TensorFlow;

import static discoduckbots.measurements.Measurements.DISTANCE_TO_LAUNCH_LINE;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="TestAutonomous", group="Linear Opmode")
@Disabled
public class TestAutonomous extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mMecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        mMecanumDrivetrain = new MecanumDrivetrain(telemetry, this, frontLeft, frontRight, backLeft, backRight);
        DcMotor wobbleMoverMotor = hardwareMap.get(DcMotor.class, "wobbleMover");
        Servo wobbleGrabber = hardwareMap.get(Servo.class, "wobbleGrabber");
        wobbleMover = new WobbleMover(wobbleMoverMotor, wobbleGrabber);
        // wait for start
        waitForStart();
        runtime.reset();

        autonomousByEncoder();
    }


    private void autonomousByEncoder() {
        double autonomousSpeed = 0.5;

//        mMecanumDrivetrain.forwardByTime(this, .1, 5);
        mMecanumDrivetrain.strafeLeftByTime(this, .1, 5);


    }
}
