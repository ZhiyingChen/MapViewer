package com.example.cw2javafxv2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HelloApplication extends Application {
    // static fields:
    private static final String VERSION = "Version 2.0";
    private static final int imageWidth = 512;
    private static final int imageHeight = 384;


    private BorderPane ContentPanel;
    private VBox TopPanel;
    private HBox MenuBox;
    private Label filenameLabel;
    private BorderPane imagePanel;
    Pane imagepane = new Pane();
    private AnchorPane Bottom;
    private BorderPane ButtonPanel;

    List<Button> Buttons = new ArrayList<>();
    private Button ButtonUp;
    private Button ButtonDown;
    private Button ButtonLeft;
    private Button ButtonRight;
    private Button ButtonMiddle;

    private String currentImagepath;

    // This map zips each image path and a list which contains the images they should turn to
    Map<String, List<String>> PicPath_TurnList = new HashMap<String, List<String>>();
    // This map zips the relationship between directions and the index in the "turn list"
    Map<String, Integer> Direction_Index = new HashMap<String, Integer>();



    @Override
    public void start(Stage stage) {
        // cite ZetCode to make initUI() method
        initUI(stage);
    }

    private void initUI(Stage stage) {

        Fill_HashMap();
        // A ContentPanel contains a menu on the top
        // a image Panel in the middle
        // and a buttonPanel at the bottom
        ContentPanel = new BorderPane();
        imagepane.setPrefSize(imageWidth,imageHeight);
        currentImagepath = null;
        // ------------------------------------------------------------------------
        // set a menu and a file name on the top of ContentPanel



        // make a menu
        MenuItem menuItem1 = new MenuItem("Flat");
        menuItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        menuItem1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                currentImagepath = "src/pictures/Flat1.jpg";
                displayImage(currentImagepath);
                setButtonsEnabled(currentImagepath);

            }
        });

        MenuItem menuItemR = new MenuItem("Room");
        menuItemR.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        menuItemR.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                currentImagepath = "src/pictures/Room1.jpg";
                displayImage(currentImagepath);
                setButtonsEnabled(currentImagepath);

            }
        });

        MenuItem menuItemK = new MenuItem("Kitchen");
        menuItemK.setAccelerator(KeyCombination.keyCombination("Ctrl+K"));
        menuItemK.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                currentImagepath = "src/pictures/Kitchen1.jpg";
                displayImage(currentImagepath);
                setButtonsEnabled(currentImagepath);

            }
        });

        MenuItem menuItem2 = new MenuItem("Close");
        menuItem2.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        menuItem2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                currentImagepath = null;

                displayImage(currentImagepath);
                setButtonsEnabled(currentImagepath);

            }
        });

        MenuItem menuItem3 = new MenuItem("Quit");
        menuItem3.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuItem3.setOnAction(e -> Platform.exit());
        MenuButton fileMenu = new MenuButton("File", null, menuItem1,menuItemR,menuItemK, menuItem2,menuItem3);

        MenuItem helpItem1 = new MenuItem("About MapViewer");
        helpItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        helpItem1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("About MapViewer");
                alert.setHeaderText("MapViewer: " + VERSION);
                alert.setContentText("\nPress ↑ to move forward\nPress ↓ to move back\nPress ← to turn left\nPress → to turn right\nPress Push to go inside");
                alert.showAndWait();
            }
        });


        MenuButton helpMenu = new MenuButton("Help",null,helpItem1);


        MenuBox = new HBox(fileMenu, helpMenu);
        //Creating separator menu items
        SeparatorMenuItem sep = new SeparatorMenuItem();
        //Adding separator objects to menu
        fileMenu.getItems().add(4, sep);

        // add Menu to the top

        ContentPanel.setTop(MenuBox);

        // ------------------------------------------------------------------------
        // Set imagePanel at the middle of ContentPanel
        imagePanel = new BorderPane();

        ContentPanel.setCenter(imagePanel);
        displayImage(currentImagepath);


        // -----------------------------------------------------------------
        // set Bottom at the bottom of ContentPanel
        Bottom = new AnchorPane();
        modifyBottom(Bottom);
        ContentPanel.setBottom(Bottom);

        Scene scene = new Scene(ContentPanel, imageWidth, imageHeight+150);

        stage.setTitle("MapViewer");
        stage.setScene(scene);
        stage.show();
    }

    // To display image, of which the path is currentImagepath, in imagePanel
    private void displayImage(String currentImagepath){

        if (currentImagepath==null){
            filenameLabel = new Label("No file displayed.");

        }
        else{
            filenameLabel = new Label("File: "+currentImagepath);
        }

        imagePanel.setTop(filenameLabel);
        try {
            if (currentImagepath!= null){
                //creating the image object
                InputStream stream = new FileInputStream(currentImagepath);
                Image image = new Image(stream);
                //Creating the image view
                ImageView imageView = new ImageView();
                //Setting image to the image view
                imageView.setImage(image);
                //Setting the image view parameters
                imageView.setX(0);
                imageView.setY(0);
                imageView.setFitWidth(imageWidth);
                imageView.setFitHeight(imageHeight);
                imageView.setPreserveRatio(true);
                imagePanel.setCenter(imageView);
            }
            else{
                imagePanel.setCenter(imagepane);
            }
        }
        catch(IOException exc) {
            System.out.println(exc+" There is no such file: "+ currentImagepath);
            imagePanel.setCenter(imagepane);

        }

        ContentPanel.setCenter(imagePanel);
    }

    //
    private void modifyBottom(AnchorPane Bottom){
        Bottom.setPadding(new Insets(5));

        // Add buttons to the Bottom
        ButtonPanel = new BorderPane();
        ButtonPanel.setPadding(new Insets(20));

        ButtonUp = new Button("↑");
        ButtonUp.setPrefSize(120,20);
        Buttons.add(ButtonUp);
        ButtonPanel.setTop(ButtonUp);

        ButtonDown = new Button("↓");
        ButtonDown.setPrefSize(120,20);
        Buttons.add(ButtonDown);
        ButtonPanel.setBottom(ButtonDown);

        ButtonLeft = new Button("←");
        ButtonLeft.setPrefSize(30,20);
        Buttons.add(ButtonLeft);
        ButtonPanel.setLeft(ButtonLeft);

        ButtonRight = new Button("→");
        ButtonRight.setPrefSize(30,20);
        Buttons.add(ButtonRight);
        ButtonPanel.setRight(ButtonRight);


        ButtonMiddle = new Button("Push");
        ButtonMiddle.setPrefSize(60,20);
        Buttons.add(ButtonMiddle);
        ButtonPanel.setCenter(ButtonMiddle);
        setButtonsEnabled(currentImagepath);

        Bottom.getChildren().addAll(ButtonPanel);
        AnchorPane.setBottomAnchor(ButtonPanel, 1d);
        AnchorPane.setRightAnchor(ButtonPanel, 180d);

        for (Iterator<Button> iterButton = Buttons.iterator(); iterButton.hasNext(); ){
            Button Buttoni = iterButton.next();
            Buttoni.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    String buttonText = Buttoni.getText();
                    Integer  Action_Index = Direction_Index.get(buttonText);

                    if (currentImagepath!=null){
                        List<String> Turn_list = PicPath_TurnList.get(currentImagepath);
                        String newImagepath = Turn_list.get(Action_Index);
                        if (newImagepath!="") {
                            displayImage(newImagepath);
                            setButtonsEnabled(newImagepath);

                            currentImagepath = newImagepath;


                        }
                        else{
                            System.out.println("Cannot turn "+buttonText);
                        }
                    }
                }
            });
        }


    }

    private void setButtonsEnabled(String currentImagepath)
    {
        if (currentImagepath!=null){
            List<String> Turn_list = PicPath_TurnList.get(currentImagepath);

            for (int i=0; i<Turn_list.size(); i++) {
                String picpath = Turn_list.get(i);
                Button Buttoni = Buttons.get(i);
                if (picpath != "") {
                    Buttoni.setDisable(false);
                } else {
                    Buttoni.setDisable(true);
                }
            }

        }
        else{
            for (int i=0; i<Buttons.size(); i++) {
                Button Buttoni = Buttons.get(i);
                Buttoni.setDisable(true);
            }
        }


    }



    public void Fill_HashMap(){
        // Fill Direction_Index
        Direction_Index.put("↑",0);
        Direction_Index.put("↓",1);
        Direction_Index.put("←",2);
        Direction_Index.put("→",3);
        Direction_Index.put("Push",4);

        // Fill PicPath_TurnList
        String path0 = "src/pictures/Flat1.jpg";
        List<String> turnlist0= List.of("","","","src/pictures/Flat2.jpg","");
        PicPath_TurnList.put(path0,turnlist0);


        String path1 = "src/pictures/Flat2.jpg";
        List<String> turnlist1= List.of("","","src/pictures/Flat1.jpg","src/pictures/Flat3.jpg","");
        PicPath_TurnList.put(path1,turnlist1);


        String path2 = "src/pictures/Flat3.jpg";
        List<String> turnlist2= List.of("","","src/pictures/Flat2.jpg","src/pictures/Flat4.jpg","");
        PicPath_TurnList.put(path2,turnlist2);


        String path3 = "src/pictures/Flat4.jpg";
        List<String> turnlist3= List.of("","","src/pictures/Flat3.jpg","src/pictures/Flat5.jpg","");
        PicPath_TurnList.put(path3,turnlist3);


        String path4 = "src/pictures/Flat5.jpg";
        List<String> turnlist4= List.of("","","src/pictures/Flat4.jpg","src/pictures/Flat6.jpg","");
        PicPath_TurnList.put(path4,turnlist4);


        String path5 = "src/pictures/Flat6.jpg";
        List<String> turnlist5= List.of("src/pictures/Flat7.jpg","","src/pictures/Flat5.jpg","","");
        PicPath_TurnList.put(path5,turnlist5);


        String path6 = "src/pictures/Flat7.jpg";
        List<String> turnlist6= List.of("src/pictures/Flat9.jpg","src/pictures/Flat6.jpg","","src/pictures/Flat8.jpg","");
        PicPath_TurnList.put(path6,turnlist6);


        String path7 = "src/pictures/Flat8.jpg";
        List<String> turnlist7= List.of("","","src/pictures/Flat7.jpg","","src/pictures/Kitchen1.jpg");
        PicPath_TurnList.put(path7,turnlist7);


        String path8 = "src/pictures/Flat9.jpg";
        List<String> turnlist8= List.of("src/pictures/Flat10.jpg","src/pictures/Flat7.jpg","","","");
        PicPath_TurnList.put(path8,turnlist8);


        String path9 = "src/pictures/Flat10.jpg";
        List<String> turnlist9= List.of("src/pictures/Flat11.jpg","src/pictures/Flat9.jpg","","","");
        PicPath_TurnList.put(path9,turnlist9);


        String path10 = "src/pictures/Flat11.jpg";
        List<String> turnlist10= List.of("src/pictures/Flat13.jpg","src/pictures/Flat10.jpg","src/pictures/Flat12.jpg","","");
        PicPath_TurnList.put(path10,turnlist10);


        String path11 = "src/pictures/Flat12.jpg";
        List<String> turnlist11= List.of("","","","src/pictures/Flat11.jpg","");
        PicPath_TurnList.put(path11,turnlist11);


        String path12 = "src/pictures/Flat13.jpg";
        List<String> turnlist12= List.of("src/pictures/Flat16.jpg","src/pictures/Flat11.jpg","src/pictures/Flat14.jpg","src/pictures/Flat15.jpg","");
        PicPath_TurnList.put(path12,turnlist12);


        String path13 = "src/pictures/Flat14.jpg";
        List<String> turnlist13= List.of("","","","src/pictures/Flat13.jpg","");
        PicPath_TurnList.put(path13,turnlist13);


        String path14 = "src/pictures/Flat15.jpg";
        List<String> turnlist14= List.of("","","src/pictures/Flat13.jpg","","src/pictures/Room1.jpg");
        PicPath_TurnList.put(path14,turnlist14);


        String path15 = "src/pictures/Flat16.jpg";
        List<String> turnlist15= List.of("src/pictures/Flat18.jpg","src/pictures/Flat13.jpg","src/pictures/Flat17.jpg","","");
        PicPath_TurnList.put(path15,turnlist15);


        String path16 = "src/pictures/Flat17.jpg";
        List<String> turnlist16= List.of("","","","src/pictures/Flat16.jpg","");
        PicPath_TurnList.put(path16,turnlist16);


        String path17 = "src/pictures/Flat18.jpg";
        List<String> turnlist17= List.of("","src/pictures/Flat16.jpg","","","");
        PicPath_TurnList.put(path17,turnlist17);


        String path18 = "src/pictures/Room1.jpg";
        List<String> turnlist18= List.of("src/pictures/Room2.jpg","src/pictures/Flat15.jpg","","","");
        PicPath_TurnList.put(path18,turnlist18);


        String path19 = "src/pictures/Room2.jpg";
        List<String> turnlist19= List.of("src/pictures/Room3.jpg","src/pictures/Room1.jpg","","","");
        PicPath_TurnList.put(path19,turnlist19);


        String path20 = "src/pictures/Room3.jpg";
        List<String> turnlist20= List.of("src/pictures/Room4.jpg","src/pictures/Room2.jpg","","","");
        PicPath_TurnList.put(path20,turnlist20);


        String path21 = "src/pictures/Room4.jpg";
        List<String> turnlist21= List.of("src/pictures/Room6.jpg","src/pictures/Room3.jpg","src/pictures/Room5.jpg","","");
        PicPath_TurnList.put(path21,turnlist21);


        String path22 = "src/pictures/Room5.jpg";
        List<String> turnlist22= List.of("","","","src/pictures/Room4.jpg","");
        PicPath_TurnList.put(path22,turnlist22);


        String path23 = "src/pictures/Room6.jpg";
        List<String> turnlist23= List.of("","src/pictures/Room4.jpg","src/pictures/Room7.jpg","src/pictures/Room8.jpg","");
        PicPath_TurnList.put(path23,turnlist23);


        String path24 = "src/pictures/Room7.jpg";
        List<String> turnlist24= List.of("","","","src/pictures/Room6.jpg","");
        PicPath_TurnList.put(path24,turnlist24);


        String path25 = "src/pictures/Room8.jpg";
        List<String> turnlist25= List.of("","","src/pictures/Room6.jpg","","");
        PicPath_TurnList.put(path25,turnlist25);


        String path26 = "src/pictures/Kitchen1.jpg";
        List<String> turnlist26= List.of("src/pictures/Kitchen2.jpg","src/pictures/Flat8.jpg","","","");
        PicPath_TurnList.put(path26,turnlist26);


        String path27 = "src/pictures/Kitchen2.jpg";
        List<String> turnlist27= List.of("src/pictures/Kitchen3.jpg","src/pictures/Kitchen1.jpg","","","");
        PicPath_TurnList.put(path27,turnlist27);


        String path28 = "src/pictures/Kitchen3.jpg";
        List<String> turnlist28= List.of("src/pictures/Kitchen4.jpg","src/pictures/Kitchen2.jpg","","","");
        PicPath_TurnList.put(path28,turnlist28);


        String path29 = "src/pictures/Kitchen4.jpg";
        List<String> turnlist29= List.of("","src/pictures/Kitchen3.jpg","src/pictures/Kitchen5.jpg","","");
        PicPath_TurnList.put(path29,turnlist29);


        String path30 = "src/pictures/Kitchen5.jpg";
        List<String> turnlist30= List.of("src/pictures/Kitchen6.jpg","","","src/pictures/Kitchen4.jpg","");
        PicPath_TurnList.put(path30,turnlist30);


        String path31 = "src/pictures/Kitchen6.jpg";
        List<String> turnlist31= List.of("","src/pictures/Kitchen5.jpg","src/pictures/Kitchen7.jpg","","");
        PicPath_TurnList.put(path31,turnlist31);


        String path32 = "src/pictures/Kitchen7.jpg";
        List<String> turnlist32= List.of("src/pictures/Kitchen8.jpg","","","src/pictures/Kitchen6.jpg","");
        PicPath_TurnList.put(path32,turnlist32);


        String path33 = "src/pictures/Kitchen8.jpg";
        List<String> turnlist33= List.of("","src/pictures/Kitchen7.jpg","","src/pictures/Kitchen9.jpg","");
        PicPath_TurnList.put(path33,turnlist33);


        String path34 = "src/pictures/Kitchen9.jpg";
        List<String> turnlist34= List.of("","","src/pictures/Kitchen8.jpg","","");
        PicPath_TurnList.put(path34,turnlist34);

    }
    public static void main(String[] args) {
        launch();
    }
}