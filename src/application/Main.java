package application;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;

import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;

import javafx.geometry.*;

import javafx.scene.input.KeyCode;

public class Main extends Application {

	public static void main(String[] args) {

		launch(args); 
		
	}

	@Override

	public void start(Stage primaryStage) {

		options(primaryStage); 
		generateMaze(); 
		showMaze(); 
		gameProcess(primaryStage); 
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	int height = 11;
	int width = 15;
	
	int SizeBlock = 16;
	
	enum GameObject {

		HALL, WALL, CHAR, CASH, ENEMY 

	};

	GameObject[][] maze = new GameObject[height][width];

	ImageView[][] images = new ImageView[height][width]; 
	
	Image hall = new Image("/img/hall.png");

	Image wall = new Image("/img/wall.png");

	Image character = new Image("/img/char.png");

	Image cash = new Image("/img/cash.png");

	Image enemy = new Image("/img/enemy.png");

	GridPane layout; 
	Stage stage; 
	Scene scene; 
	
	Random r = new Random();

	int courentX = 1;
	int courentY = 1;

	int smileX = 1;
	int smileY = 1; 
	
	Stack<Point2D> stack = new Stack<>();

	ArrayList<Point2D> neighbours = new ArrayList<>();

	ArrayList<Point2D> visited = new ArrayList<>();
	
	public void clear() {
		stack.clear();
		neighbours.clear();
		visited.clear();
		courentX = 1;
		courentY = 1;
		smileX = 1;
		smileY = 1;
	}

	public void options(Stage primaryStage) {

		stage = primaryStage;

		stage.setTitle("Java FX Maze"); 

		stage.setResizable(false); 

		stage.getIcons().add(character); 

		////////////////////////////////////////////////////////////////////////

		layout = new GridPane();

		layout.setPadding(new Insets(5, 5, 5, 5)); 

		layout.setStyle("-fx-background-color: rgb(92, 118, 137);");

		for (int i = 0; i < height; i++) {

			RowConstraints rowConst = new RowConstraints();

			rowConst.setPercentHeight(100.0 / height);

			layout.getRowConstraints().add(rowConst);

		}

		for (int i = 0; i < width; i++) {

			ColumnConstraints colConst = new ColumnConstraints();

			colConst.setPercentWidth(100.0 / width);

			layout.getColumnConstraints().add(colConst);

		}

		////////////////////////////////////////////////////////////////////////

	
		scene = new Scene(layout, SizeBlock * width, SizeBlock * height); 

		stage.setScene(scene);

		////////////////////////////////////////////////////////////////////////

	}
	
	public int getRandomCoor (int length) {
		int x = length/2 + (int)( Math.random() * (length/2));
		if(x%2 != 0)
			return x;
		else
			return getRandomCoor ( length);
	}

	public void generateMaze() {

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if ((i % 2 != 0 && j % 2 != 0) && 
						(i < height - 1 && j < width - 1)) { 
					maze[i][j] = GameObject.HALL;
					} 
				else
					maze[i][j] = GameObject.WALL;

				if (j == smileX && i == smileY) {
					maze[i][j] = GameObject.CHAR;
				}
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////
		
		maze[getRandomCoor(height)][getRandomCoor(width)] = GameObject.CASH;

		Point2D CourentPoint = new Point2D(courentX, courentY);
		stack.add(CourentPoint);
		visited.add(CourentPoint);
		
		//////////////////////////////////////////////////////////////////////////////////
		
		do {
			Neighbours neighbours = new Neighbours(CourentPoint, width, height, visited);
			this.neighbours = neighbours.getNeighbours();
			
			if (this.neighbours.size() != 0) {
				
				int randNum = (int) (Math.random() * this.neighbours.size());
				
				removeWall(CourentPoint, this.neighbours.get(randNum));
				
				CourentPoint = this.neighbours.get(randNum);
				
				visited.add(this.neighbours.get(randNum));
				
				stack.add(this.neighbours.get(randNum));
				
			} else if (stack.size() > 0) {
				
				stack.remove(stack.lastElement());
				
				CourentPoint = stack.lastElement();
				
			}
			
		} while (unvisitedCount() > 0);

	}

	public int unvisitedCount() {
		return ((width - 1) / 2) * ((height - 1) / 2) - visited.size();
	}

	public void removeWall(Point2D p1, Point2D p2) {
		
		int x = (int) Math.abs((p1.getX() + p2.getX()) / 2);
		int y = (int) Math.abs((p1.getY() + p2.getY()) / 2);

		maze[y][x] = GameObject.HALL;

		layout.getChildren().remove(images[y][x]);

		images[y][x] = new ImageView(hall);

		layout.add(images[y][x], x, y);

	}

	public void showMaze() {

		Image current;

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				switch (maze[y][x]) {

				case HALL:

					current = hall;

					break;

				case WALL:

					current = wall;

					break;

				case CHAR:

					current = character;

					break;

				case CASH:

					current = cash;

					break;

				default:

					current = enemy;

					break;

				}

				images[y][x] = new ImageView(current);

				layout.add(images[y][x], x, y);


			}

		}

		stage.show();

	}

	public void clearCell(int x, int y) {

		maze[y][x] = GameObject.HALL; 

		layout.getChildren().remove(images[y][x]);

		images[y][x] = new ImageView(hall);

		layout.add(images[y][x], x, y);

	}

	public void setSmile(int x, int y) {

		maze[y][x] = GameObject.CHAR;

		layout.getChildren().remove(images[y][x]);

		images[y][x] = new ImageView(character);

		layout.add(images[y][x], x, y);

	}

	public void gameProcess(Stage primaryStage) {

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override

			public void handle(KeyEvent event) {

				clearCell(smileX, smileY);

				if (event.getCode() == KeyCode.RIGHT && maze[smileY][smileX + 1] != GameObject.WALL) {

					smileX++;

				} else if (event.getCode() == KeyCode.LEFT && smileX > 0
						&& maze[smileY][smileX - 1] != GameObject.WALL) {

					smileX--;

				}

				if (event.getCode() == KeyCode.DOWN && maze[smileY + 1][smileX] != GameObject.WALL) {

					smileY++;

				} else if (event.getCode() == KeyCode.UP && smileY > 0 && maze[smileY - 1][smileX] != GameObject.WALL) {

					smileY--;

				}

				///////////////////////////////////////////////////////////////////////////////////
				
				if(maze[smileY][smileX] == GameObject.CASH) {
					Label secondLabel = new Label("Viktory!!!");
					secondLabel.setFont(new Font(50));
					Pane secondaryLayout = new Pane();
					secondaryLayout.getChildren().add(secondLabel);
					
					

					Scene secondScene = new Scene(secondaryLayout, 200, 100);

					Button btn = new Button("Next Lvl");
					btn.setLayoutX(50);btn.setLayoutY(75);
					btn.setPrefSize(100, 20);
					secondaryLayout.getChildren().add(btn);
					
					
					Stage newWindow = new Stage();
					newWindow.setTitle("Viktory!!!");
					newWindow.setScene(secondScene);

					btn.setOnAction(e->{
						newWindow.close();						
						NextLvl(primaryStage);
					});
					
					
					newWindow.initModality(Modality.WINDOW_MODAL);
					newWindow.setResizable(false);
					newWindow.setOnCloseRequest(e->{
						restart(primaryStage);
					});
					
					newWindow.initOwner(primaryStage);

					newWindow.setX(primaryStage.getX() + 200);
					newWindow.setY(primaryStage.getY() + 100);

					newWindow.show();
				}
				setSmile(smileX, smileY);

			}

		});

	}
	

	
	public void NextLvl(Stage stage) {
		clear();
		height += 2;
		width += 2;
		maze = new GameObject[height][width];
		images = new ImageView[height][width];
		start(stage);
	}
	
	public void restart(Stage stage) {
		clear();
		start(stage);
	}

}
