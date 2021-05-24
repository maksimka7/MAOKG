package com.company;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrintingImage extends Application{

	private HeaderBitmapImage image; // приватне поле, яке зберігає об'єкт з інформацією про заголовок зображення
	private int numberOfPixels; // приватне поле для збереження кількості пікселів з чорним кольором
	
	public PrintingImage()
	{}
	
	public PrintingImage(HeaderBitmapImage image) // перевизначений стандартний конструктор
	{
		this.image = image;
	}

	private Path readingBitmap(Stage primaryStage, Group root) throws Exception{
		ReadingImageFromFile.loadBitmapImage("C:/Users/Hanna/Desktop/sulema/lab3/sources/mytrajectory.bmp");
		this.image = ReadingImageFromFile.pr.image;
		int width = (int)this.image.getWidth();
		int height = (int)this.image.getHeight();
		int half = (int)image.getHalfOfWidth();

		Scene scene = new Scene (root, 900, 700);
		scene.setFill(Color.WHITE);
		Circle cir;

		int let = 0;
		int let1 = 0;
		int let2 = 0;
		char[][] map = new char[width][height];
		// виконуємо зчитування даних про пікселі
		BufferedInputStream reader = new BufferedInputStream (new FileInputStream("pixels.txt"));


		for(int i=0;i<height;i++)     // поки не кінець зображення по висоті
		{
			for(int j=0;j<half;j++)         // поки не кінець зображення по довжині
			{
				let = reader.read();  // зчитуємо один символ з файлу
				let1=let;
				let2=let;
				let1=let1&(0xf0);   // старший байт - перший піксель
				let1=let1>>4;       // зсув на 4 розряди
				let2=let2&(0x0f);   // молодший байт - другий піксель
				if(j*2<width) // так як 1 символ кодує 2 пікселі нам необхідно пройти до середини ширини зображення
				{
					cir = new Circle ((j)*2,(height-1-i),1, Color.valueOf((returnPixelColor(let1))));
					// за допомогою стандартного
					// примітива Коло радіусом в 1 піксель та кольором визначеним за допомогою методу
					// returnPixelColor малюємо піксель
					//root.getChildren().add(cir); //додаємо об'єкт в сцену
					if (returnPixelColor(let1) == "BLACK") // якщо колір пікселя чорний, то ставимо в масиві 1
					{
						map[j*2][height-1-i] = '1';
						numberOfPixels++; // збільшуємо кількість чорних пікселів
					}
					else
					{
						map[j*2][height-1-i] = '0';
					}
				}
				if(j*2+1<width) // для другого пікселя
				{
					cir = new Circle ((j)*2+1,(height-1-i),1,Color.valueOf((returnPixelColor(let2))));
					//root.getChildren().add(cir);
					if (returnPixelColor(let2) == "BLACK")
					{
						map[j*2+1][height-1-i] = '1';
						numberOfPixels++;
					}
					else
					{
						map[j*2+1][height-1-i] = '0';
					}
				}
			}
		}
		primaryStage.setScene(scene); // ініціалізуємо сцену
		primaryStage.show(); // візуалізуємо сцену

		reader.close();

		int[][] black;
		black = new int[numberOfPixels][2];
		int lich = 0;

		BufferedOutputStream writer = new BufferedOutputStream (new FileOutputStream("map.txt"));
		// записуємо карту для руху по траекторії в файл
		for(int i=0;i<height;i++)     // поки не кінець зображення по висоті
		{
			for(int j=0;j<width;j++)         // поки не кінець зображення по довжині
			{
				if (map[j][i] == '1')
				{
					black[lich][0] = j;
					black[lich][1] = i;
					lich++;
				}
				writer.write(map[j][i]);
			}
			writer.write(10);
		}
		writer.close();

		System.out.println("number of black color pixels = " + numberOfPixels);

		Path path2 = new Path();
		for (int l=0; l<numberOfPixels-1; l++)
		{
			path2.getElements().addAll(
					new MoveTo(black[l][0],black[l][1]),
					new LineTo (black[l+1][0],black[l+1][1])
			);
		}
		return path2;
	}


	private void treeDrawing(Group root){
		// prop drawing
		Rectangle prop_under = new Rectangle(330, 620, 140, 75);
		prop_under.setArcHeight(68);
		prop_under.setArcWidth(140);
		prop_under.setFill(Paint.valueOf("#D2B48C"));
		prop_under.setStroke(Color.BLACK);
		prop_under.setStrokeWidth(3);
		root.getChildren().add(prop_under);

		Ellipse prop = new Ellipse(400, 650, 70, 35);
		prop.setFill(Paint.valueOf("#DEB887"));
		prop.setStroke(Color.BLACK);
		prop.setStrokeWidth(3);
		root.getChildren().add(prop);

		Arc prop_light_up_arc = new Arc(400, 650, 68, 33, 100, 170);
		prop_light_up_arc.setType(ArcType.ROUND);
		prop_light_up_arc.setFill(Paint.valueOf("#F5DEB3"));
		Arc prop_light_down_arc = new Arc(400, 650, 45, 33, 100, 170);
		prop_light_down_arc.setType(ArcType.ROUND);
		prop_light_down_arc.setFill(Paint.valueOf("#DEB887"));
		root.getChildren().addAll(prop_light_up_arc, prop_light_down_arc);

		// trunk drawing
		Rectangle trunk = new Rectangle(370, 575, 60, 85);
		trunk.setArcHeight(30);
		trunk.setArcWidth(80);
		trunk.setFill(Color.BROWN);
		trunk.setStroke(Color.BLACK);
		trunk.setStrokeWidth(3);
		root.getChildren().add(trunk);

		// spruce spines drawing
		Arc arc_down_body = new Arc(400, 345, 300, 250, 227, 86);
		arc_down_body.setType(ArcType.ROUND);
		arc_down_body.setStroke(Color.BLACK);
		arc_down_body.setFill(Color.GREEN);
		arc_down_body.setStrokeWidth(3);
		Arc arc_middle_body = new Arc(400, 245, 250, 225, 230, 80);
		arc_middle_body.setType(ArcType.ROUND);
		arc_middle_body.setStroke(Color.BLACK);
		arc_middle_body.setFill(Color.GREEN);
		arc_middle_body.setStrokeWidth(3);
		Arc arc_up_body = new Arc(400, 145, 250, 200, 240, 60);
		arc_up_body.setType(ArcType.ROUND);
		arc_up_body.setStroke(Color.BLACK);
		arc_up_body.setFill(Color.GREEN);
		arc_up_body.setStrokeWidth(3);
		root.getChildren().addAll(arc_down_body, arc_middle_body, arc_up_body);
	}

	private Polygon starDrawing(Group root){
		// star drawing
		Polygon star  = new Polygon();
		star.getPoints().addAll(new Double[]{390.0, 115.0 ,
				400.0, 89.0 ,  410.0, 115.0 ,
				440.0, 119.0 ,  420.0, 135.0 ,
				424.0, 165.0 ,  400.0, 145.0 ,
				376.0, 165.0 , 380.0, 135.0,  360.0, 119.0 });
		root.getChildren().add(star);
		star.setStroke(Color.BLACK);
		star.setStrokeWidth(3);
		star.setFill(Color.RED);
		return star;
	}

	private Circle[] ballsDraw(Group root){
		//balls paint
		int balls_amount = 21;
		Circle [] balls = new Circle[balls_amount];
		Arc [] balls_light = new Arc[balls_amount];
		for (int i=0; i<balls.length; i++){
			balls[i] = new Circle(0, 0, 18);
			balls[i].setStroke(Color.BLACK);
			balls[i].setStrokeWidth(3);
			if (0 <= i && i < 3){
				balls[i].setFill(Color.BLUE);
			}else if(3 <= i && i < 6){
				balls[i].setFill(Color.ORANGE);
			}else if(6 <= i && i < 9){
				balls[i].setFill(Paint.valueOf("#00FF00"));
			}else if(9 <= i && i < 12){
				balls[i].setFill(Color.YELLOW);
			}else if(12 <= i && i < 15){
				balls[i].setFill(Color.PURPLE);
			}else if(15 <= i && i < 19){
				balls[i].setFill(Color.RED);
			}else if(19 <= i && i < 21){
				balls[i].setFill(Color.CYAN);
			}
			balls_light[i] = new Arc(100, 100, 15, 15, 90, 135);
			balls_light[i].setType(ArcType.ROUND);
			balls_light[i].setStroke(Color.WHITE);
			balls_light[i].setFill(Color.WHITE);
			balls_light[i].setOpacity(0.5);
		}
		// blue balls
		setBallCoords(balls[0], 430, 245, balls_light[0]);
		setBallCoords(balls[1], 470, 455, balls_light[1]);
		setBallCoords(balls[2], 260, 500, balls_light[2]);
		// orange balls
		setBallCoords(balls[3], 370, 269, balls_light[3]);
		setBallCoords(balls[4], 410, 455, balls_light[4]);
		setBallCoords(balls[5], 405, 585, balls_light[5]);
		// lime balls
		setBallCoords(balls[6], 350, 225, balls_light[6]);
		setBallCoords(balls[7], 355, 465, balls_light[7]);
		setBallCoords(balls[8], 280, 568, balls_light[8]);
		// yellow balls
		setBallCoords(balls[9], 430, 395, balls_light[9]);
		setBallCoords(balls[10], 330, 505, balls_light[10]);
		setBallCoords(balls[11], 510, 495, balls_light[11]);
		// purple balls
		setBallCoords(balls[12], 330, 325, balls_light[12]);
		setBallCoords(balls[13], 500, 365, balls_light[13]);
		setBallCoords(balls[14], 420, 525, balls_light[14]);
		// red balls
		setBallCoords(balls[15], 465, 305, balls_light[15]);
		setBallCoords(balls[16], 305, 425, balls_light[16]);
		setBallCoords(balls[17], 340, 565, balls_light[17]);
		setBallCoords(balls[18], 545, 535, balls_light[18]);
		// cyan balls
		setBallCoords(balls[19], 380, 340, balls_light[19]);
		setBallCoords(balls[20], 466, 578, balls_light[20]);

		for (int i=0; i<balls.length; i++){
			root.getChildren().addAll(balls[i], balls_light[i]);
		}

		return balls;
	}

	private void ballsTransition(Circle[] balls, Polygon star){
		ScaleTransition scaleTransition1 =
				new ScaleTransition(Duration.seconds(5.0), balls[0]);
		scaleTransition1.setToX(1.3f);
		scaleTransition1.setToY(1.3f);

		ScaleTransition scaleTransition2 =
				new ScaleTransition(Duration.seconds(5.0), balls[1]);
		scaleTransition2.setToX(1.3f);
		scaleTransition2.setToY(1.3f);

		ScaleTransition scaleTransition3 =
				new ScaleTransition(Duration.seconds(5.0), balls[2]);
		scaleTransition3.setToX(1.3f);
		scaleTransition3.setToY(1.3f);

		ScaleTransition scaleTransition4 =
				new ScaleTransition(Duration.seconds(5.0), balls[12]);
		scaleTransition4.setToX(1.3f);
		scaleTransition4.setToY(1.3f);

		ScaleTransition scaleTransition5 =
				new ScaleTransition(Duration.seconds(5.0), balls[13]);
		scaleTransition5.setToX(1.3f);
		scaleTransition5.setToY(1.3f);

		ScaleTransition scaleTransition6 =
				new ScaleTransition(Duration.seconds(5.0), balls[14]);
		scaleTransition6.setToX(1.3f);
		scaleTransition6.setToY(1.3f);

		RotateTransition rotForStar =
				new RotateTransition(Duration.millis(300), star);
		rotForStar.setByAngle(180f);
		rotForStar.setCycleCount(20);
		rotForStar.setAutoReverse(true);

		ParallelTransition parallelTransition =
				new ParallelTransition();
		parallelTransition.getChildren().addAll(
				scaleTransition1,
				scaleTransition2,
				scaleTransition3,
				scaleTransition4,
				scaleTransition5,
				scaleTransition6,
				rotForStar
		);

		parallelTransition.play();
	}

	@Override
	public void start(Stage primaryStage)  throws Exception{

		primaryStage.setTitle("Lab3 Arnautova");

		Group root = new Group();
		Path path2 = readingBitmap(primaryStage, root);

		treeDrawing(root);
		Polygon star = starDrawing(root);

		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(500));
		pathTransition.setPath(path2);
		pathTransition.setNode(star);
		pathTransition.play();

		Circle[] balls = ballsDraw(root);

		ballsTransition(balls, star);
	}

	private void setBallCoords(Circle cir, int x, int y, Arc arc) {
		cir.setCenterX(x);
		cir.setCenterY(y);
		arc.setCenterX(x);
		arc.setCenterY(y);
	}

	private String returnPixelColor (int color) // метод для співставлення кольорів 16-бітного зображення
	{
		String col = "BLACK";
		switch(color)
		   {
		      case 0: return "BLACK";     //BLACK;
		      case 1: return "LIGHTCORAL";  //LIGHTCORAL;
		      case 2: return "GREEN";     //GREEN
		      case 3: return "BROWN";     //BROWN
		      case 4: return "BLUE";      //BLUE;
		      case 5: return "MAGENTA";   //MAGENTA;
		      case 6: return "CYAN";      //CYAN;
		      case 7: return "LIGHTGRAY"; //LIGHTGRAY;
		      case 8: return "DARKGRAY";  //DARKGRAY;
		      case 9: return "RED";       //RED;
		      case 10:return "LIGHTGREEN";//LIGHTGREEN
		      case 11:return "YELLOW";    //YELLOW;
		      case 12:return "LIGHTBLUE"; //LIGHTBLUE;
		      case 13:return "LIGHTPINK";    //LIGHTMAGENTA
		      case 14:return "LIGHTCYAN";    //LIGHTCYAN;
		      case 15:return "WHITE";    //WHITE;
		   }
		   return col;
	}
		
	public static void main (String args[]) 
	{
	   launch(args);
	}

}
