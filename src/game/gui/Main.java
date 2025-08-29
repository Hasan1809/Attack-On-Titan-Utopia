package game.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.weapons.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import game.gui.*;

public class Main extends Application {
	Battle battle;
	boolean fl = false;
	

    public void start(Stage primaryStage) throws Exception {
    	
    	//-- MAIN MENU SCENE --//
    	Group game = new Group();
    	Scene Easy =  new Scene(game, 1920, 1080);
    	Easy.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
    	try {
			ImageView BackgroundImageEasy = BackgroundMaker(Easy, "images\\back.png");
		  game.getChildren().add(BackgroundImageEasy);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	primaryStage.setFullScreen(true);
    	 
    	 //Battle Easy = new Battle(0, 0, 1000, 3, 250);
    	 //Battle Hard = new Battle(0, 0, 1000, 5, 125);
    	 Group root = new Group();
         Scene mainScene = new Scene(root, 1920, 1080);
         mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
         Image Image = new Image(new FileInputStream("images\\Title.png"));
		 ImageView tImage = new ImageView(Image);
	     tImage.setX(580);
	   	 tImage.setY(40);
         primaryStage.setTitle("Attack on Titan utopia");
         primaryStage.setScene(mainScene);
         primaryStage.setFullScreen(true);
         Button startButton = createButton("Start", 860, 460);
         startButton.getStyleClass().add("custom-button");
         Button instructionsButton = createButton("Instructions", 860, 540);
         instructionsButton.getStyleClass().add("custom-button");
         Button settingsButton = createButton("Settings", 860, 620);
         settingsButton.getStyleClass().add("custom-button");
         startButton.setMinHeight(50);
         startButton.setMinWidth(200);
         instructionsButton.setMinHeight(50);
         instructionsButton.setMinWidth(200);
         settingsButton.setMinHeight(50);
         settingsButton.setMinWidth(200);

      
        
        ImageView BackgroundImage = BackgroundMaker(mainScene, "images\\MenuBG.jpg");
        root.getChildren().add(BackgroundImage);
        root.getChildren().addAll(startButton, instructionsButton, settingsButton, tImage);
        MediaPlayer mediaplayer = BackgroundMusic("background_music\\Attack_on_Titan_-_Opening_Feuerroter_Pfeil_und_Bogen.mp3");
       
       
        //-- MAIN MENU SCENE --//
        
        //-- DIFFICULTY SELECTION SCENE --//
        Group gameRoot = new Group();
        Scene gameScene = new Scene(gameRoot, 1920, 1080);
        primaryStage.setFullScreen(true);
        gameScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Button HardButton = createButton("Hard", 860, 245);
        HardButton.setMinHeight(50);
        HardButton.setMinWidth(200);
        HardButton.getStyleClass().add("custom-button");
        Button EasyButton = createButton("Easy", 860, 785);
        EasyButton.getStyleClass().add("custom-button");
        EasyButton.setMinHeight(50);
        EasyButton.setMinWidth(200);
        Button BackButton = createButton("Back",0,0);
        BackButton.getStyleClass().add("custom-button");
        ImageView BackgroundImageDif = BackgroundMaker(gameScene, "images\\Untitled.jpg");
       


        gameRoot.getChildren().add(BackgroundImageDif);
        gameRoot.getChildren().addAll(HardButton, EasyButton,BackButton);
      
        
        //-- DIFFICULTY SELECTION SCENE --//
        
        //--SETTINGS SCENE--//
        Group SettingRoot = new Group();
        Scene SettingScene = new Scene(SettingRoot, 1920,1080);
        ImageView BackgroundImageS = BackgroundMaker(SettingScene,"images\\set.jpg");
        SettingRoot.getChildren().add(BackgroundImageS);
        SettingScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        Button Exit = createButton("Exit", 860, 245);
        Exit.getStyleClass().add("custom-button");
        Exit.setMinHeight(50);
        Exit.setMinWidth(200);
        Label vol = createLabel("Volume",900,450);
        vol.getStyleClass().add("custom-label");
        Slider volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(mediaplayer.getVolume());
        mediaplayer.volumeProperty().bind(volumeSlider.valueProperty());
        volumeSlider.setTranslateX(660);
        volumeSlider.setTranslateY(515);
        volumeSlider.setMinSize(600,50);
        volumeSlider.getStyleClass().add("custom-slider");
        SettingRoot.getChildren().addAll(BackButton,Exit,volumeSlider,vol);
        //--SETTINGS SCENE
        
        //--EASY GAME SCENE--//
        Button endTurn = createButton("Pass Turn", 400, 900);
        endTurn.getStyleClass().add("custom-button");
        endTurn.setMinWidth(200);
        ToggleButton Auto = new ToggleButton("Auto Play");
        Auto.getStyleClass().add("custom-toggle-button");
        Auto.setMinWidth(200);
        Auto.setLayoutX(400);
        Auto.setLayoutY(1000);
     	game.getChildren().addAll(endTurn , Auto);
     	
        //Easy button logic
        EasyButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Show the game choices
				
						try {
							battle = new Battle(1, 0, 100, 3, 250);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				HashMap<Integer, WeaponRegistry> weaponShop = battle.getWeaponFactory().getWeaponShop();
				Button PiercingCannon = createButton(weaponShop.get(1).getName() +"\n Price:" +weaponShop.get(1).getPrice()+"\n Type: Piercing Cannon \n Damage: " + weaponShop.get(1).getDamage(), 25, 50);
				try {
					ImageView WeaponImage = WeaponMaker("images\\cannon1.png");
					PiercingCannon.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				PiercingCannon.setMinWidth(500);
				PiercingCannon.setMinHeight(50);
				PiercingCannon.getStyleClass().add("custom-button-weapon");
			    Button SniperCannon = createButton(weaponShop.get(2).getName() +"\n Price:" +weaponShop.get(2).getPrice()+"\n Type: Sniper Cannon \n Damage: " + weaponShop.get(2).getDamage(), 25, 250);
			    try {
					ImageView WeaponImage = WeaponMaker("images\\spear.png");
					SniperCannon.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    SniperCannon.setMinWidth(500);
			    SniperCannon.setMinHeight(50);
			    SniperCannon.getStyleClass().add("custom-button-weapon");
			    Button VolleySpreadCannon = createButton(weaponShop.get(3).getName() +"\n Price:" +weaponShop.get(3).getPrice()+"\n Type: Volley Spread Cannon \n Damage: " + weaponShop.get(3).getDamage() , 25, 450);
			    try {
					ImageView WeaponImage = WeaponMaker("images\\catapult3.png");
					VolleySpreadCannon.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    VolleySpreadCannon.setMinWidth(500);
			    VolleySpreadCannon.setMinHeight(50);
			    VolleySpreadCannon.getStyleClass().add("custom-button-weapon");
			    Button WallTrap = createButton(weaponShop.get(4).getName() +"\n Price:" +weaponShop.get(4).getPrice()+"\n Type: Wall Trap \n Damage: " + weaponShop.get(4).getDamage(), 25, 650);
			    try {
					ImageView WeaponImage = WeaponMaker("images\\Arrow_Wall.png");
					WallTrap.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    WallTrap.setMinWidth(500);
			    WallTrap.setMinHeight(50);
			    WallTrap.getStyleClass().add("custom-button-weapon");
            	Label Score = createLabel("Score : " + battle.getScore() , 0, 0);
            	Score.getStyleClass().add("custom-vbox-label");
            	Score.setMinHeight(50);
            	Score.setMinWidth(350);
             	Label Resources = createLabel("Resources : " + battle.getResourcesGathered(), 0 , 0);
             	Resources.getStyleClass().add("custom-vbox-label");
             	Resources.setMinHeight(50);
             	Resources.setMinWidth(350);
             	Label Turn = createLabel("Turn number: " + battle.getNumberOfTurns() , 0,0);
             	Turn.getStyleClass().add("custom-vbox-label");
             	Turn.setMinHeight(50);
             	Turn.setMinWidth(350);
             	Label BattlePhase = createLabel("BattlePhase: " + battle.getBattlePhase(), 0,0);
             	BattlePhase.getStyleClass().add("custom-vbox-label");
             	BattlePhase.setMinHeight(50);
             	BattlePhase.setMinWidth(350);
             	VBox labels = new VBox();
             	labels.getChildren().addAll(BattlePhase,Turn,Resources,Score);
             	labels.getStyleClass().add("custom-vbox");
             	labels.setLayoutX(0);
             	labels.setLayoutY(830);
             	Rectangle[] laneback = new Rectangle[battle.getLanes().size()];
             	Pane[] Lanes = new Pane[battle.getLanes().size()];
             	HealthBar[] Bars = new HealthBar[battle.getLanes().size()];
             	Label[] DangerLevel = new Label[battle.getLanes().size()];
             	game.getChildren().addAll(PiercingCannon,SniperCannon,VolleySpreadCannon,WallTrap, labels);
             	
             	//Creating the Lane Panes
             	for(int i = 0; i < battle.getLanes().size() ; i++){
             		// (int x, int y, int width, int height, Color color) {
                 	Pane R = createLane(650, 50+(300*i), 1200, 310, Color.LAVENDER);
                 	Rectangle lane = new Rectangle(1200,310);
                 	Image image;
					try {
						image = new Image(new FileInputStream("images\\laneEasy.png"));
						lane.setFill(new ImagePattern(image));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 	
                 	Lanes[i] = R;
                 	HealthBar bar = new HealthBar();
                 	bar.setLayoutX(630);
                 	bar.setLayoutY(165+(300*i));
                 	Bars[i]=bar;
                 	Label danger = createLabel("DangerLevel: "+ battle.getOriginalLanes().get(i).getDangerLevel(), 650,(300*i)+165);
                 	danger.getStyleClass().add("small-rotated-label");
                 	DangerLevel[i] = danger;
                 	R.getChildren().add(lane);
                 	laneback[i] = lane;
                 	game.getChildren().addAll(R,bar,danger);
             	}   
             	  EventHandler autoEvent = new EventHandler(){
              		@Override
              		public void handle(Event event) {
              			
              				auto(battle , Turn,Score, Resources, Lanes,Bars,BattlePhase,DangerLevel,laneback);
              				if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mediaplayer);
              				
              			
              			
              		}
                  };
                  Button b = new Button();
                  KeyFrame k = new KeyFrame(javafx.util.Duration.millis(1000),autoEvent, new KeyValue(b.opacityProperty(), 0));
                  Timeline t = new Timeline(k);
                  t.setCycleCount(Timeline.INDEFINITE);
             	//EndTurn Button Logic
             	
                  endTurn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    	battle.passTurn();
                    	update(Turn,Score, Resources,battle, Lanes,Bars,BattlePhase,DangerLevel,laneback);
                    	if(battle.isGameOver()){
                    		GameOver(primaryStage,battle,mainScene,mediaplayer);
                    	}
                    }
                });
              
               
               
             	Auto.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                       if(Auto.isSelected()){
                    	   Auto.setText("Stop Auto");
                    	   t.play();
                       }else {
                    	   Auto.setText("Auto Play");
                    	   t.stop();
                       }
                    }
                });


             	
            	primaryStage.setScene(Easy);
            	primaryStage.setFullScreen(true);
            	
            	//Buying Weapon logic
                PiercingCannon.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopup(primaryStage,1,battle,Turn,Score, Resources,battle, Lanes ,mainScene, Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
                SniperCannon.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopup(primaryStage,2,battle,Turn,Score, Resources,battle, Lanes,mainScene, Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
                VolleySpreadCannon.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopup(primaryStage,3,battle,Turn,Score, Resources,battle, Lanes ,mainScene ,Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
                WallTrap.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopup(primaryStage,4,battle,Turn,Score, Resources,battle, Lanes, mainScene , Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
            	
            }
        });
        HardButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Show the game choices
					try {
						battle = new Battle(1, 0, 100, 5, 125);
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
				
				
		
				HashMap<Integer, WeaponRegistry> weaponShop = battle.getWeaponFactory().getWeaponShop();
				Button PiercingCannon = createButton(weaponShop.get(1).getName() +"\n Price:" +weaponShop.get(1).getPrice()+"\n Type: Piercing Cannon \n Damage: " + weaponShop.get(1).getDamage(), 25, 50);
				try {
					ImageView WeaponImage = WeaponMaker("images\\cannon1.png");
					PiercingCannon.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				PiercingCannon.setMinWidth(500);
				PiercingCannon.setMinHeight(50);
				PiercingCannon.getStyleClass().add("custom-button-weapon");
			    Button SniperCannon = createButton(weaponShop.get(2).getName() +"\n Price:" +weaponShop.get(2).getPrice()+"\n Type: Sniper Cannon \n Damage: " + weaponShop.get(2).getDamage(), 25, 250);
			    try {
					ImageView WeaponImage = WeaponMaker("images\\spear.png");
					SniperCannon.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    SniperCannon.setMinWidth(500);
			    SniperCannon.setMinHeight(50);
			    SniperCannon.getStyleClass().add("custom-button-weapon");
			    Button VolleySpreadCannon = createButton(weaponShop.get(3).getName() +"\n Price:" +weaponShop.get(3).getPrice()+"\n Type: Volley Spread Cannon \n Damage: " + weaponShop.get(3).getDamage() , 25, 450);
			    try {
					ImageView WeaponImage = WeaponMaker("images\\catapult3.png");
					VolleySpreadCannon.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    VolleySpreadCannon.setMinWidth(500);
			    VolleySpreadCannon.setMinHeight(50);
			    VolleySpreadCannon.getStyleClass().add("custom-button-weapon");
			    Button WallTrap = createButton(weaponShop.get(4).getName() +"\n Price:" +weaponShop.get(4).getPrice()+"\n Type: Wall Trap \n Damage: " + weaponShop.get(4).getDamage(), 25, 650);
			    try {
					ImageView WeaponImage = WeaponMaker("images\\Arrow_Wall.png");
					WallTrap.setGraphic(WeaponImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    WallTrap.setMinWidth(500);
			    WallTrap.setMinHeight(50);
			    WallTrap.getStyleClass().add("custom-button-weapon");
            	Label Score = createLabel("Score : " + battle.getScore() , 0, 0);
            	Score.getStyleClass().add("custom-vbox-label");
            	Score.setMinHeight(50);
            	Score.setMinWidth(350);
             	Label Resources = createLabel("Resources : " + battle.getResourcesGathered(), 0 , 0);
             	Resources.getStyleClass().add("custom-vbox-label");
             	Resources.setMinHeight(50);
             	Resources.setMinWidth(350);
             	Label Turn = createLabel("Turn number: " + battle.getNumberOfTurns() , 0,0);
             	Turn.getStyleClass().add("custom-vbox-label");
             	Turn.setMinHeight(50);
             	Turn.setMinWidth(350);
             	Label BattlePhase = createLabel("BattlePhase: " + battle.getBattlePhase(), 0,0);
             	BattlePhase.getStyleClass().add("custom-vbox-label");
             	BattlePhase.setMinHeight(50);
             	BattlePhase.setMinWidth(350);
             	VBox labels = new VBox();
             	labels.getChildren().addAll(BattlePhase,Turn,Resources,Score);
             	labels.getStyleClass().add("custom-vbox");
             	labels.setLayoutX(0);
             	labels.setLayoutY(830);
             	Rectangle[] laneback = new Rectangle[battle.getLanes().size()];
             	Pane[] Lanes = new Pane[battle.getLanes().size()];
             	HealthBar[] Bars = new HealthBar[battle.getLanes().size()];
             	Label[] DangerLevel = new Label[battle.getLanes().size()];
             	game.getChildren().addAll(PiercingCannon,SniperCannon,VolleySpreadCannon,WallTrap,labels);
             	
             	//Creating the Lane Panes
             	for(int i = 0; i < battle.getLanes().size() ; i++){
             		// (int x, int y, int width, int height, Color color) {
                 	Pane R = createLane(650, 50 +(190*i), 1200, 200, Color.LAVENDER);
                 	Rectangle lane = new Rectangle(1200,190);
                 	Image image;
					try {
						image = new Image(new FileInputStream("images\\laneHard1.png"));
						lane.setFill(new ImagePattern(image));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 	Lanes[i] = R;
                 	HealthBar bar = new HealthBar();
                 	Label danger = createLabel("DangerLevel: "+ battle.getOriginalLanes().get(i).getDangerLevel(), 650,(190*i)+130);
                 	danger.getStyleClass().add("small-rotated-label");
                 	bar.setLayoutX(630);
                 	bar.setLayoutY(50+(190*i));
                 	Bars[i]=bar;
                 	DangerLevel[i] = danger;
                 	R.getChildren().add(lane);
                 	laneback[i] = lane;
                 	game.getChildren().addAll(R,bar,danger);
             	}       	
             	//EndTurn Button Logic
             	 EventHandler autoEvent = new EventHandler(){
               		@Override
               		public void handle(Event event) {
               			
               				auto(battle , Turn,Score, Resources, Lanes,Bars,BattlePhase,DangerLevel,laneback);
               				if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mediaplayer);
               			
               			
               		}
                   };
                   Button b = new Button();
                   KeyFrame k = new KeyFrame(javafx.util.Duration.millis(1000),autoEvent, new KeyValue(b.opacityProperty(), 0));
                   Timeline t = new Timeline(k);
                   t.setCycleCount(Timeline.INDEFINITE);
             	endTurn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    	battle.passTurn();
                    	update(Turn,Score, Resources,battle, Lanes, Bars, BattlePhase,DangerLevel,laneback);
                    	if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mediaplayer);
                    }
                });
             	
            	Auto.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                       if(Auto.isSelected()){
                    	   Auto.setText("Stop Auto");
                    	   t.play();
                       }else {
                    	   Auto.setText("Auto Play");
                    	   t.stop();
                       }
                    }
                });

            	
            	primaryStage.setScene(Easy);
            	primaryStage.setFullScreen(true);
            	
            	//Buying Weapon logic
                PiercingCannon.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopupH(primaryStage,1,battle,Turn,Score, Resources,battle, Lanes, mainScene, Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
                SniperCannon.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopupH(primaryStage,2,battle,Turn,Score, Resources,battle, Lanes, mainScene, Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
                VolleySpreadCannon.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopupH(primaryStage,3,battle,Turn,Score, Resources,battle, Lanes ,mainScene, Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
                WallTrap.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        // Show the game choices
                    	showLanesPopupH(primaryStage,4,battle,Turn,Score, Resources,battle, Lanes, mainScene, Bars,BattlePhase,DangerLevel,laneback,mediaplayer);
                    	
                    }
                });
            	
            }
        });
        //Start button logic
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Show the game choices
                primaryStage.setScene(gameScene);
                primaryStage.setFullScreen(true);
            }
        });
        Exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Show the game choices
                primaryStage.close();
            }
        });
        //back button Logic
        BackButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Show the game choices
                primaryStage.setScene(mainScene);
                primaryStage.setFullScreen(true);
            }
        });
        
        //Instructions button logic
        instructionsButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                showInstructionsPopup(primaryStage, instructionsButton);
            }
        });
        //Setting button logic
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	primaryStage.setScene(SettingScene);
            	 primaryStage.setFullScreen(true);
           }
        });

    
        
        primaryStage.show();
    }

    public void update(Label Turn, Label Score, Label Resources, Battle battle, Pane[] lanes, HealthBar[] bars, Label BattlePhase, Label[] Danger, Rectangle[] laneback) {
        Turn.setText("Turn : " + battle.getNumberOfTurns());
        Score.setText("Score : " + battle.getScore());
        Resources.setText("Resources : " + battle.getResourcesGathered());
        BattlePhase.setText("BattlePhase : " + battle.getBattlePhase());

        for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
            bars[i].setHealth((double) battle.getOriginalLanes().get(i).getLaneWall().getCurrentHealth() / battle.getOriginalLanes().get(i).getLaneWall().getBaseHealth());
        }

        for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
            int c1 = 0;
            int c2 = 0;
            int c3 = 0;
            int c4 = 0;

            Lane lane = battle.getOriginalLanes().get(i);
            Pane cLane = lanes[i];
            Danger[i].setText("DangerLevel: " + lane.getDangerLevel());
            cLane.getChildren().clear();
            if (!lane.isLaneLost()) {
                cLane.getChildren().add(laneback[i]);
                VBox weaponBox = new VBox();
                weaponBox.setLayoutX(0);
                weaponBox.setLayoutY(0);

                for (Titan titan : lane.getTitans()) {
                    tHealthBar th = new tHealthBar();
                    th.setHealth((double) titan.getCurrentHealth() / titan.getBaseHealth());
                    th.setLayoutY(20);
                    int prev;
                    int speed = titan.getSpeed();
                    if (titan.getDistance() == 100 || titan.getDistance()==0){
                    	prev = titan.getDistance();
                    	
                    }else prev = titan.getDistance() + titan.getSpeed();
                    Image tImage = null;
                    try {
                        if (titan instanceof PureTitan) {
                            tImage = new Image(new FileInputStream("images\\pure.png"));
                            
                        } else if (titan instanceof ColossalTitan) {
                            tImage = new Image(new FileInputStream("images\\colossal.png"));
                            if(titan.getSpeed()!=5){
                            speed = speed -1;
                            prev = prev -1;
                            }
                        } else if (titan instanceof ArmoredTitan) {
                            tImage = new Image(new FileInputStream("images\\armored2.png"));
                        } else if (titan instanceof AbnormalTitan) {
                            tImage = new Image(new FileInputStream("images\\abnormal.png"));
                        }
                        if (tImage != null) {
                            ImageView imageView = new ImageView(tImage);
                            imageView.setX(prev * 10 + 150);
                            imageView.setY(20);
                            th.setLayoutX(prev * 10 + 130);
                            cLane.getChildren().add(imageView);
                            if (titan.getDistance()<100 && titan.getDistance()!=0){
                            TranslateTransition translate = new TranslateTransition();
                            translate.setByX(-speed*10);
                            translate.setDuration(Duration.millis(900));
                            translate.setCycleCount(1);
                            translate.setNode(imageView);
                            translate.play();
                            TranslateTransition tr = new TranslateTransition();
                            tr.setByX(-speed*10);
                            tr.setDuration(Duration.millis(900));
                            tr.setCycleCount(1);
                            tr.setNode(th);
                            tr.play();
                            } 
                           
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    cLane.getChildren().add(th);
                }

            	for(Weapon weapon : lane.getWeapons()){
        			
        			if(weapon instanceof PiercingCannon){
        				c1+=1;
        				Image Image;
    					try {
    						Image = new Image(new FileInputStream("images\\cannon1A.png"));
    						ImageView tImage = new ImageView(Image);
    	    		    	tImage.setX(80);
    	    		    	tImage.setY(0);
    	    		    	cLane.getChildren().add(tImage);
    					} catch (FileNotFoundException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
        			}
        			if(weapon instanceof SniperCannon){
        				c2 +=1;
        				Image Image;
    					try {
    						Image = new Image(new FileInputStream("images\\spearA.png"));
    						ImageView tImage = new ImageView(Image);
    	    		    	tImage.setX(80);
    	    		    	tImage.setY(40);
    	    		    	cLane.getChildren().add(tImage);
    					} catch (FileNotFoundException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}	
            		}
        			if(weapon instanceof VolleySpreadCannon){
        				c3+=1;
        				Image Image;
    					try {
    						Image = new Image(new FileInputStream("images\\catapult3A.png"));
    						ImageView tImage = new ImageView(Image);
    	    		    	tImage.setX(80);
    	    		    	tImage.setY(80);
    	    		    	cLane.getChildren().add(tImage);
    					} catch (FileNotFoundException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}	
            		}
        			if(weapon instanceof WallTrap){
        				c4+=1;
        				Image Image;
    					try {
    						Image = new Image(new FileInputStream("images\\Arrow_WallA.png"));
    						ImageView tImage = new ImageView(Image);
    	    		    	tImage.setX(80);
    	    		    	tImage.setY(120);
    	    		    	cLane.getChildren().add(tImage);
    					} catch (FileNotFoundException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
        			}
        			
        		}

                Label weapon1 = new Label("X" + c1);
                weapon1.setLayoutX(0);
                weapon1.setLayoutY(0);
                weapon1.getStyleClass().add("custom-label");

                Label weapon2 = new Label("X" + c2);
                weapon2.setLayoutX(0);
                weapon2.setLayoutY(40);
                weapon2.getStyleClass().add("custom-label");

                Label weapon3 = new Label("X" + c3);
                weapon3.setLayoutX(0);
                weapon3.setLayoutY(80);
                weapon3.getStyleClass().add("custom-label");

                Label weapon4 = new Label("X" + c4);
                weapon4.setLayoutX(0);
                weapon4.setLayoutY(120);
                weapon4.getStyleClass().add("custom-label");

                cLane.getChildren().addAll(weapon1, weapon2, weapon3, weapon4);
            } else {
                Image image;
                try {
                    if (battle.getOriginalLanes().size() == 3) {
                        image = new Image(new FileInputStream("images\\lanedestroyedEasy.png"));
                    } else {
                        image = new Image(new FileInputStream("images\\lanedestroyedHard.png"));
                    }
                    laneback[i].setFill(new ImagePattern(image));
                    cLane.getChildren().add(laneback[i]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            lanes[i] = cLane;
        }
    }


    public int tHP (Lane lane){
    	int sum = 0;
    	for (Titan t : lane.getTitans()){
    		sum+= t.getCurrentHealth();
    	}
    	return sum;
    }
    public int tWD (Lane l){
    	int sum = 0;
    	for (Weapon w : l.getWeapons()){
    		sum+= w.getDamage();
    	}
    	return sum;
    }
    public Lane mostD(Battle battle){
    	Lane l = null;
    	int Danger = -10000;
    	for (Lane lane : battle.getOriginalLanes()){
    		
    		if (!lane.isLaneLost()){
    			if(!lane.getTitans().isEmpty()){
    				if(lane.getTitans().peek().getDistance()<5){
    				return lane;
    				}
    			}
    			int compare = tHP(lane) - tWD(lane); 
    			if (compare  > Danger){
    				l = lane;
    				Danger = compare;
    			}
    		}
    	}
    	return l;
    }
    public int closeD(Battle battle){
    	PriorityQueue<Titan> titans =  mostD(battle).getTitans();
    	if (titans.isEmpty()){
    		return 100;
    	}
    	return titans.peek().getDistance();
    }
    public int bestW(Battle battle){
    	int r = battle.getResourcesGathered();
    	int d = closeD(battle);
    	if (d <20  && r > 75){
    		return 4;
    	}else if((d>20 && d < 60)&& r > 100 ){
    		return 3;
    	}else if(mostD(battle).getTitans().size() <= 1){
    		return 2;
    	}else{
    		return 1;
    	}
    }
    
    public void auto(Battle battle ,Label Turn, Label Score, Label Resources, Pane[] lanes, HealthBar[] bars, Label BattlePhase, Label[] Danger, Rectangle[] laneback){
    	
    	int res = battle.getResourcesGathered();
    	if (res<25){
    		battle.passTurn();
    	}else{
    		try {
				battle.purchaseWeapon(bestW(battle), mostD(battle));
			} catch (InsufficientResourcesException | InvalidLaneException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 
    	}
    	update(Turn,Score, Resources,battle, lanes, bars, BattlePhase,Danger,laneback);
    	
    }

    
    // we made this to make it easier to create the buttons without more lines of code
    public Button createButton(String text, double x, double y) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", 24));
        button.setTranslateX(x);
        button.setTranslateY(y);
        return button;
    }
    // we made this to make it easier to create the labels without more lines of code
    public Label createLabel(String text, double x, double y) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 24));
        label.setTranslateX(x);
        label.setTranslateY(y);
        return label;
    }
    
    public Pane createLane(double x, double y, double width, double height, Color color) {
        Pane lanePane = new Pane();
        lanePane.setLayoutX(x);
        lanePane.setLayoutY(y);
        lanePane.setPrefSize(width, height);

      
      

        return lanePane;
    }
    
    // this is the instruction popup logic
    public void showInstructionsPopup(Stage primaryStage, Button instructionsButton) {
        Popup popup = new Popup();
        popup.centerOnScreen();
        VBox popupContent = new VBox();
        popupContent.getStyleClass().add("custom-vbox");
        popupContent.setMinHeight(300);
        popupContent.setMinWidth(300);
        Label instructionsLabel1 = new Label("Tower Defense Game Instructions:");
        Label instructionsLabel2 = new Label("1. Objective: Prevent titans from reaching your wall in each lane.");
        Label instructionsLabel3 = new Label("2. Scoring: Earn points for each titan killed.");
        Label instructionsLabel4 = new Label("3. Resource Management: Use resources to buy weapons.");
        Label instructionsLabel5 = new Label("4. Resource Acquisition: Obtain resources by killing titans.");
        Label instructionsLabel6 = new Label("5. Survival: Aim to survive for as long as possible.");
        Label instructionsLabel7 = new Label("6. Dedicate your Heart!!");
        instructionsLabel1.getStyleClass().add("custom-vbox-label");
        instructionsLabel2.getStyleClass().add("custom-vbox-label");
        instructionsLabel3.getStyleClass().add("custom-vbox-label");
        instructionsLabel4.getStyleClass().add("custom-vbox-label");
        instructionsLabel5.getStyleClass().add("custom-vbox-label");
        instructionsLabel6.getStyleClass().add("custom-vbox-label");
        instructionsLabel7.getStyleClass().add("custom-vbox-label");
        popupContent.getChildren().addAll(instructionsLabel1, instructionsLabel2, instructionsLabel3 , instructionsLabel4, instructionsLabel5, instructionsLabel6,instructionsLabel7);
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
        popup.setX(585);
        popup.setY(390);
        popup.show(primaryStage);
    }
    public void showLanesPopup(Stage primaryStage, int weaponcode, Battle battle, Label turn, Label score, Label resources, Battle battle2, Pane[] lanes, Scene mainScene, HealthBar[] bar,Label BattlePhase,Label[] Danger, Rectangle[] laneback,MediaPlayer mp) {
        Popup popup = new Popup();
        popup.setX(810);
        popup.setY(390);
        VBox popupContent = new VBox();
        popupContent.setMinSize(300, 300);
        popupContent.getStyleClass().add("custom-vbox");
        Button lane1 = createButton("lane 1",0,0);
        lane1.getStyleClass().add("custom-button");
        Button lane2 = createButton("lane 2",0,10);
        lane2.getStyleClass().add("custom-button");
        Button lane3 = createButton("lane 3",0,20);
        lane3.getStyleClass().add("custom-button");
        lane1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(0));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        lane2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(1));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        lane3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(2));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        popupContent.getChildren().addAll(lane1,lane2,lane3);
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
     
        popup.show(primaryStage);
    }
    public void showLanesPopupH(Stage primaryStage, int weaponcode, Battle battle, Label turn, Label score, Label resources, Battle battle2, Pane[] lanes, Scene mainScene, HealthBar[] bar, Label BattlePhase, Label[] Danger, Rectangle[] laneback,MediaPlayer mp) {
        Popup popup = new Popup();
        popup.setX(685);
        popup.setY(290);
        VBox popupContent = new VBox();
        popupContent.setMinSize(500, 500);
        popupContent.getStyleClass().add("custom-vbox");
        Button lane1 = createButton("lane 1",0,0);
        lane1.getStyleClass().add("custom-button");
        Button lane2 = createButton("lane 2",0,10);
        lane2.getStyleClass().add("custom-button");
        Button lane3 = createButton("lane 3",0,20);
        lane3.getStyleClass().add("custom-button");
        Button lane4 = createButton("lane 4",0,30);
        lane4.getStyleClass().add("custom-button");
        Button lane5 = createButton("lane 5",0,40);
        lane5.getStyleClass().add("custom-button");
        lane1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(0));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        lane2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(1));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        lane3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(2));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        lane4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(3));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        lane5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
					battle.purchaseWeapon(weaponcode, battle.getOriginalLanes().get(4));
				} catch (InsufficientResourcesException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "You dont have enough money D:");
				} catch (InvalidLaneException e) {
					// TODO Auto-generated catch block
					errorMessage(primaryStage, "This Lane is lost solider!");
				}
                update(turn,score, resources,battle, lanes,bar,BattlePhase,Danger,laneback);
                if(battle.isGameOver())GameOver(primaryStage,battle,mainScene,mp);
                popup.hide();
            }
        });
        popupContent.getChildren().addAll(lane1,lane2,lane3,lane4,lane5);
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
        popup.show(primaryStage);
    }
    //function in order to play any background music
    public MediaPlayer BackgroundMusic(String Path){
    	Media song = new Media(new File(Path).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.5);
        return mediaPlayer;
    }
    //function in order to set any background image
    public ImageView BackgroundMaker(Scene main, String Path) throws FileNotFoundException{
    	Image Image = new Image(new FileInputStream(Path));
    	ImageView backgroundImage = new ImageView(Image);
    	backgroundImage.fitWidthProperty().bind(main.widthProperty()); 
    	backgroundImage.fitHeightProperty().bind(main.heightProperty());
    	return backgroundImage;
    }
    public ImageView WeaponMaker(String Path) throws FileNotFoundException{
    	Image Image = new Image(new FileInputStream(Path));
    	ImageView backgroundImage = new ImageView(Image);
    	return backgroundImage;
    }
    
    public void errorMessage(Stage Stage,String massage){
    	Popup popup = new Popup();
    	popup.setX(785);
        popup.setY(450);
        VBox popupContent = new VBox();
        popupContent.getStyleClass().add("custom-vbox");
        Label L = new Label(massage);
        L.getStyleClass().add("custom-vbox-label");
        popupContent.getChildren().add(L);
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
        popup.show(Stage);
    }
    public void GameOver(Stage stage, Battle battle , Scene mainMenu, MediaPlayer mp){
    	Popup popup = new Popup();
        VBox popupContent = new VBox();
        popupContent.getStyleClass().add("custom-vbox");
        Label L = new Label("You lost!");
        L.getStyleClass().add("custom-vbox-label");
        Label score = new Label("You finished with score: "+ battle.getScore());
        score.getStyleClass().add("custom-vbox-label");
        Button Return = new Button("Return to main menu");
        Return.getStyleClass().add("custom-button");
        Return.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                popup.hide();
                popup.hide();
                mp.stop();
            	try {
					start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        popup.setX(685);
        popup.setY(390);
        popupContent.getChildren().addAll(L, score , Return);
        popup.getContent().add(popupContent);
        popup.setAutoHide(false);
        popup.show(stage);
    }
    class HealthBar extends StackPane {
        private Rectangle healthRectangle;

        public HealthBar() {
         
            healthRectangle = new Rectangle(10, 100, Color.GREEN); 
            getChildren().add(healthRectangle);
        }

        public void setHealth(double healthPercentage) {
          
            healthRectangle.setHeight(healthPercentage * 100);
            if (healthPercentage > 0.5) {
                healthRectangle.setFill(Color.GREEN);
            } else if (healthPercentage > 0.2) {
                healthRectangle.setFill(Color.YELLOW);
            } else {
                healthRectangle.setFill(Color.RED);
            }
        }
    }
    class tHealthBar extends StackPane {
        private Rectangle healthRectangle;

        public tHealthBar() {
         
            healthRectangle = new Rectangle(3, 5, Color.GREEN); 
            getChildren().add(healthRectangle);
        }

        public void setHealth(double healthPercentage) {
          
            healthRectangle.setWidth(healthPercentage * 100);
            if (healthPercentage > 0.5) {
                healthRectangle.setFill(Color.GREEN);
            } else if (healthPercentage > 0.2) {
                healthRectangle.setFill(Color.YELLOW);
            } else {
                healthRectangle.setFill(Color.RED);
            }
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
