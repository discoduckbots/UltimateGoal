package discoduckbots.opmode.poc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import discoduckbots.hardware.IMU;
import discoduckbots.hardware.Intake;
import discoduckbots.hardware.Shooter;

@TeleOp(name = "Test Op", group = "Sensor")
@Disabled
public class TestOp extends LinearOpMode {
   private  DcMotorEx intakeMotor;
   private DcMotorEx shooterMotor;
   private Servo pusherServo;
   private Intake intake;
   private Shooter shooter;

    @Override
    public void runOpMode() throws InterruptedException {

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        pusherServo = hardwareMap.get(Servo.class, "pusher");
        intake = new Intake(intakeMotor,null);
        shooter = new Shooter(shooterMotor, pusherServo);



        waitForStart();

        while (opModeIsActive()){

            if(gamepad1.a) {
                intake.intake();
            }
            else if(gamepad1.b) {
                intake.outtake();
            }

            if(gamepad1.left_bumper) {
                shooter.setPowerForHighGoal();
            }

            if(gamepad1.x) {
                shooter.pushRing();
            }

            if(gamepad1.y) {
                shooter.resetPusher();
            }


        }
    }
}
