import SOMMap.KohonenSelfOrganizingMap;

import java.awt.*;
import java.util.Random;

public class ClassificationTask {

    double[][] inputs;
    KohonenSelfOrganizingMap SOM;
    public ClassificationTask(int inputSize, int networkSize) {
        Random random = new Random(1);
        inputs = new double[inputSize][2];
        for (int i = 0; i < inputSize / 3; ++i) {
            inputs[i][0] = (5 + random.nextDouble()) / 10;
            inputs[i][1] = (5 + random.nextDouble()) / 10;
        }
        for (int i = 0; i < inputSize / 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                inputs[inputSize / 3 + i][j] = random.nextDouble() / 10;
            }
        }
        for (int i = 0; i < inputSize / 3; ++i) {
            inputs[2 * inputSize / 3 + i][1] = (5 + random.nextDouble()) / 10;
            inputs[2 * inputSize / 3 + i][0] = random.nextDouble() / 10;
        }
        SOM = new KohonenSelfOrganizingMap(networkSize, inputs[0]);
        SOM.setLearningRadius(1);
    }

    public boolean trainNetwork(int iterationCount, DrawNetwork draw) {
        int k = 0;
        for (int j = 0; j < iterationCount; ++j) {
            SOM.setInput(inputs[k % (inputs.length)]);
            SOM.learn();
            System.out.println(j);
            for (double[] input : inputs) {
                draw.addCircle((int) (input[0] * 100), (int) (input[1] * 100), 2, Color.BLUE);
            }
            ++k;
            for (int i = 0; i < SOM.getLayer().getNeurons().length; ++i) {
                if (SOM.getWinner() == i && k < inputs.length / 3) {
                    draw.addCircle((int) (SOM.getLayer().getNeurons()[i].getWeights()[0] * 100),
                            (int) (SOM.getLayer().getNeurons()[i].getWeights()[1] * 100), 2, Color.orange);
                } else if (SOM.getWinner() == i && k < 2 * inputs.length / 3) {
                    draw.addCircle((int) (SOM.getLayer().getNeurons()[i].getWeights()[0] * 100),
                            (int) (SOM.getLayer().getNeurons()[i].getWeights()[1] * 100), 2, Color.RED);
                } else if (SOM.getWinner() == i && k < inputs.length) {
                    draw.addCircle((int) (SOM.getLayer().getNeurons()[i].getWeights()[0] * 100),
                            (int) (SOM.getLayer().getNeurons()[i].getWeights()[1] * 100), 2, Color.GREEN);
                } else {
                    draw.addCircle((int) (SOM.getLayer().getNeurons()[i].getWeights()[0] * 100),
                            (int) (SOM.getLayer().getNeurons()[i].getWeights()[1] * 100), 2);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            draw.clearCircles();
            if (k == inputs.length) {
                k = 0;
            }
        }
        return true;
    }

}
