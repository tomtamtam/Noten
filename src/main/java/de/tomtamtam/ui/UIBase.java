package de.tomtamtam.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UIBase extends Application
{
    public UIBase()
    {
    }

    public void Run(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        //Title
        primaryStage.setTitle("Noten");

        //Icon
        Image icon = new Image("nerd-emotiguy.png");
        primaryStage.getIcons().add(icon);

        //Size
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);

        //Scene Title ( Grades )
        Text title = new Text();
        title.setText("Noten");
        title.setX(50);
        title.setY(50);
        title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));

        //Scene SubTitle ( Subjects )
        Text subTittle = new Text();
        subTittle.setText("Fächer: ");
        subTittle.setX(50);
        subTittle.setY(100);
        subTittle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 15));

        //Line
        Line line = new Line();
        line.setStartX(50);
        line.setStartY(110);
        line.endXProperty().bind(scene.widthProperty().subtract(50));
        line.setEndY(110);

        root.getChildren().add(title);
        root.getChildren().add(subTittle);
        root.getChildren().add(line);

        //Show
        primaryStage.show();
    }
}
