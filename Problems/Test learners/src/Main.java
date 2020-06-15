import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();

        System.out.println((x < 1 || x > 4) ? "Unknown number" : x == 1 ? "Yes!" : "No!");
    }
}