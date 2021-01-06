package discoduckbots.opmode.poc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.IMU;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;

import static discoduckbots.measurements.Measurements.DISTANCE_TO_LAUNCH_LINE;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="BlueStoneSide-TF", group="Linear Opmode")
public class AutonomousWithGyro extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private IMU imu = null;
    private BNO055IMU gyro = null;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        gyro = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new IMU(gyro);
        imu.initialize();

        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();

        double autonomousSpeed = 0.6;

        // wait for start
        waitForStart();
        runtime.reset();

        mecanumDrivetrain.driveByDistance(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed, imu, 0);
        mecanumDrivetrain.driveByDistance(28, MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed, imu, 0);
    }
}
