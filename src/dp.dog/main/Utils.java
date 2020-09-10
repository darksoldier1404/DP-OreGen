package dp.dog.main;

import java.util.Map;
import java.util.Random;

public class Utils {
    public static <Object> Object getWeightedRandom(Map<Object, Double> weights) {
        Random random = new Random();
        Object result = null;
        double bestValue = Double.MAX_VALUE;

        for (Object element : weights.keySet()) {
            double value = -Math.log(random.nextDouble()) / weights.get(element);
            if (value < bestValue) {
                bestValue = value;
                result = element;
            }
        }
        return result;
    }
}
