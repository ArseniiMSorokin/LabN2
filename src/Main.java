import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Создание объекта sc класса Scanner для ввода данных
        Scanner sc = new Scanner(System.in);
        // Cчитывается размер матрицы и записывается в переменную N
        int N = sc.nextInt();
        // Создается двумерный массив, который отображает матрицу N×N
        byte[][] matrix = new byte[N][N];
        // Вложенный цикл, который заполняет значения в матрицу построчно
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = sc.nextByte();
            }
        }
        // Переменная для проверки на выполнение условия ориентированности графа
        boolean grUndirected = true;
        // Вложенный цикл для сравнения элемента матрицы с транспонированным элементом,
        // Если хотя бы один элемент не совпадёт, граф считается ориентированным и
        // переменная boolean меняет значение на false, иначе неориентированный и boolean остается true
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    grUndirected = false;
                    break;
                }
            }
            // Прерывание цикла, так как одного несовпадения достаточно, чтобы граф считался ориентированным
            if (!grUndirected) {
                break;
            }
        }
        // Проверка на не ориентированность графа
        if (grUndirected) {
            // Вводится переменная, которая обозначает количество четных вершин в графе
            int oddDegCo = 0;
            // Вложенный цикл для проверки каждого элемента матрицы
            for (int i = 0; i < N; i++) {
                // Переменная для подсчёта степени вершин в графе
                // Складываются все значения в строке матрицы, если оно нечётно, увеличиваем переменную oddDegCo на 1
                int deg = 0;
                for (int j = 0; j < N; j++) {
                    deg += matrix[i][j];
                }
                if (deg % 2 != 0) {
                    oddDegCo++;
                }
            }
            // Переменная типа boolean, которая проверяет что количество нечётных вершин
            // равно 2 или их вообще нет
            boolean eulUnDir = (oddDegCo == 2 || oddDegCo == 0);
            System.out.println("(1) Неориентированный");
            // Вывод переменной типа boolean true/false
            System.out.println("(2) Эйлеровость : " + eulUnDir);
            System.out.println("(3) Степени вершин: ");
            // Вложенный цикл работающий как на строке 39, только выводит степень вершины на экран
            for (int i = 0; i < N; i++) {
                int deg = 0;
                for (int j = 0; j < N; j++) {
                    deg += matrix[i][j];
                }
                System.out.println("    Вершина " + (i + 1) + ": " + deg + " - Степень");
            }
            System.out.println("(4) Список рёбер графа:");
            // Вложенный цикл, второй начинается от значения первого +1, чтобы не было повторений
            // рёбер графа, если значение элемента матрицы равно 1, то выводится ребро i - j
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (matrix[i][j] == 1) {
                        System.out.println("    Ребро: " + (i + 1) + " - " + (j + 1));
                    }
                }
            }
        } else {
            // Переменная типа boolean, которая будет обозначать является ли граф Эйлеровым или нет
            boolean eulDir = true;
            // Два целочисленных одномерных массива, обозначающих количество ребёр (входящих/исходящих)
            int[] inDeg = new int[N];
            int[] outDeg = new int[N];
            // Цикл, где в inDeg кладётся сумма значений столбца матрицы, а в outDeg сумма значений строки
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    inDeg[j] += matrix[i][j];
                    outDeg[i] += matrix[i][j];
                }
            }
            // Цикл, в котором сравнивается два массива по элементам с одинаковыми индексами,
            // если хотя бы один элемент не совпадёт -> переменная типа boolean принимает значение false
            // и выходит из цикла
            for (int i = 0; i < N; i++) {
                if (inDeg[i] != outDeg[i]) {
                    eulDir = false;
                    break;
                }
            }
            System.out.println("(1) Ориентированный");
            System.out.println("(2) Эйлеровость : " + eulDir);
            System.out.println("(3) Cтепени вершин");
            // Так как уже есть список с входящими/исходящими рёбрами то просто выводятся степени вершины
            for (int k = 0; k < N; k++) {
                System.out.println("    Вершина: " + (k + 1) + ". Исходящая степень - " + outDeg[k] + ". Входящая степень - " + inDeg[k]);
            }
            System.out.println("(4) Список рёбер графа:");
            // Циклы, проходящие по строкам матрицы и если элемент равен 1, то строится ребро i->j
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (matrix[i][j] == 1) {
                        System.out.println("    Ребро: " + (i + 1) + " -> " + (j + 1));
                    }
                }
            }
        }
        // Создание двумерного массива отображающего матрицу достижимости
        byte[][] reachMatrix = new byte[N][N];
        // Заполнение этого массива элементами из матрицы смежности
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                reachMatrix[i][j] = matrix[i][j];
            }
        }
        // Алгоритм Флойда-Уоршелла
        // 
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
        // Вывод двумерного массива отображающего матрицу достижимости построчно
        for (int i = 0; i < N; i++) {
            System.out.print("    ");
            for (int j = 0; j < N; j++) {
                System.out.print(reachMatrix[i][j] + "    ");
            }
            System.out.println();
        }
    }
}