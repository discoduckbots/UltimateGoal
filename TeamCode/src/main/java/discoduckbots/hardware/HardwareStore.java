package discoduckbots.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HardwareStore {
    private MecanumDrivetrain mecanumDrivetrain;
    private Intake intake;
    private Shooter shooter;
    private WobbleMover wobbleMover;
    private RingBlocker ringBlocker = null;
    private IMU imu;
    private NormalizedColorSensor colorSensor = null;


    public HardwareStore(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode) {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
//        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        DcMotorEx intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        Servo intakePusher = hardwareMap.get(Servo.class, "intakePusher");
        intake = new Intake(intakeMotor, intakePusher);

        DcMotorEx shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        Servo pusherServo = hardwareMap.get(Servo.class, "pusher");
        shooter = new Shooter(shooterMotor, pusherServo);

        DcMotor wobbleMoverMotor = hardwareMap.get(DcMotor.class, "wobbleMover");
        Servo wobbleGrabber = hardwareMap.get(Servo.class, "wobbleGrabber");
        wobbleMover = new WobbleMover(wobbleMoverMotor, wobbleGrabber);

        Servo ringBlockerServo = hardwareMap.get(Servo.class, "ringBlocker");
        ringBlocker = new RingBlocker(ringBlockerServo);

        BNO055IMU gyro = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new IMU(gyro);
        imu.initialize();

        mecanumDrivetrain = createDrivetrain(telemetry, opMode, imu, colorSensor, frontLeft, frontRight, backLeft, backRight);
    }

    protected MecanumDrivetrain createDrivetrain(Telemetry telemetry,
                                 LinearOpMode opMode,
                                 IMU imu,
                                 NormalizedColorSensor colorSensor,
                                 DcMotor frontLeft,
                                 DcMotor frontRight,
                                 DcMotor backLeft,
                                 DcMotor backRight){
        return new MecanumDrivetrain(telemetry, opMode, imu, colorSensor, frontLeft, frontRight, backLeft, backRight);
    }

    public MecanumDrivetrain getMecanumDrivetrain() {
        return mecanumDrivetrain;
    }

    public Intake getIntake() {
        return intake;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public WobbleMover getWobbleMover() {
        return wobbleMover;
    }
    public RingBlocker getRingBlocker() {
        return ringBlocker;
    }

    public IMU getImu(){
        return imu;
    }

    public NormalizedColorSensor getColorSensor(){
        return colorSensor;
    }
}
