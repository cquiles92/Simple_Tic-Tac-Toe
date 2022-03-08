package tictactoe;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static String input = "_________";

    public static void main(String[] args) {
        initGame();
    }

    public static void initGame() {
        printGame();
        final char PLAYER_ONE = 'X';
        final char PLAYER_TWO = 'O';
        char currentTurn = PLAYER_ONE;
        do {
            getInputs(currentTurn);
            if (currentTurn == PLAYER_ONE) {
                currentTurn = PLAYER_TWO;
            } else {
                currentTurn = PLAYER_ONE;
            }
        } while (!gameState());
    }

    private static void printGame() {
        final char TIC_TAC_TOE_X = 'X';
        final char TIC_TAC_TOE_O = 'O';
        final char TIC_TAC_TOE_WHITESPACE = ' ';
        final char TIC_TAC_TOE_SIDE_BORDER = '|';
        final String TOP_BOTTOM_BORDER = "---------";

        System.out.println(TOP_BOTTOM_BORDER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    System.out.print(TIC_TAC_TOE_SIDE_BORDER + " ");
                }
                char charIndex = input.charAt((3 * i) + j);
                if (charIndex == TIC_TAC_TOE_O) {
                    System.out.print(TIC_TAC_TOE_O + " ");
                } else if (charIndex == TIC_TAC_TOE_X) {
                    System.out.print(TIC_TAC_TOE_X + " ");
                } else {
                    System.out.print(TIC_TAC_TOE_WHITESPACE + " ");
                }
                if (j == 2) {
                    System.out.println(TIC_TAC_TOE_SIDE_BORDER);
                }
            }
        }

        System.out.println(TOP_BOTTOM_BORDER);
    }

    private static void getInputs(char currentPlayer) {
        boolean isNotValid = true;
        do {
            System.out.println("Enter the coordinates: ");
            String userInput = SCANNER.nextLine().trim();
            if (!userInput.matches("\\d*\\s\\d*")) {
                System.out.println("You should enter numbers!");
                continue;
            }
            String[] coordinates = userInput.split(" ");
            int row = Integer.parseInt(coordinates[0]);
            int column = Integer.parseInt(coordinates[1]);
            if (row < 0 || column < 0 || row > 3 || column > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            int charToCheck = (row - 1) * 3 + (column - 1);
            if (input.charAt(charToCheck) != '_') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                input = input.substring(0, charToCheck) + currentPlayer +
                        input.substring(charToCheck + 1);
                isNotValid = false;
            }
        } while (isNotValid);

        printGame();
    }

    private static boolean gameState() {
        int numberOfX = 0;
        int numberOfO = 0;
        boolean winnerX = gameTester('X');
        boolean winnerO = gameTester('O');

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'X') {
                numberOfX++;
            }
            if (input.charAt(i) == 'O') {
                numberOfO++;
            }
        }
        //impossible state (either too many X's or O's or both sides win)
        if (numberOfX - numberOfO >= 2 || numberOfO - numberOfX >= 2 ||
                winnerX && winnerO) {
            System.out.println("Impossible");
            return true;
        }
        //Draw game condition || 9 total plays and no winners
        else if (numberOfX + numberOfO == 9 && (!winnerX && !winnerO)) {
            System.out.println("Draw");
            return true;
        }
        //Game not finished if neither player wins and less than 9
//        else if (numberOfX + numberOfO != 9 && (!winnerX && !winnerO)) {
//            System.out.println("Game not finished");
//
//        }
        //X wins
        else if (winnerX) {
            System.out.println("X wins");
            return true;
        }
        //O wins
        else if (winnerO) {
            System.out.println("O wins");
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkHorizontal(char side) {
        String winCondition = "" + side + side + side;
        return (input.substring(0, 3).equals(winCondition)
                || input.substring(3, 6).equals(winCondition) ||
                input.substring(6, 9).equals(winCondition));
    }

    private static boolean checkVertical(char side) {
        String winCondition = String.valueOf(side + side + side);
        String columnOne = String.valueOf(input.charAt(0) +
                input.charAt(3) + input.charAt(6));
        String columnTwo = String.valueOf(input.charAt(1) +
                input.charAt(4) + input.charAt(7));
        String columnThree = String.valueOf(input.charAt(2) +
                input.charAt(5) + input.charAt(8));
        return columnOne.equals(winCondition) || columnTwo.equals(winCondition) ||
                columnThree.equals(winCondition);
    }

    private static boolean checkDiagonal(char side) {
        String winCondition = String.valueOf(side + side + side);
        String diagonalTopLeft = String.valueOf(input.charAt(0) + input.charAt(4)
                + input.charAt(8));
        String diagonalTopRight = String.valueOf(input.charAt(2) + input.charAt(4)
                + input.charAt(6));
        return diagonalTopLeft.equals(winCondition) ||
                diagonalTopRight.equals(winCondition);
    }

    private static boolean gameTester(char side) {
        return checkHorizontal(side) || checkVertical(side) ||
                checkDiagonal(side);
    }
}
