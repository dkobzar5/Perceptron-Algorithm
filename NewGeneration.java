import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NewGeneration {
    public static float[][] getChildrensGenotype(ArrayList list) {
        int[][] pairsOfParents = createParentsArray(list);
        float[][][] parentsGenotypes = createParentsGenotypeArray(pairsOfParents, list);
        float[][] childrensGenotype = createChildGenotype(parentsGenotypes);
        return childrensGenotype;
    }
    public static float[][] createChildGenotype(float[][][] parentsGenotypes){
        Random random = new Random();
        float[][] childGenotype = new float[parentsGenotypes.length][352];
        for(int i = 0; i < parentsGenotypes.length; i++){
            for(int j = 0; j < 352; j++){
                int mutation = random.nextInt(100);
                if(mutation == 0 || mutation == 1) {
                    float minTemp = -1f;
                    float maxTemp = 1f;
                    childGenotype[i][j] = (float) (Math.random() * (maxTemp - minTemp) + minTemp);
                }else{
                    childGenotype[i][j] = parentsGenotypes[i][random.nextInt(2)][j];
                }
            }
        }
        return childGenotype;
    }
    public static int[][] createParentsArray(ArrayList list) {
//        int[] snakesScores = {1600, 1225, 625, 2401, 361, 1024, 361, 144, 196, 441, 1369, 1089, 784, 196, 289, 25, 1369, 1444, 4, 729, 25, 225, 16, 1369, 324, 784, 1024, 144, 1089, 169, 1225, 4, 484, 100, 729, 2116, 961, 4489, 9, 676, 16, 64, 2601, 4096, 1600, 2025, 900, 1764, 400, 1600, 1600, 1156, 1521, 1600, 1296, 16, 169, 1, 1024, 484, 81, 1, 784, 1156, 625, 100, 2809, 289, 729, 3969, 4900, 484, 4, 2601, 625, 900, 841, 400, 1089, 64, 324, 144, 1089, 16, 1, 144, 3136, 64, 529, 2209, 324, 1, 441, 9, 4, 1024, 256, 576, 841, 784};
        int[] snakesScores = getSnakesScore(list);
        int totalOfScores = countScoresTotal(snakesScores);
        int[][] array = new int[snakesScores.length][2];
        for (int[] parents : array) {
            parents[0] = chooseParent(snakesScores, totalOfScores);
            parents[1] = chooseParent(snakesScores, totalOfScores);
        }

        return array;
    }

    public static float[][][] createParentsGenotypeArray(int[][] parents, ArrayList list) {
        float[][][] array = new float[parents.length][2][352];
        for (int i = 0; i < parents.length; i++) {
            array[i][0] = ((Snake) (((ArrayList) list.get(parents[i][0])).get(0))).brain.getGenotype();
            array[i][1] = ((Snake) (((ArrayList) list.get(parents[i][1])).get(0))).brain.getGenotype();
        }
        return array;
    }

    public static int chooseParent(int[] snakesScores, int totalOfScores) {
        int randomValue = new Random().nextInt(totalOfScores);

        int index = 0;
        int cumulativeProbability = 0;
        for (int i = 0; i < snakesScores.length; i++) {
            cumulativeProbability += snakesScores[i];
            if (cumulativeProbability >= randomValue) {
                index = i;
                break;
            }
        }
//        System.out.println(index + " score of the snake " + snakesScores[index]);
        return index;
    }

    public static int[] getSnakesScore(ArrayList list) {
        int[] snakesScores = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Snake curSnake = (Snake) (((ArrayList) list.get(i)).get(0));
            int points = countScore(curSnake);
            snakesScores[i] = points;
//            System.out.println(i + ": " + points + " points(" + curSnake.liveTime + ", " + curSnake.applesEaten + "); ");
        }
        return snakesScores;
    }

    public static int countScore(Snake snake) {
        int t = snake.liveTime;
        int a = snake.applesEaten;
        int result = (int) (t * t * Math.pow(2, Math.min(a, 10)) * Math.max(1, a - 9));
        return result;
    }

    public static int countScoresTotal(int[] snakesScores) {
        int totals = 0;
        for (int fitness : snakesScores) {
            totals += fitness;
        }
        return totals;
    }
}
