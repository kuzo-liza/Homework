import java.util.List;

public class MaxSubArray {

         static List <Integer> search(List <Integer> array) {

            int maxSum = 0;
            int currentSum = 0;

            int leftIndex = 0;
            int rightIndex = 0;
            int currentIndex = 0;

            for (int i = 0; i < array.size(); i++) {
                int temp = array.get(i);
                currentSum += temp;

                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    leftIndex = currentIndex;
                    rightIndex = i;
                }

                if (currentSum < 0) {
                    currentSum = 0;
                    currentIndex = i++;
                }
            }
            return array.subList(leftIndex, rightIndex + 1);
        }
    }