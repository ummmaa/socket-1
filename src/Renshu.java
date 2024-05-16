public class Renshu {
    public int doubleValue(int x) {
        return x * 2;
    }

    public int sumUpToN(int n) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public int sumFromPtoQ(int p, int q) {
        int result = 0;
        if (p < q) {
            for (int i = p; i <= q; i++) {
                result += i;
            }
            return result;
        } else {
            return -1;
        }
    }

    public int sumFromArrayIndex(int[] a, int index) {
        if (index >= 0 && index < a.length) {
            int result = 0;
            for (int i = index; i < a.length; i++) {
                result += a[i];
            }
            return result;
        } else {
            return -1;
        }
    }

    public int selectMaxValue(int[] a) {
        int index = a[0];
        for (int i = 0; i < a.length; i++) {
            if (index < a[i]) {
                index = a[i];
            }
        }
        return index;
    }

    public int selectMinValue(int[] a) {
        int index = a[0];
        for (int i = 0; i < a.length; i++) {
            if (index > a[i]) {
                index = a[i];
            }
        }
        return index;
    }

    public int selectMaxIndex(int[] a) {
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[index] < a[i]) {
                index = i;
            }
        }
        return index;
    }

    public int selectMinIndex(int[] a) {
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[index] > a[i]) {
                index = i;
            }
        }
        return index;
    }

    void swapArrayElements(int[] p, int i, int j) {
        int memory = p[j];
        p[j] = p[i];
        p[i] = memory;
    }

    boolean swapTwoArrays(int[] a, int[] b) {
        if (a.length == b.length) {
            for(int i = 0; i < a.length; i ++){
                int temp = a[i];
                a[i] = b[i];
                b[i] = temp;
            }
            return true;
        }else{
            return false;
        }
    }
}
