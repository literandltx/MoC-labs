package org.example.lab3;

import org.example.lab3.utils.FileUtils.FileUtils;
import org.example.lab3.utils.FileUtils.ParserSE;

import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SE(); // done
//        MinM();
    }

    public static void MinM() {
        final String filePathSE = "src/main/java/org/example/lab3/data/MitM_vars/MitM_RSA_2048_20_regular/15.txt";

        final List<String> raw = FileUtils.readFileAsList(filePathSE);
        final List<BigInteger> listC = ParserSE.getC(raw);
        final List<BigInteger> listN = ParserSE.getN(raw);

        final BigInteger C = listC.getFirst();
        final BigInteger N = listN.getFirst();
        int l = 20;
        int exp = 65537; // 0x10001
    }

    public static void SE() {
        final String filePathSE = "src/main/java/org/example/lab3/data/SE_vars/SE_RSA_1024_5_hard/15.txt";
        final int exp = 5;

        final List<String> raw = FileUtils.readFileAsList(filePathSE);

        final List<BigInteger> listC = ParserSE.getC(raw);
        final List<BigInteger> listN = ParserSE.getN(raw);

        final BigInteger N = listN.stream()
                .reduce(BigInteger.ONE, BigInteger::multiply);

        final BigInteger C = eChineseRemainderTheorem(listC, listN);
        final BigInteger M = nthRoot(C, exp);

        System.out.println("C: " + C.toString(16));
        System.out.println("M: " + M.toString(16));
        System.out.println("Verify: " + M.modPow(BigInteger.valueOf(exp), N).equals(C));
        System.out.println(M.modPow(BigInteger.valueOf(exp), N).toString(16));
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
