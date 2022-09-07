import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
  // 1 + 2 + ... + 9 = (1 + 9) * 9 / 2 = 45
  // 45 / 3 = 15
  public final static Integer SUM = 15;
  public final static Integer n = 3;

  public static void main(String[] args) {
    List<List<String>> res = getAllValidSudokuNum();
    for (int i = 0; i < res.size(); i++) {
      List<String> sudoku = res.get(i);
      System.out.println(i + "th sudoku:");
      for (int j = 0; j < sudoku.size(); j++) {
        System.out.println(sudoku.get(j));
      }
      System.out.println();
    }
  }

  public static List<List<String>> getAllValidSudokuNum() {
    int res = 0;
    int[] rows = new int[n];  // row[0]
    int[] cols = new int[n];
    int[][] board = new int[n][n];
    List<List<String>> allValidSudoku = new ArrayList<>();
    Set<Integer> usedNum = new HashSet<>();

    backtrack(0, 0, board, rows, cols, usedNum, allValidSudoku);

    return allValidSudoku;
  }

  public static void backtrack(int row, int col, int[][] board,
                               int[] rows, int[] cols,
                               Set<Integer> usedNum,
                               List<List<String>> allValidSudoku) {
    // reach to one valid sudoku
    if (row == n) {
      List<String> sudoku = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < n; j++) {
          sb.append(board[i][j]);
        }
        sudoku.add(sb.toString());
      }
      allValidSudoku.add(sudoku);
      return;
    }

    // try to set board[row][col] as val
    for (int val = 1; val <= 9; val++) {
      // pruning
      if (rows[row] + val > SUM) continue;
      if (cols[col] + val > SUM) continue;
      if (usedNum.contains(val)) continue;

      rows[row] += val;
      cols[col] += val;
      board[row][col] = val;
      usedNum.add(val);

      if (col == n - 1) {
        backtrack(row + 1, 0, board, rows, cols, usedNum, allValidSudoku);
      } else {
        backtrack(row, col + 1, board, rows, cols, usedNum, allValidSudoku);
      }

      usedNum.remove(val);
      board[row][col] = 0;
      rows[row] -= val;
      cols[col] -= val;
    }
  }

  public static int toBox(int row, int col) {
    return row / 3 * 3 + col / 3;
  }
}