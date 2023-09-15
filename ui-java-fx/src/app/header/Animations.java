package app.header;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Animations {
    public void startFadeTransition(Shape shape, boolean longer){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), shape);
        fadeTransition.setFromValue(1.0); // Fully visible
        fadeTransition.setToValue(0.2);   // 20% opacity

        if(longer){
            fadeTransition.setCycleCount(6);
        }
        else{
            fadeTransition.setCycleCount(4);
        }
        fadeTransition.setAutoReverse(true); // Reverse the animation
        fadeTransition.play();
    }

    public void startFillTransition(Shape shape){
        FillTransition ft = new FillTransition(Duration.millis(2000), shape, Color.GREY, Color.PINK);

        ft.setCycleCount(4);
        ft.setAutoReverse(true);
        ft.play();
    }

    public void startPathTransition(Label label1, Label label2, Label label3){
        Line path = new Line();
        path.setStartX(0);
        path.setStartY(0);
        path.setEndX(0);
        path.setEndY(60); // Adjust the Y coordinate to control the vertical movement

        // Create PathTransition animations for label pairs
        PathTransition transition01 = createPathTransition(label1, path);
        PathTransition transition02 = createPathTransition(label2, path);
        PathTransition transition03 = createPathTransition(label3, path);

        // Set the cycle count and auto-reverse for animations
        transition01.setCycleCount(2);
        transition02.setCycleCount(2);
        transition03.setCycleCount(2);

        transition01.setAutoReverse(true);
        transition02.setAutoReverse(true);
        transition03.setAutoReverse(true);

        transition01.play();
        transition02.setDelay(Duration.seconds(1.0)); // Delay by 1 second
        transition02.play();
        transition03.setDelay(Duration.seconds(2)); // Delay by 2 seconds
        transition03.play();
    }

    private PathTransition createPathTransition(Label label1,Line path) {
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(2.0)); // Set the duration
        transition.setPath(path);
        transition.setNode(label1);
        return transition;
    }
}
