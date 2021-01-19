package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.IMU;
import discoduckbots.hardware.MecanumDrivetrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Test OpMode", group="Linear Opmode")
public class TestTeleop extends LinearOpMode {
    private MecanumDrivetrain mecanumDrivetrain = null;
    private IMU imu = null;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        imu = hardwareStore.getImu();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        double testSpeed = 0.3;
        double strafeSpeed = 0.4;

        while (opModeIsActive()) {
            telemetry.addLine().addData("current heading:" ,imu.getIMUHeading());
            telemetry.update();

            if (gamepad1.y) {
                mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_FORWARD, testSpeed, 0);
            }
            else if (gamepad1.x) {
                mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, strafeSpeed, 0);
            }
            else if (gamepad1.b) {
                mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, strafeSpeed, 0);
            }
            else if (gamepad1.a) {
                mecanumDrivetrain.driveByGyro(10, MecanumDrivetrain.DIRECTION_REVERSE, testSpeed, 0);
            }

            if (gamepad2.x) {
                mecanumDrivetrain.gyroTurn(-45, testSpeed, this);
            }
            else if (gamepad2.y){
                mecanumDrivetrain.gyroTurn(-90, testSpeed, this);
            }
            else if (gamepad2.a) {
                mecanumDrivetrain.gyroTurn(45, testSpeed, this);
            }
            else if (gamepad2.b){
                mecanumDrivetrain.gyroTurn(90, testSpeed, this);
            }

            idle();
        }

        telemetry.addData("TestTeleop" , "Stopping");
    }

    private void turn(){

    }
}
