package discoduckbots.opmode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class RingStackDetector {
    private static final String LABEL_4_RING = "Quad";
    private static final String LABEL_1_RING = "Single";
    public int getSlot(List<Recognition> recognitions) {

        if (recognitions== null || recognitions.size()== 0){
            return 0;
        } else if (recognitions.get(0).getLabel().equals(LABEL_1_RING)){
            return 1;
        }else if (recognitions.get(0).getLabel().equals(LABEL_4_RING)){
            return 4;
        }
        return -1;
    }
}
