package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class Main extends Application {

	static ArrayList<College> Colleges;
	static College sourceCollege = null;
	static College destinationCollege = null;
	Pane root = new Pane();
	ComboBox<Label> source = new ComboBox<Label>();
	ComboBox<Label> Target = new ComboBox<Label>();
	static float mapHieght = 715;
	static float mapWidth = 1200;

	@Override
	public void start(Stage stage) throws FileNotFoundException {
		Stage primaryStage = new Stage();

		Scene scene = new Scene(root, 1580, 715);
		primaryStage.setTitle("Birzeit University Map");
		root.setStyle("-fx-background-color:#ced6e0;\r\n");
		initialize();
		Label names[] = new Label[Colleges.size()];
		Label s = new Label("Source:");
		s.setFont(new Font(30));
		s.setTextFill(Color.BLACK);
		Label d = new Label("Target:");
		d.setFont(new Font(30));
		d.setTextFill(Color.BLACK);
		source.setStyle("-fx-background-color: #dfe4ea;\r\n");
		Target.setStyle("-fx-background-color: #dfe4ea;\r\n");
		for (int i = 0, j = 0; i < names.length; i++, j++) {
			names[i] = new Label();
			names[i].setFont(new Font(20));
			names[i].setTextFill(Color.BLACK);
			names[i].setText(Colleges.get(i).name);
			source.getItems().add(names[i]);
			names[j] = new Label();
			names[j].setFont(new Font(20));
			names[j].setTextFill(Color.BLACK);
			names[j].setText(Colleges.get(j).name);
			Target.getItems().add(names[j]);
		}
		source.setTranslateX(1315);
		source.setTranslateY(50);
		source.setPrefSize(180, 50);
		Target.setTranslateX(1315);
		Target.setTranslateY(150);
		Target.setPrefSize(180, 50);
		s.setTranslateX(1200);
		s.setTranslateY(50);
		d.setTranslateX(1200);
		d.setTranslateY(150);

		source.setOnAction(e -> {
			sourceCollege = Dijkstra.allNodes.get(source.getValue().getText());
			if (sourceCollege != null) {
				sourceCollege.getTest()
						.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});
		Target.setOnAction(i -> {
			destinationCollege = Dijkstra.allNodes.get(Target.getValue().getText());
			if (destinationCollege != null) {
				destinationCollege.getTest()
						.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});

		Image image10 = new Image("https://img.icons8.com/?size=512&id=s1zBglnEKEfn&format=png");
		ImageView imageView10 = new ImageView();
		imageView10.setImage(image10);
		Button run = new Button("Run", imageView10);
		run.setStyle("-fx-background-color:transparent;");
		imageView10.setFitHeight(40);
		imageView10.setFitWidth(40);
		run.setFont(new Font(30));
		run.setTranslateX(1290);
		run.setTranslateY(220);
		run.setMinWidth(170);
		run.setMinHeight(80);
		run.setAlignment(Pos.CENTER);
		run.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(25), Insets.EMPTY)));

		DropShadow shadow = new DropShadow();

		run.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent eh) -> {
			run.setEffect(shadow);
		});

		run.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent ej) -> {
			run.setEffect(null);
		});

		TextArea path = new TextArea();
		path.setTranslateX(1280);
		path.setTranslateY(320);
		path.setMinSize(270, 220);
		path.setMaxSize(270, 220);
		path.setEditable(false);
		path.setStyle("-fx-background-color: #dfe4ea;");

		Label p = new Label("Path:");
		p.setFont(new Font(30));
		p.setTranslateX(1210);
		p.setTranslateY(320);
		p.setTextFill(Color.BLACK);

		TextField t1 = new TextField();
		t1.setTranslateX(1350);
		t1.setTranslateY(570);
		t1.setPrefSize(190, 50);
		t1.setEditable(false);
		t1.setFont(new Font(20));
		t1.setStyle("-fx-background-color: #dfe4ea;");

		Label t = new Label("Distance:");
		t.setFont(new Font(30));
		t.setTranslateX(1200);
		t.setTranslateY(570);
		t.setTextFill(Color.BLACK);

		run.setOnAction(e -> {
			int v = 0, w = 0;
			for (int i = 0; i < Colleges.size(); i++) {
				if (sourceCollege.getFullName().equals(Colleges.get(i).getFullName()))
					v = i;
				if (destinationCollege.getFullName().equals(Colleges.get(i).getFullName()))
					w = i;
			}
			if (sourceCollege != null && destinationCollege != null) {
				Dijkstra graph = new Dijkstra(Colleges, Colleges.get(v), Colleges.get(w));
				graph.generateDijkstra();
				drawPathOnMap(graph.pathTo(Colleges.get(w)));
				root.getChildren().add(group);
				path.setText(graph.getPathString());
				t1.setText(graph.distanceString + " M");
			}
		});

		Image image11 = new Image("https://img.icons8.com/?size=512&id=115647&format=png");
		ImageView imageView11 = new ImageView();
		imageView11.setImage(image11);
		Button reset = new Button("", imageView11);
		reset.setStyle("-fx-background-color:transparent;");
		imageView11.setFitHeight(40);
		imageView11.setFitWidth(40);
		reset.setPrefSize(150, 60);
		reset.setAlignment(Pos.CENTER);
		reset.setTranslateX(1330);
		reset.setTranslateY(640);
		reset.setFont(new Font(30));

		DropShadow shadow1 = new DropShadow();

		reset.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent eh) -> {
			reset.setEffect(shadow1);
		});

		reset.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent ej) -> {
			reset.setEffect(null);
		});

		reset.setOnAction(action -> {
			sourceCollege.getTest()
					.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			destinationCollege.getTest()
					.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			sourceCollege = new College();
			destinationCollege = new College();
			group.getChildren().clear();
			root.getChildren().remove(group);
			source.setValue(new Label(""));
			Target.setValue(new Label(""));
			path.setText(null);
			t1.setText(null);
		});

		root.getChildren().addAll(source, Target, run, path, t1, s, d, reset, p, t);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void initialize() {
		Image image1 = new Image("C:\\Users\\User\\Desktop\\Snd Semester\\COMP336 - Algorithms\\Project_3\\src\\application\\birzeit.jpg");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitHeight(mapHieght);
		imageView1.setFitWidth(mapWidth);
		imageView1.setVisible(true);
		root.getChildren().add(imageView1);
		for (int i = 0; i < Colleges.size(); i++) {

			Image buttonImage = new Image("C:\\Users\\User\\Desktop\\Snd Semester\\COMP336 - Algorithms\\Project_3\\src\\application");
			ImageView buttonImageView = new ImageView(buttonImage);
			buttonImageView.setFitWidth(10);
			buttonImageView.setFitHeight(10);
			buttonImageView.setPreserveRatio(true);

			Button b = new Button();
			b.setGraphic(buttonImageView);
			Colleges.get(i).setTest(b);
			b.setUserData(Colleges.get(i));
			b.setTranslateX(getX(Colleges.get(i).x));
			b.setTranslateY(getY(Colleges.get(i).y));

			b.setMinWidth(10);
			b.setMinHeight(10);
			b.setMaxWidth(10);
			b.setMaxHeight(10);
			b.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			// The circle color before clicking on it
			b.setOnAction(event -> {
				b.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
				// The circle color after clicking on it
				if (sourceCollege == null) {
					sourceCollege = (College) b.getUserData();
					Label l = new Label();
					l.setFont(new Font(20));
					l.setTextFill(Color.BLACK);
					l.setText(sourceCollege.name);
					source.setValue(l);
				} else if (destinationCollege == null && sourceCollege != null) {
					destinationCollege = (College) b.getUserData();
					Label l = new Label();
					l.setFont(new Font(20));
					l.setTextFill(Color.BLACK);
					l.setText(destinationCollege.name);
					Target.setValue(l);
				}
			});

			Label lb = new Label(Colleges.get(i).name);
			lb.setFont(new Font(10));
			lb.setTextFill(Color.BLACK);
			// The color of the names of colleges and buildings
			lb.setTranslateX(getX(Colleges.get(i).x));
			lb.setTranslateY(getY(Colleges.get(i).y) - 10);

			root.getChildren().add(b);
			root.getChildren().add(lb);
		}

	}

	Group group = new Group();

	private void drawPathOnMap(College[] faculties) {
		for (int i = 0; i < faculties.length - 1; i++) {
			Line line = new Line(getX(faculties[i].x) + 5, getY(faculties[i].y) + 5, getX(faculties[i + 1].x) + 5,
					getY(faculties[i + 1].y) + 5);
			line.setStroke(Color.BLACK);
			line.setStrokeWidth(2);
			group.getChildren().add(line);
		}

	}

	private float getX(float xCollege) {
		float div = mapWidth / 1200;
		return ((3.3334f * xCollege) - 45) * div + mapWidth / 2;
	}

	private float getY(float yCollege) {
		float div = mapHieght / 715;
		return ((-3.97222f * yCollege) - 22.5f) * div + mapHieght / 2;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Colleges = Dijkstra.readFile();

		launch(args);
	}
}
