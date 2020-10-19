package x.xx.leetcode.all;

public class P50PowxN {
    public double myPow(double x, int n) {
        return n < 0 ? 1.0 / quickMul(x, -(long) n) : quickMul(x, n);
    }

    private double quickMul(double x, long i) {
        if (i == 0) return 1.0;
        double y = quickMul(x, i / 2);
        y *= y;
        return i % 2 == 0 ? y : y * x;
    }

    private double quickMul2(double x, long i) {
        double ans = 1.0;
        double n = x;
        while (i > 0) {
            if (i % 2 == 1) {
                ans *= n;
            }
            n *= n;
            i /= 2;
        }
        System.out.println(ans);
        return ans;
    }

}
