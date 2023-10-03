package ru.nsu.kislitsyn.polynomial;


import java.util.Arrays;

/**
* This is a class of polynomials and some arithmetic operations with it.
*/
public class Polynomial {
    private final int[] coeffs;
    private final int power;

    /**
    * This is a constructor of object.
    *
    * @param coeffs is an array of coefficients of the new polynomial.
    */
    public Polynomial(int[] coeffs) {
        this.coeffs = coeffs;
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
        int[] answ = new int[Math.max(this.coeffs.length, p2.coeffs.length)];
        if (this.coeffs.length > p2.coeffs.length) {
            for (int i = 0; i <= p2.power; i++) {
                answ[i] = this.coeffs[i] + p2.coeffs[i];
            }
            System.arraycopy(this.coeffs, p2.coeffs.length,
                    answ, p2.coeffs.length, this.coeffs.length - p2.coeffs.length);
        } else {
            for (int i = 0; i <= this.power; i++) {
                answ[i] = this.coeffs[i] + p2.coeffs[i];
            }
            System.arraycopy(p2.coeffs, this.coeffs.length,
                    answ, this.coeffs.length, p2.coeffs.length - this.coeffs.length);

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
            System.arraycopy(this.coeffs, poly.coeffs.length,
                    answ, poly.coeffs.length, this.coeffs.length - poly.coeffs.length);
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
    public int evaluate(int x) {
        int answ = 0;
        int powerOfX = 1;
        for (int i = 0; i <= this.power; i++) {
            answ += this.coeffs[i] * powerOfX;
            powerOfX *= x;
        }
        return answ;
    }


    /**
    * This function compares two polynomials.
    *
    * @param obj is a polynomial which is compared to *this*.
    *
    * @return true if polynomials are equal, otherwise - false.
    */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Polynomial poly = (Polynomial) obj;

        if (poly.effectiveLen() != this.effectiveLen()) {
            return false;
        }

        for (int i = 0; i <= this.power; i++) {
            if (this.coeffs[i] != poly.coeffs[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function is used to measure length of the polynomial (without 0 in the beginning).
     *
     * @return len of the polynomial.
     */
    private int effectiveLen() {
        for (int i = this.coeffs.length; i >= 1; i--) {
            if (this.coeffs[i - 1] != 0) {
                return i;
            }
        }
        return 0;
    }

    /**
    * Overriding hashcode.
    *
    * @return hash of the polynomial.
    */
    @Override
    public int hashCode() {
        int res = 31;
        int len = this.effectiveLen();
        for (int i = 0; i <= len - 1; i++) {
            res = 17 * res + this.coeffs[i];
        }
        return res;
    }

    /**
    * This function calculates *degree* differential of polynomial.
    *
    * @param degree int number - the power of differential we should return.
    *
    * @return the polynomial which is *degree* power of *this*.
    */
    public Polynomial differentiate(int degree) {

        if (degree > this.power) {
            return new Polynomial(new int[]{0});
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
        StringBuilder res = new StringBuilder();

        for (int i = this.coeffs.length - 1; i >= 2; i--) {
            if (this.coeffs[i] != 0) {
                res.append(sign(this.coeffs[i])).append(" ").append(notOne(Math.abs(this.coeffs[i]))).append("x^").append(i).append(" ");
            }
        }

        if (this.coeffs.length > 1 && this.coeffs[1] != 0) {
            res.append(sign(this.coeffs[1])).append(" ").append(notOne(Math.abs(this.coeffs[1]))).append("x ");
        }

        if (this.coeffs.length > 0 && this.coeffs[0] != 0) {
            res.append(sign(this.coeffs[0])).append(" ").append(Math.abs(this.coeffs[0]));
        }

        if (this.coeffs.length > 0 && this.coeffs[this.coeffs.length - 1] < 0) {
            res.deleteCharAt(1);
        } else {
            res.delete(0, 2);
        }

        if (res.toString().isEmpty()) {
            res.append("0");
        }

        return res.toString();
    }

    /**
    * This function is needed for good string representation of polynomial.
    *
    * @param a value.
    *
    * @return (String)abs(a) or empty line, if abs(a) == 1.
    */
    private String notOne(int a) {
        if (Math.abs(a) == 1) {
            return "";
        }
        return a + "";
    }

    /**
    * The sign function return the sign of integer value.
    *
    * @param a value.
    *
    * @return the string containing the sign of value.
    */
    private String sign(int a) {
        if (a < 0) {
            return "-";
        }
        return "+";
    }

    /**
     * Simple getter of coeffs.
     *
     * @return coeffs of polynomial.
     */
    public int[] getCoeffs() {
        return this.coeffs;
    }

    /**
     * Simple getter of power of polynomial.
     *
     * @return this.power.
     */
    public int getPower() {
        return this.power;
    }
}