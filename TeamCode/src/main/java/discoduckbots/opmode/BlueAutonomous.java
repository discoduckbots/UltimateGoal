package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.WobbleMover;
import discoduckbots.sensors.TensorFlow;

import static discoduckbots.measurements.Measurements.DISTANCE_TO_LAUNCH_LINE;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="BlueStoneSide-TF", group="Linear Opmode")
public class BlueAutonomous extends LinearOpMode {


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
        mMecanumDrivetrain = new MecanumDrivetrain(telemetry, frontLeft, frontRight, backLeft, backRight);
        DcMotor wobbleMoverMotor = hardwareMap.get(DcMotor.class, "wobbleMover");
        Servo wobbleGrabber = hardwareMap.get(Servo.class, "wobbleGrabber");
        wobbleMover = new WobbleMover(wobbleMoverMotor, wobbleGrabber);
        // wait for start
        waitForStart();
        runtime.reset();

        autonomousByEncoder();
    }


    private void autonomousByEncoder() {
        double autonomousSpeed = 0.1;

        boolean tensorFlowDetection = true;
        if (tensorFlowDetection) {
            int distanceToMoveBack = 0;
            TensorFlow tensorFlow = new TensorFlow();
            RingStackDetector ringStackDetector = new RingStackDetector();
            tensorFlow.initTensorflow(telemetry, hardwareMap);
          //  int number = ringStackDetector.getSlot(tensorFlow.detect());
            int number = 0;
            if (number == 0){
                distanceToMoveBack = 10;
                mMecanumDrivetrain.driveByDistance(10
                        ,MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed);
                mMecanumDrivetrain.driveByDistance(25,  MecanumDrivetrain.DIRECTION_REVERSE,autonomousSpeed);
            }else if (number == 1){

                distanceToMoveBack = 30;
                mMecanumDrivetrain.driveByDistance(10,MecanumDrivetrain.DIRECTION_STRAFE_LEFT,autonomousSpeed);
                mMecanumDrivetrain.driveByDistance(10,MecanumDrivetrain.DIRECTION_REVERSE,autonomousSpeed);
                mMecanumDrivetrain.driveByDistance(10,MecanumDrivetrain.DIRECTION_STRAFE_RIGHT,autonomousSpeed);
            }else if (number == 4){
                distanceToMoveBack = 40;
                mMecanumDrivetrain.driveByDistance(10,MecanumDrivetrain.DIRECTION_STRAFE_LEFT,autonomousSpeed);
                mMecanumDrivetrain.driveByDistance(10,MecanumDrivetrain.DIRECTION_REVERSE,autonomousSpeed);
            }
            wobbleMover.drop(this);
            mMecanumDrivetrain.driveByDistance(distanceToMoveBack,MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed);

        } else {

            //sleep(25000);
            mMecanumDrivetrain.driveByDistance(DISTANCE_TO_LAUNCH_LINE, MecanumDrivetrain.DIRECTION_FORWARD, autonomousSpeed);
        }

        while (opModeIsActive() && mMecanumDrivetrain.isMoving()) {
            telemetry.addData("Step 6", "Strafe left 55\"");
            telemetry.update();
        }
        mMecanumDrivetrain.stop();
    }
}
