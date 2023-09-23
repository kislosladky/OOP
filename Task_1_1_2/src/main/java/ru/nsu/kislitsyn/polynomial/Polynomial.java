package ru.nsu.kislitsyn.polynomial;


import java.util.Arrays;

/**
 * This is a class of polynomials and some arithmetic operations with it.
 */
public class Polynomial {
    int[] coeffs;
    int power;

    /**
     * This is a constructor of object.
     *
     * @param coeffs is an array of coefficients of the new polynomial.
     */
    public Polynomial(int[] coeffs) {
        this.coeffs = coeffs.clone();
        this.power = coeffs.length - 1;
    }

    /**
     * This function computes the sum of two polynomials.
     *
     * @param p2 is a polynomial we should add to *this*.
     *
     * @return the polynomial which appears to be the sum of two inputs.
     */
    public Polynomial add(Polynomial p2) {
        int[] answ;
        if (this.coeffs.length > p2.coeffs.length) {
            answ = this.coeffs.clone();
            for (int i = 0; i <= p2.power; i++) {
                answ[i] += p2.coeffs[i];
            }
        } else {
            answ = p2.coeffs.clone();
            for (int i = 0; i <= this.power; i++) {
                answ[i] += this.coeffs[i];
            }
        }
        return new Polynomial(answ);
    }

    /**
     * This function computes the difference of two polynomials.
     *
     * @param poly is a polynomial we need to subtract from *this*.
     *
     * @return the polynomial which appears to be the difference of two inputs.
     */
    public Polynomial subtract(Polynomial poly) {
        int[] answ = new int[Math.max(this.coeffs.length, poly.coeffs.length)];
        for (int i = 0; i < Math.min(this.coeffs.length, poly.coeffs.length); i++) {
            answ[i] = this.coeffs[i] - poly.coeffs[i];
        }

        if (this.coeffs.length > poly.coeffs.length) {
            System.arraycopy(this.coeffs, poly.coeffs.length, answ, poly.coeffs.length, this.coeffs.length - poly.coeffs.length);
        } else {
            for (int i = this.coeffs.length; i < poly.coeffs.length; i++) {
                answ[i] = -poly.coeffs[i];
            }
        }

        return new Polynomial(answ);
    }

    /**
     * This function calculates the product of two polynomials.
     *
     * @param poly is the second polynomial we need to multiply.
     *
     * @return the polynomial which appears to be the product of input ones.
     */
    public Polynomial multiply(Polynomial poly) {
        int[] answ = new int[this.power + poly.power + 1];
        Arrays.fill(answ, 0);
        for (int i = 0; i <= this.power; i++) {
            for (int j = 0; j <= poly.power; j++) {
                answ[i + j] += this.coeffs[i] * poly.coeffs[j];
            }
        }

        return new Polynomial(answ);
    }

    /**
     * This method calculates the value of polynomial at some *x* point.
     *
     * @param x is a point where we need to evaluate polynomial.
     *
     * @return the value of polynomial at this point.
     */
    public double evaluate(double x) {
        double answ = 0;
        for (int i = 0; i <= this.power; i++) {
            answ += this.coeffs[i] * Math.pow(x, i);
        }
        return answ;
    }


    /**
     * This function compares two polynomials.
     *
     * @param poly is a polynomial which is compared to *this*.
     *
     * @return true if polynomials are equal, otherwise - false.
     */
    public boolean equals(Polynomial poly) {
//        if (this.power != poly.power) {
//            return false;
//        }
//        for (int i = 0; i < this.coeffs.length; i++) {
//            if (this.coeffs[i] != poly.coeffs[i]) {
//                return false;
//            }
//        }
//        return true;
        return Arrays.equals(this.coeffs, poly.coeffs);
    }


    /**
     * This function calculates *degree* differential of polynomial.
     *
     * @param degree is an int number - the power of differential we should return.
     *
     *
     * @return the polynomial which is *degree* power of *this*.
     */
    public Polynomial differentiate(int degree) {

        if (degree > this.power) {
            return new Polynomial(new int[] {0});
        }
        int[] answ = this.coeffs.clone();
        for (int i = 0; i < degree; i++) {
            for (int j = 1; j < answ.length - i; j++) {
                answ[j - 1] = j * answ[j];
            }
        }
        int[] realAnsw = new int[answ.length - degree];
        System.arraycopy(answ, 0, realAnsw, 0, realAnsw.length);
        return new Polynomial(realAnsw);
    }

    /**
     * This method makes the string representation of polynomial.
     *
     * @return polynomial at string representation.
     */
    @Override
    public String toString() {
        String answ = "";
        int j = this.power;
        if (j > 1) {
            if (this.coeffs[j] != 0) {
                answ += notOne(this.coeffs[j]) + "x^" + j-- + " ";
            }
        }
        for (; j > 1; j--) {
            if (this.coeffs[j] != 0) {
                answ += sign(this.coeffs[j]) + " " + notOne(Math.abs(this.coeffs[j])) + "x^" + j + " ";
            }
        }
        if (this.power > 1) {
            if (this.coeffs[1] != 0) {
                answ += sign(this.coeffs[1]) + " " + notOne(Math.abs(this.coeffs[1])) + "x";
            }
            if (this.coeffs[0] != 0) {
                answ += " "  + sign(this.coeffs[0]) + " " + Math.abs(this.coeffs[0]);
            }
        } else if (this.power == 1) {
            if (this.coeffs[1] != 0) {
                answ += notOne(this.coeffs[1]) + "x";
            }
            if (this.coeffs[0] != 0) {
                answ += " " + sign(this.coeffs[0]) + " " + Math.abs(this.coeffs[0]);
            }
        } else if (this.power == 0 && this.coeffs[0] != 0) {
            answ += sign(this.coeffs[0]) + " " + Math.abs(this.coeffs[0]);
        }

        if (answ.isEmpty()) {
            return "0";
        } else {
            return answ;
        }

    }

    /**
     * This function is needed for good string representation of polynomial.
     *
     * @param a is a value.
     *
     * @return string's sign, which contains either a value converted to string or an empty string if abs(a) == 1.
     */
    String notOne(int a) {
        if (Math.abs(a) == 1) {
            return "";
        }
        return a + "";
    }

    /**
     * The sign function return the sign of integer value.
     *
     * @param a is value.
     *
     * @return the string containing the sign of value.
     */
    String sign(int a) {
        if (a < 0) {
            return "-";
        }
        return "+";
    }
}