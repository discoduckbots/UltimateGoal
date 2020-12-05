package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.MecanumDrivetrain;

import static discoduckbots.measurements.Measurements.DISTANCE_TO_LAUNCH_LINE;

public class BlueAutonomous extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mMecanumDrivetrain = null;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        mMecanumDrivetrain = new MecanumDrivetrain(telemetry, frontLeft, frontRight, backLeft, backRight);

        // wait for start
        waitForStart();
        runtime.reset();

        autonomousByEncoder();
    }


    private void autonomousByEncoder() {
        double autonomousSpeed = 0.10;


        sleep(25000);
        mMecanumDrivetrain.driveByDistance(DISTANCE_TO_LAUNCH_LINE, MecanumDrivetrain.DIRECTION_FORWARD, autonomousSpeed);
        while (opModeIsActive() && mMecanumDrivetrain.isMoving()) {
            telemetry.addData("Step 6", "Strafe left 55\"");
            telemetry.update();
        }
        mMecanumDrivetrain.stop();
    }
}
