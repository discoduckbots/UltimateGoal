package discoduckbots.opmode.poc;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.HardwareStore;
import discoduckbots.hardware.IMU;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;

import static discoduckbots.measurements.Measurements.DISTANCE_TO_LAUNCH_LINE;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Gyro Autonomous", group="Linear Opmode")
public class AutonomousWithGyro extends LinearOpMode {

    private NormalizedColorSensor colorSensor;
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private IMU imu = null;
    private BNO055IMU gyro = null;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();

        gyro = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new IMU(gyro);
        imu.initialize();

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        telemetry.addLine("")
                .addData("Current Gyro Heading: ", imu.getIMUHeading());
        telemetry.update();

        double autonomousSpeed = 0.4;

        // wait for start
        waitForStart();
        runtime.reset();
        telemetry.addLine()
                .addData("Strafing Left", "5 inches");
        telemetry.update();
        mecanumDrivetrain.driveByDistance(5, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed, imu, 0, telemetry);
        telemetry.addLine()
                .addData("Driving Reverse Until Red Tape", " ");
        telemetry.update();
        //int direction, double baseSpeed, IMU imu, double targetHeading, Telemetry telemetry, NormalizedColorSensor colorSensor
        mecanumDrivetrain.driveWithColorSensor(MecanumDrivetrain.DIRECTION_REVERSE, 0.2, imu, 0, telemetry, colorSensor);
//        mecanumDrivetrain.driveByDistance(14, MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed, imu, 0, telemetry);
        telemetry.addLine()
                .addData("Driving Complete", "Done");
        telemetry.update();
    }
}
