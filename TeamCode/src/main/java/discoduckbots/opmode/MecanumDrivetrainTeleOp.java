/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import discoduckbots.hardware.Intake;
import discoduckbots.hardware.MecanumDrivetrain;
import discoduckbots.hardware.Shooter;
import discoduckbots.hardware.WobbleMover;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mecanum OpMode", group="Linear Opmode")
public class MecanumDrivetrainTeleOp extends LinearOpMode {

    private static final double THROTTLE = 0.45;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private Intake intake = null;
    private Shooter shooter = null;
    private WobbleMover wobbleMover = null;


    @Override
    public void runOpMode() {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        mecanumDrivetrain = new MecanumDrivetrain(telemetry, frontLeft, frontRight, backLeft, backRight);

        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intake = new Intake(intakeMotor);

        DcMotor shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        Servo pusherServo = hardwareMap.get(Servo.class, "pusher");
        shooter = new Shooter(shooterMotor, pusherServo);

//        DcMotor wobbleMoverMotor = hardwareMap.get(DcMotor.class, "wobbleMover");
//        Servo wobbleGrabber = hardwareMap.get(Servo.class, "wobbleGrabber");
//        wobbleMover = new WobbleMover(wobbleMoverMotor, wobbleGrabber);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            /* Gamepad 1 */
            mecanumDrivetrain.drive(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x, THROTTLE);

            if (gamepad1.a) {
                intake.intake();
            } else if (gamepad1.b) {
                intake.outtake();
            } else {
                intake.stop();
            }

            if (gamepad1.right_trigger > 0){
                shooter.shoot();
            }

            if (gamepad1.x){
                shooter.pushRing();
            }else if (gamepad1.y){
                shooter.resetPusher();
            }

            /* Gamepad 2 */
//            if (gamepad2.left_stick_y < 0){
//                wobbleMover.lower(gamepad2.left_stick_y);
//            }
//            else if (gamepad2.left_stick_y > 0){
//                wobbleMover.lift(gamepad2.left_stick_y);
//            }
//            else{
//                wobbleMover.stop();
//            }
//
//            if (gamepad2.a){
//                wobbleMover.grab();
//            }
//            if (gamepad2.b){
//                wobbleMover.release();
//            }
        }


        telemetry.addData("MecanumDrivetrainTeleOp", "Stopping");

        shutDown();
    }

    private void shutDown(){
        mecanumDrivetrain.stop();
        intake.stop();
        shooter.stop();
    }

}
