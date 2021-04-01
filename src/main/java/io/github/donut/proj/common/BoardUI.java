package io.github.donut.proj.common;

import io.github.donut.proj.controllers.GameController;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.Objects;

/**
 * BoardUI class; handles drawing board to screen in javafx
 * @author Kord Bonaidi
 */
public class BoardUI extends GridPane implements ISubject, IObserver {

    /**
     * Container for data to be sent out to IObservers subscribed to this class
     * @see EventManager#notify(ISubject, Object)
     * @author Kord Boniadi
     */
    public static class UserSelectionData {
        private final int x;    // columns
        private final int y;    // rows

        /**
         * Constructor
         * @author Kord Boniadi
         */
        public UserSelectionData() {
            this(0, 0);
        }

        /**
         * Constructor
         * @param x columns
         * @param y rows
         * @author Kord Boniadi
         */
        public UserSelectionData(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return x coordinate (column)
         */
        public int getX() {
            return this.x;
        }

        /**
         * @return y coordinate (row)
         */
        public int getY() {
            return this.y;
        }


        /**
         * Indicates whether some other object is "equal to" this one.
         *
         * @param o the reference object with which to compare.
         * @return {@code true} if this object is the same as the obj
         * argument; {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserSelectionData)) return false;
            UserSelectionData that = (UserSelectionData) o;
            return getX() == that.getX() && getY() == that.getY();
        }

        /**
         * Returns a hash code value for the object. This method is
         * supported for the benefit of hash tables such as those provided by
         * @return a hash code value
         */
        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY());
        }


        /**
         * @return String representation of the object
         */
        @Override
        public String toString() {
            return "UserSelectionData{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private final int GRID_SIZE = 3;
    private Image xImage;
    private Image oImage;
    private Image emptyImage;

    /**
     * Default GUI board constructor
     * Constructs an empty board in javafx
     * @author Kord Boniadi
     */
    public BoardUI() {
        try {
            xImage = new Image(Objects.requireNonNull(
                    getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/X_white.png")
            ));
            oImage = new Image(Objects.requireNonNull(
                    getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/O_white.png")
            ));
            emptyImage = new Image(Objects.requireNonNull(
                    getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/Empty.png")
            ));
        } catch(Exception e) {
            Logger.log(e);
        }
        boardConstruction();
    }

    /**
     * helper class for BoardUI initialization
     * @see #BoardUI()
     * @author Kord Boniadi
     */
    private void boardConstruction() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Pane clickable = new Pane();
                setBoardBorder(clickable, x, y);
                ImageView token = new ImageView(emptyImage);
                token.setPreserveRatio(true);
                token.setFitHeight(100);
                token.setFitWidth(100);
                clickable.getChildren().add(token);
                clickable.setOnMouseClicked(event -> {
                    Object eventType = new UserSelectionData(
                            GridPane.getColumnIndex((Node) event.getSource()),
                            GridPane.getRowIndex((Node) event.getSource())
                    );
                    EventManager.notify(this, eventType);
                });
                this.add(clickable, x, y);
            }
        }
        this.setAlignment(Pos.CENTER);
    }

    private void setBoardBorder(Pane pane, int x, int y) {
        if (x == 1 && y % 2 == 0) { // top and bottom sides
            pane.setBorder(new Border(new BorderStroke(
                    Color.rgb(70, 63, 63), null, null, null,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                    null,
                    BorderStroke.THICK,
                    Insets.EMPTY
            )));
        } else if (x % 2 == 0 && y == 1) {  // left and right sides
            pane.setBorder(new Border(new BorderStroke(
                    Color.rgb(70, 63, 63), null, null, null,
                    BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    null,
                    BorderStroke.THICK,
                    Insets.EMPTY
            )));

        } else if (x == 1) {
            pane.setBorder(new Border(new BorderStroke( // center
                    Color.rgb(70, 63, 63), null, null, null,
                    BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                    null,
                    BorderStroke.THICK,
                    Insets.EMPTY
            )));
        }
    }


    /**
     * Clears Board view setting it to original starting state
     * @author Kord Bonaidi
     */
    public void clearBoard() {
        ObservableList<Node> nodes = this.getChildren();
        for (var n : nodes) {
            if (n instanceof Pane) {
                ((ImageView) ((Pane) n).getChildren().get(0)).setImage(emptyImage);
            }
        }
    }

    /**
     * Draws Board image based on Data level board class info
     * @param currState data level board state
     * @author Kord Boniadi
     */
    public void drawBoard(Board currState) {
        ObservableList<Node> nodes = this.getChildren();
        ImageView image;
        for (Node n : nodes) {
            if (n instanceof Pane) {
                image = (ImageView) ((Pane) n).getChildren().get(0);
                int x = GridPane.getColumnIndex(n);
                int y = GridPane.getRowIndex(n);

                switch (currState.getToken(x, y)) {
                case X -> image.setImage(xImage);
                case O -> image.setImage(oImage);
                case BLANK -> image.setImage(emptyImage);
                default -> throw new RuntimeException("Board contained an invalid value");
                }
            }
        }
    }

    /**
     * Receives data from a subscribed subject
     * @param eventType object container
     * @author Kord Boniadi
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof GameController.DrawInfo)
            drawBoard(((GameController.DrawInfo) eventType).getUpdatedBoard());
    }
}
