// // Create a framework for a game like chess, with classes for all `Piece`, `Player`, and `Board`.
//2 feb

import java.util.ArrayList;
import java.util.List;

// Enum to represent piece colors
enum Color {
    WHITE, BLACK
}

// Abstract class representing a chess piece
abstract class Piece {
    protected Color color;
    protected int x, y; // Position on the board

    public Piece(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    // Abstract method to validate a move
    public abstract boolean isValidMove(int newX, int newY, Board board);

    // Move the piece to a new position
    public void move(int newX, int newY, Board board) {
        if (isValidMove(newX, newY, board)) {
            board.removePiece(x, y);
            this.x = newX;
            this.y = newY;
            board.placePiece(this, newX, newY);
        } else {
            System.out.println("Invalid move!");
        }
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "W" : "B";
    }
}

// Concrete class for a Pawn
class Pawn extends Piece {
    public Pawn(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        int direction = (color == Color.WHITE) ? 1 : -1;
        return newX == x + direction && newY == y;
    }
}

// Concrete class for a Rook
class Rook extends Piece {
    public Rook(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        return newX == x || newY == y;
    }
}

// Concrete class for a Knight
class Knight extends Piece {
    public Knight(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}

// Concrete class for a Bishop
class Bishop extends Piece {
    public Bishop(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        return Math.abs(newX - x) == Math.abs(newY - y);
    }
}

// Concrete class for a Queen
class Queen extends Piece {
    public Queen(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        return newX == x || newY == y || Math.abs(newX - x) == Math.abs(newY - y);
    }
}

// Concrete class for a King
class King extends Piece {
    public King(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return dx <= 1 && dy <= 1;
    }
}

// Class representing a Player
class Player {
    private String name;
    private Color color;
    private List<Piece> pieces;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name + " (" + color + ")";
    }
}

// Class representing the Chess Board
class Board {
    private Piece[][] grid;

    public Board() {
        grid = new Piece[8][8]; // 8x8 chessboard
    }

    // Place a piece on the board
    public void placePiece(Piece piece, int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            grid[x][y] = piece;
        }
    }

    // Remove a piece from the board
    public void removePiece(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            grid[x][y] = null;
        }
    }

    // Get the piece at a specific position
    public Piece getPiece(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return grid[x][y];
        }
        return null;
    }

    // Display the board
    public void display() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = grid[i][j];
                System.out.print((piece != null) ? piece : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

// Class representing the Game
class Game {
    private Player[] players;
    private Board board;
    private int currentPlayerIndex;

    public Game(Player player1, Player player2) {
        this.players = new Player[] { player1, player2 };
        this.board = new Board();
        this.currentPlayerIndex = 0;
        initializePieces();
    }

    // Initialize pieces on the board
    private void initializePieces() {
        // Place pawns
        for (int i = 0; i < 8; i++) {
            players[0].addPiece(new Pawn(Color.WHITE, 1, i));
            players[1].addPiece(new Pawn(Color.BLACK, 6, i));
        }
        // Place rooks
        players[0].addPiece(new Rook(Color.WHITE, 0, 0));
        players[0].addPiece(new Rook(Color.WHITE, 0, 7));
        players[1].addPiece(new Rook(Color.BLACK, 7, 0));
        players[1].addPiece(new Rook(Color.BLACK, 7, 7));
        // Place knights
        players[0].addPiece(new Knight(Color.WHITE, 0, 1));
        players[0].addPiece(new Knight(Color.WHITE, 0, 6));
        players[1].addPiece(new Knight(Color.BLACK, 7, 1));
        players[1].addPiece(new Knight(Color.BLACK, 7, 6));
        // Place bishops
        players[0].addPiece(new Bishop(Color.WHITE, 0, 2));
        players[0].addPiece(new Bishop(Color.WHITE, 0, 5));
        players[1].addPiece(new Bishop(Color.BLACK, 7, 2));
        players[1].addPiece(new Bishop(Color.BLACK, 7, 5));
        // Place queens
        players[0].addPiece(new Queen(Color.WHITE, 0, 3));
        players[1].addPiece(new Queen(Color.BLACK, 7, 3));
        // Place kings
        players[0].addPiece(new King(Color.WHITE, 0, 4));
        players[1].addPiece(new King(Color.BLACK, 7, 4));

        // Place all pieces on the board
        for (Player player : players) {
            for (Piece piece : player.getPieces()) {
                board.placePiece(piece, piece.x, piece.y);
            }
        }
    }

    // Switch turns between players
    private void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    // Play the game
    public void play() {
        while (true) {
            Player currentPlayer = players[currentPlayerIndex];
            System.out.println("Current Player: " + currentPlayer);
            board.display();

            // Simulate a move (for demonstration purposes)
            if (currentPlayer.getColor() == Color.WHITE) {
                Piece piece = currentPlayer.getPieces().get(0); // Move the first piece
                piece.move(piece.x + 1, piece.y, board); // Move forward
            } else {
                Piece piece = currentPlayer.getPieces().get(0); // Move the first piece
                piece.move(piece.x - 1, piece.y, board); // Move forward
            }

            switchTurn();
        }
    }
}

// Main class to run the game
public class ChessGame {
    public static void main(String[] args) {
        Player player1 = new Player("Alice", Color.WHITE);
        Player player2 = new Player("Bob", Color.BLACK);

        Game game = new Game(player1, player2);
        game.play();
    }
}
