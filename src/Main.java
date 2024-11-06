import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        boolean grUndirected = true;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    grUndirected = false;
                    break;
                }
            }
            if (!grUndirected) {
                break;
            }
        }
        if (grUndirected) {
            int oddDegCo = 0;
            for (int i = 0; i < N; i++) {
                int deg = 0;
                for (int j = 0; j < N; j++) {
                    deg += matrix[i][j];
                }
                if (deg % 2 != 0) {
                    oddDegCo++;
                }
            }
            boolean eulUnDir = (oddDegCo == 2 || oddDegCo == 0);
            System.out.println("(1) Неориентированный");
            System.out.println("(2) Эйлеровость : " + eulUnDir);
            System.out.println("(3) Степени вершин: ");
            for (int i = 0; i < N; i++) {
                int deg = 0;
                for (int j = 0; j < N; j++) {
                    deg += matrix[i][j];
                }
                System.out.println("    Вершина " + (i + 1) + ": " + deg + " - Степень");
            }
            System.out.println("(4) Список рёбер графа:");
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (matrix[i][j] == 1) {
                        System.out.println("    Ребро: " + (i + 1) + " - " + (j + 1));
                    }
                }
            }
        } else {
            boolean eulDir = true;
            int[] inDeg = new int[N];
            int[] outDeg = new int[N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    inDeg[j] += matrix[i][j];
                    outDeg[i] += matrix[i][j];
                }
            }
            for (int i = 0; i < N; i++) {
                if (inDeg[i] != outDeg[i]) {
                    eulDir = false;
                    break;
                }
            }
            System.out.println("(1) Ориентированный");
            System.out.println("(2) Эйлеровость : " + eulDir);
            System.out.println("(3) Cтепени вершин");
            for (int k = 0; k < N; k++) {
                System.out.println("    Вершина: " + (k + 1) + ". Исходящая степень - " + outDeg[k] + ". Входящая степень - " + inDeg[k]);
            }
            System.out.println("(4) Список рёбер графа:");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (matrix[i][j] == 1) {
                        System.out.println("    Ребро: " + (i + 1) + " -> " + (j + 1));
                    }
                }
            }
        }
        int[][] reachMatrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                reachMatrix[i][j] = matrix[i][j];
            }
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (reachMatrix[i][k] == 1 && reachMatrix[k][j] == 1) {
                        reachMatrix[i][j] = 1;
                    }
                }
            }
        }
        System.out.println("(5) Матрица достижимости:");
        for (int i = 0; i < N; i++) {
            System.out.print("    ");
            for (int j = 0; j < N; j++) {
                System.out.print(reachMatrix[i][j] + "    ");
            }
            System.out.println();
        }
    }
}