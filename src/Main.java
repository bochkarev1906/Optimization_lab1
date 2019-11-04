public class Main {
    static double a = -Math.PI / 2;
    static double b = Math.PI / 2;
    static double e = 0.001;
    static int dichotomyIterations = 0;
    static int goldenRatioIterations = 0;
    static int fibonachiIterations = 0;
    static int countFuncCalls = 0;

    public static double function(double x){
        countFuncCalls++;
        return Math.sin(x);
    }

    public static double dichotomy(double a, double b, double e){
        double delta = 0.9 * e / 2;
        double x1 = (a + b) / 2 - delta;
        double x2 = (a + b) / 2 + delta;
        dichotomyIterations++;
        if(Math.abs(a - b) < e){
            return (a + b) / 2;
        }
        else{
            if(function(x1) > function(x2)){
                return dichotomy(x1, b, e);
            }
            else{
                return dichotomy(a, x2, e);
            }
        }
    }

    public static double goldenRatio(double a, double b, double e){
        double x1 = a + 0.381966011 * (b - a);
        double x2 = a + 0.618003399 * (b - a);
        goldenRatioIterations++;
        if(Math.abs(a - b) < e){
            return (a + b) / 2;
        }
        else {
            if(function(x1) > function(x2)){
                return goldenRatio(x1, b, e);
            }
            else {
                return goldenRatio(a, x2, e);
            }
        }
    }

    public static double findNFib(int n){
        return (Math.pow(((1 + Math.pow(5, 0.5)) / 2), n) - Math.pow(((1 - Math.pow(5, 0.5)) / 2), n)) / Math.pow(5, 0.5);
    }

    public static int findN(double a, double b, double e){
        int k = 0;
        while ((b - a) / e >= findNFib(k)){
            k++;
        }
        return k - 2;
    }

    public static double methodFibonachi(double a, double b, double e){
        fibonachiIterations = 0;
        double x1, x2, f1, f2;
        int n = findN(a, b, e);
        x1 = a + findNFib(n) / findNFib(n + 2) * (b - a);
        x2 = a + findNFib(n + 1) / findNFib(n + 2) * (b - a);
        f1 = function(x1);
        f2 = function(x2);
        fibonachiIterations++;
        return (methodFibonachiRec(a, b, e, x1, x2, f1, f2, n));
    }

    public static double methodFibonachiRec(double a, double b, double e, double x1, double x2, double f1, double f2, int n){
        fibonachiIterations++;
        if(fibonachiIterations == n){
            return (x2 + x1) / 2;
        }
        else{
            if (f1 >= f2){
                return methodFibonachiRec(x1, b, e, x2, x1 + findNFib(n - fibonachiIterations + 2) / findNFib(n - fibonachiIterations + 3) * (b - x1), f2, function(x1 + findNFib(n - fibonachiIterations + 2) / findNFib(n - fibonachiIterations + 3) * (b - x1)), n);
            }
            else{
                return methodFibonachiRec(a, x2, e, a + findNFib(n - fibonachiIterations + 1) / findNFib(n - fibonachiIterations + 3) * (x2 - a), x1, function(a + findNFib(n - fibonachiIterations + 1) / findNFib(n - fibonachiIterations + 3) * (x2 - a)), f1, n);
            }
        }
    }

    public static double searchOnLine(){
        double x0 = 0;
        double v = 0.001;
        double x = 0, h = 0, nextX = 0;
        if(function(x0) > function(x0 + v)){
            x = x0 + v;
            h = v;
        }
        else {
            if(function(x0) > function(x0 - v)){
                x = x0 - v;
                h = -v;
            }
        }
        h *= 2;
        nextX = x + h;
        while (function(x) > function(nextX)) {
            h *= 2;
            x = nextX;
            nextX += h;
        }
        return methodFibonachi(Math.min(x - h, nextX), Math.max(x - h, nextX), e);
    }

    public static int n;
    public static int step;
    public static double[] s = new double[n];
    public static double[] x = new double[n];

    public static double func (double [] arr){
        double c = 0;
        for (int i = 0 ; i < arr.length; i++){
            c += i * arr[i];
        }
        return c;
    }

    public static double searchInDirection (double [] s, int step, double [] x) {
        double [] arr = new double[n];
        for (int i = 0; i < n; i++){
            arr[i] = x[i] + s[i] * step;
        }
        return func(arr);
    }

    /// Part 2

    public static double function(double x1, double x2)
    {
        return (x2 - x1) * (x2 - x1) + 100 * (1 - x1) * (1 - x1);
    }

    public static double[] grad(double x1, double x2){
        double g[] = {202 * x1 - 2 * x2 - 200,  2 * (x2 - x1)};
        return g;
    }

    public static double abs(double[] grad){
        return Math.sqrt(grad[0] * grad[0] + grad[1] * grad[1]);
    }

    public static void find(){
        double x[] = {0, 0};
        double ee[] = {e, e};
        double s = 200 * grad(x[0], x[1])[0] * (x[0] - 1) - 2 * (grad(x[0], x[1])[0] - grad(x[0], x[1])[1]) * (x[1] - x[0]);
        double k = 2 * (grad(x[0], x[1])[0] - grad(x[0], x[1])[1]) * (grad(x[0], x[1])[0] - grad(x[0], x[1])[1]) + 200 * grad(x[0], x[1])[0] * grad(x[0], x[1])[0];
        double l = s / k;
        double step[] = {l, l};
        double xNext[] = {x[0] - step[0] * grad(x[0], x[1])[0], x[1] - step[1] * grad(x[0], x[1])[1]};
        while(xNext[0] - x[0] > ee[0] || xNext[1] - x[1] > ee[1]){
            x = new double[]{xNext[0], xNext[1]};
            s = 200 * grad(x[0], x[1])[0] * (x[0] - 1) - 2 * (grad(x[0], x[1])[0] - grad(x[0], x[1])[1]) * (x[1] - x[0]);
            k = 2 * (grad(x[0], x[1])[0] - grad(x[0], x[1])[1]) * (grad(x[0], x[1])[0] - grad(x[0], x[1])[1]) + 200 * grad(x[0], x[1])[0] * grad(x[0], x[1])[0];
            step = new double[]{s / k, s / k};
            xNext = new double[]{xNext[0] - step[0] * grad(x[0], x[1])[0], x[1] - step[1] * grad(x[0], x[1])[1]};
        }

       System.out.println(xNext[0] + "; " + xNext[1]);

    }

    public static void main(String args[]){
        System.out.println("Метод дихотомии: " + dichotomy(a, b, e) + "; количество итераций: " + dichotomyIterations + "; количество вызовов функции: " + countFuncCalls);
        dichotomyIterations = 0;
        countFuncCalls = 0;

        System.out.println("Метод золотого сечения: " + goldenRatio(a, b, e) + "; количество итераций: " + goldenRatioIterations + "; количество вызовов функции: " + countFuncCalls);
        countFuncCalls = 0;
        goldenRatioIterations = 0;

        System.out.println("Метод Фибоначчи: " + methodFibonachi(a, b, e) + "; количество итераций: " + fibonachiIterations + "; количество вызовов функции: " + countFuncCalls);
        countFuncCalls = 0;

        System.out.println("Поиск минимума функции на прямой: " + searchOnLine());

        System.out.print("Алгоритм наискорейшего спуска: ");
        find();
    }
}