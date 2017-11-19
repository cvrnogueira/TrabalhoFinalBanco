package dic.sentimentos;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class JanelaFX extends Application {

	private ComboBox<String> consulta04;
	private TextField consulta04Palavra;
	private static TextArea textArea;
	Window stage = null;

	Scanner scanner = new Scanner(System.in);
	HashTable table;
	InvertedIndex minusOne;
	InvertedIndex zero;
	InvertedIndex one;
	Integer tweetIndex;


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setup();
		textArea = new TextArea();

		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #1abc9c;");
		GridPane leftPane = new GridPane();
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(10);
		leftPane.setVgap(10);
		leftPane.setPadding(new Insets(10, 10, 10, 10));
		Button btnConsulta = new Button("Criar o dicionário");
		btnConsulta.setPrefWidth(250);
		btnConsulta.setStyle("-fx-background-color:#ecf0f1");
		leftPane.add(btnConsulta, 0, 1);
		Button btnConsulta2 = new Button("Adicionar mais tweets aos existentes");
		btnConsulta2.setPrefWidth(250);
		btnConsulta2.setStyle("-fx-background-color:#ecf0f1");
		leftPane.add(btnConsulta2, 0, 3);
		Button btnConsulta3 = new Button("Determinar a polaridade de Tweets novos");
		btnConsulta3.setPrefWidth(250);
		btnConsulta3.setStyle("-fx-background-color:#ecf0f1");
		leftPane.add(btnConsulta3, 0 , 5);
		Button btnConsulta4 = new Button("Funcionalidade adiconal");
		btnConsulta4.setPrefWidth(250);
		btnConsulta4.setStyle("-fx-background-color:#ecf0f1");
		leftPane.add(btnConsulta4, 0, 7);

		String[] options = { "Novo Arquivo", "Append no existente"};
		String[] options2 = { "-1", "0", "1"};


		btnConsulta.setOnAction(e -> {
			String selectedFileName = null;
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File selectedFile= fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				selectedFileName = selectedFile.getName();
				consulta01(selectedFileName);
			}
			else {
				fileChooser.setTitle("Erro ao selecionar o arquivo");
			}


		});
		//-------consulta 02
		btnConsulta2.setOnAction(e -> {
			String selectedFileName = null;
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File selectedFile= fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				selectedFileName = selectedFile.getName();
				consulta02(selectedFileName);
			}
			else {
				fileChooser.setTitle("Erro ao selecionar o arquivo");
			}
		});
		//-------consulta 03

		btnConsulta3.setOnAction(e -> {
			String selectedFileName = null;
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File selectedFile= fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				selectedFileName = selectedFile.getName();
				consulta03(selectedFileName);
			}
			else {
				fileChooser.setTitle("Erro ao selecionar o arquivo");
			}
		});
		//-------consulta 04

		consulta04 = new ComboBox<String>(FXCollections.observableArrayList(options2));
		leftPane.add(consulta04, 0, 8);
		consulta04.setStyle("-fx-background-color:#ecf0f1");
		consulta04.setPromptText("Polaridade");



		consulta04Palavra = new TextField("Palavra que deseja procurar");
		consulta04Palavra.setPrefWidth(200);
		consulta04Palavra.setStyle("-fx-background-color:#ecf0f1");
		leftPane.add(consulta04Palavra, 1, 7);
		btnConsulta4.setOnAction(e -> {
			consulta04();
		});

		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setSpacing(5);
		root.getChildren().add(new Label("Output consulta04:"));
		root.getChildren().add(textArea);
		
		pane.setLeft(leftPane);
		pane.setBottom(root);
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Dicionario de sentimentos");
		
		primaryStage.show();
	}

	// Initializing data here
	private void setup() throws IOException {

		scanner = new Scanner(System.in);
		table = new HashTable();
		minusOne = new InvertedIndex(-1);
		zero = new InvertedIndex(0);
		one = new InvertedIndex(1);
		tweetIndex = 0;
	}


	private void consulta01(String selectedFileName) {

		Path path1 = Paths.get(selectedFileName); //name of the file that user chooses is get here
		try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
			String line = null;
			int score = 0;
			String word = null;
			while ((line = reader.readLine()) != null) {
				Scanner sc = new Scanner(line).useDelimiter(","); //delimiter is a comma
				String finalLine = sc.next(); //line without the value
				score = sc.nextInt(); // value

				Scanner sc2 = new Scanner(finalLine).useDelimiter(" "); //identify all individual strings
				while(sc2.hasNext()){
					word = sc2.next().toLowerCase();
					word = Normalizer.normalize(word, Normalizer.Form.NFD);
					word = word.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
					word = word.toLowerCase(); //reforce this ideia
					if(word.length() > 2){
						table.put(word, score);
						switch(score){
						case -1: {
							minusOne.put(word, tweetIndex);
						}
						break;
						case 1: {
							one.put(word, tweetIndex);
						}
						break;
						case 0: {
							zero.put(word, tweetIndex);
						}
						break;
						default: {
							Stage dialogStage = new Stage();
							dialogStage.initModality(Modality.WINDOW_MODAL);
							VBox vbox = new VBox(new Text("Peso inválido!"));
							vbox.setAlignment(Pos.CENTER);
							vbox.setPadding(new Insets(46));
							dialogStage.setScene(new Scene(vbox));
							dialogStage.show();
						}
						}

						if (table.getValueFromKey(word).returnList() !=null){
							if(!(table.getValueFromKey(word).returnList().contains(tweetIndex))){
								table.getValueFromKey(word).addTweet(tweetIndex);
							}
						}
						else{
							table.getValueFromKey(word).addTweet(tweetIndex);
						}
					}
				}
				tweetIndex = tweetIndex + 1;
			} 
			
			String retorno = table.salvaEmArquivo("novo.txt");
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text(retorno));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(45));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
		}
		catch (IOException x) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Erro de E/S: " +  x));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(46));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
		}

	}

	private void consulta02(String selectedFile) {
			if(selectedFile != null){
				consulta01(selectedFile); //atualiza dicionario e salva no arquivo os novos
				Stage dialogStage = new Stage();
				dialogStage.initModality(Modality.WINDOW_MODAL);
				VBox vbox = new VBox(new Text("Dicionario atualizado e arquivo também"));
				vbox.setAlignment(Pos.CENTER);
				vbox.setPadding(new Insets(46));
				dialogStage.setScene(new Scene(vbox));
				dialogStage.show();
			}
		}

	public void consulta03(String selectedFileName) {

		Integer tweetIndex2 = 0;
		Path path3 = Paths.get(selectedFileName);
		Path path4 = Paths.get("polaridadeNova.csv");
		int soma = 0;
		try (BufferedReader reader = Files.newBufferedReader(path3, Charset.forName("utf8"))) {
			String line = null;
			String word = null;
			while ((line = reader.readLine()) != null) {
				Scanner sc = new Scanner(line).useDelimiter(" ");
				soma = 0;
				while(sc.hasNext()){
					word = sc.next().toLowerCase();
					word = Normalizer.normalize(word, Normalizer.Form.NFD);
					word = word.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
					if(word.length() > 2){
						if(table.getValueFromKey(word) != null ){
							soma +=table.getValueFromKey(word).getTotalScore();
						}
					}
				}
				if(tweetIndex2 == 0){
					try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path4, Charset.forName("utf8")))) {
						writer.format(" %s,%s%n",line, soma);
					}
					catch (IOException x) {
						Stage dialogStage = new Stage();
						dialogStage.initModality(Modality.WINDOW_MODAL);
						VBox vbox = new VBox(new Text("Erro de E/S: " +  x));
						vbox.setAlignment(Pos.CENTER);
						vbox.setPadding(new Insets(46));
						dialogStage.setScene(new Scene(vbox));
						dialogStage.show();
					}
				}
				else {
					try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path4, Charset.forName("utf8"), StandardOpenOption.APPEND))) {
						if(soma > 0.1){
							soma = 1;
						}
						else if(soma < -0.1){
							soma = -1;
						}
						else if(soma >= -0.1 && soma <= 0.1){
							soma = 0;
						}
						writer.format(" %s,%s%n",line, soma);
					}
					catch (IOException x) {
						Stage dialogStage = new Stage();
						dialogStage.initModality(Modality.WINDOW_MODAL);
						VBox vbox = new VBox(new Text("Erro de E/S: " +  x));
						vbox.setAlignment(Pos.CENTER);
						vbox.setPadding(new Insets(46));
						dialogStage.setScene(new Scene(vbox));
						dialogStage.show();
					}
				}
				tweetIndex2 = tweetIndex2 + 1;
			}
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Salvo em novo arquivo de nome polaridadeNova.csv!"));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(46));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();

		} 
		catch (IOException x) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Erro de E/S: " +  x));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(46));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
		}

	}


	public void consulta04() {
		String inputWord = consulta04Palavra.getText();
		textArea.setText(" ");
		String terminalInput =  (String) consulta04.getSelectionModel().getSelectedItem();

		LinkedList<Integer> tweets  = new LinkedList(); //vai armazenar a intersecao entre os indexes da palavra e os do escore

		if(terminalInput != null){
			int terminalInput2 = Integer.parseInt(terminalInput);
			inputWord = Normalizer.normalize(inputWord, Normalizer.Form.NFD);
			inputWord = inputWord.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
			inputWord = inputWord.toLowerCase();

			switch(terminalInput2){
			case -1: {
				if(minusOne.getValueFromKey(inputWord) == null){
					textArea.setText("Nao ha tweets com essa palavra " + inputWord + "nesse escore: -1");
				}
				else if(minusOne.getValueFromKey(inputWord).returnTweetsList() ==null){
					textArea.setText("Nao ha tweets com esse escore " + -1);
				}
				else{
					searchListOfTweets (minusOne.getValueFromKey(inputWord).returnTweetsList());
				}
			}
			break;
			case 1: {
				if(one.getValueFromKey(inputWord) == null){
					textArea.setText("Nao ha tweets com essa palavra " + inputWord + "nesse escore: 1");
				}
				else if(one.getValueFromKey(inputWord).returnTweetsList() ==null){
					textArea.setText("Nao ha tweets com esse escore " + 1);
				}
				else{
					searchListOfTweets (one.getValueFromKey(inputWord).returnTweetsList());
				}
			}
			break;
			case 0: {
				if(zero.getValueFromKey(inputWord) == null){
					textArea.setText("Nao ha tweets com essa palavra " + inputWord + "nesse escore: 0");
				}
				else if(zero.getValueFromKey(inputWord).returnTweetsList() ==null){
					textArea.setText("Nao ha tweets com esse escore: 0");
				}
				else{
					searchListOfTweets (zero.getValueFromKey(inputWord).returnTweetsList());
				}
			}
			break;
			}

		}
		else{

			if (table.getValueFromKey(inputWord) == null) {
				textArea.setText("Nao ha tweets com essa palavra:  " +inputWord );
			}
			else{
				tweets = table.getValueFromKey(inputWord).returnTweetList();
				searchListOfTweets (tweets);
			}
		}
	}

	public static void searchListOfTweets(LinkedList<Integer> tweets){



		textArea.appendText("\n");
		textArea.appendText("[");
		for (Integer t: tweets){
			textArea.appendText(" " + t);	 
		}
		textArea.appendText("]");
		textArea.appendText("\n");


		Path path2 = Paths.get("pt.csv");
		try (BufferedReader reader = Files.newBufferedReader(path2, Charset.forName("utf8"))) {
			String line = null;
			Integer tweetIndex = 0;
			ArrayList <String> retorno = new ArrayList <String>();
			while ((line = reader.readLine()) != null) {

				Scanner sc = new Scanner(line).useDelimiter(","); // separador é ,
				String finalLine = sc.next(); //linha sem o peso
				sc.nextInt(); // peso

				//vejo se o index de agora eh algum dos que eu quero, se sim, dou append numa lista de retorno,
				//que vai ter todos os tweets que teem aquela palavra e mais aquele escore
				if (tweets.contains(tweetIndex)){
					retorno.add(finalLine);
				}
				tweetIndex = tweetIndex + 1;
			} 

			for (String tweetRet: retorno){
				textArea.appendText(tweetRet);
				textArea.appendText("\n");

			}

		}
		catch (IOException x) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Erro de E/S: " +  x));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(46));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
