package org.example.lab3;

import org.example.lab3.utils.FileUtils.FileUtils;
import org.example.lab3.utils.FileUtils.Parser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println(measureExecutionTime(Main::SE));
        System.out.println(measureExecutionTime(Main::MitM));
    }

    private static long measureExecutionTime(final Runnable method) {
        final long startTime = System.currentTimeMillis();
        method.run();
        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static void MitM() {
        final String filePathSE = "src/main/java/org/example/lab3/data/MitM_vars/MitM_RSA_2048_20_regular/15.txt";

        final List<String> raw = FileUtils.readFileAsList(filePathSE);
        final BigInteger C = Parser.parseCFromMitM(raw);
        final BigInteger N = Parser.parseNFromMitM(raw);

        final BigInteger exp = new BigInteger("10001", 16);
        final long l = (int) Math.pow(2, 10); // l=20

        final long start = System.currentTimeMillis();

        final List<BigInteger> T = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            T.add(BigInteger.valueOf(i + 1));
        }

        final List<BigInteger> TE = new ArrayList<>();
        for (final BigInteger elem : T) {
            TE.add(elem.pow(exp.intValue()).mod(N));
        }

        final List<BigInteger> Cs = new ArrayList<>();
        for (final BigInteger te : TE) {
            Cs.add(C.multiply(te.modInverse(N)).mod(N));
        }

        final int[] attack = new int[]{0, 0};
        for (int i = 0; i < Cs.size(); i++) {
            for (int j = 0; j < TE.size(); j++) {
                if (Cs.get(i).equals(TE.get(j))) {
                    attack[0] = i + 1;
                    attack[1] = j + 1;

                    break;
                }
            }

//            if (attack[0] != 0) { break; }
        }

        final long end = System.currentTimeMillis();

        final BigInteger M1 = BigInteger.valueOf(attack[0]);
        final BigInteger M2 = BigInteger.valueOf(attack[1]);
        final BigInteger M = M1.multiply(M2);
        final BigInteger actualC = M.modPow(exp, N);

        // verification
        System.out.println("        M1: " + M1.toString(16));
        System.out.println("        M2: " + M2.toString(16));
        System.out.println("     M1*M2: " + M.toString(16));
        System.out.println("  actual C: " + actualC.toString(16));
        System.out.println("expected C: " + C.toString(16));
        System.out.println("expected == actual: " + C.equals(actualC));
        System.out.println("time spent: " + (end - start) + "ms");
    }

    public static void SE() {
        final String filePathSE = "src/main/java/org/example/lab3/data/SE_vars/SE_RSA_1024_5_hard/15.txt";
        final int exp = 5;

        final List<String> raw = FileUtils.readFileAsList(filePathSE);

        final List<BigInteger> listC = Parser.parseCFromSE(raw);
        final List<BigInteger> listN = Parser.parseNFromSE(raw);

        final BigInteger N = listN.stream()
                .reduce(BigInteger.ONE, BigInteger::multiply);

        final BigInteger C = eChineseRemainderTheorem(listC, listN);
        final BigInteger M = nthRoot(C, exp);
        final BigInteger actualC = M.modPow(BigInteger.valueOf(exp), N);

        System.out.println("         M: " + M.toString(16));
        System.out.println("expected C: " + C.toString(16));
        System.out.println("  actual C: " + actualC.toString(16));
        System.out.println("expected == actual: " + C.equals(actualC));
    }

    public static BigInteger nthRoot(final BigInteger C, final int exp) {
        BigInteger low = BigInteger.ONE;
        BigInteger high = C;
        BigInteger result = BigInteger.ZERO;

        while (low.compareTo(high) <= 0) {
            final BigInteger mid = low.add(high).shiftRight(1);
            final BigInteger midPowerExp = mid.pow(exp);

            int comparison = midPowerExp.compareTo(C);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                // mid^exp < C, move low bound up
                result = mid;
                low = mid.add(BigInteger.ONE);
            } else {
                // mid^exp > C, move high bound down
                high = mid.subtract(BigInteger.ONE);
            }
        }

        return result;
    }

    public static BigInteger eChineseRemainderTheorem(final List<BigInteger> listC, final List<BigInteger> listN) {
        final BigInteger modulo = listN.stream()
                .reduce(BigInteger.ONE, BigInteger::multiply);

        BigInteger solution = BigInteger.ZERO;

        for (int i = 0; i < listC.size(); i++) {
            final BigInteger a_i = listN.get(i);
            final BigInteger n_i = listC.get(i);

            final BigInteger M_i = modulo.divide(a_i);
            final BigInteger inv_i = M_i.modInverse(a_i);

            solution = solution.add(n_i.multiply(M_i).multiply(inv_i)).mod(modulo);
        }

        return solution;
    }

}
