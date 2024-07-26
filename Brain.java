//import java.util.Random;
//
//public class Brain {
//    private static final int HIDDEN_LAYER_SIZE = 12;
//    public static float[][] wagiHiddenLayer = new float[HIDDEN_LAYER_SIZE][24];
//    public static float[][] wagiResultLayer = new float[4][HIDDEN_LAYER_SIZE];
//    public static float[] inputData= {1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05f, 0.0f, 0.0f, 0.05f, 0.0f, 0.0f, 0.05f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
//    public static float[] neuronShift = new float[HIDDEN_LAYER_SIZE];
//
//    public static void main(String[] args) {
//        fillTheMatrix1();
//        fillNeuronShift1();
//        float[] hiddenLayer = createHiddenLayer(inputData);
////        showMatrix();
//        showHiddenLayer(hiddenLayer);
//    }
////    public char chooseDirection(float[] inputData){
////        fillTheMatrix();
////        float[] hiddenLayer = createHiddenLayer(inputData);
////        showHiddenLayer(hiddenLayer);
////        return 'R';
////    }
//    public static void showMatrix(){
//        for(int i = 0; i < HIDDEN_LAYER_SIZE; i++){
//            System.out.print("[");
//            for(int j = 0; j < 24; j++){
//                System.out.print(wagiHiddenLayer[i][j] + ", ");
//            }
//            System.out.println("]");
//
//        }
//    }
//    public static void fillTheMatrix1(){
//        float minTemp = -1f;
//        float maxTemp = 1f;
//        for(int i = 0; i < HIDDEN_LAYER_SIZE; i++){
//            for(int j = 0; j < 24; j++){
//                wagiHiddenLayer[i][j] = (float) (Math.random() * (maxTemp - minTemp) + minTemp);
//            }
//        }
//    }
//    public static void fillNeuronShift1(){
//        float minTemp = -1f;
//        float maxTemp = 1f;
//        for(int i = 0; i < HIDDEN_LAYER_SIZE; i++){
//            neuronShift[i] = (float) (Math.random() * (maxTemp - minTemp) + minTemp);
//        }
//    }
//    public static void showHiddenLayer(float[] hiddenLayer){
//        for(int i = 0; i < HIDDEN_LAYER_SIZE; i++){
//            System.out.println(hiddenLayer[i]);
//        }
//    }
//    public static float[] createHiddenLayer(float[] inputData){
//        float[] hiddenLayer = new float[HIDDEN_LAYER_SIZE];
//        for(int i = 0; i < HIDDEN_LAYER_SIZE; i++){
//
//            for(int j = 0; j < inputData.length; j++){
//                hiddenLayer[i] += inputData[j] * wagiHiddenLayer[i][j];
//            }
//            hiddenLayer[i]*=neuronShift[i];
//            hiddenLayer[i] = activationFunc(hiddenLayer[i]);
//        }
//
//        return hiddenLayer;
//    }
//    public static float[] getResult(float[] hiddenLayer){
//        float[] resultLayer = new float[HIDDEN_LAYER_SIZE];
//        for(int i = 0; i < HIDDEN_LAYER_SIZE; i++){
//
//            for(int j = 0; j < hiddenLayer.length; j++){
//                resultLayer[i] += hiddenLayer[j] * wagiResultLayer[i][j];
//            }
//            resultLayer[i]*=neuronShiftResult[i];
//        }
//
//        return resultLayer;
//    }
//    public static float activationFunc(float item){
//        return Math.max(0, item);
//    }
//
//}
