import org.apache.commons.lang3.ArrayUtils;

public class Brain2 {
    private static final int HIDDEN_LAYER_SIZE = 12;// кількість прихованих нейронів
    private static final int INPUT_DATA_SIZE = 24;// кількість вхідних даних + зміщення B
    private static final int OUT_PUT_SIZE = 4;
    private float[][] wagiHiddenLayer;
    private float[][] wagiOutPutLayer;

    public Brain2(){
        wagiHiddenLayer = fillMatrix(HIDDEN_LAYER_SIZE, INPUT_DATA_SIZE + 1);
        wagiOutPutLayer = fillMatrix(OUT_PUT_SIZE, HIDDEN_LAYER_SIZE + 1);
    }
    public Brain2(float[] genotype){
        decodeGenotype(genotype);
    }
    public void decodeGenotype(float[] genotype){

        wagiHiddenLayer = new float[HIDDEN_LAYER_SIZE][INPUT_DATA_SIZE+1];
        float[] matrix1Arr = new float[300];
        for(int i = 0; i < matrix1Arr.length; i++){
            matrix1Arr[i] = genotype[i];
        }
        for(int i = 0; i < HIDDEN_LAYER_SIZE; i++){
            for(int j = 0; j < INPUT_DATA_SIZE+1; j++){
                int F = (i*(INPUT_DATA_SIZE+1))+j;
                wagiHiddenLayer[i][j] = matrix1Arr[(i*(INPUT_DATA_SIZE+1))+j];
            }
        }

        wagiOutPutLayer = new float[OUT_PUT_SIZE][HIDDEN_LAYER_SIZE+1];
        float[] matrix2Arr = new float[52];
        for(int j = 0; j < 52; j++) {
            matrix2Arr[j] = genotype[j + 300];
        }
        for(int i = 0; i < OUT_PUT_SIZE; i++){
            for(int j = 0; j < HIDDEN_LAYER_SIZE+1; j++){
                wagiOutPutLayer[i][j] = matrix2Arr[(i*(HIDDEN_LAYER_SIZE+1))+j];
            }
        }
//        showMatrix(HIDDEN_LAYER_SIZE, INPUT_DATA_SIZE+1, wagiHiddenLayer);
//        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
//        for(int j = wagiHiddenLayer.length; j < wagiOutPutLayer.length; j++){
//            genotype = ArrayUtils.addAll(genotype, wagiOutPutLayer[j]);
//        }
    }
    private float[] Think(float[] inputData) {
        inputData = ArrayUtils.addAll(inputData, 1f);
        float[] sums = addMatrix(HIDDEN_LAYER_SIZE, INPUT_DATA_SIZE+1, inputData, wagiHiddenLayer);
        float[] hiddenLayer = createHiddenLayer(sums);
        hiddenLayer = ArrayUtils.addAll(hiddenLayer, 1f);
        float[] outputs = addMatrix(OUT_PUT_SIZE, HIDDEN_LAYER_SIZE+1, hiddenLayer, wagiOutPutLayer);
        return outputs;
    }
    public char chooseDirection(float[] scanData, char curDirection){
        float[] results = Think(scanData);
        char newDirection = curDirection;
        int maxIndex = 0;

        for(int i = 0; i<results.length; i++){
            if (results[i] > results[maxIndex]){
                maxIndex = i;
            }
        }
        switch (maxIndex){
            case 0:
                if(curDirection != 'R') {
                    newDirection = 'L';
                }
                break;
            case 1:
                if(curDirection != 'D') {
                    newDirection = 'U';
                }
                break;
            case 2:
                if(curDirection != 'L') {
                    newDirection = 'R';
                }
                break;
            case 3:
                if(curDirection != 'U') {
                    newDirection = 'D';
                }
                break;
        }
        return newDirection;
    }
    private float[][] fillMatrix(int lines, int rows) {
        float[][] matrix = new float[lines][rows];
        float minTemp = -1f;
        float maxTemp = 1f;
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = (float) (Math.random() * (maxTemp - minTemp) + minTemp);
            }
        }
        return matrix;
    }

    private void showMatrix(int lines, int rows, float[][] matrix) {
        for (int i = 0; i < lines; i++) {
            System.out.print("[");
            for (int j = 0; j < rows; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
            System.out.println("]");
        }
    }

    private float[] addMatrix(int length, int rows, float[] matrix1, float[][] matrix2) {
        float[] layer = new float[length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < rows; j++) {
                layer[i] +=  matrix1[j] * matrix2[i][j];
            }
        }
        return layer;
    }
    private float[] createHiddenLayer(float[] array){
        float[] hiddenLayer = new float[array.length];
        for(int i = 0; i < array.length; i++){
            hiddenLayer[i] = activationFunc(array[i]);
        }
        return hiddenLayer;
    }
    private float activationFunc(float number){
        return Math.max(0, number);
    }

    public float[] getGenotype(){
        float[] genotype = {};
        for(int i = 0; i < wagiHiddenLayer.length; i++){
            genotype = ArrayUtils.addAll(genotype, wagiHiddenLayer[i]);
        }
        for(int j = 0; j < wagiOutPutLayer.length; j++){
            genotype = ArrayUtils.addAll(genotype, wagiOutPutLayer[j]);
        }
        return genotype;
    }
}
