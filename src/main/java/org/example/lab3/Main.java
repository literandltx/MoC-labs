package org.example.lab3;

import org.example.lab3.utils.FileUtils.FileUtils;
import org.example.lab3.utils.FileUtils.ParserSE;

import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SE();
        MinM();
    }

    public static void MinM() {
        final String filePathSE = "src/main/java/org/example/lab3/data/MitM_vars/MitM_RSA_2048_20_regular/15.txt";

        final List<String> raw = FileUtils.readFileAsList(filePathSE);
        final List<BigInteger> listC = ParserSE.getC(raw);
        final List<BigInteger> listN = ParserSE.getN(raw);

        final BigInteger C = listC.getFirst();
        final BigInteger N = listN.getFirst();
    }

    public static void SE() {
        final String filePathSE = "src/main/java/org/example/lab3/data/SE_vars/SE_RSA_1024_5_hard/15.txt";

        final List<String> raw = FileUtils.readFileAsList(filePathSE);
        final List<BigInteger> listC = ParserSE.getC(raw);
        final List<BigInteger> listN = ParserSE.getN(raw);

        BigInteger C = eChineseRemainderTheorem(listC, listN);

        System.out.println(C);
    }

    public static BigInteger eChineseRemainderTheorem(final List<BigInteger> listC, final List<BigInteger> listN) {
        BigInteger modulo = BigInteger.ONE;
        BigInteger solution = BigInteger.ZERO;

        for (BigInteger n_i : listN) {
            modulo = modulo.multiply(n_i);
        }

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
