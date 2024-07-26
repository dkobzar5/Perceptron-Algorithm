//import java.util.Random;
//
//public class Test {
//    public static int[] array = new int[10];
//    public static void main(String[] args) {
//        System.out.println(array);
//        foo(array);
//        showArray();
//    }
//    public static void foo(int [] localArray){
//        Random random = new Random();
//        for(int i = 0; i < array.length; i++){
//            localArray[i] = random.nextInt();
//        }
//    }
//    public static void showArray(){
//        for(int i = 0; i < array.length; i++){
//            System.out.println(array[i]);
//        }
//    }
//}
import java.util.ArrayList;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt(2));
    }
}