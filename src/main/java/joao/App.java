package joao;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Pane canvas = new Pane();
        Scene scene = new Scene(canvas, 420, 360, Color.AQUA);

        Color[] colors = {Color.RED, Color.BROWN, Color.VIOLET, Color.GREEN, Color.GRAY, Color.YELLOW};

        Circle ball = new Circle(10, Color.BLACK);
        Circle center = new Circle(5, Color.RED);

        Point2D P_0 = new Point2D(scene.getWidth()/2, ball.getRadius());                                //top
        Point2D P_1 = new Point2D(scene.getWidth() - ball.getRadius(), scene.getHeight()/2);            //right
        Point2D P_2 = new Point2D(scene.getWidth()/2, scene.getHeight() - ball.getRadius());            //bottom
        Point2D P_3 = new Point2D(ball.getRadius(), scene.getHeight()/2);                               //left
        Point2D P_4 = new Point2D(scene.getWidth()/2, scene.getHeight()/2);                             //center

        canvas.getChildren().add(ball);
        canvas.getChildren().add(center);

        center.relocate((P_4.getX()-center.getRadius()), P_4.getY());

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>(){
            Point2D p0 = P_0;
            Point2D p1 = P_1;

            double tmp = 0.01;

            int random = 0;

            int randomSteps = 0;

                public void handle(ActionEvent t) {
                    Point2D pC = new Point2D(center.getLayoutX(), center.getLayoutY());
                    tmp += 0.01;

                    random = (int) (Math.random() * colors.length);
                    randomSteps = (int) (Math.random() * (20-(-20)+1)+(-20));

                    ball.setLayoutX(BezierCurveX(p0, pC, p1, tmp));
                    ball.setLayoutY(BezierCurveY(p0, pC, p1, tmp));

                    if (tmp <= 1.01 && tmp >= 0.99)
                    {
                        if (ball.getLayoutX() >= P_1.getX())
                        {
                            p0 = P_1;
                            p1 = P_2;
                            tmp = 0;

                            center.setLayoutX(center.getLayoutX() + randomSteps);
                            center.setLayoutY(center.getLayoutY() + randomSteps);

                            changeRandomColor(ball, colors, random);
                        }

                        if (ball.getLayoutY() >= P_2.getY())
                        {
                            p0 = P_2;
                            p1 = P_3;
                            tmp = 0;

                            center.setLayoutX(center.getLayoutX() + randomSteps);
                            center.setLayoutY(center.getLayoutY() + randomSteps);

                            changeRandomColor(ball, colors, random);
                        }

                        if (ball.getLayoutX() <= P_3.getX())
                        {
                            p0 = P_3;
                            p1 = P_0;
                            tmp = 0;

                            center.setLayoutX(center.getLayoutX() + randomSteps);
                            center.setLayoutY(center.getLayoutY() + randomSteps);

                            changeRandomColor(ball, colors, random);
                        }

                        if (ball.getLayoutY() <= P_0.getY())
                        {
                            p0 = P_0;
                            p1 = P_1;
                            tmp = 0;

                            center.setLayoutX(center.getLayoutX() + randomSteps);
                            center.setLayoutY(center.getLayoutY() + randomSteps);

                            changeRandomColor(ball, colors, random);
                        }
                    }
                }
        }));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();

        stage.setTitle("Crazy Ball");
        stage.setScene(scene);
        stage.show();
    }

    public double BezierCurveX(Point2D p0, Point2D p1, Point2D p2, double t)
    {
        return Math.pow(1-t, 2) * p0.getX() + 2 * (1-t) * t * p1.getX() + Math.pow(t,2) * p2.getX();
    }

    public double BezierCurveY(Point2D p0, Point2D p1, Point2D p2, double t)
    {
        return Math.pow(1-t, 2) * p0.getY() + 2 * (1-t) * t * p1.getY() + Math.pow(t,2) * p2.getY();
    }

    public void changeRandomColor(Circle ball, Color[] colors, int i)
    {
        ball.setFill(colors[i]);
    }

    public static void main(String[] args) {
        launch();
    }

}