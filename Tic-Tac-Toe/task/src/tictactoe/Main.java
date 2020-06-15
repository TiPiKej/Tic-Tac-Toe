package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final TicTacToe area = new TicTacToe();
        final Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cells: ");
        char[] input = scanner.nextLine().trim().toCharArray();

        for (char ch : input) {
            area.setArea(ch);
        }

        System.out.println(area.toString());

        System.out.print("Enter the coordinates: ");
        char[] coords = scanner.nextLine().replaceAll("\\s", "").toCharArray();



        System.out.println(area.toString());
        scanner.close();
    }
}

class TicTacToe {
    char[][] area = new char[3][3];
    private char curPosition = 0;

    public void setArea(char ch, int x, int y) {
        area[x][y] = ch;
    }

    public void setArea(char ch) {
        while ( // check next free place
                area[this.curPosition / 3][this.curPosition % 3] == 'X' ||
                area[this.curPosition / 3][this.curPosition % 3] == 'O' ||
                area[this.curPosition / 3][this.curPosition % 3] == '_'
        ) this.curPosition++;

        area[this.curPosition / 3][this.curPosition % 3] = ch;
        this.curPosition++;
    }

    public boolean isOccuped(int x, int y) {
        boolean res = false;
        
        if (this.area[x][y] == 'X' || this.area[x][y] == 'O'){
            res = true;
        }

        return res;
    }

    public String checkWin() {
//      possible variations of wins
//      1       2       3       4       5       6       7       8
//      XXX     ___     ___     X__     _X_     __X     X__     __X
//      ___     XXX     ___     X__     _X_     __X     _X_     _X_
//      ___     ___     XXX     X__     _X_     __X     __X     X__

        int wins = 0; // 0 - no one won, 1 - one win, 2 - two wins (cannot be) ...
        char winChar = 0;
        int usedArea = 0; // how many area squares has been set
        int xSet = 0;
        int oSet = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if (area[i][j] == 'X' || area[i][j] == 'O'){
                    usedArea++;
                    if (area[i][j] == 'X') xSet++;
                    else oSet++;
                }
            }
            if (area[i][0] == area[i][1] && area[i][1] == area[i][2]) { // 1,2,3
                wins++;
                winChar = area[i][0];
            }
            if (area[0][i] == area[1][i] && area[1][i] == area[2][i]) { // 4,5,6
                wins++;
                winChar = area[0][i];
            }
        }

        if (area[0][0] == area[1][1] && area[1][1] == area[2][2] || area[0][2] == area[1][1] && area[1][1] == area[2][0]) { // 7,8
            wins++;
            winChar = area[1][1];
        }

        if (wins == 1) return winChar + " wins";
        if (wins > 1 || xSet > oSet + 1 || xSet + 1 < oSet) return "Impossible";
        if (usedArea == 9) return "Draw";
        return "Game not finished";
    }

    @Override
    public String toString() {
        String lineBorder = "---------\n";
        String line1 = "| " + this.area[0][0] + " " + this.area[0][1] + " " + this.area[0][2] + " |\n";
        String line2 = "| " + this.area[1][0] + " " + this.area[1][1] + " " + this.area[1][2] + " |\n";
        String line3 = "| " + this.area[2][0] + " " + this.area[2][1] + " " + this.area[2][2] + " |\n";

        return new StringBuilder().append(lineBorder).append(line1).append(line2).append(line3).append(lineBorder).toString();
    }

}
