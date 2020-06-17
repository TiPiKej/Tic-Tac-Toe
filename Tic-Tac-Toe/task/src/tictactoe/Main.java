package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final TicTacToe area = new TicTacToe();
        final Scanner scanner = new Scanner(System.in);

        while (area.checkWin().equals("Game not finished") || area.checkWin().equals("Impossible") || area.checkWin().equals("Draw")) {
            area.makeMove(scanner);
            System.out.println(area.toString());
        }

        System.out.println(area.checkWin());

        scanner.close();
    }
}

class TicTacToe {
    char[][] area;
    private char curPosition = 0;
    boolean userX = true; // true - X, false - O

    public TicTacToe() {
        area = new char[3][3];
    }

    public void setArea(int x, int y) {
        area[y][x] = this.userX ? 'X' : 'O';
        this.userX = !this.userX;
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
        if (x < 1 || x > 3 || y < 1 || y > 3) {
            return false;
        }

        if (this.area[y - 1][x - 1] == 'X' || this.area[y - 1][x - 1] == 'O'){
            return true;
        }

        return false;
    }

    public void makeMove(Scanner scanner) {
        // get first vars
        System.out.print("Enter the coordinates: ");
        char[] coordsChar = scanner.nextLine().replaceAll("\\s", "").toCharArray();
        int[] coordsInt = {(int)coordsChar[0] - 48, (int)coordsChar[1] - 48};
        // parse coords
        coordsInt[1] = coordsInt[1] == 1 ? 3 : coordsInt[1] == 3 ? 1 : coordsInt[1];
        // verification
        while (coordsInt[0] < 1 || 3 < coordsInt[0] || coordsInt[1] < 1 || 3 < coordsInt[1] || this.isOccuped(coordsInt[0], coordsInt[1])) {
            if (this.isOccuped(coordsInt[0], coordsInt[1])) { // check if is in use
                System.out.println("This cell is occupied! Choose another one!");
            } else if (
                    coordsInt[0] == 0 || coordsInt[1] == 0 ||
                    coordsInt[0] <= 9 || coordsInt[1] <= 9) { // check if user type number between 1 to 3
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                System.out.println("You should enter numbers!");
            }

            // getting another variables
            System.out.print("Enter the coordinates: ");
            coordsChar = scanner.nextLine().replaceAll("\\s", "").toCharArray();
            coordsInt[0] = (int)coordsChar[0] - 48;
            coordsInt[1] = (int)coordsChar[1] - 48;
            // parsing
            if (coordsInt[1] == 1) {
                coordsInt[1] = 3;
            } else if(coordsInt[1] == 3) {
                coordsInt[1] = 1;
            }
        }
        // setting move
        this.setArea(coordsInt[0] - 1, coordsInt[1] - 1);
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

            if (area[i][1] == '\u0000') {
                continue;
            }

            if (area[i][0] == area[i][1] && area[i][1] == area[i][2] && area[i][1] != '\u0000') { // 1,2,3
                wins++;
                winChar = area[i][0];
            }
            if (area[0][i] == area[1][i] && area[1][i] == area[2][i] && area[1][i] != '\u0000') { // 4,5,6
                wins++;
                winChar = area[0][i];
            }
        }

        if (area[1][1] != '\u0000' && area[0][0] == area[1][1] && area[1][1] == area[2][2] || area[1][1] != '\u0000' && area[0][2] == area[1][1] && area[1][1] == area[2][0]) { // 7,8
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
        String line1 = "| " + (this.area[0][0] == '\u0000' ? " " : this.area[0][0]) + " " + (this.area[0][1] == '\u0000' ? " " : this.area[0][1]) + " " + (this.area[0][2] == '\u0000' ? " " : this.area[0][2]) + " |\n";
        String line2 = "| " + (this.area[1][0] == '\u0000' ? " " : this.area[1][0]) + " " + (this.area[1][1] == '\u0000' ? " " : this.area[1][1]) + " " + (this.area[1][2] == '\u0000' ? " " : this.area[1][2]) + " |\n";
        String line3 = "| " + (this.area[2][0] == '\u0000' ? " " : this.area[2][0]) + " " + (this.area[2][1] == '\u0000' ? " " : this.area[2][1]) + " " + (this.area[2][2] == '\u0000' ? " " : this.area[2][2]) + " |\n";

        return new StringBuilder().append(lineBorder).append(line1).append(line2).append(line3).append(lineBorder).toString();
    }

}
