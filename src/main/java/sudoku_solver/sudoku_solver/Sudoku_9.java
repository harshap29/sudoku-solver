package sudoku_solver.sudoku_solver;

import java.io.FileReader;
import java.util.Scanner;

public class Sudoku_9 {
    static int N = readFile(1);
    static int[] ans = new int[N];

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	int[][] input = new int[N][N];
	int[][] originalInput = new int[N][N];
	int[][] inter = new int[N][N];
	int d = 1;
	for (int a = 0; a < N; a++) {
	    ans[a] = d;
	    d++;
	}
	int lineNumber = 2;

	System.out.println("File Reading...");
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++) {
		input[i][j] = readFile(lineNumber);
		originalInput[i][j] = readFile(lineNumber);
		inter[i][j] = readFile(lineNumber);
		lineNumber++;
	    }
	}
	sudokuSolver(input, 0, 0, originalInput, inter, false);
	if (!findIfSudokuHasZero(input)) {
	    printSudoku(input);
	} else {
	    System.out.println("Sudoku not been solved...");
	}
    }

    private static int readFile(int lineNumber) {
	// TODO Auto-generated method stub
	Scanner reader = null;
	int returnValue = 0;
	int counterLineNumber = 1;
	try {
	    reader = new Scanner(new FileReader("input_9.txt"));
	    while (lineNumber != counterLineNumber) {
		counterLineNumber++;
		reader.nextLine();
	    }
	    returnValue = Integer.parseInt(reader.nextLine());
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
	return returnValue;
    }

    private static void sudokuSolver(int[][] input, int m, int n, int[][] originalInput, int[][] inter,
	    boolean backtrack) {
	if (backtrack && originalInput[m][n] != 0) {
	    int newi = 0;
	    int newj = 0;

	    if (newj - 1 < 0) {
		if (newi > 0) {
		    newi = newi - 1;
		    newj = N - 1;
		} else {
		    return;
		}
	    } else {
		newj = newj - 1;
	    }
	    sudokuSolver(input, newi, newj, originalInput, inter, true);
	}
	int rv = findValue(input, m, n, originalInput, ((input[m][n] - 1) < 0 ? 0 : (input[m][n] - 1)));
	if (m == 8 && n == 8 && rv != 0) {
	    input[m][n] = rv;
	    return;
	} else {
	    if (rv == 0) {
		int newi = 0;
		int newj = 0;

		if (n - 1 < 0) {
		    if (m > 0) {
			newi = m - 1;
			newj = N - 1;
			if (originalInput[newi][newj] != 0) {
			    if (newj - 1 < 0) {
				if (newi > 0) {
				    newi = newi - 1;
				    newj = N - 1;
				} else {
				    return;
				}
			    } else {
				newj = newj - 1;
			    }
			}
		    } else {
			return;
		    }
		} else {
		    newj = n - 1;
		    newi = m;
		    if (originalInput[newi][newj] != 0) {
			if (newj - 1 < 0) {
			    if (newi > 0) {
				newi = newi - 1;
				newj = N - 1;
			    } else {
				return;
			    }
			} else {
			    newj = newj - 1;
			}
		    }
		}
		input[m][n] = rv;// reset value
		// printSudoku(input);
		// System.out.println("---------------------------------------");
		sudokuSolver(input, newi, newj, originalInput, inter, true);

	    } else {
		input[m][n] = rv;
		if (m < N && n < N) {
		    if (n == (N - 1)) {
			// printSudoku(input);
			// System.out.println("---------------------------------------");
			sudokuSolver(input, m + 1, 0, originalInput, inter, false);
		    } else {
			// printSudoku(input);
			// System.out.println("---------------------------------------");
			sudokuSolver(input, m, n + 1, originalInput, inter, false);
		    }

		}
	    }
	}
    }

    private static boolean findIfSudokuHasZero(int[][] input) {
	// TODO Auto-generated method stub
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++) {
		if (input[i][j] == 0) {
		    return true;
		}
	    }
	}
	return false;
    }

    private static void printSudoku(int[][] input) {
	// TODO Auto-generated method stub
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++) {
		System.out.print(input[i][j] + " ");
	    }
	    System.out.print("\n");
	}
    }

    private static int findValue(int[][] input, int i, int j, int[][] originalInput, int offset) {
	// TODO Auto-generated method stub
	if (originalInput[i][j] == 0) {
	    int rv = 0;
	    int rootOfSizeOfSudoku = ((int) Math.sqrt(N));
	    int maxk = 0;
	    int mink = 0;
	    int maxp = 0;
	    int minp = 0;

	    for (int m = offset; m < N; m++) {
		boolean row = false;
		boolean column = false;
		boolean grid = false;

		// compare with row and column
		for (int n = 0; n < N; n++) {
		    if (input[i][n] == ans[m]) {
			row = true;
			break;
		    }
		}
		for (int o = 0; o < N; o++) {
		    if (input[o][j] == ans[m]) {
			column = true;
			break;
		    }
		}

		// compare with grid
		mink = i - i % rootOfSizeOfSudoku;
		maxk = mink + rootOfSizeOfSudoku;

		minp = j - j % rootOfSizeOfSudoku;
		maxp = minp + rootOfSizeOfSudoku;
		for (int z = mink; z < maxk; z++) {
		    for (int x = minp; x < maxp; x++) {
			if (input[z][x] == ans[m] || (ans[m] == input[i][j])) {
			    grid = true;
			    break;
			}
		    }
		}

		if (!column && !row && !grid) {
		    rv = ans[m];
		    break;
		}
	    }
	    return rv;
	} else {
	    return input[i][j];
	}
    }

}
