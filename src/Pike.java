import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.pow;

//  Ai = Ai-55 + Ai-24 mod mod
//  Bi = Bi-57 + Bi-7 mod mod
//  Ci = Ci-58 + Ci-19 mod mod
//  zi = Ai/mod zi+1 = Bi/mod zi+2 = Ci/mod
public class Pike {
    private ArrayList<Long> A = new ArrayList<>();
    private ArrayList<Long> B = new ArrayList<>();
    private ArrayList<Long> C = new ArrayList<>();
    private ArrayList<Float> Z = new ArrayList<>();
    private long mod = (long) pow(2, 32);

    public Pike() {
        startNumbers(A, 55);
        startNumbers(B, 57);
        startNumbers(C, 58);
    }

    public void makeSomeNumbers(int qt) {
        int third_qt = qt / 3 + 1;

        int i=0,j=0,k=0;

        while (i < third_qt - 55 || j < third_qt - 57 || k < third_qt - 58){
            Long Ai = (A.get(i) + A.get(31 + i));
            Long Bj = (B.get(j) + B.get(50 + j));
            Long Ck = (C.get(k) + C.get(39 + k));

            if ((Ai > mod && Bj > mod && Ck > mod) || (Ai < mod && Bj < mod && Ck < mod)){
                A.add(Ai % mod);
                B.add(Bj % mod);
                C.add(Ck % mod);
                i++; j++; k++;
            }

            if ((Ai > mod && Bj > mod) || (Ai < mod && Bj < mod)){
                A.add(Ai % mod);
                B.add(Bj % mod);
                i++; j++;
            }

            if ((Ai > mod && Ck > mod) || (Ai < mod && Ck < mod)){
                A.add(Ai % mod);
                C.add(Ck % mod);
                i++; k++;
            }

            if ((Bj > mod && Ck > mod) || (Bj < mod && Ck < mod)){
                B.add(Bj % mod);
                C.add(Ck % mod);
                j++; k++;
            }
        }

        for(i = 0; i < third_qt; i ++) {

            float Zi = (float)A.get(i) / (float)mod;
            float Zi1 = (float)B.get(i) / (float)mod;
            float Zi2 = (float)C.get(i) / (float)mod;
            Z.add(Zi);
            Z.add(Zi1);
            Z.add(Zi2);
        }
        float Mat = M(Z, qt);
        D(Z, qt, Mat);
        equability(Z);
        makeTable(Z);
    }

    public float M(ArrayList<Float> array, int T) {
        float sum = 0;
        for(int i = 0; i < T; i++) {
            sum += array.get(i);
        }
        System.out.println("M == " + sum / T);
        return sum / T;
    }

    public float D(ArrayList<Float> array, int T, float M) {
        float sum = 0;
        for (int i = 0; i < T; i++) {
            sum += pow(M, 2) - pow(array.get(i), 2);
        }
        System.out.println("D == " + -sum / T);
        return -sum / T;
    }

    public int[] equability(ArrayList<Float> array) {
        int[] res = new int[10];
        float point = (float)0.1;
        for (int i = 0; i < array.size(); i++) {
            while (array.get(i) > point) {
                point += 0.1;
            }
            point *= 10;
            res[(int)point - 1]++;
            point = (float)0.1;
        }
        System.out.println("Raspredelenie == " + Arrays.toString(res));
        return res;
    }

    public float R(ArrayList<Float> array, int s) {
        float R, sum = 0;
        for (int i = 0; i < array.size() - s; i++) {
            sum += array.get(i) * array.get(i + s);
        }
        float pre = (float)12 / (array.size() - s);
        R = (pre * sum) - 3;
        return R;
    }

    public String makeTable(ArrayList<Float> array){
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 8; i++) {
            sb.append("This s = " + i + " -> R = " + R(array, i) + "\n");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public void startNumbers(ArrayList<Long> array, int stopPoint) {
        do {
            Random rand = new Random();
            long number = rand.nextLong() % mod;
            if(number < 0) {
                number *= -1;
            }
            if(!array.contains(number)) {
                array.add(number);
            }
        } while (array.size() < stopPoint);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("A size = " + A.size() + "\n");
        for(int i = 0; i < A.size(); i++) {
            sb.append(A.get(i) + " ");
        }
        sb.append("\nB size = " + B.size() + "\n");
        for(int i = 0; i < B.size(); i++) {
            sb.append(B.get(i) + " ");
        }
        sb.append("\nC size = " + C.size() + "\n");
        for(int i = 0; i < C.size(); i++) {
            sb.append(C.get(i) + " ");
        }
        sb.append("\nZ size = " + Z.size() + "\n");
        for(int i = 0; i < Z.size(); i++) {
            sb.append(Z.get(i) + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Pike m = new Pike();

        m.makeSomeNumbers(10000);
        System.out.println(m);
    }
}
