package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.MecanumDrivetrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Test OpMode", group="Linear Opmode")
public class TestTeleop extends LinearOpMode {
    private static final double THROTTLE = 1.0;

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mMecanumDrivetrain = null;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        mMecanumDrivetrain = new MecanumDrivetrain(telemetry, frontLeft, frontRight, backLeft, backRight);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        double testSpeed = 0.4;

        while (opModeIsActive()) {
            if (gamepad1.y) {
                mMecanumDrivetrain.driveByDistance(20, MecanumDrivetrain.DIRECTION_FORWARD, testSpeed, telemetry);
            }
            else if (gamepad1.x) {
                mMecanumDrivetrain.driveByDistance(20, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, testSpeed, telemetry);
            }
            else if (gamepad1.b) {
                mMecanumDrivetrain.driveByDistance(20, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, testSpeed, telemetry);
            }
            else if (gamepad1.a) {
                mMecanumDrivetrain.driveByDistance(20, MecanumDrivetrain.DIRECTION_REVERSE, testSpeed, telemetry);
            }

            idle();
        }

        telemetry.addData("TestTeleop" , "Stopping");
    }
}
