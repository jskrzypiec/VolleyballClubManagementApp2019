package proj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Query;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.boot.Metadata;  
import org.hibernate.boot.MetadataSources;  
import org.hibernate.boot.registry.StandardServiceRegistry;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import static javafx.application.Application.launch;


public class App extends Application{    
	private Administrator aZal = new Administrator();
	@SuppressWarnings("unused")
	private Trener tZal = new Trener();
	private Zawodnik zZal = new Zawodnik();
	
	//@SuppressWarnings("null")
	@Override
	public void start(Stage stage) {
		//hibernate - sessionFactory
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  
		SessionFactory factory = meta.getSessionFactoryBuilder().build();	
		
		//gridPane
		GridPane okno = new GridPane();
		//CSS do okna
		okno.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
				
		//wyl. paska na gorze ; przyciski - zakmnij,zwin
		stage.initStyle(StageStyle.UNDECORATED);
		Button zamknij = new Button("");
		zamknij.setMinSize(34, 34);
		zamknij.setId("zamknij");
		Button zwin = new Button("");
		zwin.setMinSize(34, 34);
		zwin.setId("zwin");
		
		//miejsce na lewo - zamknij,zwin
		HBox s_przyciski = new HBox();
		s_przyciski.setMinSize(300,34);
		s_przyciski.setId("s_przyciski");
		okno.add(s_przyciski, 0,0,1,1);
		
		//pasek na przyciski - zamknij,zwin
		HBox przyciski = new HBox(); 
		przyciski.setMinSize(900,34);  
		przyciski.setSpacing(10); //odleg³oœci w poziomie
		przyciski.setAlignment(Pos.TOP_RIGHT); 
		//dodanie przyciskow
		przyciski.getChildren().addAll(zwin,zamknij);
		przyciski.setId("pp");
		okno.add(przyciski, 1,0,1,1);
		
		//panel lewy 
		VBox panel_lewy = new VBox();
		panel_lewy.setSpacing(9);
		//panel_lewy.setMinWidth(200);
		panel_lewy.setMinSize(300,34);
		//panel_lewy.setAlignment(Pos.TOP_LEFT);
		panel_lewy.setId("lewy");
		okno.add(panel_lewy, 0,1,1,1);
		
		//panel prawy
		VBox panel_prawy = new VBox();
		//panel_prawy.setMinWidth(600);
		panel_prawy.setMinSize(900,34);
		//panel_prawy.setAlignment(Pos.TOP_LEFT);
		panel_prawy.setId("prawy");
		okno.add(panel_prawy, 1,1,1,1);
		
		//logowanie w prawym_panelu
		Label loginL = new Label("login"); 
		TextField loginTF = new TextField();
		loginTF.setId("logowanieTF");
		Label hasloL = new Label("has³o"); 
		PasswordField hasloTF = new PasswordField();
		hasloTF.setId("logowanieTF");
		Button logujB = new Button("zaloguj siê");
		Label messgL = new Label();
		//
		VBox log_etyk = new VBox(); 
		log_etyk.setSpacing(5);
		VBox log_reszta = new VBox();
		log_reszta.setSpacing(5);
		log_etyk.getChildren().addAll(loginL,hasloL);
		log_reszta.getChildren().addAll(loginTF,hasloTF,logujB,messgL);
		//
		HBox logowanie = new HBox();
		logowanie.setId("logowanieHBox");
		logowanie.getChildren().addAll(log_etyk,log_reszta);
		//
		panel_prawy.getChildren().addAll(logowanie);
		
//////////////////////////////////////////////////////////////////przykladowe dane ///////////////////////////////////////////
		loginTF.setText("jskrzypiec");
		hasloTF.setText("123123123");
		//skrzypiec - 123123123
		//wgora - skoda312
		//kdomel - okna921
//////////////////////////////////////////////////////////////////przykladowe dane ///////////////////////////////////////////

		
		//logujB action
		logujB.setOnAction(log_a->
    	{	
    		//do systemu zalogowania
    		int zal=0;
    		/*Administrator aZal = new Administrator();
    		@SuppressWarnings("unused")
			Trener tZal = new Trener();
    		Zawodnik zZal = new Zawodnik();*/
    		
    		//sesja do zalogowania
    		Session session = factory.openSession();
    	    Transaction t = session.beginTransaction();
    		try {
    		@SuppressWarnings("unchecked")
    		List<Zawodnik> wynikZaw = session.createQuery("from Zawodnik").getResultList();
    		@SuppressWarnings("unchecked")
			List<Trener> wynikTren = session.createQuery("from Trener").getResultList();
    		@SuppressWarnings("unchecked")
			List<Administrator> wynikAdm = session.createQuery("from Administrator").getResultList();
    		//loginTF.setText(wynikL.get(0).getImie());
    		
    		//system logowania 
    		//jako kto zalogowano
    		for(int i=0; i<wynikAdm.size(); i++) 
    		{
    			if(loginTF.getText().equals(wynikAdm.get(i).getLogin_()) &&  hasloTF.getText().equals(wynikAdm.get(i).getHaslo_()))
    			{
    				aZal = wynikAdm.get(i);
    				zal = 1;
    				break;
    			}
    		}
    		for(int i=0; i<wynikTren.size(); i++) 
    		{
    			if(loginTF.getText().equals(wynikTren.get(i).getLogin_()) &&  hasloTF.getText().equals(wynikTren.get(i).getHaslo_()))
    			{
    				tZal = wynikTren.get(i);
    				zal = 2;
    				break;
    			}
    		}
    		for(int i=0; i<wynikZaw.size(); i++) 
    		{
    			if(loginTF.getText().equals(wynikZaw.get(i).getLogin_()) &&  hasloTF.getText().equals(wynikZaw.get(i).getHaslo_()))
    			{
    				zZal = wynikZaw.get(i); 
    				zal = 3;
    				break;
    			}
    		}
    		t.commit();
    		}finally{
    			session.close();
    		}
    		
    		//zaleznie od tego kto zalogowany
    		//3-zawodnik
    		//
    		if(zal == 3) 
    		{
    			Button zaw_startB = new Button("Start");
    			Button zaw_treningiB = new Button("Moje treningi");
    			Button zaw_grupaB = new Button("Moja grupa");
    			Button zaw_zawB = new Button("Lista zawodników");
    			Button zaw_trenB = new Button("Lista trenerów");
    			Button zaw_edycjaB = new Button("Edytuj moje dane");
    			Button zaw_wylogujB = new Button("wyloguj");
    			zaw_startB.setId("zaw_lewyButtons");
    			zaw_treningiB.setId("zaw_lewyButtons");
    			zaw_grupaB.setId("zaw_lewyButtons");
    			zaw_zawB.setId("zaw_lewyButtons");
    			zaw_trenB.setId("zaw_lewyButtons");
    			zaw_edycjaB.setId("zaw_lewyButtons");
    			zaw_wylogujB.setId("zaw_lewyButtons");
    			//wyczyszczenie panelu prawego, dodanie przycisków do panelu lewego
    			panel_prawy.getChildren().clear();
    			panel_lewy.getChildren().addAll(zaw_startB, zaw_treningiB, zaw_grupaB, zaw_zawB, zaw_trenB, zaw_edycjaB, zaw_wylogujB);

    			//zawodnik - start (automatycznie po zalogowaniu) / zaw - start
    			VBox zaw_start = new VBox();
    			zaw_start.setId("zaw_startVBox");
    			Label zaw_startTF1 = new Label("Zalogowano jako: ");
    			Label zaw_startTF2 = new Label("Wzrost: ");
    			Label zaw_startTF3 = new Label("Adres: ");
    			Label zaw_startTF4 = new Label("Klub: ");
    			Label zaw_startTF5 = new Label("Grupa: ");
    			Label zaw_startTF6 = new Label("Pozycja: ");
    			//sesja do zaw_Start
    			Session session_zawStart = factory.openSession();
    			Transaction t_ZawStart = session_zawStart.beginTransaction();
    			try {
	    			//info startowe
	    			zaw_startTF1.setText(zaw_startTF1.getText() + zZal.getImie() + " " + zZal.getNazwisko() + " (zawodnik)");
	    			//wzrost,waga,nr tel,rok ur
	    			zaw_startTF2.setText(zaw_startTF2.getText() + zZal.getWzrost() + 
	    					", waga: " + zZal.getWaga() + 
	    					", nrTel: " + zZal.getNrTelefonu() +
	    					", rokUr: " + zZal.getRokUrodzenia() );
	    			//adres
	    			zaw_startTF3.setText(zaw_startTF3.getText() + zZal.getAdres_id().getUlica() + " " + zZal.getAdres_id().getNrDomu());
	    			//wysw. nrMieszkania tylko jesli jest podany
	    			if(zZal.getAdres_id().getNrMieszkania() != null)
	    			{
	    				zaw_startTF3.setText(zaw_startTF3.getText() + "/" + zZal.getAdres_id().getNrMieszkania() );
	    			}
	    			else
	    			{
	    				zaw_startTF3.setText(zaw_startTF3.getText() + "");
	    			}
	    			//wysw. kodu tylko jesli jest podany
	    			if(zZal.getAdres_id().getKodPocztowy() != null)
	    			{
	    				zaw_startTF3.setText(zaw_startTF3.getText() + ", " + zZal.getAdres_id().getKodPocztowy() );
	    			}
	    			else
	    			{
	    				zaw_startTF3.setText(zaw_startTF3.getText() + "");
	    			}
	    			zaw_startTF3.setText(zaw_startTF3.getText() + ", " + zZal.getAdres_id().getMiasto_id().getNazwa() );
	    			//klub
	    			if(zZal.getKlub_id() != null)
	    			{
	    				zaw_startTF4.setText(zaw_startTF4.getText() + zZal.getKlub_id().getNazwa() );
	    			}
	    			else 
	    			{
	    				zaw_startTF4.setText(zaw_startTF4.getText() + "-brak-" );
	    			}
	    			//grupa
	    			if(zZal.getGrupa_id() != null)
	    			{
	    				zaw_startTF5.setText(zaw_startTF5.getText() + zZal.getGrupa_id().getNazwa() );
	    			}
	    			else 
	    			{
	    				zaw_startTF5.setText(zaw_startTF5.getText() + "-brak-" );
	    			}
	    			//pozycja
	    			zaw_startTF6.setText(zaw_startTF6.getText() + zZal.getPozycja_id().getNazwa() );
	    			//
	    			zaw_start.getChildren().addAll(zaw_startTF1, zaw_startTF2, zaw_startTF3, zaw_startTF4, zaw_startTF5, zaw_startTF6);
	    			panel_prawy.getChildren().addAll(zaw_start);
	    			}
    			finally {
    				t_ZawStart.commit();
        	    	session_zawStart.close();
        	    }
    			
        	    //zawodnik - start
    			zaw_startB.setOnAction(zStart_a->
    			{
    				panel_prawy.getChildren().clear();
    				//
    				//start-zawodnik
        			//info startowe
        			zaw_startTF1.setText("Zalogowano jako: " + zZal.getImie() + " " + zZal.getNazwisko() + " (zawodnik)");
        			//wzrost,waga,nr tel,rok ur
        			zaw_startTF2.setText("Wzrost: " + zZal.getWzrost() + 
        					", waga: " + zZal.getWaga() + 
        					", nrTel: " + zZal.getNrTelefonu() +
        					", rokUr: " + zZal.getRokUrodzenia() );
        			//adres
        			zaw_startTF3.setText("Adres: " + zZal.getAdres_id().getUlica() + " " + zZal.getAdres_id().getNrDomu());
        			//wysw. nrMieszkania tylko jesli jest podany
        			if(zZal.getAdres_id().getNrMieszkania() != null)
        			{
        				zaw_startTF3.setText(zaw_startTF3.getText() + "/" + zZal.getAdres_id().getNrMieszkania() );
        			}
        			else
        			{
        				zaw_startTF3.setText(zaw_startTF3.getText() + "");
        			}
        			//wysw. kodu tylko jesli jest podany
        			if(zZal.getAdres_id().getKodPocztowy() != null)
        			{
        				zaw_startTF3.setText(zaw_startTF3.getText() + ", " + zZal.getAdres_id().getKodPocztowy() );
        			}
        			else
        			{
        				zaw_startTF3.setText(zaw_startTF3.getText() + "");
        			}
        			zaw_startTF3.setText(zaw_startTF3.getText() + ", " + zZal.getAdres_id().getMiasto_id().getNazwa() );
        			//klub
        			if(zZal.getKlub_id() != null)
        			{
        				zaw_startTF4.setText("Klub: " + zZal.getKlub_id().getNazwa() );
        			}
        			else 
        			{
        				zaw_startTF4.setText("Klub: " + "-brak-" );
        			}
        			//grupa
        			if(zZal.getGrupa_id() != null)
        			{
        				zaw_startTF5.setText("Grupa: " + zZal.getGrupa_id().getNazwa() );
        			}
        			else 
        			{
        				zaw_startTF5.setText("Grupa: " + "-brak-" );
        			}
        			//pozycja
        			zaw_startTF6.setText("Pozycja: " + zZal.getPozycja_id().getNazwa() );
    				//
    				
    				panel_prawy.getChildren().addAll(zaw_start);
    			});
    			
    			//zawodnik - treningi
    			zaw_treningiB.setOnAction(zTreningi_a->
    			{
					panel_prawy.getChildren().clear();
					VBox zaw_treningi = new VBox();
	    			zaw_treningi.setId("zaw_treningiVBox");
	    			Label zaw_trenTF1 = new Label("Treningi: ");
	    			zaw_treningi.getChildren().addAll(zaw_trenTF1);
	    			//sesja do zaw_treningi
	    			Session sessionZawTren = factory.openSession();
	    			Transaction t_ZawTren = sessionZawTren.beginTransaction();
	    			try {
	    				//list LABELi, które bêd¹ zawieraæ szczegó³y o treningach
	    				ArrayList<Label> zaw_tren_Labels = new ArrayList<Label>();
	    				//zapytanie
	    				@SuppressWarnings("unchecked")
						List<Zajecia> wynZaw_zajecia = sessionZawTren.createQuery("from Zajecia where grupa_id=" + zZal.getGrupa_id().getId() ).getResultList();
	    				//
	    				for(int i=0,j=0; i<wynZaw_zajecia.size(); i++)
	        			{
	        				//Label - dzienTygodnia (godz-godz), sala:...
	        				zaw_tren_Labels.add( new Label( 
	        						wynZaw_zajecia.get(i).getDzienTygodnia_id().getNazwa() + " (" + wynZaw_zajecia.get(i).getGodzRozpoczecia() +
	        						"-" + wynZaw_zajecia.get(i).getGodzZakonczenia() + "), sala: ") );
	        				if(wynZaw_zajecia.get(i).getSala_id().getNazwa() != null)
	        				{
	        					zaw_tren_Labels.get(j).setText( zaw_tren_Labels.get(j).getText() + wynZaw_zajecia.get(i).getSala_id().getNazwa());
	        				}
	        				j++;
	        				//Label - adres Sali
	        				zaw_tren_Labels.add( new Label( "adres sali: " + wynZaw_zajecia.get(i).getSala_id().getAdres_id().getUlica() + " " +
	        						wynZaw_zajecia.get(i).getSala_id().getAdres_id().getNrDomu()) );
	        				j++;
	        			}
	    				//
	    				//
	        			zaw_treningi.getChildren().addAll( zaw_tren_Labels );
	        			
	        			//
	        			t_ZawTren.commit();
	    			}finally {
	    				sessionZawTren.close();
	    			}
	    			panel_prawy.getChildren().addAll(zaw_treningi);
    			});
    			
    			//zawodnik - grupa
    			zaw_grupaB.setOnAction(zGrupa_a->
    			{
    				//sesja do zaw_grupa
        			Session sessionZawGrupa = factory.openSession();
        			Transaction t_ZawGrupa = sessionZawGrupa.beginTransaction();
    	    	    
    				panel_prawy.getChildren().clear();
    				VBox zaw_grupa = new VBox();
    				zaw_grupa.setId("zaw_grupaVBox");
    				
        			try {
        				//zawdonik - grupa nazwa
        				Label zaw_grupaTF1 = new Label("Twoja grupa: " + zZal.getGrupa_id().getNazwa() );
        				//zawodnik - grupa kategoria 
        				Label zaw_grupaTF2 = new Label("Kategoria: " + zZal.getGrupa_id().getKategoria_id().getNazwa() );
        				//zawodnik - grupa trener
        				Label zaw_grupaTF3 = new Label("Trener: " + zZal.getGrupa_id().getTrener_id().getImie() + " " + 
        						zZal.getGrupa_id().getTrener_id().getNazwisko() + " (tel." + zZal.getGrupa_id().getTrener_id().getNrTelefonu() + ")");
            			zaw_grupa.getChildren().addAll(zaw_grupaTF1, zaw_grupaTF2, zaw_grupaTF3);
        				//zawodnik - grupa zawodnicy
            			Label odstep = new Label();
            			Label zaw_grupaTF4 = new Label("Twoja grupa: ");
            			zaw_grupa.getChildren().addAll(odstep, zaw_grupaTF4);
        				//list LABELi, które bêd¹ zawieraæ szczegó³y o zawodnikach z grupy
        				ArrayList<Label> zaw_grupa_Labels = new ArrayList<Label>();
        				//zapytanie
        				@SuppressWarnings("unchecked")
						List<Zawodnik> wynZaw_grupa = sessionZawGrupa.createQuery("from Zawodnik where grupa_id=" + zZal.getGrupa_id().getId() ).getResultList();
        				for(int i=0; i<wynZaw_grupa.size(); i++)
        				{
        					//Label - zawodnik...
        					zaw_grupa_Labels.add( new Label( wynZaw_grupa.get(i).getImie() + " " + wynZaw_grupa.get(i).getNazwisko() + " - " +
        							wynZaw_grupa.get(i).getPozycja_id().getNazwa() ) );
            				
        				}
        				zaw_grupa.getChildren().addAll( zaw_grupa_Labels );
        				
        				t_ZawGrupa.commit();
        			}finally {
        				sessionZawGrupa.close();
        			}
        			panel_prawy.getChildren().addAll(zaw_grupa);
        		});
    			
    			//zawodnik - zawodnicy
    			zaw_zawB.setOnAction(zZaw_a->
    			{
    				panel_prawy.getChildren().clear();
    				//sesja do zaw_zaw
        			Session sessionZawZaw = factory.openSession();
        			Transaction t_ZawZaw = sessionZawZaw.beginTransaction();
        			try {
        				@SuppressWarnings("unchecked")
						List<Zawodnik> wynikZawZaw = sessionZawZaw.createQuery("from Zawodnik where klub_id=" + zZal.getKlub_id().getId() + 
																				"order by grupa_id desc, pozycja_id desc" ).getResultList();
        				//tablela
        				TableView<Zawodnik> TV_zaw = new TableView<>();
        				//kol-imie
        				TableColumn<Zawodnik, String> TC_zaw_imie = new TableColumn<>("Imie");
        				TC_zaw_imie.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("imie"));
        				TC_zaw_imie.setMinWidth(100);
        				//kol-nazwisko
        				TableColumn<Zawodnik, String> TC_zaw_nazw = new TableColumn<>("Nazwisko");
        				TC_zaw_nazw.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("nazwisko"));
        				TC_zaw_nazw.setMinWidth(110);
        				//kol-rokUr
        				TableColumn<Zawodnik, Integer> TC_zaw_rok = new TableColumn<>("Rok ur.");
        				TC_zaw_rok.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("rokUrodzenia"));
        				TC_zaw_rok.setMinWidth(90);
        				//kol-wzrost
        				TableColumn<Zawodnik, Integer> TC_zaw_wzrost = new TableColumn<>("Wzrost");
        				TC_zaw_wzrost.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("wzrost"));
        				TC_zaw_wzrost.setMinWidth(85);
        				//kol-waga
        				TableColumn<Zawodnik, Integer> TC_zaw_waga = new TableColumn<>("Waga");
        				TC_zaw_waga.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("waga"));
        				TC_zaw_waga.setMinWidth(75);
        				//kol-nrTel
        				TableColumn<Zawodnik, Integer> TC_zaw_tel = new TableColumn<>("Nr tel.");
        				TC_zaw_tel.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("nrTelefonu"));
        				TC_zaw_tel.setMinWidth(95);
        				//kol-grupa
        				TableColumn<Zawodnik, String> TC_zaw_grupa = new TableColumn<>("Grupa");
        				TC_zaw_grupa.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getGrupa_id().getNazwa() );
							}
        	            });
        				TC_zaw_grupa.setMinWidth(110);
        				//kol-pozycja
        				TableColumn<Zawodnik, String> TC_zaw_pozycja = new TableColumn<>("Pozycja");
        				TC_zaw_pozycja.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getPozycja_id().getNazwa() );
							}
        	            });
        				TC_zaw_pozycja.setMinWidth(100);
        				//lista obiektow - przepisanie elementow list "wynikZawZaw" do list "OL_zaw"
        				ObservableList<Zawodnik> OL_zaw = FXCollections.observableArrayList();
        				for(int i=0; i<wynikZawZaw.size(); i++)
        				{
        					OL_zaw.add( wynikZawZaw.get(i) );
        				}
        				//ustawienie kolumn
        				TV_zaw.getColumns().addAll(TC_zaw_imie, TC_zaw_nazw, TC_zaw_rok, TC_zaw_wzrost, TC_zaw_waga, TC_zaw_tel, TC_zaw_grupa, TC_zaw_pozycja);
        				//dodanie elementow list do tabeli
        				TV_zaw.setItems(OL_zaw);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_zaw);
        			}finally {
        				t_ZawZaw.commit();
        				sessionZawZaw.close();
        			}
    			});
    			
    			//zawodnik - trenerzy
    			zaw_trenB.setOnAction(zTren_a->{
    				panel_prawy.getChildren().clear();
    				//sesja do zaw_trener
        			Session sessionZawTrener = factory.openSession();
        			Transaction t_ZawTrener = sessionZawTrener.beginTransaction();
        			try {
        				@SuppressWarnings("unchecked")
						List<Trener> wynikZawTrener = sessionZawTrener.createQuery("from Trener where klub_id=" + zZal.getKlub_id().getId() ).getResultList();
        				//tablela
        				TableView<Trener> TV_trener = new TableView<>();
        				//kol-imie
        				TableColumn<Trener, String> TC_trener_imie = new TableColumn<>("Imie");
        				TC_trener_imie.setCellValueFactory(new PropertyValueFactory<Trener, String>("imie"));
        				TC_trener_imie.setMinWidth(100);
        				//kol-nazwisko
        				TableColumn<Trener, String> TC_trener_nazw = new TableColumn<>("Nazwisko");
        				TC_trener_nazw.setCellValueFactory(new PropertyValueFactory<Trener, String>("nazwisko"));
        				TC_trener_nazw.setMinWidth(110);
        				//kol-rokUr
        				TableColumn<Trener, Integer> TC_trener_rok = new TableColumn<>("Rok ur.");
        				TC_trener_rok.setCellValueFactory(new PropertyValueFactory<Trener, Integer>("rokUrodzenia"));
        				TC_trener_rok.setMinWidth(90);
        				//kol-nrTel
        				TableColumn<Trener, Integer> TC_trener_tel = new TableColumn<>("Nr tel.");
        				TC_trener_tel.setCellValueFactory(new PropertyValueFactory<Trener, Integer>("nrTelefonu"));
        				TC_trener_tel.setMinWidth(85);
        				//kol-klub
        				TableColumn<Trener, String> TC_trener_klub = new TableColumn<>("Grupa");
        				TC_trener_klub.setCellValueFactory(new Callback<CellDataFeatures<Trener, String>,ObservableValue<String>>(){
							@Override
							public ObservableValue<String> call(CellDataFeatures<Trener, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getKlub_id().getNazwa() );
							}
        	            });
        				TC_trener_klub.setMinWidth(200);
        				
        				//lista obiektow - przepisanie elementow list "wynikZawTrener" do list "OL_trener"
        				ObservableList<Trener> OL_trener = FXCollections.observableArrayList();
        				for(int i=0; i<wynikZawTrener.size(); i++)
        				{
        					OL_trener.add( wynikZawTrener.get(i) );
        				}
        				//ustawienie kolumn
        				TV_trener.getColumns().addAll(TC_trener_imie, TC_trener_nazw, TC_trener_rok, TC_trener_tel, TC_trener_klub);
        				//dodanie elementow list do tabeli
        				TV_trener.setItems(OL_trener);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_trener);
        			}finally {
        				t_ZawTrener.commit();
        				sessionZawTrener.close();
        			}
    			});
    			
    			//zawodnik - edytuj DANE / zawodnik - edycja
    			zaw_edycjaB.setOnAction(zEdycja_a->
    			{
    				panel_prawy.getChildren().clear();
    				Label zeL1_wzrost = new Label("wzrost ");
    				Label zeL2_waga = new Label("waga ");
    				Label zeL3_tel = new Label("nr tel ");
    				Label zeL4_ul = new Label("ulica ");
    				Label zeL5_dom = new Label("nr domu ");
    				Label zeL6_mieszk = new Label("nr mieszkania ");
    				Label zeL7_kod = new Label("kod pocztowy ");
    				Label zeL8_hasl = new Label("has³o ");
    				TextField zeTF1_wzrost = new TextField();
    				TextField zeTF2_waga = new TextField();
    				TextField zeTF3_tel = new TextField();
    				TextField zeTF4_ul = new TextField();
    				TextField zeTF5_dom = new TextField();
    				TextField zeTF6_mieszk = new TextField();
    				TextField zeTF7_kod = new TextField();
    				PasswordField zeTF8_hasl = new PasswordField();
    				Button zeB1_zmien = new Button("zmieñ dane"); 
    				zeB1_zmien.setId("zeB1");
    				Label zeM_ogolnie = new Label();
    				Label zeM1_wzrost = new Label(); 
    				Label zeM2_waga = new Label();
    				Label zeM3_tel = new Label();
    				Label zeM4_ul = new Label();
    				Label zeM5_dom = new Label();
    				Label zeM6_mieszk = new Label();
    				Label zeM7_kod = new Label();
    				Label zeM8_hasl = new Label();
    				zeM1_wzrost.setId("ZawEdycjaMessage");
    				zeM2_waga.setId("ZawEdycjaMessage");
    				zeM3_tel.setId("ZawEdycjaMessage");
    				zeM4_ul.setId("ZawEdycjaMessage");
    				zeM5_dom.setId("ZawEdycjaMessage");
    				zeM6_mieszk.setId("ZawEdycjaMessage");
    				zeM7_kod.setId("ZawEdycjaMessage");
    				zeM8_hasl.setId("ZawEdycjaMessage");
    				Label zeLP1_wzrost = new Label("?"); 
    				Label zeLP2_waga = new Label("?");
    				Label zeLP3_tel = new Label("?");
    				Label zeLP4_ul = new Label("?");
    				Label zeLP5_dom = new Label("?");
    				Label zeLP6_mieszk = new Label("?");
    				Label zeLP7_kod = new Label("?");
    				Label zeLP8_hasl = new Label("?");
    				zeLP1_wzrost.setId("zeLP1_wzrost");
    				zeLP2_waga.setId("zeLP2_waga");
    				zeLP3_tel.setId("zeLP3_tel");
    				zeLP4_ul.setId("zeLP4_ul");
    				zeLP5_dom.setId("zeLP5_dom");
    				zeLP6_mieszk.setId("zeLP6_mieszk");
    				zeLP7_kod.setId("zeLP7_kod");
    				zeLP8_hasl.setId("zeLP8_hasl");
    				zeLP1_wzrost.setTooltip(new Tooltip("100-299, np.'187', '201'") );
    				zeLP2_waga.setTooltip(new Tooltip("10-999, np.'70', '105'") );
    				zeLP3_tel.setTooltip(new Tooltip("9-cyfrowy numer, np.'600500400'") );
    				zeLP4_ul.setTooltip(new Tooltip("nazwa ulicy wielk¹ lietr¹, np.'Karpacka'") );
    				zeLP5_dom.setTooltip(new Tooltip("numer domu, np.'1a', '606'") );
    				zeLP6_mieszk.setTooltip(new Tooltip("numer mieszkania, np.'1', '104'") );
    				zeLP7_kod.setTooltip(new Tooltip("kod pocztowy w formacie LL-LLL, np.'19-400'") );
    				zeLP8_hasl.setTooltip(new Tooltip("haslo-minimum 5 znaków") );
    				VBox zaw_edycjaL = new VBox();  zaw_edycjaL.setId("zeL");
    				zaw_edycjaL.setSpacing(11);
    				VBox zaw_edycjaTF = new VBox(); zaw_edycjaTF.setId("zeTF");
    				zaw_edycjaTF.setSpacing(10);
    				VBox zaw_edycjaLP = new VBox();// edycja label podpowiedŸ
    				zaw_edycjaLP.setSpacing(11);
    				VBox zaw_edycjaM = new VBox(); zaw_edycjaM.setId("zeM");
    				zaw_edycjaM.setSpacing(11);
    				zaw_edycjaL.getChildren().addAll(zeL1_wzrost, zeL2_waga, zeL3_tel, zeL4_ul, zeL5_dom, zeL6_mieszk, zeL7_kod, zeL8_hasl);
    				zaw_edycjaTF.getChildren().addAll(zeTF1_wzrost, zeTF2_waga, zeTF3_tel, zeTF4_ul, zeTF5_dom, zeTF6_mieszk, zeTF7_kod, zeTF8_hasl, zeB1_zmien, zeM_ogolnie);
    				zaw_edycjaLP.getChildren().addAll(zeLP1_wzrost, zeLP2_waga, zeLP3_tel, zeLP4_ul, zeLP5_dom, zeLP6_mieszk, zeLP7_kod, zeLP8_hasl);
    				zaw_edycjaM.getChildren().addAll(zeM1_wzrost, zeM2_waga, zeM3_tel, zeM4_ul, zeM5_dom, zeM6_mieszk, zeM7_kod, zeM8_hasl);				
    				HBox zaw_edycjaHBox = new HBox(zaw_edycjaL, zaw_edycjaTF, zaw_edycjaLP, zaw_edycjaM);
    				zaw_edycjaHBox.getChildren().addAll();
    				zaw_edycjaHBox.setId("zeHBox");
    				panel_prawy.getChildren().addAll(zaw_edycjaHBox);		
    				//wczytanie danych do pól tekstowych
					zeTF1_wzrost.setText( Integer.toString(zZal.getWzrost()) );
					zeTF2_waga.setText( Integer.toString(zZal.getWaga()) );
					zeTF3_tel.setText( Integer.toString(zZal.getNrTelefonu()) );
					zeTF4_ul.setText( zZal.getAdres_id().getUlica() );
					zeTF5_dom.setText( zZal.getAdres_id().getNrDomu() );
					zeTF6_mieszk.setText( Integer.toString(zZal.getAdres_id().getNrMieszkania()) );
					zeTF7_kod.setText( zZal.getAdres_id().getKodPocztowy() );
					zeTF8_hasl.setText( zZal.getHaslo_() );
    				//akcja przycisku zatwierdzajacego zmiane
    				zeB1_zmien.setOnAction(zeZmien_a->{
    					//sesja do zaw_edycja_zmien
            			Session sessionZawEdycja_zmien = factory.openSession();
            			try {
	    					//regex
	    					//wzrost 
	    					Pattern wzrostP = Pattern.compile("[12][0-9]{2}");
	    					Matcher testWzrost = wzrostP.matcher(zeTF1_wzrost.getText() );
	    					if(testWzrost.matches())
	    						zeM1_wzrost.setText(" ok");
	    					else 
	    						zeM1_wzrost.setText(" niepoprawny wzrost");
	    					//waga
	    					Pattern wagaP = Pattern.compile("[1-9][0-9]{1,2}");
	    					Matcher testWaga = wagaP.matcher(zeTF2_waga.getText() );
	    					if(testWaga.matches())
	    						zeM2_waga.setText(" ok");
	    					else 
	    						zeM2_waga.setText(" niepoprawna waga");
	    					//nr tel
	    					Pattern nrTelP = Pattern.compile("[1-9][0-9]{8}");
	    					Matcher testNrTel = nrTelP.matcher(zeTF3_tel.getText() );
	    					if(testNrTel.matches())
	    						zeM3_tel.setText(" ok");
	    					else 
	    						zeM3_tel.setText(" niepoprawny nr tel");
	    					//ulica
	    					Pattern ulicaP = Pattern.compile("[A-Za-z ]{3,50}");
	    					Matcher testUlica = ulicaP.matcher(zeTF4_ul.getText() );
	    					if(testUlica.matches())
	    						zeM4_ul.setText(" ok");
	    					else 
	    						zeM4_ul.setText(" niepoprawna nazwa ulicy");
	    					//nr domu
	    					Pattern nrDomuP = Pattern.compile("[1-9][0-9]{0,5}[A-Za-z]{0,1}");
	    					Matcher testNrDomu = nrDomuP.matcher(zeTF5_dom.getText() );
	    					if(testNrDomu.matches())
	    						zeM5_dom.setText(" ok");
	    					else 
	    						zeM5_dom.setText(" niepoprawny nr domu");
	    					//nr mieszkania
	    					Pattern nrMieszkP = Pattern.compile("[1-9][0-9]{0,3}");
	    					Matcher testNrMieszk = nrMieszkP.matcher(zeTF6_mieszk.getText() );
	    					if(testNrMieszk.matches())
	    						zeM6_mieszk.setText(" ok");
	    					else 
	    						zeM6_mieszk.setText(" niepoprawny nr mieszkania");
	    					//kod pocztowy
	    					Pattern kodPocztP = Pattern.compile("[0-9]{2}[-][0-9]{3}");
	    					Matcher testKodPoczt = kodPocztP.matcher(zeTF7_kod.getText() );
	    					if(testKodPoczt.matches())
	    						zeM7_kod.setText(" ok");
	    					else 
	    						zeM7_kod.setText(" niepoprawny kod pocztowy");
	    					//haslo
	    					Pattern hasloP = Pattern.compile("[A-Za-z0-9]{5,32}");
	    					Matcher testHaslo = hasloP.matcher(zeTF8_hasl.getText() );
	    					if(testHaslo.matches())
	    						zeM8_hasl.setText(" ok");
	    					else 
	    						zeM8_hasl.setText(" niepoprawne has³o");
	    					
	    					if(testWzrost.matches() && testWaga.matches() && testNrTel.matches() && testUlica.matches() &&
	    							testNrDomu.matches() && testNrMieszk.matches() &&  testKodPoczt.matches() && testHaslo.matches() )
	    					{
	    						zZal.setWzrost( Integer.parseInt(zeTF1_wzrost.getText()) );
	    						zZal.setWaga( Integer.parseInt(zeTF2_waga.getText()) );
	    						zZal.setNrTelefonu( Integer.parseInt(zeTF3_tel.getText()) );
	    						zZal.getAdres_id().setUlica( zeTF4_ul.getText() );
	    						zZal.getAdres_id().setNrDomu( zeTF5_dom.getText() );
	    						zZal.getAdres_id().setNrMieszkania( Integer.parseInt(zeTF6_mieszk.getText()) );
	    						zZal.getAdres_id().setKodPocztowy( zeTF7_kod.getText() );
	    						zZal.setHaslo_( zeTF8_hasl.getText() );
	    						zeM_ogolnie.setText( "poprawne dane" );
	    					}
	    					else
	    					{
	    						zeM_ogolnie.setText( "b³êdne dane" );
	    					}
        				}finally {
        				Transaction t_ZawEdycja_zmien = sessionZawEdycja_zmien.beginTransaction();
        				sessionZawEdycja_zmien.update(zZal);
        				t_ZawEdycja_zmien.commit();	
        				sessionZawEdycja_zmien.close();
        				}
    				});
    			});
    			
    			//zawodnik - wyloguj
    			zaw_wylogujB.setOnAction(zWyloguj_a->
    			{
    				//przywrócenie logowanie HBox zawierajacego 2xVbox(etykiety, pola)
    				panel_lewy.getChildren().clear();
    				panel_prawy.getChildren().clear();
    				panel_prawy.getChildren().addAll(logowanie);
    				//wyczyszczenie message label z ew. informacji "b³êdny login lub/i has³o"
    				messgL.setText("");
    			});
    			
    		}
    		//2-trener
    		else if(zal==2)
    		{
    			Button tren_startB = new Button("Start");
    			Button tren_grupyB = new Button("Moje grupy");
    			Button tren_treningiB = new Button("Moje treningi");
    			Button tren_zawB = new Button("Lista zawodników");
    			Button tren_trenB = new Button("Lista trenerów");
    			Button tren_edycjaB = new Button("Edytuj moje dane");
    			Button tren_wylogujB = new Button("wyloguj");
    			tren_startB.setId("tren_lewyButtons");
    			tren_grupyB.setId("tren_lewyButtons");
    			tren_treningiB.setId("tren_lewyButtons");
    			tren_zawB.setId("tren_lewyButtons");
    			tren_trenB.setId("tren_lewyButtons");
    			tren_edycjaB.setId("tren_lewyButtons");
    			tren_wylogujB.setId("tren_lewyButtons");
    			//wyczyszczenie panelu prawego, dodanie przycisków do panelu lewego
    			panel_prawy.getChildren().clear();
    			panel_lewy.getChildren().addAll(tren_startB, tren_grupyB, tren_treningiB, tren_zawB, tren_trenB, tren_edycjaB, tren_wylogujB);
    			
    			//trener - start (automatycznie po zalogowaniu) / tren - start
    			VBox tren_start = new VBox();
    			tren_start.setId("tren_startVBox");
    			Label tren_startTF1 = new Label("Zalogowano jako: ");
    			Label tren_startTF2 = new Label("Rok urodzenia: ");
    			Label tren_startTF3 = new Label("Telefon kontaktowy: ");
    			Label tren_startTF4 = new Label("Adres: ");
    			Label tren_startTF5 = new Label("Klub: ");
    			//sesja do tren_Start
    			Session session_trenStart = factory.openSession();
    			Transaction t_TrenStart = session_trenStart.beginTransaction();
    			try {
	    			//info startowe
    				tren_startTF1.setText(tren_startTF1.getText() + tZal.getImie() + " " + tZal.getNazwisko() + " (trener)");
    				//rok urodzenia
    				tren_startTF2.setText(tren_startTF2.getText() + tZal.getRokUrodzenia() );
    				//telefon
    				if(tZal.getNrTelefonu() != null)
    					tren_startTF3.setText(tren_startTF3.getText() + tZal.getNrTelefonu() );
    				else
    					tren_startTF3.setText("-brak numeru-");
    				//adres
    				if(tZal.getAdres_id() != null)
    				{
    					tren_startTF4.setText(tren_startTF4.getText() + tZal.getAdres_id().getUlica() + 
    							" " + tZal.getAdres_id().getNrDomu() );
    					if(tZal.getAdres_id().getNrMieszkania() != null)
    						tren_startTF4.setText(tren_startTF4.getText() + "/" + tZal.getAdres_id().getNrMieszkania());
    					if(tZal.getAdres_id().getKodPocztowy() != null)
    						tren_startTF4.setText(tren_startTF4.getText() + ", " + tZal.getAdres_id().getKodPocztowy() );
    					tren_startTF4.setText(tren_startTF4.getText() + " " + tZal.getAdres_id().getMiasto_id().getNazwa() );
    				}
    				else 
    				{
    					tren_startTF4.setText("-brak adresu-");
    				}
    				//klub
    				if(tZal.getKlub_id() != null) {
    					tren_startTF5.setText(tren_startTF5.getText() + tZal.getKlub_id().getNazwa() );
    				}
    				else 
    				{
    					tren_startTF5.setText("-brak klubu-");
    				}
    				//
    				tren_start.getChildren().addAll(tren_startTF1, tren_startTF2, tren_startTF3, tren_startTF4, tren_startTF5);
    				panel_prawy.getChildren().addAll(tren_start);
    			}finally {
    				t_TrenStart.commit();
    				session_trenStart.close();
    			}
    			
    			//trener - start 
    			tren_startB.setOnAction(tStart_a->{
    				panel_prawy.getChildren().clear();
    				//info startowe
    				tren_startTF1.setText("Zalogowano jako: " + tZal.getImie() + " " + tZal.getNazwisko() + " (trener)");
    				//rok urodzenia
    				tren_startTF2.setText("Rok urodzenia: " + tZal.getRokUrodzenia() );
    				//telefon
    				if(tZal.getNrTelefonu() != null)
    					tren_startTF3.setText("Telefon kontaktowy: " + tZal.getNrTelefonu() );
    				else
    					tren_startTF3.setText("-brak numeru-");
    				//adres
    				if(tZal.getAdres_id() != null)
    				{
    					tren_startTF4.setText("Adres: " + tZal.getAdres_id().getUlica() + 
    							" " + tZal.getAdres_id().getNrDomu() );
    					if(tZal.getAdres_id().getNrMieszkania() != null)
    						tren_startTF4.setText(tren_startTF4.getText() + "/" + tZal.getAdres_id().getNrMieszkania());
    					if(tZal.getAdres_id().getKodPocztowy() != null)
    						tren_startTF4.setText(tren_startTF4.getText() + ", " + tZal.getAdres_id().getKodPocztowy() );
    					tren_startTF4.setText(tren_startTF4.getText() + " " + tZal.getAdres_id().getMiasto_id().getNazwa() );
    				}
    				else 
    				{
    					tren_startTF4.setText("-brak adresu-");
    				}
    				//klub
    				if(tZal.getKlub_id() != null) {
    					tren_startTF5.setText("Klub: " + tZal.getKlub_id().getNazwa() );
    				}
    				else 
    				{
    					tren_startTF5.setText("-brak klubu-");
    				}
    				//
    				tren_start.getChildren().clear();
    				tren_start.getChildren().addAll(tren_startTF1, tren_startTF2, tren_startTF3, tren_startTF4, tren_startTF5);
    				panel_prawy.getChildren().addAll(tren_start);
    			});
    			
    			//trener - grupy
    			tren_grupyB.setOnAction(tGrupy_a->{
    				panel_prawy.getChildren().clear();
    				//sesja do zaw_zaw
    				Session sessionTrenGrupy = factory.openSession();
        			Transaction t_TrenGrupy = sessionTrenGrupy.beginTransaction();
        			//
        			VBox tren_grupy = new VBox();
        			tren_grupy.setId("tren_grupyVBox");
	    			Label tren_grupyTF1 = new Label("Grupy: ");
        			try {
        				//lista grup, które prowadzi TRENER
        				@SuppressWarnings("unchecked")
    					List<Grupa> wynikTrenGrup = sessionTrenGrupy.createQuery("from Grupa where trener_id=" + tZal.getId() ).getResultList();
        				//list LABELi, które bêd¹ zawieraæ szczegó³y o grupach
	    				ArrayList<Label> tren_grupy_Labels = new ArrayList<Label>();
	    				//dodanie grup do ArrayListy 'tren_grupy_Labels' 				
	    				for(int i=0; i<wynikTrenGrup.size(); i++)
	    				{
	    					//list zawodników, którzy s¹ w danej GRUPIE trenera
	    					@SuppressWarnings("unchecked")
							List<Zawodnik> wynikTrenGroupIloscZaw = sessionTrenGrupy.createQuery("from Zawodnik where grupa_id=" + 
	    															wynikTrenGrup.get(i).getId() ).getResultList();
	    					tren_grupy_Labels.add(new Label("Grupa: " + wynikTrenGrup.get(i).getNazwa() +
	    							", kategoria: " + wynikTrenGrup.get(i).getKategoria_id().getNazwa() +
	    							", liczba zawodników: " + wynikTrenGroupIloscZaw.size() ) );
	    				}
        				tren_grupy.getChildren().addAll(tren_grupy_Labels);
        				panel_prawy.getChildren().addAll(tren_grupyTF1, tren_grupy);
        			}finally {
        				t_TrenGrupy.commit();
        				sessionTrenGrupy.close();
        			}
    			});
    			
    			//trener - treningi
    			tren_treningiB.setOnAction(tTreningi_a->{
    				panel_prawy.getChildren().clear();
    				//sesja do trener_treningi
        			Session sessionTrenTreningi = factory.openSession();
        			Transaction t_TrenTreningi = sessionTrenTreningi.beginTransaction();
        			try {
        				//lista wszystkich zajec
        				@SuppressWarnings("unchecked")
						List<Zajecia> wynikTrenTreningi = sessionTrenTreningi.createQuery("from Zajecia order by grupa_id asc, dzienTygodnia_id asc").getResultList();
        				//tablela zajec danego TRENERA
        				TableView<Zajecia> TV_trenTreningi = new TableView<>();
        				//kol-dzien tygodnia
        				TableColumn<Zajecia, String> TC_trenTreningi_dzien = new TableColumn<>("Dzieñ tygodnia");
        				TC_trenTreningi_dzien.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getDzienTygodnia_id().getNazwa() );
							}
        	            });
        				TC_trenTreningi_dzien.setMinWidth(160);
        				//kol-godz rozpoczecia
        				TableColumn<Zajecia, String> TC_trenTreningi_godzRozp = new TableColumn<>("Godz. rozpoczêcia");
        				TC_trenTreningi_godzRozp.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
							@SuppressWarnings("deprecation")
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
								Integer min = arg0.getValue().getGodzRozpoczecia().getMinutes();
								String minuty=Integer.toString(min);
								if(min==0)
									minuty = "00";
								return new SimpleStringProperty(Integer.toString(arg0.getValue().getGodzRozpoczecia().getHours()) + ":" +minuty);
							}
        	            });
        				TC_trenTreningi_godzRozp.setMinWidth(190);
        				//kol-godz zakonczenia
        				TableColumn<Zajecia, String> TC_trenTreningi_godzZak = new TableColumn<>("Godz. zakoñczenia");
        				TC_trenTreningi_godzZak.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
							@SuppressWarnings("deprecation")
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
								Integer min = arg0.getValue().getGodzZakonczenia().getMinutes();
								String minuty=Integer.toString(min);
								if(min==0)
									minuty = "00";
								return new SimpleStringProperty(Integer.toString(arg0.getValue().getGodzZakonczenia().getHours()) + ":" +minuty);
							}
        	            });
        				TC_trenTreningi_godzZak.setMinWidth(190);
        				//kol-grupa (nazwa)
        				TableColumn<Zajecia, String> TC_trenTreningi_grpNazwa = new TableColumn<>("Nazwa grupy");
        				TC_trenTreningi_grpNazwa.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
							@SuppressWarnings("deprecation")
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getGrupa_id().getNazwa() );
							}
        	            });
        				TC_trenTreningi_grpNazwa.setMinWidth(170);
        				//kol-sala (adres)
        				TableColumn<Zajecia, String> TC_trenTreningi_salaAdres = new TableColumn<>("Adres sali");
        				TC_trenTreningi_salaAdres.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
							@SuppressWarnings("deprecation")
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getSala_id().getAdres_id().getUlica() +
										" " + arg0.getValue().getSala_id().getAdres_id().getNrDomu() );
							}
        	            });
        				TC_trenTreningi_salaAdres.setMinWidth(180);
        				//lista obiektow - przepisanie elementow list "wynikTrenTreningi" do list "OL_trenTreningi"
        				ObservableList<Zajecia> OL_trenTreningi = FXCollections.observableArrayList();
        				for(int i=0; i<wynikTrenTreningi.size(); i++)
        				{
        					//je¿eli zajêcia s¹ grupy, która jest danego TRENERA //zajêcia->grupa->trener
        					//if(wynikTrenTreningi.get(i).getGrupa_id().getTrener_id().getId() == tZal.getId() )
        						OL_trenTreningi.add( wynikTrenTreningi.get(i) );
        				}
        				//ustawienie kolumn
        				TV_trenTreningi.getColumns().addAll(TC_trenTreningi_dzien, TC_trenTreningi_godzRozp, TC_trenTreningi_godzZak, TC_trenTreningi_grpNazwa, TC_trenTreningi_salaAdres);
        				//dodanie elementow list do tabeli
        				TV_trenTreningi.setItems(OL_trenTreningi);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_trenTreningi);
        			}finally {
        				t_TrenTreningi.commit();
        				sessionTrenTreningi.close();
        			}	
    			});
    			
    			//trener - zawodnicy 
    			//
    			tren_zawB.setOnAction(tZaw_a->
    			{
    				panel_prawy.getChildren().clear();
    				//sesja do tren_zaw
        			Session sessionTrenZaw = factory.openSession();
        			Transaction t_TrenZaw = sessionTrenZaw.beginTransaction();
        			try {
        				@SuppressWarnings("unchecked")
						List<Zawodnik> wynikTrenZaw = sessionTrenZaw.createQuery("from Zawodnik where klub_id=" + tZal.getKlub_id().getId() + 
																				"order by grupa_id desc, pozycja_id desc" ).getResultList();
        				//tablela
        				TableView<Zawodnik> TV_TrenZaw = new TableView<>();
        				//kol-imie
        				TableColumn<Zawodnik, String> TC_TrenZaw_imie = new TableColumn<>("Imie");
        				TC_TrenZaw_imie.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("imie"));
        				TC_TrenZaw_imie.setMinWidth(100);
        				//kol-nazwisko
        				TableColumn<Zawodnik, String> TC_TrenZaw_nazw = new TableColumn<>("Nazwisko");
        				TC_TrenZaw_nazw.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("nazwisko"));
        				TC_TrenZaw_nazw.setMinWidth(110);
        				//kol-rokUr
        				TableColumn<Zawodnik, Integer> TC_TrenZaw_rok = new TableColumn<>("Rok ur.");
        				TC_TrenZaw_rok.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("rokUrodzenia"));
        				TC_TrenZaw_rok.setMinWidth(90);
        				//kol-wzrost
        				TableColumn<Zawodnik, Integer> TC_TrenZaw_wzrost = new TableColumn<>("Wzrost");
        				TC_TrenZaw_wzrost.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("wzrost"));
        				TC_TrenZaw_wzrost.setMinWidth(85);
        				//kol-waga
        				TableColumn<Zawodnik, Integer> TC_TrenZaw_waga = new TableColumn<>("Waga");
        				TC_TrenZaw_waga.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("waga"));
        				TC_TrenZaw_waga.setMinWidth(75);
        				//kol-nrTel
        				TableColumn<Zawodnik, Integer> TC_TrenZaw_tel = new TableColumn<>("Nr tel.");
        				TC_TrenZaw_tel.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("nrTelefonu"));
        				TC_TrenZaw_tel.setMinWidth(95);
        				//kol-grupa
        				TableColumn<Zawodnik, String> TC_TrenZaw_grupa = new TableColumn<>("Grupa");
        				TC_TrenZaw_grupa.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getGrupa_id().getNazwa() );
							}
        	            });
        				TC_TrenZaw_grupa.setMinWidth(110);
        				//kol-pozycja
        				TableColumn<Zawodnik, String> TC_TrenZaw_pozycja = new TableColumn<>("Pozycja");
        				TC_TrenZaw_pozycja.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
							@Override
							public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getPozycja_id().getNazwa() );
							}
        	            });
        				TC_TrenZaw_pozycja.setMinWidth(100);
        				//lista obiektow - przepisanie elementow list "wynikZawZaw" do list "OL_zaw"
        				ObservableList<Zawodnik> OL_zaw = FXCollections.observableArrayList();
        				for(int i=0; i<wynikTrenZaw.size(); i++)
        				{
        					OL_zaw.add( wynikTrenZaw.get(i) );
        				}
        				//ustawienie kolumn
        				TV_TrenZaw.getColumns().addAll(TC_TrenZaw_imie, TC_TrenZaw_nazw, TC_TrenZaw_rok, TC_TrenZaw_wzrost, TC_TrenZaw_waga, TC_TrenZaw_tel, TC_TrenZaw_grupa, TC_TrenZaw_pozycja);
        				//dodanie elementow list do tabeli
        				TV_TrenZaw.setItems(OL_zaw);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_TrenZaw);
        			}finally {
        				t_TrenZaw.commit();
        				sessionTrenZaw.close();
        			}
    			});
    			//
    			
    			//trener - trenerzy
    			//
    			tren_trenB.setOnAction(tTren_a->{
    				panel_prawy.getChildren().clear();
    				//sesja do zaw_trener
        			Session sessionTrenTrener = factory.openSession();
        			Transaction t_TrenTrener = sessionTrenTrener.beginTransaction();
        			try {
        				@SuppressWarnings("unchecked")
						List<Trener> wynikTrenTrener = sessionTrenTrener.createQuery("from Trener where klub_id=" + tZal.getKlub_id().getId() ).getResultList();
        				//tablela
        				TableView<Trener> TV_TrenTrener = new TableView<>();
        				//kol-imie
        				TableColumn<Trener, String> TC_TrenTrener_imie = new TableColumn<>("Imie");
        				TC_TrenTrener_imie.setCellValueFactory(new PropertyValueFactory<Trener, String>("imie"));
        				TC_TrenTrener_imie.setMinWidth(100);
        				//kol-nazwisko
        				TableColumn<Trener, String> TC_TrenTrener_nazw = new TableColumn<>("Nazwisko");
        				TC_TrenTrener_nazw.setCellValueFactory(new PropertyValueFactory<Trener, String>("nazwisko"));
        				TC_TrenTrener_nazw.setMinWidth(110);
        				//kol-rokUr
        				TableColumn<Trener, Integer> TC_TrenTrener_rok = new TableColumn<>("Rok ur.");
        				TC_TrenTrener_rok.setCellValueFactory(new PropertyValueFactory<Trener, Integer>("rokUrodzenia"));
        				TC_TrenTrener_rok.setMinWidth(90);
        				//kol-nrTel
        				TableColumn<Trener, Integer> TC_TrenTrener_tel = new TableColumn<>("Nr tel.");
        				TC_TrenTrener_tel.setCellValueFactory(new PropertyValueFactory<Trener, Integer>("nrTelefonu"));
        				TC_TrenTrener_tel.setMinWidth(85);
        				//kol-klub
        				TableColumn<Trener, String> TC_TrenTrener_klub = new TableColumn<>("Grupa");
        				TC_TrenTrener_klub.setCellValueFactory(new Callback<CellDataFeatures<Trener, String>,ObservableValue<String>>(){
							@Override
							public ObservableValue<String> call(CellDataFeatures<Trener, String> arg0) {
								return new SimpleStringProperty(arg0.getValue().getKlub_id().getNazwa() );
							}
        	            });
        				TC_TrenTrener_klub.setMinWidth(200);
        				
        				//lista obiektow - przepisanie elementow list "wynikZawTrener" do list "OL_trener"
        				ObservableList<Trener> OL_trener = FXCollections.observableArrayList();
        				for(int i=0; i<wynikTrenTrener.size(); i++)
        				{
        					OL_trener.add( wynikTrenTrener.get(i) );
        				}
        				//ustawienie kolumn
        				TV_TrenTrener.getColumns().addAll(TC_TrenTrener_imie, TC_TrenTrener_nazw, TC_TrenTrener_rok, TC_TrenTrener_tel, TC_TrenTrener_klub);
        				//dodanie elementow list do tabeli
        				TV_TrenTrener.setItems(OL_trener);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_TrenTrener);
        			}finally {
        				t_TrenTrener.commit();
        				sessionTrenTrener.close();
        			}
    			});
    			//trener - edycja - dokonczyc
    			//
    			tren_edycjaB.setOnAction(tEdycja_a->
    			{
    				panel_prawy.getChildren().clear();
    				Label teL1_tel = new Label("nr tel ");
    				Label teL2_ul = new Label("ulica ");
    				Label teL3_dom = new Label("nr domu ");
    				Label teL4_mieszk = new Label("nr mieszkania ");
    				Label teL5_kod = new Label("kod pocztowy ");
    				Label teL6_hasl = new Label("has³o ");
    				TextField teTF1_tel = new TextField();
    				TextField teTF2_ul = new TextField();
    				TextField teTF3_dom = new TextField();
    				TextField teTF4_mieszk = new TextField();
    				TextField teTF5_kod = new TextField();
    				PasswordField teTF6_hasl = new PasswordField();
    				Button teB1_zmien = new Button("zmieñ dane"); 
    				teB1_zmien.setId("teB1");
    				Label teM_ogolnie = new Label();
    				Label teM1_tel = new Label();
    				Label teM2_ul = new Label();
    				Label teM3_dom = new Label();
    				Label teM4_mieszk = new Label();
    				Label teM5_kod = new Label();
    				Label teM6_hasl = new Label();
    				teM1_tel.setId("TrenEdycjaMessage");
    				teM2_ul.setId("TrenEdycjaMessage");
    				teM3_dom.setId("TrenEdycjaMessage");
    				teM4_mieszk.setId("TrenEdycjaMessage");
    				teM5_kod.setId("TrenEdycjaMessage");
    				teM6_hasl.setId("TrenEdycjaMessage");
    				Label teLP1_tel = new Label("?");
    				Label teLP2_ul = new Label("?");
    				Label teLP3_dom = new Label("?");
    				Label teLP4_mieszk = new Label("?");
    				Label teLP5_kod = new Label("?");
    				Label teLP6_hasl = new Label("?");
    				teLP1_tel.setId("zeLP3_tel");
    				teLP2_ul.setId("zeLP4_ul");
    				teLP3_dom.setId("zeLP5_dom");
    				teLP4_mieszk.setId("zeLP6_mieszk");
    				teLP5_kod.setId("zeLP7_kod");
    				teLP6_hasl.setId("zeLP8_hasl");
    				teLP1_tel.setTooltip(new Tooltip("9-cyfrowy numer, np.'600500400'") );
    				teLP2_ul.setTooltip(new Tooltip("nazwa ulicy wielk¹ lietr¹, np.'Karpacka'") );
    				teLP3_dom.setTooltip(new Tooltip("numer domu, np.'1a', '606'") );
    				teLP4_mieszk.setTooltip(new Tooltip("numer mieszkania, np.'1', '104'") );
    				teLP5_kod.setTooltip(new Tooltip("kod pocztowy w formacie LL-LLL, np.'19-400'") );
    				teLP6_hasl.setTooltip(new Tooltip("haslo-minimum 5 znaków") );
    				VBox tren_edycjaL = new VBox();  tren_edycjaL.setId("teL");
    				tren_edycjaL.setSpacing(11);
    				VBox tren_edycjaTF = new VBox(); tren_edycjaTF.setId("teTF");
    				tren_edycjaTF.setSpacing(10);
    				VBox tren_edycjaLP = new VBox();// edycja label podpowiedŸ
    				tren_edycjaLP.setSpacing(11);
    				VBox tren_edycjaM = new VBox(); tren_edycjaM.setId("teM");
    				tren_edycjaM.setSpacing(11);
    				tren_edycjaL.getChildren().addAll(teL1_tel, teL2_ul, teL3_dom, teL4_mieszk, teL5_kod, teL6_hasl);
    				tren_edycjaTF.getChildren().addAll(teTF1_tel, teTF2_ul, teTF3_dom, teTF4_mieszk, teTF5_kod, teTF6_hasl, teB1_zmien, teM_ogolnie);
    				tren_edycjaLP.getChildren().addAll(teLP1_tel, teLP2_ul, teLP3_dom, teLP4_mieszk, teLP5_kod, teLP6_hasl);
    				tren_edycjaM.getChildren().addAll(teM1_tel, teM2_ul, teM3_dom, teM4_mieszk, teM5_kod, teM6_hasl);				
    				HBox zaw_edycjaHBox = new HBox(tren_edycjaL, tren_edycjaTF, tren_edycjaLP, tren_edycjaM);
    				zaw_edycjaHBox.getChildren().addAll();
    				zaw_edycjaHBox.setId("zeHBox");
    				panel_prawy.getChildren().addAll(zaw_edycjaHBox);		
    				//wczytanie danych do pól tekstowych
					teTF1_tel.setText( Integer.toString(tZal.getNrTelefonu()) );
					teTF2_ul.setText( tZal.getAdres_id().getUlica() );
					teTF3_dom.setText( tZal.getAdres_id().getNrDomu() );
					teTF4_mieszk.setText( Integer.toString(tZal.getAdres_id().getNrMieszkania()) );
					teTF5_kod.setText( tZal.getAdres_id().getKodPocztowy() );
					teTF6_hasl.setText( tZal.getHaslo_() );
    				//akcja przycisku zatwierdzajacego zmiane
    				teB1_zmien.setOnAction(teZmien_a->{
    					//sesja do tren_edycja_zmien
            			Session sessionTrenEdycja_zmien = factory.openSession();
            			try {
	    					//regex
	    					//nr tel
	    					Pattern nrTelP = Pattern.compile("[1-9][0-9]{8}");
	    					Matcher testNrTel = nrTelP.matcher(teTF1_tel.getText() );
	    					if(testNrTel.matches())
	    						teM1_tel.setText(" ok");
	    					else 
	    						teM1_tel.setText(" niepoprawny nr tel");
	    					//ulica
	    					Pattern ulicaP = Pattern.compile("[A-Za-z ]{3,50}");
	    					Matcher testUlica = ulicaP.matcher(teTF2_ul.getText() );
	    					if(testUlica.matches())
	    						teM2_ul.setText(" ok");
	    					else 
	    						teM2_ul.setText(" niepoprawna nazwa ulicy");
	    					//nr domu
	    					Pattern nrDomuP = Pattern.compile("[1-9][0-9]{0,5}[A-Za-z]{0,1}");
	    					Matcher testNrDomu = nrDomuP.matcher(teTF3_dom.getText() );
	    					if(testNrDomu.matches())
	    						teM3_dom.setText(" ok");
	    					else 
	    						teM3_dom.setText(" niepoprawny nr domu");
	    					//nr mieszkania
	    					Pattern nrMieszkP = Pattern.compile("[1-9][0-9]{0,3}");
	    					Matcher testNrMieszk = nrMieszkP.matcher(teTF4_mieszk.getText() );
	    					if(testNrMieszk.matches())
	    						teM4_mieszk.setText(" ok");
	    					else 
	    						teM4_mieszk.setText(" niepoprawny nr mieszkania");
	    					//kod pocztowy
	    					Pattern kodPocztP = Pattern.compile("[0-9]{2}[-][0-9]{3}");
	    					Matcher testKodPoczt = kodPocztP.matcher(teTF5_kod.getText() );
	    					if(testKodPoczt.matches())
	    						teM5_kod.setText(" ok");
	    					else 
	    						teM5_kod.setText(" niepoprawny kod pocztowy");
	    					//haslo
	    					Pattern hasloP = Pattern.compile("[A-Za-z0-9]{5,32}");
	    					Matcher testHaslo = hasloP.matcher(teTF6_hasl.getText() );
	    					if(testHaslo.matches())
	    						teM6_hasl.setText(" ok");
	    					else 
	    						teM6_hasl.setText(" niepoprawne has³o");
	    					
	    					if(testNrTel.matches() && testUlica.matches() && testNrDomu.matches() && 
	    							testNrMieszk.matches() &&  testKodPoczt.matches() && testHaslo.matches() )
	    					{
	    						tZal.setNrTelefonu( Integer.parseInt(teTF1_tel.getText()) );
	    						tZal.getAdres_id().setUlica( teTF2_ul.getText() );
	    						tZal.getAdres_id().setNrDomu( teTF3_dom.getText() );
	    						tZal.getAdres_id().setNrMieszkania( Integer.parseInt(teTF4_mieszk.getText()) );
	    						tZal.getAdres_id().setKodPocztowy( teTF5_kod.getText() );
	    						tZal.setHaslo_( teTF6_hasl.getText() );
	    						teM_ogolnie.setText( "poprawne dane" );
	    					}
	    					else
	    					{
	    						teM_ogolnie.setText( "b³êdne dane" );
	    					}
        				}finally {
        				Transaction t_TrenEdycja_zmien = sessionTrenEdycja_zmien.beginTransaction();
        				sessionTrenEdycja_zmien.update(tZal);
        				t_TrenEdycja_zmien.commit();	
        				sessionTrenEdycja_zmien.close();
        				}
    				});
    			});
    			//
    			
    			//trener - wyloguj
    			tren_wylogujB.setOnAction(tWyloguj_a->
    			{
    				//przywrócenie logowanie HBox zawierajacego 2xVbox(etykiety, pola)
    				panel_lewy.getChildren().clear();
    				panel_prawy.getChildren().clear();
    				panel_prawy.getChildren().addAll(logowanie);
    				//wyczyszczenie message label z ew. informacji "b³êdny login lub/i has³o"
    				messgL.setText("");
    			});
    		}
    		//1-administrator
    		else if(zal==1)
    		{
    			//dokonczyc - zmienic kolejnosc wyswietlania :)
    			Button adm_startB = new Button("Start");
    			Button adm_tabDzienTygB = new Button("Tabla DzienTyg");
    			Button adm_tabMiastoB = new Button("Tabela Miasto");
    			Button adm_tabPozycjaB = new Button("Tabela Pozycja");
    			Button adm_tabKategoriaB = new Button("Tabela Kategoria");
    			Button adm_tabKlubB = new Button("Tabela Klub");
    			Button adm_tabAdresB = new Button("Tabela Adres");
    			Button adm_tabTrenerB = new Button("Tabela Trener");
    			Button adm_tabGrupaB = new Button("Tabela Grupa");
    			Button adm_tabSalaB = new Button("Tabela Sala");
    			Button adm_tabZajeciaB = new Button("Tabela Zajecia");
    			Button adm_tabZawodnikB = new Button("Tabela Zawodnik");
    			Button adm_tabAdminB = new Button("Tabela Administrator");
    			Button adm_WylogujB = new Button("wyloguj");
    			adm_startB.setId("adm_lewyButtons");
    			adm_tabDzienTygB.setId("adm_lewyButtons");
    			adm_tabMiastoB.setId("adm_lewyButtons");
    			adm_tabPozycjaB.setId("adm_lewyButtons");
    			adm_tabKategoriaB.setId("adm_lewyButtons");
    			adm_tabKlubB.setId("adm_lewyButtons");
    			adm_tabAdresB.setId("adm_lewyButtons");
    			adm_tabTrenerB.setId("adm_lewyButtons");
    			adm_tabGrupaB.setId("adm_lewyButtons");
    			adm_tabSalaB.setId("adm_lewyButtons");
    			adm_tabZajeciaB.setId("adm_lewyButtons");
    			adm_tabZawodnikB.setId("adm_lewyButtons");
    			adm_tabAdminB.setId("adm_lewyButtons");
    			adm_WylogujB.setId("adm_lewyButtons");
    			//wyczyszczenie panelu prawego, dodanie przycisków do panelu lewego
    			panel_prawy.getChildren().clear();
    			panel_lewy.getChildren().addAll(adm_startB, adm_tabDzienTygB, adm_tabMiastoB, adm_tabPozycjaB, adm_tabKategoriaB, adm_tabKlubB, adm_tabAdresB, 
    											adm_tabTrenerB, adm_tabGrupaB, adm_tabSalaB, adm_tabZajeciaB, adm_tabZawodnikB, adm_tabAdminB, adm_WylogujB);
    			
    			//adm - start (automatycznie po zalogowaniu) / administrator - start / admin - start
    			VBox adm_start = new VBox();
    			adm_start.setId("adm_startVBox");
    			Label adm_startTF1 = new Label("Zalogowano jako: " + aZal.getImie() + " " + aZal.getNazwisko() + " (administrator)");
    			//
    			adm_start.getChildren().addAll(adm_startTF1);
				panel_prawy.getChildren().addAll(adm_start);
				
				//adm - start 
				adm_startB.setOnAction(aStart_a->{
					panel_prawy.getChildren().clear();
					adm_start.getChildren().clear();
					adm_startTF1.setText("Zalogowano jako: " + aZal.getImie() + " " + aZal.getNazwisko() + " (administrator)");
					adm_start.getChildren().addAll(adm_startTF1);
					panel_prawy.getChildren().addAll(adm_start);
				});
				
				//adm - dzienTyg
				adm_tabDzienTygB.setOnAction(adm_DzienTyg_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"DzienTygodnia\"");
        			panel_prawy.getChildren().addAll(tabela);
					//sesja do adm_dzienTyg
        			Session sessionAdmDzienTyg = factory.openSession();
        			Transaction t_AdmDzienTyg = sessionAdmDzienTyg.beginTransaction();
        			//tablela dni tygodnia
    				TableView<DzienTygodnia> TV_adm_dzienTyg = new TableView<>();
    				//kol-id dzienTyg
    				TableColumn<DzienTygodnia, Integer> TC_admDzienTyg_id = new TableColumn<>("Id");
    				TC_admDzienTyg_id.setCellValueFactory(new PropertyValueFactory<DzienTygodnia, Integer>("id"));
    				TC_admDzienTyg_id.setMinWidth(440);
    				//kol-nazwa dnia
    				TableColumn<DzienTygodnia, String> TC_admDzienTyg_nazwa = new TableColumn<>("Nazwa");
    				TC_admDzienTyg_nazwa.setCellValueFactory(new PropertyValueFactory<DzienTygodnia, String>("nazwa"));
    				TC_admDzienTyg_nazwa.setMinWidth(440);
        			try {
        				//lista wszystkich dni tygodnia
        				@SuppressWarnings("unchecked")
						List<DzienTygodnia> wynikAdmDzienTyg = sessionAdmDzienTyg.createQuery("from DzienTygodnia").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<DzienTygodnia> OL_admDzienTyg = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmDzienTyg.size(); i++)
        				{
        					OL_admDzienTyg.add( wynikAdmDzienTyg.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_dzienTyg.getColumns().addAll(TC_admDzienTyg_id, TC_admDzienTyg_nazwa);
        				//dodanie elementow list do tabeli
        				TV_adm_dzienTyg.setItems(OL_admDzienTyg);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_dzienTyg);
        			}finally {
        				t_AdmDzienTyg.commit();
        				sessionAdmDzienTyg.close();
        			}
        			//dodanie przycisków pod tabel¹ 
        			HBox adm_dzienTyg_HB = new HBox();
        			adm_dzienTyg_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_dzienTyd_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_dzienTyd_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_dzienTyd_usun = new Button("Usuñ zaznaczony rekord");
        			adm_dzienTyg_HB.getChildren().addAll(adm_dzienTyd_dodaj, adm_dzienTyd_zmien, adm_dzienTyd_usun);
        			panel_prawy.getChildren().addAll(adm_dzienTyg_HB);
        			//adm - dodaj dzien
        			adm_dzienTyd_dodaj.setOnAction(adm_DzienTyg_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				HBox adm_dzienTyg_dodaj_nazwaHB = new HBox();
        				adm_dzienTyg_dodaj_nazwaHB.setId("adm_dzienTyg_dodaj_nazwaHB");
        				Label adm_dzienTyg_dodaj_nazwaL = new Label("Podaj nazwê: ");
        				TextField adm_dzienTyg_dodaj_nazwaTF = new TextField();
        				Label adm_dzienTyg_dodaj_nazwaM = new Label();
        				adm_dzienTyg_dodaj_nazwaM.getStyleClass().add("adm_1messg");
        				Button adm_dzienTyg_dodaj_dodajB = new Button("dodaj");
        				adm_dzienTyg_dodaj_nazwaHB.getChildren().addAll(adm_dzienTyg_dodaj_nazwaL, adm_dzienTyg_dodaj_nazwaTF, adm_dzienTyg_dodaj_nazwaM, adm_dzienTyg_dodaj_dodajB);
        				Label adm_dzienTyg_dodaj_messg = new Label();
        				panel_prawy.getChildren().addAll(adm_dzienTyg_dodaj_nazwaHB, adm_dzienTyg_dodaj_messg);
        				adm_dzienTyg_dodaj_dodajB.setOnAction(adm_dt_dodaj_dodaj_a->{
        					//wzorzec
            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,15}");
        					Matcher testNazwa = nazwaP.matcher(adm_dzienTyg_dodaj_nazwaTF.getText() );
        					if(testNazwa.matches())
        					{
        						adm_dzienTyg_dodaj_nazwaM.setText(" ok");
        						adm_dzienTyg_dodaj_messg.setText("dodano nowy rekord");
        						DzienTygodnia nowyDzienTyg = new DzienTygodnia(adm_dzienTyg_dodaj_nazwaTF.getText());
        						//sesja do adm_dzienTyg - dodaj
        	        			Session sessionAdmDzienTyg_dodaj = factory.openSession();
        	        			Transaction t_AdmDzienTyg_dodaj = sessionAdmDzienTyg_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmDzienTyg_dodaj.save(nowyDzienTyg);
        	        				t_AdmDzienTyg_dodaj.commit();
        	        			}catch (Exception e){
                    				if (t_AdmDzienTyg_dodaj != null) {
                    					t_AdmDzienTyg_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
        	        				sessionAdmDzienTyg_dodaj.close();
        	        			}
        					}
        					else 
        					{
        						adm_dzienTyg_dodaj_nazwaM.setText(" niepoprawna nazwa");
        						adm_dzienTyg_dodaj_messg.setText("niepoprawne dane");
        					}
        				});
        			});
        			//adm - zmien dzien
        			adm_dzienTyd_zmien.setOnAction(adm_DzienTyg_zmien_a->{
        				if(TV_adm_dzienTyg.getSelectionModel().getSelectedItem() != null)
        				{
	        				panel_prawy.getChildren().clear();
	        				HBox adm_dzienTyg_zmien_nazwaHB = new HBox();
	        				adm_dzienTyg_zmien_nazwaHB.setId("adm_dzienTyg_dodaj_nazwaHB");
	        				Label adm_dzienTyg_zmien_nazwaL = new Label("Podaj nazwê: ");
	        				TextField adm_dzienTyg_zmien_nazwaTF = new TextField();
	        				Label adm_dzienTyg_zmien_nazwaM = new Label();
	        				adm_dzienTyg_zmien_nazwaM.getStyleClass().add("adm_1messg");
	        				Button adm_dzienTyg_zmien_zmienB = new Button("zmieñ");
	        				adm_dzienTyg_zmien_nazwaHB.getChildren().addAll(adm_dzienTyg_zmien_nazwaL, adm_dzienTyg_zmien_nazwaTF, adm_dzienTyg_zmien_nazwaM, adm_dzienTyg_zmien_zmienB);
	        				Label adm_dzienTyg_zmien_messg = new Label();
	        				panel_prawy.getChildren().addAll(adm_dzienTyg_zmien_nazwaHB, adm_dzienTyg_zmien_messg);
	        				//zaznaczony dzien tygodnia
	        				DzienTygodnia zaznaczonyDzien = TV_adm_dzienTyg.getSelectionModel().getSelectedItem();
	        				//wypelnienie TF danymi ZAZNACZONEGO DNIA
	        				adm_dzienTyg_zmien_nazwaTF.setText(zaznaczonyDzien.getNazwa() );
	        				//adm - zmien dzien - zmien
	        				adm_dzienTyg_zmien_zmienB.setOnAction(adm_dt_dodaj_dodaj_a->{
	        					//wzorzec
	            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,15}");
	        					Matcher testNazwa = nazwaP.matcher(adm_dzienTyg_zmien_nazwaTF.getText() );
	        					if(testNazwa.matches())
	        					{
	        						adm_dzienTyg_zmien_nazwaM.setText(" ok");
	        						adm_dzienTyg_zmien_messg.setText("zmieniono rekord");
	        						zaznaczonyDzien.setNazwa( adm_dzienTyg_zmien_nazwaTF.getText() );
	        						//sesja do adm_dzienTyg - dodaj
	        	        			Session sessionAdmDzienTyg_zmien = factory.openSession();
	        	        			Transaction t_AdmDzienTyg_zmien = sessionAdmDzienTyg_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmDzienTyg_zmien.update(zaznaczonyDzien);
	        	        				t_AdmDzienTyg_zmien.commit();
	        	        			}catch (Exception e){
	                    				if (t_AdmDzienTyg_zmien != null) {
	                    					t_AdmDzienTyg_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmDzienTyg_zmien.close();
	        	        			}
	        					}
	        					else 
	        					{
	        						adm_dzienTyg_zmien_nazwaM.setText(" niepoprawna nazwa");
	        						adm_dzienTyg_zmien_messg.setText("niepoprawne dane");
	        					}
	        				});
        				}
        			});
        			//adm - usun dzien
        			adm_dzienTyd_usun.setOnAction(adm_DzienTyg_usun_a->{
        				//zaznaczony dzien tygodnia
        				DzienTygodnia zaznaczonyDzien = TV_adm_dzienTyg.getSelectionModel().getSelectedItem();
        				if(zaznaczonyDzien != null) 
        				{
	        				//sesja do adm_dzienTyg_usun
	            			Session sessionAdmDzienTyg_usun = factory.openSession();
	            			Transaction t_AdmDzienTyg_usun = sessionAdmDzienTyg_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego dnia
	            				sessionAdmDzienTyg_usun.remove(zaznaczonyDzien);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich dni tygodnia
	                			@SuppressWarnings("unchecked")
	        					List<DzienTygodnia> wynikAdmDzienTyg_usun = sessionAdmDzienTyg_usun.createQuery("from DzienTygodnia").getResultList();
	                			//lista obiektow - przepisanie elementow listy do OL_list
	                			ObservableList<DzienTygodnia> OL_admDzienTyg_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmDzienTyg_usun.size(); i++)
	                			{
	                				OL_admDzienTyg_usun.add( wynikAdmDzienTyg_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_dzienTyg.setItems(OL_admDzienTyg_usun);
	                			//
	                			t_AdmDzienTyg_usun.commit();
	                			sessionAdmDzienTyg_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmDzienTyg_usun != null) {
	            					t_AdmDzienTyg_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
        				}
        			});
				});
    			
				//adm - miasto
				adm_tabMiastoB.setOnAction(adm_Miasto_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Miasto\"");
        			panel_prawy.getChildren().addAll(tabela);
					//sesja do adm_miasto
        			Session sessionAdmMiasto = factory.openSession();
        			Transaction t_AdmMiasto = sessionAdmMiasto.beginTransaction();
        			//tablela miast
    				TableView<Miasto> TV_adm_miasto = new TableView<>();
    				//kol-id miasta
    				TableColumn<Miasto, Integer> TC_admMiasto_id = new TableColumn<>("Id");
    				TC_admMiasto_id.setCellValueFactory(new PropertyValueFactory<Miasto, Integer>("id"));
    				TC_admMiasto_id.setMinWidth(440);
    				//kol-nazwa miasta
    				TableColumn<Miasto, String> TC_admMiasto_nazwa = new TableColumn<>("Nazwa");
    				TC_admMiasto_nazwa.setCellValueFactory(new PropertyValueFactory<Miasto, String>("nazwa"));
    				TC_admMiasto_nazwa.setMinWidth(440);
    				try {
    					//lista wszystkich miast
        				@SuppressWarnings("unchecked")
						List<Miasto> wynikAdmMiasto = sessionAdmMiasto.createQuery("from Miasto").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Miasto> OL_admMiasto = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmMiasto.size(); i++)
        				{
        					OL_admMiasto.add( wynikAdmMiasto.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_miasto.getColumns().addAll(TC_admMiasto_id, TC_admMiasto_nazwa);
        				//dodanie elementow list do tabeli
        				TV_adm_miasto.setItems(OL_admMiasto);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_miasto);
    				}finally {
    					t_AdmMiasto.commit();
    					sessionAdmMiasto.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_miasto_HB = new HBox();
        			adm_miasto_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_miasto_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_miasto_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_miasto_usun = new Button("Usuñ zaznaczony rekord");
        			adm_miasto_HB.getChildren().addAll(adm_miasto_dodaj, adm_miasto_zmien, adm_miasto_usun);
        			panel_prawy.getChildren().addAll(adm_miasto_HB);
        			//adm - dodaj miasto
        			adm_miasto_dodaj.setOnAction(adm_miasto_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				HBox adm_miasto_dodaj_nazwaHB = new HBox();
        				adm_miasto_dodaj_nazwaHB.setId("adm_miasto_dodaj_nazwaHB");
        				Label adm_miasto_dodaj_nazwaL = new Label("Podaj nazwê: ");
        				TextField adm_miasto_dodaj_nazwaTF = new TextField();
        				Label adm_miasto_dodaj_nazwaM = new Label();
        				adm_miasto_dodaj_nazwaM.getStyleClass().add("adm_1messg");
        				Button adm_miasto_dodaj_dodajB = new Button("dodaj");
        				adm_miasto_dodaj_nazwaHB.getChildren().addAll(adm_miasto_dodaj_nazwaL, adm_miasto_dodaj_nazwaTF, adm_miasto_dodaj_nazwaM, adm_miasto_dodaj_dodajB);
        				Label adm_miasto_dodaj_messg = new Label();
        				panel_prawy.getChildren().addAll(adm_miasto_dodaj_nazwaHB, adm_miasto_dodaj_messg);
        				adm_miasto_dodaj_dodajB.setOnAction(adm_dt_dodaj_dodaj_a->{
        					//wzorzec
            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,40}");
        					Matcher testNazwa = nazwaP.matcher(adm_miasto_dodaj_nazwaTF.getText() );
        					if(testNazwa.matches())
        					{
        						adm_miasto_dodaj_nazwaM.setText(" ok");
        						adm_miasto_dodaj_messg.setText("dodano nowy rekord");
        						Miasto noweMiasto = new Miasto(adm_miasto_dodaj_nazwaTF.getText());
        						//sesja do adm_miasto - dodaj
        	        			Session sessionAdmMiasto_dodaj = factory.openSession();
        	        			Transaction t_AdmMiasto_dodaj = sessionAdmMiasto_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmMiasto_dodaj.save(noweMiasto);
        	        				t_AdmMiasto_dodaj.commit();
        	        			}catch (Exception e){
                    				if (t_AdmMiasto_dodaj != null) {
                    					t_AdmMiasto_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmMiasto_dodaj.close();
        	        			}
        					}
        					else 
        					{
        						adm_miasto_dodaj_nazwaM.setText(" niepoprawna nazwa");
        						adm_miasto_dodaj_messg.setText("niepoprawne dane");
        					}
        				});
        			});
        			//adm - zmien miasto
        			adm_miasto_zmien.setOnAction(adm_miasto_zmien_a->{
        				if(TV_adm_miasto.getSelectionModel().getSelectedItem() != null)
        				{
	        				panel_prawy.getChildren().clear();
	        				HBox adm_miasto_zmien_nazwaHB = new HBox();
	        				adm_miasto_zmien_nazwaHB.setId("adm_miasto_dodaj_nazwaHB");
	        				Label adm_miasto_zmien_nazwaL = new Label("Podaj nazwê: ");
	        				TextField adm_miasto_zmien_nazwaTF = new TextField();
	        				Label adm_miasto_zmien_nazwaM = new Label();
	        				adm_miasto_zmien_nazwaM.getStyleClass().add("adm_1messg");
	        				Button adm_miasto_zmien_zmienB = new Button("zmieñ");
	        				adm_miasto_zmien_nazwaHB.getChildren().addAll(adm_miasto_zmien_nazwaL, adm_miasto_zmien_nazwaTF, adm_miasto_zmien_nazwaM, adm_miasto_zmien_zmienB);
	        				Label adm_miasto_zmien_messg = new Label();
	        				panel_prawy.getChildren().addAll(adm_miasto_zmien_nazwaHB, adm_miasto_zmien_messg);
	        				//zaznaczony miasto 
	        				Miasto zaznaczoneMiasto = TV_adm_miasto.getSelectionModel().getSelectedItem();
	        				//wypelnienie TF danymi ZAZNACZONEGO MIASTA
	        				adm_miasto_zmien_nazwaTF.setText(zaznaczoneMiasto.getNazwa() );
	        				
	        				adm_miasto_zmien_zmienB.setOnAction(adm_dt_dodaj_dodaj_a->{
	        					//wzorzec
	            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,40}");
	        					Matcher testNazwa = nazwaP.matcher(adm_miasto_zmien_nazwaTF.getText() );
	        					if(testNazwa.matches())
	        					{
	        						adm_miasto_zmien_nazwaM.setText(" ok");
	        						adm_miasto_zmien_messg.setText("zmieniono rekord");
	        						zaznaczoneMiasto.setNazwa( adm_miasto_zmien_nazwaTF.getText() );
	        						//sesja do adm_miasto - dodaj
	        	        			Session sessionAdmMiasto_zmien = factory.openSession();
	        	        			Transaction t_AdmMiasto_zmien = sessionAdmMiasto_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmMiasto_zmien.update(zaznaczoneMiasto);
	        	        				t_AdmMiasto_zmien.commit();
	        	        			}catch (Exception e){
	                    				if (t_AdmMiasto_zmien != null) {
	                    					t_AdmMiasto_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmMiasto_zmien.close();
	        	        			}
	        					}
	        					else 
	        					{
	        						adm_miasto_zmien_nazwaM.setText(" niepoprawna nazwa");
	        						adm_miasto_zmien_messg.setText("niepoprawne dane");
	        					}
	        				});
        				}
        			});
        			//adm - usun miasto
        			adm_miasto_usun.setOnAction(adm_Miasto_usun_a->{
        				//zaznaczone miasto
        				Miasto zaznaczoneMiasto = TV_adm_miasto.getSelectionModel().getSelectedItem();
        				if(zaznaczoneMiasto != null) 
        				{
	        				//sesja do adm_Miasto_usun
	            			Session sessionAdmMiasto_usun = factory.openSession();
	            			Transaction t_AdmMiasto_usun = sessionAdmMiasto_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego miasta
	            				sessionAdmMiasto_usun.remove(zaznaczoneMiasto);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich miast
	                			@SuppressWarnings("unchecked")
	        					List<Miasto> wynikAdmMiasto_usun = sessionAdmMiasto_usun.createQuery("from Miasto").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Miasto> OL_admMiasto_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmMiasto_usun.size(); i++)
	                			{
	                				OL_admMiasto_usun.add( wynikAdmMiasto_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_miasto.setItems(OL_admMiasto_usun);
	                			//
	                			t_AdmMiasto_usun.commit();
	                			sessionAdmMiasto_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmMiasto_usun != null) {
	            					t_AdmMiasto_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
        				}
        			});
				}); 
				
				//adm - pozycja
				adm_tabPozycjaB.setOnAction(adm_pozycja_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Pozycja\"");
        			panel_prawy.getChildren().addAll(tabela);
					//sesja do adm_pozycja
        			Session sessionAdmPozycja = factory.openSession();
        			Transaction t_AdmPozycja = sessionAdmPozycja.beginTransaction();
        			//tablela pozycji
    				TableView<Pozycja> TV_adm_pozycja = new TableView<>();
    				//kol-id pozycji
    				TableColumn<Pozycja, Integer> TC_admPozycja_id = new TableColumn<>("Id");
    				TC_admPozycja_id.setCellValueFactory(new PropertyValueFactory<Pozycja, Integer>("id"));
    				TC_admPozycja_id.setMinWidth(440);
    				//kol-nazwa pozycji
    				TableColumn<Pozycja, String> TC_admPozycja_nazwa = new TableColumn<>("Nazwa");
    				TC_admPozycja_nazwa.setCellValueFactory(new PropertyValueFactory<Pozycja, String>("nazwa"));
    				TC_admPozycja_nazwa.setMinWidth(440);
    				try {
    					//lista wszystkich pozycji
        				@SuppressWarnings("unchecked")
						List<Pozycja> wynikAdmPozycja = sessionAdmPozycja.createQuery("from Pozycja").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Pozycja> OL_admPozycja = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmPozycja.size(); i++)
        				{
        					OL_admPozycja.add( wynikAdmPozycja.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_pozycja.getColumns().addAll(TC_admPozycja_id, TC_admPozycja_nazwa);
        				//dodanie elementow list do tabeli
        				TV_adm_pozycja.setItems(OL_admPozycja);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_pozycja);
    				}finally {
    					t_AdmPozycja.commit();
    					sessionAdmPozycja.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_pozycja_HB = new HBox();
        			adm_pozycja_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_pozycja_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_pozycja_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_pozycja_usun = new Button("Usuñ zaznaczony rekord");
        			adm_pozycja_HB.getChildren().addAll(adm_pozycja_dodaj, adm_pozycja_zmien, adm_pozycja_usun);
        			panel_prawy.getChildren().addAll(adm_pozycja_HB);
        			//adm - dodaj pozycja
        			adm_pozycja_dodaj.setOnAction(adm_pozycja_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				HBox adm_pozycja_dodaj_nazwaHB = new HBox();
        				adm_pozycja_dodaj_nazwaHB.setId("adm_pozycja_dodaj_nazwaHB");
        				Label adm_pozycja_dodaj_nazwaL = new Label("Podaj nazwê: ");
        				TextField adm_pozycja_dodaj_nazwaTF = new TextField();
        				Label adm_pozycja_dodaj_nazwaM = new Label();
        				adm_pozycja_dodaj_nazwaM.getStyleClass().add("adm_1messg");
        				Button adm_pozycja_dodaj_dodajB = new Button("dodaj");
        				adm_pozycja_dodaj_nazwaHB.getChildren().addAll(adm_pozycja_dodaj_nazwaL, adm_pozycja_dodaj_nazwaTF, adm_pozycja_dodaj_nazwaM, adm_pozycja_dodaj_dodajB);
        				Label adm_pozycja_dodaj_messg = new Label();
        				panel_prawy.getChildren().addAll(adm_pozycja_dodaj_nazwaHB, adm_pozycja_dodaj_messg);
        				adm_pozycja_dodaj_dodajB.setOnAction(adm_dt_dodaj_dodaj_a->{
        					//wzorzec
            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,50}");
        					Matcher testNazwa = nazwaP.matcher(adm_pozycja_dodaj_nazwaTF.getText() );
        					if(testNazwa.matches())
        					{
        						adm_pozycja_dodaj_nazwaM.setText(" ok");
        						adm_pozycja_dodaj_messg.setText("dodano nowy rekord");
        						Pozycja nowaPozycja = new Pozycja(adm_pozycja_dodaj_nazwaTF.getText());
        						//sesja do adm_pozycja - dodaj
        	        			Session sessionAdmPozycja_dodaj = factory.openSession();
        	        			Transaction t_AdmPozycja_dodaj = sessionAdmPozycja_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmPozycja_dodaj.save(nowaPozycja);
        	        				t_AdmPozycja_dodaj.commit();
        	        			}catch (Exception e){
                    				if (t_AdmPozycja_dodaj != null) {
                    					t_AdmPozycja_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmPozycja_dodaj.close();
        	        			}
        					}
        					else 
        					{
        						adm_pozycja_dodaj_nazwaM.setText(" niepoprawna nazwa");
        						adm_pozycja_dodaj_messg.setText("niepoprawne dane");
        					}
        				});
        			});
        			//adm - zmien pozycja
        			adm_pozycja_zmien.setOnAction(adm_pozycja_zmien_a->{
        				if(TV_adm_pozycja.getSelectionModel().getSelectedItem() != null)
        				{
	        				panel_prawy.getChildren().clear();
	        				HBox adm_pozycja_zmien_nazwaHB = new HBox();
	        				adm_pozycja_zmien_nazwaHB.setId("adm_pozycja_dodaj_nazwaHB");
	        				Label adm_pozycja_zmien_nazwaL = new Label("Podaj nazwê: ");
	        				TextField adm_pozycja_zmien_nazwaTF = new TextField();
	        				Label adm_pozycja_zmien_nazwaM = new Label();
	        				adm_pozycja_zmien_nazwaM.getStyleClass().add("adm_1messg");
	        				Button adm_pozycja_zmien_zmienB = new Button("zmieñ");
	        				adm_pozycja_zmien_nazwaHB.getChildren().addAll(adm_pozycja_zmien_nazwaL, adm_pozycja_zmien_nazwaTF, adm_pozycja_zmien_nazwaM, adm_pozycja_zmien_zmienB);
	        				Label adm_pozycja_zmien_messg = new Label();
	        				panel_prawy.getChildren().addAll(adm_pozycja_zmien_nazwaHB, adm_pozycja_zmien_messg);
	        				//zaznaczona pozycja 
	        				Pozycja zaznaczonepozycja = TV_adm_pozycja.getSelectionModel().getSelectedItem();
	        				//wypelnienie TF danymi ZAZNACZONEJ pozycji
	        				adm_pozycja_zmien_nazwaTF.setText(zaznaczonepozycja.getNazwa() );
	        				//adm - pozycja zmien - zmien
	        				adm_pozycja_zmien_zmienB.setOnAction(adm_dt_dodaj_dodaj_a->{
	        					//wzorzec
	            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,50}");
	        					Matcher testNazwa = nazwaP.matcher(adm_pozycja_zmien_nazwaTF.getText() );
	        					if(testNazwa.matches())
	        					{
	        						adm_pozycja_zmien_nazwaM.setText(" ok");
	        						adm_pozycja_zmien_messg.setText("zmieniono rekord");
	        						zaznaczonepozycja.setNazwa( adm_pozycja_zmien_nazwaTF.getText() );
	        						//sesja do adm_pozycja - dodaj
	        	        			Session sessionAdmPozycja_zmien = factory.openSession();
	        	        			Transaction t_AdmPozycja_zmien = sessionAdmPozycja_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmPozycja_zmien.update(zaznaczonepozycja);
	        	        				t_AdmPozycja_zmien.commit();
	        	        			}catch (Exception e){
	                    				if (t_AdmPozycja_zmien != null) {
	                    					t_AdmPozycja_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmPozycja_zmien.close();
	        	        			}
	        					}
	        					else 
	        					{
	        						adm_pozycja_zmien_nazwaM.setText(" niepoprawna nazwa");
	        						adm_pozycja_zmien_messg.setText("niepoprawne dane");
	        					}
	        				});
        				}
        			});
        			//adm - usun pozycja
        			adm_pozycja_usun.setOnAction(adm_pozycja_usun_a->{
        				//zaznaczone pozycja
        				Pozycja zaznaczonepozycja = TV_adm_pozycja.getSelectionModel().getSelectedItem();
        				if(zaznaczonepozycja != null) 
        				{
	        				//sesja do adm_pozycja_usun
	            			Session sessionAdmPozycja_usun = factory.openSession();
	            			Transaction t_AdmPozycja_usun = sessionAdmPozycja_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego pozycji
	            				sessionAdmPozycja_usun.remove(zaznaczonepozycja);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich pozycji
	                			@SuppressWarnings("unchecked")
	        					List<Pozycja> wynikAdmpozycja_usun = sessionAdmPozycja_usun.createQuery("from Pozycja").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Pozycja> OL_admPozycja_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmpozycja_usun.size(); i++)
	                			{
	                				OL_admPozycja_usun.add( wynikAdmpozycja_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_pozycja.setItems(OL_admPozycja_usun);
	                			//
	                			t_AdmPozycja_usun.commit();
	                			sessionAdmPozycja_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmPozycja_usun != null) {
	            					t_AdmPozycja_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
        				}
        			});
				}); 
				
				//adm - kategoria
				adm_tabKategoriaB.setOnAction(adm_kategoria_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Kategoria\"");
        			panel_prawy.getChildren().addAll(tabela);
					//sesja do adm_kategoria
        			Session sessionAdmKategoria = factory.openSession();
        			Transaction t_AdmKategoria = sessionAdmKategoria.beginTransaction();
        			//tablela kategorii
    				TableView<Kategoria> TV_adm_kategoria = new TableView<>();
    				//kol-id kategorii
    				TableColumn<Kategoria, Integer> TC_admKategoria_id = new TableColumn<>("Id");
    				TC_admKategoria_id.setCellValueFactory(new PropertyValueFactory<Kategoria, Integer>("id"));
    				TC_admKategoria_id.setMinWidth(440);
    				//kol-nazwa kategorii
    				TableColumn<Kategoria, String> TC_admKategoria_nazwa = new TableColumn<>("Nazwa");
    				TC_admKategoria_nazwa.setCellValueFactory(new PropertyValueFactory<Kategoria, String>("nazwa"));
    				TC_admKategoria_nazwa.setMinWidth(440);
    				try {
    					//lista wszystkich kategorii
        				@SuppressWarnings("unchecked")
						List<Kategoria> wynikAdmKategoria = sessionAdmKategoria.createQuery("from Kategoria").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Kategoria> OL_admKategoria = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmKategoria.size(); i++)
        				{
        					OL_admKategoria.add( wynikAdmKategoria.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_kategoria.getColumns().addAll(TC_admKategoria_id, TC_admKategoria_nazwa);
        				//dodanie elementow list do tabeli
        				TV_adm_kategoria.setItems(OL_admKategoria);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_kategoria);
    				}finally {
    					t_AdmKategoria.commit();
    					sessionAdmKategoria.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_kategoria_HB = new HBox();
        			adm_kategoria_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_kategoria_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_kategoria_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_kategoria_usun = new Button("Usuñ zaznaczony rekord");
        			adm_kategoria_HB.getChildren().addAll(adm_kategoria_dodaj, adm_kategoria_zmien, adm_kategoria_usun);
        			panel_prawy.getChildren().addAll(adm_kategoria_HB);
        			//adm - dodaj kategoria
        			adm_kategoria_dodaj.setOnAction(adm_kategoria_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				HBox adm_kategoria_dodaj_nazwaHB = new HBox();
        				adm_kategoria_dodaj_nazwaHB.setId("adm_kategoria_dodaj_nazwaHB");
        				Label adm_kategoria_dodaj_nazwaL = new Label("Podaj nazwê: ");
        				TextField adm_kategoria_dodaj_nazwaTF = new TextField();
        				Label adm_kategoria_dodaj_nazwaM = new Label();
        				adm_kategoria_dodaj_nazwaM.getStyleClass().add("adm_1messg");
        				Button adm_kategoria_dodaj_dodajB = new Button("dodaj");
        				adm_kategoria_dodaj_nazwaHB.getChildren().addAll(adm_kategoria_dodaj_nazwaL, adm_kategoria_dodaj_nazwaTF, adm_kategoria_dodaj_nazwaM, adm_kategoria_dodaj_dodajB);
        				Label adm_kategoria_dodaj_messg = new Label();
        				panel_prawy.getChildren().addAll(adm_kategoria_dodaj_nazwaHB, adm_kategoria_dodaj_messg);
        				adm_kategoria_dodaj_dodajB.setOnAction(adm_dt_dodaj_dodaj_a->{
        					//wzorzec
            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,20}");
        					Matcher testNazwa = nazwaP.matcher(adm_kategoria_dodaj_nazwaTF.getText() );
        					if(testNazwa.matches())
        					{
        						adm_kategoria_dodaj_nazwaM.setText(" ok");
        						adm_kategoria_dodaj_messg.setText("dodano nowy rekord");
        						Kategoria nowaKategoria = new Kategoria(adm_kategoria_dodaj_nazwaTF.getText());
        						//sesja do adm_kategoria - dodaj
        	        			Session sessionAdmKategoria_dodaj = factory.openSession();
        	        			Transaction t_AdmKategoria_dodaj = sessionAdmKategoria_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmKategoria_dodaj.save(nowaKategoria);
        	        				t_AdmKategoria_dodaj.commit();
        	        			}catch (Exception e){
                    				if (t_AdmKategoria_dodaj != null) {
                    					t_AdmKategoria_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmKategoria_dodaj.close();
        	        			}
        					}
        					else 
        					{
        						adm_kategoria_dodaj_nazwaM.setText(" niepoprawna nazwa");
        						adm_kategoria_dodaj_messg.setText("niepoprawne dane");
        					}
        				});
        			});
        			//adm - zmien kategoria
        			adm_kategoria_zmien.setOnAction(adm_kategoria_zmien_a->{
        				if(TV_adm_kategoria.getSelectionModel().getSelectedItem() != null)
        				{
	        				panel_prawy.getChildren().clear();
	        				HBox adm_kategoria_zmien_nazwaHB = new HBox();
	        				adm_kategoria_zmien_nazwaHB.setId("adm_kategoria_dodaj_nazwaHB");
	        				Label adm_kategoria_zmien_nazwaL = new Label("Podaj nazwê: ");
	        				TextField adm_kategoria_zmien_nazwaTF = new TextField();
	        				Label adm_kategoria_zmien_nazwaM = new Label();
	        				adm_kategoria_zmien_nazwaM.getStyleClass().add("adm_1messg");
	        				Button adm_kategoria_zmien_zmienB = new Button("zmieñ");
	        				adm_kategoria_zmien_nazwaHB.getChildren().addAll(adm_kategoria_zmien_nazwaL, adm_kategoria_zmien_nazwaTF, adm_kategoria_zmien_nazwaM, adm_kategoria_zmien_zmienB);
	        				Label adm_kategoria_zmien_messg = new Label();
	        				panel_prawy.getChildren().addAll(adm_kategoria_zmien_nazwaHB, adm_kategoria_zmien_messg);
	        				//zaznaczona kategoria 
	        				Kategoria zaznaczonekategoria = TV_adm_kategoria.getSelectionModel().getSelectedItem();
	        				//wypelnienie TF danymi ZAZNACZONEJ kategorii
	        				adm_kategoria_zmien_nazwaTF.setText(zaznaczonekategoria.getNazwa() );
	        				//adm - kategoria zmien - zmien
	        				adm_kategoria_zmien_zmienB.setOnAction(adm_dt_dodaj_dodaj_a->{
	        					//wzorzec
	            				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,20}");
	        					Matcher testNazwa = nazwaP.matcher(adm_kategoria_zmien_nazwaTF.getText() );
	        					if(testNazwa.matches())
	        					{
	        						adm_kategoria_zmien_nazwaM.setText(" ok");
	        						adm_kategoria_zmien_messg.setText("zmieniono rekord");
	        						zaznaczonekategoria.setNazwa( adm_kategoria_zmien_nazwaTF.getText() );
	        						//sesja do adm_kategoria - dodaj
	        	        			Session sessionAdmKategoria_zmien = factory.openSession();
	        	        			Transaction t_AdmKategoria_zmien = sessionAdmKategoria_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmKategoria_zmien.update(zaznaczonekategoria);
	        	        				t_AdmKategoria_zmien.commit();
	        	        			}catch (Exception e){
	                    				if (t_AdmKategoria_zmien != null) {
	                    					t_AdmKategoria_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmKategoria_zmien.close();
	        	        			}
	        					}
	        					else 
	        					{
	        						adm_kategoria_zmien_nazwaM.setText(" niepoprawna nazwa");
	        						adm_kategoria_zmien_messg.setText("niepoprawne dane");
	        					}
	        				});
        				}
        			});
        			//adm - usun kategoria
        			adm_kategoria_usun.setOnAction(adm_kategoria_Usun_a->{
        				//zaznaczone kategoria
        				Kategoria zaznaczonekategoria = TV_adm_kategoria.getSelectionModel().getSelectedItem();
        				if(zaznaczonekategoria != null) 
        				{
	        				//sesja do adm_kategoria_usun
	            			Session sessionAdmKategoria_usun = factory.openSession();
	            			Transaction t_AdmKategoria_usun = sessionAdmKategoria_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego kategorii
	            				sessionAdmKategoria_usun.remove(zaznaczonekategoria);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich kategorii
	                			@SuppressWarnings("unchecked")
	        					List<Kategoria> wynikAdmkategoria_usun = sessionAdmKategoria_usun.createQuery("from Kategoria").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Kategoria> OL_admKategoria_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmkategoria_usun.size(); i++)
	                			{
	                				OL_admKategoria_usun.add( wynikAdmkategoria_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_kategoria.setItems(OL_admKategoria_usun);
	                			//
	                			t_AdmKategoria_usun.commit();
	                			sessionAdmKategoria_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmKategoria_usun != null) {
	            					t_AdmKategoria_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
        				}
        			});
				}); 
				
				//adm - klub
				adm_tabKlubB.setOnAction(adm_klub_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Klub\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela klubów
    				TableView<Klub> TV_adm_klub = new TableView<>();
    				//kol-id klubu
    				TableColumn<Klub, Integer> TC_admKlub_id = new TableColumn<>("Id");
    				TC_admKlub_id.setCellValueFactory(new PropertyValueFactory<Klub, Integer>("id"));
    				TC_admKlub_id.setMinWidth(100);
    				//kol-nazwa klubu
    				TableColumn<Klub, String> TC_admKlub_nazwa = new TableColumn<>("Nazwa");
    				TC_admKlub_nazwa.setCellValueFactory(new PropertyValueFactory<Klub, String>("nazwa"));
    				TC_admKlub_nazwa.setMinWidth(300);
    				//kol-rok za³o¿enia
    				TableColumn<Klub, String> TC_admKlub_rokZal = new TableColumn<>("Rok za³o¿enia");
    				TC_admKlub_rokZal.setCellValueFactory(new PropertyValueFactory<Klub, String>("rokZalozenia"));
    				TC_admKlub_rokZal.setMinWidth(240);
    				//kol-rok rozwi¹zania
    				TableColumn<Klub, String> TC_admKlub_rokRoz = new TableColumn<>("Rok rozwi¹zania");
    				TC_admKlub_rokRoz.setCellValueFactory(new PropertyValueFactory<Klub, String>("rokRozwiazania"));
    				TC_admKlub_rokRoz.setMinWidth(250);
    				//sesja do adm_klub
        			Session sessionAdmKlub = factory.openSession();
        			Transaction t_AdmKlub = sessionAdmKlub.beginTransaction();
    				try {
    					//lista wszystkich klubow
        				@SuppressWarnings("unchecked")
						List<Klub> wynikAdmKlub = sessionAdmKlub.createQuery("from Klub").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Klub> OL_admKlub = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmKlub.size(); i++)
        				{
        					OL_admKlub.add( wynikAdmKlub.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_klub.getColumns().addAll(TC_admKlub_id, TC_admKlub_nazwa, TC_admKlub_rokZal, TC_admKlub_rokRoz);
        				//dodanie elementow list do tabeli
        				TV_adm_klub.setItems(OL_admKlub);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_klub);
    				}finally {
    					t_AdmKlub.commit();
    					sessionAdmKlub.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_klub_HB = new HBox();
        			adm_klub_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_klub_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_klub_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_klub_usun = new Button("Usuñ zaznaczony rekord");
        			adm_klub_HB.getChildren().addAll(adm_klub_dodaj, adm_klub_zmien, adm_klub_usun);
        			panel_prawy.getChildren().addAll(adm_klub_HB);	
        			//elementy potrzebne do dodawania, edycji ::
        			//nazwa
    				HBox adm_klub_dodaj_nazwaHB = new HBox();
    				adm_klub_dodaj_nazwaHB.getStyleClass().add("center");
    				Label adm_klub_dodaj_nazwaL = new Label("Nazwa: "); 
    				TextField adm_klub_dodaj_nazwaTF = new TextField(); 
    				Label adm_klub_dodaj_nazwaM = new Label(); 
    				adm_klub_dodaj_nazwaL.setId("adm_edycjaL");
    				adm_klub_dodaj_nazwaTF.setId("adm_edycjaTF");
    				adm_klub_dodaj_nazwaM.setId("adm_edycjaM");
    				adm_klub_dodaj_nazwaHB.getChildren().addAll(adm_klub_dodaj_nazwaL, adm_klub_dodaj_nazwaTF, adm_klub_dodaj_nazwaM);
    				//rokZal
    				HBox adm_klub_dodaj_rokZalHB = new HBox();
    				adm_klub_dodaj_rokZalHB.getStyleClass().add("center");
    				Label adm_klub_dodaj_rokZalL = new Label("Rok za³o¿enia: "); 
    				TextField adm_klub_dodaj_rokZalTF = new TextField(); 
    				Label adm_klub_dodaj_rokZalM = new Label(); 
    				adm_klub_dodaj_rokZalL.setId("adm_edycjaL");
    				adm_klub_dodaj_rokZalTF.setId("adm_edycjaTF");
    				adm_klub_dodaj_rokZalM.setId("adm_edycjaM");
    				adm_klub_dodaj_rokZalHB.getChildren().addAll(adm_klub_dodaj_rokZalL, adm_klub_dodaj_rokZalTF, adm_klub_dodaj_rokZalM);
    				//rokRozw
    				HBox adm_klub_dodaj_rokRozwHB = new HBox();
    				adm_klub_dodaj_rokRozwHB.getStyleClass().add("center");
    				Label adm_klub_dodaj_rokRozwL = new Label("Rok rozwi¹zania: "); 
    				TextField adm_klub_dodaj_rokRozwTF = new TextField(); 
    				Label adm_klub_dodaj_rokRozwM = new Label(); 
    				adm_klub_dodaj_rokRozwL.setId("adm_edycjaL");
    				adm_klub_dodaj_rokRozwTF.setId("adm_edycjaTF");
    				adm_klub_dodaj_rokRozwM.setId("adm_edycjaM");
    				adm_klub_dodaj_rokRozwHB.getChildren().addAll(adm_klub_dodaj_rokRozwL, adm_klub_dodaj_rokRozwTF, adm_klub_dodaj_rokRozwM);
    				//messg - wiadomosc o poprawnosci wszystkich danych
    				HBox adm_klub_zatw_messgHB = new HBox();
    				adm_klub_zatw_messgHB.getStyleClass().add("center");
    				Button adm_klub_zatw_zatwB = new Button("zatw");
    				Label adm_klub_zatw_messg = new Label();
    				adm_klub_zatw_messg.getStyleClass().add("adm_dodajMessg");
    				adm_klub_zatw_messgHB.getChildren().addAll(adm_klub_zatw_messg, adm_klub_zatw_zatwB);
    				//wzorce
    				Pattern nazwaP = Pattern.compile("[A-Za-z ]{3,50}");
    				Pattern rokP = Pattern.compile("[12][0-9]{3}");
        			//////////////
    				//klub - dodaj
        			adm_klub_dodaj.setOnAction(adm_klub_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//dodanie wszystkich HB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_klub_dodaj_nazwaHB,adm_klub_dodaj_rokZalHB, adm_klub_zatw_messgHB);
        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
        				adm_klub_zatw_zatwB.setText("dodaj");
        				//akcja przycisku zatwierdŸ (tutaj dodaj)
        				adm_klub_zatw_zatwB.setOnAction(adm_klub_zatw_dodaj_a->{
        					//pobranie tekstu do sprawdzenia wzorców
        					Matcher testNazwa = nazwaP.matcher(adm_klub_dodaj_nazwaTF.getText() );
        					Matcher testRokZal = rokP.matcher(adm_klub_dodaj_rokZalTF.getText() );
        					//sprawdzenie wzorców pojedynczo
            				if(testNazwa.matches())
        						adm_klub_dodaj_nazwaM.setText(" ok");
        					else 
        						adm_klub_dodaj_nazwaM.setText(" niepoprawna nazwa");
            				if(testRokZal.matches())
            					adm_klub_dodaj_rokZalM.setText(" ok");
        					else 
        						adm_klub_dodaj_rokZalM.setText(" niepoprawny rok");
            				//sprawdzenie wszystkich wzorców razem
            				if( testNazwa.matches() && testRokZal.matches() )
            				{
            					Klub nowyKlub = new Klub(adm_klub_dodaj_nazwaTF.getText(), Integer.parseInt(adm_klub_dodaj_rokZalTF.getText()) );
            					//sesja do adm_klub - dodaj
        	        			Session sessionAdmKlub_dodaj = factory.openSession();
        	        			Transaction t_AdmKlub_dodaj = sessionAdmKlub_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmKlub_dodaj.save(nowyKlub);
        	        				t_AdmKlub_dodaj.commit();
        	        				adm_klub_zatw_messg.setText("dodano nowy rekord");
        	        			}catch (Exception e){
                    				if (t_AdmKlub_dodaj != null) {
                    					t_AdmKlub_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmKlub_dodaj.close();
        	        			}
            				}
            				else 
            				{
            					adm_klub_zatw_messg.setText("niepoprawne dane");
            				}
        				});
        			});
        			//////////////
        			//klub - zmien
					adm_klub_zmien.setOnAction(adm_klub_dodaj_a->{
						Klub zaznaczonyKlub = TV_adm_klub.getSelectionModel().getSelectedItem();
						if(zaznaczonyKlub!=null)
						{
							panel_prawy.getChildren().clear();
							//dodanie wszystkich HB do panelu prawego
	        				panel_prawy.getChildren().addAll(adm_klub_dodaj_nazwaHB,adm_klub_dodaj_rokZalHB,adm_klub_dodaj_rokRozwHB, adm_klub_zatw_messgHB);
	        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
	        				adm_klub_zatw_zatwB.setText("zmieñ");
	        				//
	        				//wype³nienie TF'ów danymi zaznaczonego rekordu
	        				adm_klub_dodaj_nazwaTF.setText( zaznaczonyKlub.getNazwa() );
	        				if( zaznaczonyKlub.getRokZalozenia()!=null )
	        					adm_klub_dodaj_rokZalTF.setText( Integer.toString(zaznaczonyKlub.getRokZalozenia()) );
	        				if( zaznaczonyKlub.getRokRozwiazania()!=null )
	        					adm_klub_dodaj_rokRozwTF.setText( Integer.toString(zaznaczonyKlub.getRokRozwiazania()) );
	        				//
							//akcja przycisku zatwierdŸ (tutaj zmieñ)
	        				adm_klub_zatw_zatwB.setOnAction(adm_klub_zatw_dodaj_a->{
	        					//pobranie tekstu do sprawdzenia wzorców
	        					Matcher testNazwa = nazwaP.matcher(adm_klub_dodaj_nazwaTF.getText() );
	        					Matcher testRokZal = rokP.matcher(adm_klub_dodaj_rokZalTF.getText() );
	        					Matcher testRokRozw = rokP.matcher(adm_klub_dodaj_rokRozwTF.getText() );
	        					//sprawdzenie wzorców pojedynczo
	            				if(testNazwa.matches())
	        						adm_klub_dodaj_nazwaM.setText(" ok");
	        					else 
	        						adm_klub_dodaj_nazwaM.setText(" niepoprawna nazwa");
	            				if(testRokZal.matches())
	        						adm_klub_dodaj_rokZalM.setText(" ok");
	        					else 
	        						adm_klub_dodaj_rokZalM.setText(" niepoprawny rok");
	            				if(testRokRozw.matches())
	        						adm_klub_dodaj_rokRozwM.setText(" ok");
	        					else 
	        						adm_klub_dodaj_rokRozwM.setText(" niepoprawny rok");
	            				//sprawdzenie wszystkich wzorców razem
	            				if( testNazwa.matches() && testRokZal.matches() && testRokRozw.matches() )
	            				{
	            					//zmiany w zaznaczonym rekordzie
	            					zaznaczonyKlub.setNazwa( adm_klub_dodaj_nazwaTF.getText() );
	            					zaznaczonyKlub.setRokZalozenia( Integer.parseInt(adm_klub_dodaj_rokZalTF.getText()) );
	            					zaznaczonyKlub.setRokRozwiazania( Integer.parseInt(adm_klub_dodaj_rokRozwTF.getText()) );
	            					//sesja do adm_klub - zmien
	        	        			Session sessionAdmKlub_zmien = factory.openSession();
	        	        			Transaction t_AdmKlub_zmien = sessionAdmKlub_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmKlub_zmien.update(zaznaczonyKlub);
	        	        				t_AdmKlub_zmien.commit();
	        	        				adm_klub_zatw_messg.setText("zmieniono rekord");
	        	        			}catch (Exception e){
	                    				if (t_AdmKlub_zmien != null) {
	                    					t_AdmKlub_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmKlub_zmien.close();
	        	        			}
	            				}
	            				else 
	            				{
	            					adm_klub_zatw_messg.setText("niepoprawne dane");
	            				}
	        				});
						}
					});
					//////////////
        			//klub - usun
					adm_klub_usun.setOnAction(adm_klub_dodaj_a->{
						Klub zaznaczonyKlub = TV_adm_klub.getSelectionModel().getSelectedItem();
						if(zaznaczonyKlub!=null) 
						{
							//sesja do adm_klub_usun
	            			Session sessionAdmKlub_usun = factory.openSession();
	            			Transaction t_AdmKlub_usun = sessionAdmKlub_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego rekordu
	            				sessionAdmKlub_usun.remove(zaznaczonyKlub);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich rekordów
	                			@SuppressWarnings("unchecked")
	        					List<Klub> wynikAdmklub_usun = sessionAdmKlub_usun.createQuery("from Klub").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Klub> OL_admKlub_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmklub_usun.size(); i++)
	                			{
	                				OL_admKlub_usun.add( wynikAdmklub_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_klub.setItems(OL_admKlub_usun);
	                			//
	                			t_AdmKlub_usun.commit();
	                			sessionAdmKlub_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmKlub_usun != null) {
	            					t_AdmKlub_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
						}
					});
				});
				
				//adm - adres
				adm_tabAdresB.setOnAction(adm_adres_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Adres\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela adresów
    				TableView<Adres> TV_adm_adres = new TableView<>();
    				//kol-id adresu
    				TableColumn<Adres, Integer> TC_admAdres_id = new TableColumn<>("Id");
    				TC_admAdres_id.setCellValueFactory(new PropertyValueFactory<Adres, Integer>("id"));
    				TC_admAdres_id.setMinWidth(100);
    				//kol-ulica adresu
    				TableColumn<Adres, String> TC_admAdres_ulica = new TableColumn<>("Ulica");
    				TC_admAdres_ulica.setCellValueFactory(new PropertyValueFactory<Adres, String>("ulica"));
    				TC_admAdres_ulica.setMinWidth(200);
    				//kol-nrDomu adresu
    				TableColumn<Adres, String> TC_admAdres_nrDom = new TableColumn<>("Nr domu");
    				TC_admAdres_nrDom.setCellValueFactory(new PropertyValueFactory<Adres, String>("nrDomu"));
    				TC_admAdres_nrDom.setMinWidth(120);
    				//kol-nrMieszkania adresu
    				TableColumn<Adres, Integer> TC_admAdres_nrMieszk = new TableColumn<>("Nr Mieszkania");
    				TC_admAdres_nrMieszk.setCellValueFactory(new PropertyValueFactory<Adres, Integer>("nrMieszkania"));
    				TC_admAdres_nrMieszk.setMinWidth(150);
    				//kol-kodPocztowy adresu
    				TableColumn<Adres, String> TC_admAdres_kodPoczt = new TableColumn<>("Kod pocztowy");
    				TC_admAdres_kodPoczt.setCellValueFactory(new PropertyValueFactory<Adres, String>("kodPocztowy"));
    				TC_admAdres_kodPoczt.setMinWidth(150);
    				//kol-miasto adresu
    				TableColumn<Adres, String> TC_admAdres_miasto = new TableColumn<>("Miasto");
    				TC_admAdres_miasto.setCellValueFactory(new Callback<CellDataFeatures<Adres, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Adres, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getMiasto_id().getNazwa() );
						}
    	            });
    				TC_admAdres_miasto.setMinWidth(150);
    				//sesja do adm_adres
        			Session sessionAdmAdres = factory.openSession();
        			Transaction t_AdmAdres = sessionAdmAdres.beginTransaction();
    				try {
    					//lista wszystkich adresow
        				@SuppressWarnings("unchecked")
						List<Adres> wynikAdmAdres = sessionAdmAdres.createQuery("from Adres").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Adres> OL_admAdres = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmAdres.size(); i++)
        				{
        					OL_admAdres.add( wynikAdmAdres.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_adres.getColumns().addAll(TC_admAdres_id, TC_admAdres_ulica, TC_admAdres_nrDom, TC_admAdres_nrMieszk, TC_admAdres_kodPoczt, TC_admAdres_miasto);
        				//dodanie elementow list do tabeli
        				TV_adm_adres.setItems(OL_admAdres);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_adres);
    				}finally {
    					t_AdmAdres.commit();
    					sessionAdmAdres.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_adres_HB = new HBox();
        			adm_adres_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_adres_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_adres_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_adres_usun = new Button("Usuñ zaznaczony rekord");
        			adm_adres_HB.getChildren().addAll(adm_adres_dodaj, adm_adres_zmien, adm_adres_usun);
        			panel_prawy.getChildren().addAll(adm_adres_HB);	
        			//adm - dodaj adres
        			adm_adres_dodaj.setOnAction(adm_adres_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//ulica
        				HBox adm_adres_dodaj_ulicaHB = new HBox();
        				adm_adres_dodaj_ulicaHB.setId("adm_adres_dodajHB");
        				Label adm_adres_dodaj_ulicaL = new Label("Ulica: "); 
        				TextField adm_adres_dodaj_ulicaTF = new TextField(); 
        				Label adm_adres_dodaj_ulicaM = new Label(); 
        				adm_adres_dodaj_ulicaL.setId("adm_edycjaL");
        				adm_adres_dodaj_ulicaTF.setId("adm_edycjaTF");
        				adm_adres_dodaj_ulicaM.setId("adm_edycjaM");
        				adm_adres_dodaj_ulicaHB.getChildren().addAll(adm_adres_dodaj_ulicaL, adm_adres_dodaj_ulicaTF, adm_adres_dodaj_ulicaM);
        				//nrDomu
        				HBox adm_adres_dodaj_domHB = new HBox();
        				adm_adres_dodaj_domHB.setId("adm_adres_dodajHB");
        				Label adm_adres_dodaj_domL = new Label("Nr domu: ");
        				TextField adm_adres_dodaj_domTF = new TextField();
        				Label adm_adres_dodaj_domM = new Label();
        				adm_adres_dodaj_domL.setId("adm_edycjaL");
        				adm_adres_dodaj_domTF.setId("adm_edycjaTF");
        				adm_adres_dodaj_domM.setId("adm_edycjaM");
        				adm_adres_dodaj_domHB.getChildren().addAll(adm_adres_dodaj_domL, adm_adres_dodaj_domTF, adm_adres_dodaj_domM);
        				//nrMieszk
        				HBox adm_adres_dodaj_mieszkHB = new HBox();
        				adm_adres_dodaj_mieszkHB.setId("adm_adres_dodajHB");
        				Label adm_adres_dodaj_mieszkL = new Label("Nr mieszkania: ");
        				TextField adm_adres_dodaj_mieszkTF = new TextField();
        				Label adm_adres_dodaj_mieszkM = new Label();
        				adm_adres_dodaj_mieszkL.setId("adm_edycjaL");
        				adm_adres_dodaj_mieszkTF.setId("adm_edycjaTF");
        				adm_adres_dodaj_mieszkM.setId("adm_edycjaM");
        				adm_adres_dodaj_mieszkHB.getChildren().addAll(adm_adres_dodaj_mieszkL, adm_adres_dodaj_mieszkTF, adm_adres_dodaj_mieszkM);
        				//kodPocztowy
        				HBox adm_adres_dodaj_kodHB = new HBox();
        				adm_adres_dodaj_kodHB.setId("adm_adres_dodajHB");
        				Label adm_adres_dodaj_kodL = new Label("Kod pocztowy: ");
        				TextField adm_adres_dodaj_kodTF = new TextField();
        				Label adm_adres_dodaj_kodM = new Label();
        				adm_adres_dodaj_kodL.setId("adm_edycjaL");
        				adm_adres_dodaj_kodTF.setId("adm_edycjaTF");
        				adm_adres_dodaj_kodM.setId("adm_edycjaM");
        				adm_adres_dodaj_kodHB.getChildren().addAll(adm_adres_dodaj_kodL, adm_adres_dodaj_kodTF, adm_adres_dodaj_kodM);
        				//miasto
        				HBox adm_adres_dodaj_miastoHB = new HBox();
        				adm_adres_dodaj_miastoHB.setId("adm_adres_dodajHB");
        				Label adm_adres_dodaj_miastoL = new Label("Miasto: ");
        				//sesja do adm_adres_dodaj_adres
            			Session sessionAdmAdr_dodaj = factory.openSession();
            			Transaction t_AdmAdr_dodaj = sessionAdmAdr_dodaj.beginTransaction();
            			//OL_list
            			ObservableList<Miasto> OL_admAdr_miasto = FXCollections.observableArrayList();
            			try {
            				@SuppressWarnings("unchecked")
    						List<Miasto> wynikAdmAdr_miasto = sessionAdmAdr_dodaj.createQuery("from Miasto").getResultList();
            				//przepisanie el. listy do OL_listy
            				for(int i=0; i<wynikAdmAdr_miasto.size(); i++)
            				{
            					OL_admAdr_miasto.add( wynikAdmAdr_miasto.get(i) );
            				}
            			}finally {
            				t_AdmAdr_dodaj.commit();
            				sessionAdmAdr_dodaj.close();
            			}
        				final ComboBox adm_adres_dodaj_adres_CB = new ComboBox(OL_admAdr_miasto);
        				adm_adres_dodaj_adres_CB.setPromptText("wybierz miasto");
        				//
        				Label adm_adres_dodaj_miastoM = new Label();
        				adm_adres_dodaj_miastoL.setId("adm_edycjaL");
        				adm_adres_dodaj_adres_CB.setId("adm_edycjaTF");
        				adm_adres_dodaj_miastoM.setId("adm_edycjaM");
        				adm_adres_dodaj_miastoHB.getChildren().addAll(adm_adres_dodaj_miastoL, adm_adres_dodaj_adres_CB, adm_adres_dodaj_miastoM);
        				//messg - wiadomosc o poprawnosci wszystkich danych
        				HBox adm_adres_dodaj_messgHB = new HBox();
        				adm_adres_dodaj_messgHB.setId("adm_adres_dodajHB");
        				Button adm_adres_dodaj_dodajB = new Button("dodaj");
        				Label adm_adres_dodaj_messg = new Label();
        				adm_adres_dodaj_messg.getStyleClass().add("adm_dodajMessg");
        				adm_adres_dodaj_messgHB.getChildren().addAll(adm_adres_dodaj_messg, adm_adres_dodaj_dodajB);
        				//dodanie wszystkichHB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_adres_dodaj_ulicaHB, adm_adres_dodaj_domHB, adm_adres_dodaj_mieszkHB, adm_adres_dodaj_kodHB,
        												adm_adres_dodaj_miastoHB, adm_adres_dodaj_messgHB);
        				adm_adres_dodaj_dodajB.setOnAction(adm_dt_dodaj_dodaj_a->{
        					//wzorce
            				Pattern ulicaP = Pattern.compile("[A-Za-z]{3,20}");
        					Matcher testUlica = ulicaP.matcher(adm_adres_dodaj_ulicaTF.getText() );
        					if(testUlica.matches())
        						adm_adres_dodaj_ulicaM.setText(" ok");
        					else 
        						adm_adres_dodaj_ulicaM.setText(" niepoprawna ulica");
        					//nrDomu
        					Pattern domP = Pattern.compile("[1-9][0-9]{0,5}[A-Za-z]{0,1}");
        					Matcher testDom = domP.matcher(adm_adres_dodaj_domTF.getText() );
        					if(testDom.matches())
        						adm_adres_dodaj_domM.setText(" ok");
        					else 
        						adm_adres_dodaj_domM.setText(" niepoprawny nr domu");
        					//nr mieszkania
	    					Pattern nrMieszkP = Pattern.compile("[1-9][0-9]{0,3}");
	    					Matcher testNrMieszk = nrMieszkP.matcher(adm_adres_dodaj_mieszkTF.getText() );
	    					if(testNrMieszk.matches())
	    						adm_adres_dodaj_mieszkM.setText(" ok");
	    					else 
	    						adm_adres_dodaj_mieszkM.setText(" niepoprawny nr mieszkania");
	    					//kod pocztowy
	    					Pattern kodPocztP = Pattern.compile("[0-9]{2}[-][0-9]{3}");
	    					Matcher testKodPoczt = kodPocztP.matcher(adm_adres_dodaj_kodTF.getText() );
	    					if(testKodPoczt.matches())
	    						adm_adres_dodaj_kodM.setText(" ok");
	    					else 
	    						adm_adres_dodaj_kodM.setText(" niepoprawny kod pocztowy");
	    					//spr czy miasto zosta³o wybran
	    					if(adm_adres_dodaj_adres_CB.getSelectionModel().getSelectedItem()!=null)
	    						adm_adres_dodaj_miastoM.setText(" ok");
	    					else 
	    						adm_adres_dodaj_miastoM.setText(" wybierz miasto");
	    					//sprawdzenie wszystkich danych
	    					if(testUlica.matches() && testDom.matches() && testNrMieszk.matches() && testKodPoczt.matches() && 
	    							(adm_adres_dodaj_adres_CB.getSelectionModel().getSelectedItem()!=null) )
	    					{
	    						adm_adres_dodaj_messg.setText("dodano nowy rekord");
	    						Adres nowyAdres = new Adres(adm_adres_dodaj_ulicaTF.getText(), adm_adres_dodaj_domTF.getText(), Integer.parseInt(adm_adres_dodaj_mieszkTF.getText()),
	    										adm_adres_dodaj_kodTF.getText(), (Miasto)adm_adres_dodaj_adres_CB.getSelectionModel().getSelectedItem() );
	    						//sesja do adm_Adres - dodaj
        	        			Session sessionAdmAdres_dodaj = factory.openSession();
        	        			Transaction t_AdmAdres_dodaj = sessionAdmAdres_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmAdres_dodaj.save(nowyAdres);
        	        				t_AdmAdres_dodaj.commit();
        	        			}catch (Exception e){
                    				if (t_AdmAdres_dodaj != null) {
                    					t_AdmAdres_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmAdres_dodaj.close();
        	        			}					
	    					}
	    					else
	    					{
	    						adm_adres_dodaj_messg.setText("niepoprawne dane");
	    					}
        				});
        			});
        			//adm - zmien adres
        			adm_adres_zmien.setOnAction(adm_adres_zmien_a->
        			{
        				if(TV_adm_adres.getSelectionModel().getSelectedItem() != null)
        				{
		        			panel_prawy.getChildren().clear();
		        			//zaznaczony element
		        			Adres zaznaczonyAdres = TV_adm_adres.getSelectionModel().getSelectedItem();
		    				//ulica
		    				HBox adm_adres_ulicaHB = new HBox();
		    				adm_adres_ulicaHB.getStyleClass().add("center");
		    				Label adm_adres_ulicaL = new Label("Ulica: "); 
		    				TextField adm_adres_ulicaTF = new TextField(); 
		    				Label adm_adres_ulicaM = new Label(); 
		    				adm_adres_ulicaL.setId("adm_edycjaL");
		    				adm_adres_ulicaTF.setId("adm_edycjaTF");
		    				adm_adres_ulicaM.setId("adm_edycjaM");
		    				adm_adres_ulicaHB.getChildren().addAll(adm_adres_ulicaL, adm_adres_ulicaTF, adm_adres_ulicaM);
		    				//nrDomu
		    				HBox adm_adres_domHB = new HBox();
		    				adm_adres_domHB.getStyleClass().add("center");
		    				Label adm_adres_domL = new Label("Nr domu: ");
		    				TextField adm_adres_domTF = new TextField();
		    				Label adm_adres_domM = new Label();
		    				adm_adres_domL.setId("adm_edycjaL");
		    				adm_adres_domTF.setId("adm_edycjaTF");
		    				adm_adres_domM.setId("adm_edycjaM");
		    				adm_adres_domHB.getChildren().addAll(adm_adres_domL, adm_adres_domTF, adm_adres_domM);
		    				//nrMieszk
		    				HBox adm_adres_mieszkHB = new HBox();
		    				adm_adres_mieszkHB.getStyleClass().add("center");
		    				Label adm_adres_mieszkL = new Label("Nr mieszkania: ");
		    				TextField adm_adres_mieszkTF = new TextField();
		    				Label adm_adres_mieszkM = new Label();
		    				adm_adres_mieszkL.setId("adm_edycjaL");
		    				adm_adres_mieszkTF.setId("adm_edycjaTF");
		    				adm_adres_mieszkM.setId("adm_edycjaM");
		    				adm_adres_mieszkHB.getChildren().addAll(adm_adres_mieszkL, adm_adres_mieszkTF, adm_adres_mieszkM);
		    				//kodPocztowy
		    				HBox adm_adres_kodHB = new HBox();
		    				adm_adres_kodHB.getStyleClass().add("center");
		    				Label adm_adres_kodL = new Label("Kod pocztowy: ");
		    				TextField adm_adres_kodTF = new TextField();
		    				Label adm_adres_kodM = new Label();
		    				adm_adres_kodL.setId("adm_edycjaL");
		    				adm_adres_kodTF.setId("adm_edycjaTF");
		    				adm_adres_kodM.setId("adm_edycjaM");
		    				adm_adres_kodHB.getChildren().addAll(adm_adres_kodL, adm_adres_kodTF, adm_adres_kodM);
		    				//miasto
	        				HBox adm_adres_zmien_miastoHB = new HBox();
	        				adm_adres_zmien_miastoHB.getStyleClass().add("center");
	        				Label adm_adres_zmien_miastoL = new Label("Miasto: ");
	        				//sesja do adm_adres_zmien_adres
	            			Session sessionAdmAdr_zmien = factory.openSession();
	            			Transaction t_AdmAdr_zmien = sessionAdmAdr_zmien.beginTransaction();
	            			//OL_list
	            			ObservableList<Miasto> OL_admAdr_miasto = FXCollections.observableArrayList();
	            			try {
	            				@SuppressWarnings("unchecked")
	    						List<Miasto> wynikAdmAdr_miasto = sessionAdmAdr_zmien.createQuery("from Miasto").getResultList();
	            				//przepisanie el. listy do OL_listy
	            				for(int i=0; i<wynikAdmAdr_miasto.size(); i++)
	            				{
	            					OL_admAdr_miasto.add( wynikAdmAdr_miasto.get(i) );
	            				}
	            			}finally {
	            				t_AdmAdr_zmien.commit();
	            				sessionAdmAdr_zmien.close();
	            			}
	        				final ComboBox<Miasto> adm_adres_zmien_adres_CB = new ComboBox<Miasto>(OL_admAdr_miasto);
	        				//adm_adres_zmien_adres_CB.setPromptText("wybierz miasto");
	        				//
	        				Label adm_adres_zmien_miastoM = new Label();
	        				adm_adres_zmien_miastoL.setId("adm_edycjaL");
	        				adm_adres_zmien_adres_CB.setId("adm_edycjaTF");
	        				adm_adres_zmien_miastoM.setId("adm_edycjaM");
	        				adm_adres_zmien_miastoHB.getChildren().addAll(adm_adres_zmien_miastoL, adm_adres_zmien_adres_CB, adm_adres_zmien_miastoM);
		    				//wyplenienie TF'ów danymi zaznaczonego rekordu
		    				adm_adres_ulicaTF.setText(zaznaczonyAdres.getUlica());
		    				adm_adres_domTF.setText(zaznaczonyAdres.getNrDomu());
		    				if( zaznaczonyAdres.getNrMieszkania() != null )
		    					adm_adres_mieszkTF.setText( Integer.toString(zaznaczonyAdres.getNrMieszkania()) );
		    				adm_adres_kodTF.setText(zaznaczonyAdres.getKodPocztowy());
		    				for(int i=0; i<OL_admAdr_miasto.size(); i++)
		    					if( zaznaczonyAdres.getMiasto_id().getNazwa().equals(OL_admAdr_miasto.get(i).getNazwa()) )
		    						adm_adres_zmien_adres_CB.setValue( zaznaczonyAdres.getMiasto_id() );
		    				//
		    				HBox adm_adres_zmien_messgHB = new HBox();
		    				adm_adres_zmien_messgHB.getStyleClass().add("center");
		    				Button adm_adres_zmien_zmienB = new Button("zmien");
		    				Label adm_adres_zmien_messg = new Label();
		    				adm_adres_zmien_messg.getStyleClass().add("adm_dodajMessg"); //szerokosc
		    				adm_adres_zmien_messgHB.getChildren().addAll(adm_adres_zmien_messg, adm_adres_zmien_zmienB);
		        			panel_prawy.getChildren().addAll(adm_adres_ulicaHB, adm_adres_domHB, adm_adres_mieszkHB, adm_adres_kodHB, adm_adres_zmien_miastoHB
		        											,adm_adres_zmien_messgHB);
		        			adm_adres_zmien_zmienB.setOnAction(adm_dt_zmien_zmien_a->{
			        			//wzorce
	            				Pattern ulicaP = Pattern.compile("[A-Za-z]{3,20}");
	        					Matcher testUlica = ulicaP.matcher(adm_adres_ulicaTF.getText() );
	        					if(testUlica.matches())
	        						adm_adres_ulicaM.setText(" ok");
	        					else 
	        						adm_adres_ulicaM.setText(" niepoprawna ulica");
	        					//nrDomu
	        					Pattern domP = Pattern.compile("[1-9][0-9]{0,5}[A-Za-z]{0,1}");
	        					Matcher testDom = domP.matcher(adm_adres_domTF.getText() );
	        					if(testDom.matches())
	        						adm_adres_domM.setText(" ok");
	        					else 
	        						adm_adres_domM.setText(" niepoprawny nr domu");
	        					//nr mieszkania
		    					Pattern nrMieszkP = Pattern.compile("[1-9][0-9]{0,3}");
		    					Matcher testNrMieszk = nrMieszkP.matcher(adm_adres_mieszkTF.getText() );
		    					if(testNrMieszk.matches())
		    						adm_adres_mieszkM.setText(" ok");
		    					else 
		    						adm_adres_mieszkM.setText(" niepoprawny nr mieszkania");
		    					//kod pocztowy
		    					Pattern kodPocztP = Pattern.compile("[0-9]{2}[-][0-9]{3}");
		    					Matcher testKodPoczt = kodPocztP.matcher(adm_adres_kodTF.getText() );
		    					if(testKodPoczt.matches())
		    						adm_adres_kodM.setText(" ok");
		    					else 
		    						adm_adres_kodM.setText(" niepoprawny kod pocztowy");
		    					//sprawdzenie wszystkich danych
		    					if(testUlica.matches() && testDom.matches() && testNrMieszk.matches() && testKodPoczt.matches() && 
		    							(adm_adres_zmien_adres_CB.getSelectionModel().getSelectedItem()!=null) )
		    					{
		    						adm_adres_zmien_messg.setText("zmieniono rekord");
		    						zaznaczonyAdres.setUlica(adm_adres_ulicaTF.getText() );
		    						zaznaczonyAdres.setNrDomu(adm_adres_domTF.getText());
		    						zaznaczonyAdres.setNrMieszkania(Integer.parseInt(adm_adres_mieszkTF.getText()));
		    						zaznaczonyAdres.setKodPocztowy(adm_adres_kodTF.getText());
		    						zaznaczonyAdres.setMiasto_id( (Miasto)adm_adres_zmien_adres_CB.getSelectionModel().getSelectedItem() );
		    						//sesja do adm_Adres - zmien
	        	        			Session sessionAdmAdres_zmien = factory.openSession();
	        	        			Transaction t_AdmAdres_zmien = sessionAdmAdres_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmAdres_zmien.update(zaznaczonyAdres);
	        	        				t_AdmAdres_zmien.commit();
	        	        			}catch (Exception e){
	                    				if (t_AdmAdres_zmien != null) {
	                    					t_AdmAdres_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmAdres_zmien.close();
	        	        			}				
		    					}
		    					else
		    					{
		    						adm_adres_zmien_messg.setText("niepoprawne dane");
		    					}
		        			});
        				}
        			});
        			//adm - usun adres
        			adm_adres_usun.setOnAction(adm_adres_usun_a->
        			{
        				//zaznaczony rekord
    					Adres zaznaczonyAdres = TV_adm_adres.getSelectionModel().getSelectedItem();
        				if(zaznaczonyAdres != null)
        				{	
        					//sesja do adm_adres_usun
	            			Session sessionAdmAdr_usun = factory.openSession();
	            			Transaction t_AdmAdr_usun = sessionAdmAdr_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego adresu
	            				sessionAdmAdr_usun.remove(zaznaczonyAdres);
	            				//odœwie¿enie stanu tabeli po usuniêciu
	            				//lista wszystkich adresow
	            				@SuppressWarnings("unchecked")
	    						List<Adres> wynikAdmAdres = sessionAdmAdr_usun.createQuery("from Adres").getResultList();
	            				//lista obiektow - przepisanie elementow listy do OL_list
	            				ObservableList<Adres> OL_admAdres = FXCollections.observableArrayList();
	            				for(int i=0; i<wynikAdmAdres.size(); i++)
	            				{
	            					OL_admAdres.add( wynikAdmAdres.get(i) );
	            				}
	            				//dodanie elementow list do tabeli
	            				TV_adm_adres.setItems(OL_admAdres);
	            				t_AdmAdr_usun.commit();
	            				sessionAdmAdr_usun.close();
	            			}catch(Exception e) {	
	            				if (t_AdmAdr_usun != null) {
	            					t_AdmAdr_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
        				}
        			});
				});

				//adm - trener
				adm_tabTrenerB.setOnAction(adm_trener_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Trener\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela trenerów
    				TableView<Trener> TV_adm_trener = new TableView<>();
    				//kol-id trenera
    				TableColumn<Trener, Integer> TC_admTrener_id = new TableColumn<>("Id");
    				TC_admTrener_id.setCellValueFactory(new PropertyValueFactory<Trener, Integer>("id"));
    				TC_admTrener_id.setMinWidth(50);
    				//kol-imie trenera
    				TableColumn<Trener, String> TC_admTrener_imie = new TableColumn<>("Imie");
    				TC_admTrener_imie.setCellValueFactory(new PropertyValueFactory<Trener, String>("imie"));
    				TC_admTrener_imie.setMinWidth(150);
    				//kol-nazw trenera
    				TableColumn<Trener, String> TC_admTrener_nazw = new TableColumn<>("Nazwisko");
    				TC_admTrener_nazw.setCellValueFactory(new PropertyValueFactory<Trener, String>("nazwisko"));
    				TC_admTrener_nazw.setMinWidth(180);
    				//kol-rokUr trenera
    				TableColumn<Trener, String> TC_admTrener_rok = new TableColumn<>("Rok urodzenia");
    				TC_admTrener_rok.setCellValueFactory(new PropertyValueFactory<Trener, String>("rokUrodzenia"));
    				TC_admTrener_rok.setMinWidth(180);
    				//kol-nrTel trenera
    				TableColumn<Trener, String> TC_admTrener_tel = new TableColumn<>("Nr telefonu");
    				TC_admTrener_tel.setCellValueFactory(new PropertyValueFactory<Trener, String>("nrTelefonu"));
    				TC_admTrener_tel.setMinWidth(150);
    				//kol-adres
    				TableColumn<Trener, String> TC_admTrener_adres = new TableColumn<>("Adres (id: ulica,miasto)");
    				TC_admTrener_adres.setCellValueFactory(new Callback<CellDataFeatures<Trener, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Trener, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getAdres_id().getId() + ": " + arg0.getValue().getAdres_id().getUlica() +
														", " + arg0.getValue().getAdres_id().getMiasto_id().getNazwa() );
						}
    	            });
    				TC_admTrener_adres.setMinWidth(400);
    				//kol-klub
    				TableColumn<Trener, String> TC_admTrener_klub = new TableColumn<>("Klub");
    				TC_admTrener_klub.setCellValueFactory(new Callback<CellDataFeatures<Trener, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Trener, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getKlub_id().getNazwa() );
						}
    	            });
    				//kol-login trenera
    				TableColumn<Trener, String> TC_admTrener_log = new TableColumn<>("Login");
    				TC_admTrener_log.setCellValueFactory(new PropertyValueFactory<Trener, String>("login_"));
    				TC_admTrener_log.setMinWidth(170);
    				//kol-haslo trenera
    				TableColumn<Trener, String> TC_admTrener_haslo = new TableColumn<>("Has³o");
    				TC_admTrener_haslo.setCellValueFactory(new PropertyValueFactory<Trener, String>("haslo_"));
    				TC_admTrener_haslo.setMinWidth(170);
    				//sesja do adm_trener
        			Session sessionAdmTrener = factory.openSession();
        			Transaction t_AdmTrener = sessionAdmTrener.beginTransaction();
    				try {
    					//lista wszystkich trenerow
        				@SuppressWarnings("unchecked")
						List<Trener> wynikAdmTrener = sessionAdmTrener.createQuery("from Trener").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Trener> OL_admTrener = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmTrener.size(); i++)
        				{
        					OL_admTrener.add( wynikAdmTrener.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_trener.getColumns().addAll(TC_admTrener_id, TC_admTrener_imie, TC_admTrener_nazw, TC_admTrener_rok, TC_admTrener_tel,
        												TC_admTrener_adres, TC_admTrener_klub, TC_admTrener_log, TC_admTrener_haslo);
        				//dodanie elementow list do tabeli
        				TV_adm_trener.setItems(OL_admTrener);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_trener);
    				}finally {
    					t_AdmTrener.commit();
    					sessionAdmTrener.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_trener_HB = new HBox();
        			adm_trener_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_trener_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_trener_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_trener_usun = new Button("Usuñ zaznaczony rekord");
        			adm_trener_HB.getChildren().addAll(adm_trener_dodaj, adm_trener_zmien, adm_trener_usun);
        			panel_prawy.getChildren().addAll(adm_trener_HB);	
        			//elementy potrzebne do dodawania, edycji ::
        			//imie
    				HBox adm_trener_dodaj_imieHB = new HBox();
    				adm_trener_dodaj_imieHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_imieL = new Label("Imie: "); 
    				TextField adm_trener_dodaj_imieTF = new TextField(); 
    				Label adm_trener_dodaj_imieM = new Label(); 
    				adm_trener_dodaj_imieL.setId("adm_edycjaL");
    				adm_trener_dodaj_imieTF.setId("adm_edycjaTF");
    				adm_trener_dodaj_imieM.setId("adm_edycjaM");
    				adm_trener_dodaj_imieHB.getChildren().addAll(adm_trener_dodaj_imieL, adm_trener_dodaj_imieTF, adm_trener_dodaj_imieM);
    				//nazwisko
    				HBox adm_trener_dodaj_nazwHB = new HBox();
    				adm_trener_dodaj_nazwHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_nazwL = new Label("Nazwisko: "); 
    				TextField adm_trener_dodaj_nazwTF = new TextField(); 
    				Label adm_trener_dodaj_nazwM = new Label(); 
    				adm_trener_dodaj_nazwL.setId("adm_edycjaL");
    				adm_trener_dodaj_nazwTF.setId("adm_edycjaTF");
    				adm_trener_dodaj_nazwM.setId("adm_edycjaM");
    				adm_trener_dodaj_nazwHB.getChildren().addAll(adm_trener_dodaj_nazwL, adm_trener_dodaj_nazwTF, adm_trener_dodaj_nazwM);
    				//rokUrodzenia
    				HBox adm_trener_dodaj_rokUrHB = new HBox();
    				adm_trener_dodaj_rokUrHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_rokUrL = new Label("Rok urodzenia: "); 
    				TextField adm_trener_dodaj_rokUrTF = new TextField(); 
    				Label adm_trener_dodaj_rokUrM = new Label(); 
    				adm_trener_dodaj_rokUrL.setId("adm_edycjaL");
    				adm_trener_dodaj_rokUrTF.setId("adm_edycjaTF");
    				adm_trener_dodaj_rokUrM.setId("adm_edycjaM");
    				adm_trener_dodaj_rokUrHB.getChildren().addAll(adm_trener_dodaj_rokUrL, adm_trener_dodaj_rokUrTF, adm_trener_dodaj_rokUrM);
    				//nrTelefonu
    				HBox adm_trener_dodaj_nrTelHB = new HBox();
    				adm_trener_dodaj_nrTelHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_nrTelL = new Label("Nr telefonu: "); 
    				TextField adm_trener_dodaj_nrTelTF = new TextField(); 
    				Label adm_trener_dodaj_nrTelM = new Label(); 
    				adm_trener_dodaj_nrTelL.setId("adm_edycjaL");
    				adm_trener_dodaj_nrTelTF.setId("adm_edycjaTF");
    				adm_trener_dodaj_nrTelM.setId("adm_edycjaM");
    				adm_trener_dodaj_nrTelHB.getChildren().addAll(adm_trener_dodaj_nrTelL, adm_trener_dodaj_nrTelTF, adm_trener_dodaj_nrTelM);
    				//adres - comboBox
    				//sesja do comboBox
    				Session sessionAdmTrener_adres_CB = factory.openSession();
        			Transaction t_AdmTrener_adres_CB = sessionAdmTrener_adres_CB.beginTransaction();
        			//OL_list
        			ObservableList<Adres> OL_admTrener_adres = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Adres> wynikAdmTrener_adres = sessionAdmTrener_adres_CB.createQuery("from Adres").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmTrener_adres.size(); i++)
        				{
        					OL_admTrener_adres.add( wynikAdmTrener_adres.get(i) );
        				}
        			}finally {
        				t_AdmTrener_adres_CB.commit();
        				sessionAdmTrener_adres_CB.close();
        			}
        			final ComboBox<Adres> adm_trener_adresCB = new ComboBox<Adres>(OL_admTrener_adres);
    				adm_trener_adresCB.setPromptText("wybierz adres");
    				HBox adm_trener_dodaj_adresHB = new HBox();
    				adm_trener_dodaj_adresHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_adresL = new Label("Adres: "); 
    				Label adm_trener_dodaj_adresM = new Label(); 
    				adm_trener_dodaj_adresL.setId("adm_edycjaL");
    				adm_trener_adresCB.setId("adm_edycjaTF");
    				adm_trener_dodaj_adresM.setId("adm_edycjaM");
    				adm_trener_dodaj_adresHB.getChildren().addAll(adm_trener_dodaj_adresL, adm_trener_adresCB, adm_trener_dodaj_adresM);
    				//klub - comboBox
    				//sesja do comboBox
    				Session sessionAdmTrener_klub_CB = factory.openSession();
        			Transaction t_AdmTrener_klub_CB = sessionAdmTrener_klub_CB.beginTransaction();
        			//OL_list
        			ObservableList<Klub> OL_admTrener_klub = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Klub> wynikAdmTrener_klub = sessionAdmTrener_klub_CB.createQuery("from Klub").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmTrener_klub.size(); i++)
        				{
        					OL_admTrener_klub.add( wynikAdmTrener_klub.get(i) );
        				}
        			}finally {
        				t_AdmTrener_klub_CB.commit();
        				sessionAdmTrener_klub_CB.close();
        			}
        			final ComboBox<Klub> adm_trener_klubCB = new ComboBox<Klub>(OL_admTrener_klub);
    				adm_trener_klubCB.setPromptText("wybierz klub");
    				HBox adm_trener_dodaj_klubHB = new HBox();
    				adm_trener_dodaj_klubHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_klubL = new Label("Klub: "); 
    				Label adm_trener_dodaj_klubM = new Label(); 
    				adm_trener_dodaj_klubL.setId("adm_edycjaL");
    				adm_trener_klubCB.setId("adm_edycjaTF");
    				adm_trener_dodaj_klubM.setId("adm_edycjaM");
    				adm_trener_dodaj_klubHB.getChildren().addAll(adm_trener_dodaj_klubL, adm_trener_klubCB, adm_trener_dodaj_klubM);
    				//login
    				HBox adm_trener_dodaj_logHB = new HBox();
    				adm_trener_dodaj_logHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_logL = new Label("Login: "); 
    				TextField adm_trener_dodaj_logTF = new TextField(); 
    				Label adm_trener_dodaj_logM = new Label(); 
    				adm_trener_dodaj_logL.setId("adm_edycjaL");
    				adm_trener_dodaj_logTF.setId("adm_edycjaTF");
    				adm_trener_dodaj_logM.setId("adm_edycjaM");
    				adm_trener_dodaj_logHB.getChildren().addAll(adm_trener_dodaj_logL, adm_trener_dodaj_logTF, adm_trener_dodaj_logM);
    				//haslo
    				HBox adm_trener_dodaj_haslHB = new HBox();
    				adm_trener_dodaj_haslHB.getStyleClass().add("center");
    				Label adm_trener_dodaj_haslL = new Label("Has³o: "); 
    				TextField adm_trener_dodaj_haslTF = new TextField(); 
    				Label adm_trener_dodaj_haslM = new Label(); 
    				adm_trener_dodaj_haslL.setId("adm_edycjaL");
    				adm_trener_dodaj_haslTF.setId("adm_edycjaTF");
    				adm_trener_dodaj_haslM.setId("adm_edycjaM");
    				adm_trener_dodaj_haslHB.getChildren().addAll(adm_trener_dodaj_haslL, adm_trener_dodaj_haslTF, adm_trener_dodaj_haslM);
    				//messg - wiadomosc o poprawnosci wszystkich danych
    				HBox adm_trener_zatw_messgHB = new HBox();
    				adm_trener_zatw_messgHB.getStyleClass().add("center");
    				Button adm_trener_zatw_zatwB = new Button("zatw");
    				Label adm_trener_zatw_messg = new Label();
    				adm_trener_zatw_messg.getStyleClass().add("adm_dodajMessg");
    				adm_trener_zatw_messgHB.getChildren().addAll(adm_trener_zatw_messg, adm_trener_zatw_zatwB);
    				//wzorce
    				Pattern imieP = Pattern.compile("[A-Z][a-z]{2,15}");
    				Pattern nazwP = Pattern.compile("[A-Z][a-z]{2,31}");
    				Pattern rokP = Pattern.compile("[12][0-9]{3}");
    				Pattern telP = Pattern.compile("[1-9][0-9]{8}");
    				Pattern logP = Pattern.compile("[A-Za-z0-9]{3,32}");
    				Pattern haslP = Pattern.compile("[A-Za-z0-9]{5,32}");
        			//////////////////
        			//trener - dodaj
        			adm_trener_dodaj.setOnAction(adm_trener_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//dodanie wszystkich HB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_trener_dodaj_imieHB, adm_trener_dodaj_nazwHB, adm_trener_dodaj_rokUrHB, 
        												adm_trener_dodaj_nrTelHB, adm_trener_dodaj_adresHB, adm_trener_dodaj_klubHB, 
        												adm_trener_dodaj_logHB, adm_trener_dodaj_haslHB, adm_trener_zatw_messgHB);
        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
        				adm_trener_zatw_zatwB.setText("dodaj");
        				//akcja przycisku zatwierdŸ (tutaj dodaj)
        				adm_trener_zatw_zatwB.setOnAction(adm_trener_zatw_dodaj_a->{
        					//pobranie tekstu do sprawdzenia wzorców
        					Matcher testImie = imieP.matcher( adm_trener_dodaj_imieTF.getText() );
        					Matcher testNazw = nazwP.matcher( adm_trener_dodaj_nazwTF.getText() );
        					Matcher testRok = rokP.matcher( adm_trener_dodaj_rokUrTF.getText() );
        					Matcher testTel = telP.matcher( adm_trener_dodaj_nrTelTF.getText() );
        					Matcher testLog = logP.matcher( adm_trener_dodaj_logTF.getText() );
        					Matcher testHasl = haslP.matcher( adm_trener_dodaj_haslTF.getText() );
        					//sprawdzenie wzorców i comboBoxów
        					if(testImie.matches())
        						adm_trener_dodaj_imieM.setText(" ok");
        					else 
        						adm_trener_dodaj_imieM.setText(" niepoprawne imie");
        					if(testNazw.matches())
        						adm_trener_dodaj_nazwM.setText(" ok");
        					else 
        						adm_trener_dodaj_nazwM.setText(" niepoprawne nazwisko");
        					if(testRok.matches())
        						adm_trener_dodaj_rokUrM.setText(" ok");
        					else 
        						adm_trener_dodaj_rokUrM.setText(" niepoprawny rok");
        					if(testTel.matches())
        						adm_trener_dodaj_nrTelM.setText(" ok");
        					else 
        						adm_trener_dodaj_nrTelM.setText(" niepoprawny nr telefonu");
        					if(adm_trener_adresCB.getSelectionModel().getSelectedItem()!=null)
        						adm_trener_dodaj_adresM.setText(" ok");
        					else
        						adm_trener_dodaj_adresM.setText(" wybierz adres");
        					if(adm_trener_klubCB.getSelectionModel().getSelectedItem()!=null)
        						adm_trener_dodaj_klubM.setText(" ok");
        					else
        						adm_trener_dodaj_klubM.setText(" wybierz klub");
        					if(testLog.matches())
        						adm_trener_dodaj_logM.setText(" ok");
        					else 
        						adm_trener_dodaj_logM.setText(" niepoprawny login");
        					if(testHasl.matches())
        						adm_trener_dodaj_haslM.setText(" ok");
        					else 
        						adm_trener_dodaj_haslM.setText(" niepoprawne has³o");
        					//sprawdzenie wszystkich wzorców i comboBoxów razem
        					if( testImie.matches() && testNazw.matches() && testRok.matches() && testTel.matches() &&
        							adm_trener_adresCB.getSelectionModel().getSelectedItem()!=null && 
        							adm_trener_klubCB.getSelectionModel().getSelectedItem()!=null && 
        							testLog.matches() && testHasl.matches())
        					{
        						//nowy rekord
        						Trener nowyTrener = new Trener(adm_trener_dodaj_imieTF.getText(), adm_trener_dodaj_nazwTF.getText(), 
        											Integer.parseInt(adm_trener_dodaj_rokUrTF.getText()), 
        											Integer.parseInt(adm_trener_dodaj_nrTelTF.getText()), 
        											adm_trener_adresCB.getSelectionModel().getSelectedItem(),
        											adm_trener_klubCB.getSelectionModel().getSelectedItem(), 
        											adm_trener_dodaj_logTF.getText(), adm_trener_dodaj_haslTF.getText());
        						//sesja do adm_trener - dodaj
        	        			Session sessionAdmTrener_dodaj = factory.openSession();
        	        			Transaction t_AdmTrener_dodaj = sessionAdmTrener_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmTrener_dodaj.save(nowyTrener);
        	        				t_AdmTrener_dodaj.commit();
        	        				adm_trener_zatw_messg.setText("dodano nowy rekord");
        	        			}catch (Exception e){
                    				if (t_AdmTrener_dodaj != null) {
                    					t_AdmTrener_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmTrener_dodaj.close();
        	        			}
        					}
        					else 
            				{
            					adm_trener_zatw_messg.setText("niepoprawne dane");
            				}
        				});
        			});
        			//trener - zmien
					adm_trener_zmien.setOnAction(adm_trener_zmien_a->{
						Trener zaznaczonyTrener = TV_adm_trener.getSelectionModel().getSelectedItem();
						if(zaznaczonyTrener!=null)
						{
							panel_prawy.getChildren().clear();
	        				//dodanie wszystkich HB do panelu prawego
	        				panel_prawy.getChildren().addAll(adm_trener_dodaj_imieHB, adm_trener_dodaj_nazwHB, adm_trener_dodaj_rokUrHB, 
	        												adm_trener_dodaj_nrTelHB, adm_trener_dodaj_adresHB, adm_trener_dodaj_klubHB, 
	        												adm_trener_dodaj_logHB, adm_trener_dodaj_haslHB, adm_trener_zatw_messgHB);
	        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
	        				adm_trener_zatw_zatwB.setText("zmieñ");		
	        				//
	        				//wype³nienie TF'ów danymi zaznaczonego rekord
	        				adm_trener_dodaj_imieTF.setText( zaznaczonyTrener.getImie() );
	        				adm_trener_dodaj_nazwTF.setText( zaznaczonyTrener.getNazwisko() );
	        				adm_trener_dodaj_rokUrTF.setText( Integer.toString(zaznaczonyTrener.getRokUrodzenia()) );
	        				adm_trener_dodaj_nrTelTF.setText( Integer.toString(zaznaczonyTrener.getNrTelefonu()) );
	        				adm_trener_adresCB.setValue( zaznaczonyTrener.getAdres_id() );
	        				adm_trener_klubCB.setValue( zaznaczonyTrener.getKlub_id() );
	        				adm_trener_dodaj_logTF.setText( zaznaczonyTrener.getLogin_() );
	        				adm_trener_dodaj_haslTF.setText( zaznaczonyTrener.getHaslo_() );
	        				//
	        				//akcja przycisku zatwierdŸ (tutaj zmieñ)
	        				adm_trener_zatw_zatwB.setOnAction(adm_trener_zatw_zmien_a->{
	        					//pobranie tekstu do sprawdzenia wzorców
	        					Matcher testImie = imieP.matcher( adm_trener_dodaj_imieTF.getText() );
	        					Matcher testNazw = nazwP.matcher( adm_trener_dodaj_nazwTF.getText() );
	        					Matcher testRok = rokP.matcher( adm_trener_dodaj_rokUrTF.getText() );
	        					Matcher testTel = telP.matcher( adm_trener_dodaj_nrTelTF.getText() );
	        					Matcher testLog = logP.matcher( adm_trener_dodaj_logTF.getText() );
	        					Matcher testHasl = haslP.matcher( adm_trener_dodaj_haslTF.getText() );
	        					//sprawdzenie wzorców i comboBoxów
	        					if(testImie.matches())
	        						adm_trener_dodaj_imieM.setText(" ok");
	        					else 
	        						adm_trener_dodaj_imieM.setText(" niepoprawne imie");
	        					if(testNazw.matches())
	        						adm_trener_dodaj_nazwM.setText(" ok");
	        					else 
	        						adm_trener_dodaj_nazwM.setText(" niepoprawne nazwisko");
	        					if(testRok.matches())
	        						adm_trener_dodaj_rokUrM.setText(" ok");
	        					else 
	        						adm_trener_dodaj_rokUrM.setText(" niepoprawny rok");
	        					if(testTel.matches())
	        						adm_trener_dodaj_nrTelM.setText(" ok");
	        					else 
	        						adm_trener_dodaj_nrTelM.setText(" niepoprawny nr telefonu");
	        					if(adm_trener_adresCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_trener_dodaj_adresM.setText(" ok");
	        					else
	        						adm_trener_dodaj_adresM.setText(" wybierz adres");
	        					if(adm_trener_klubCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_trener_dodaj_klubM.setText(" ok");
	        					else
	        						adm_trener_dodaj_klubM.setText(" wybierz klub");
	        					if(testLog.matches())
	        						adm_trener_dodaj_logM.setText(" ok");
	        					else 
	        						adm_trener_dodaj_logM.setText(" niepoprawny login");
	        					if(testHasl.matches())
	        						adm_trener_dodaj_haslM.setText(" ok");
	        					else 
	        						adm_trener_dodaj_haslM.setText(" niepoprawne has³o");
	        					//sprawdzenie wszystkich wzorców i comboBoxów razem
	        					if( testImie.matches() && testNazw.matches() && testRok.matches() && testTel.matches() &&
	        							adm_trener_adresCB.getSelectionModel().getSelectedItem()!=null && 
	        							adm_trener_klubCB.getSelectionModel().getSelectedItem()!=null && 
	        							testLog.matches() && testHasl.matches())
	        					{
	        						//zmiany w zaznaczonym rekordzie
	        						zaznaczonyTrener.setImie( adm_trener_dodaj_imieTF.getText() );
	        						zaznaczonyTrener.setNazwisko( adm_trener_dodaj_nazwTF.getText() );
	        						zaznaczonyTrener.setRokUrodzenia( Integer.parseInt(adm_trener_dodaj_rokUrTF.getText()) );
	        						zaznaczonyTrener.setNrTelefonu( Integer.parseInt(adm_trener_dodaj_nrTelTF.getText()) );
	        						zaznaczonyTrener.setAdres_id( adm_trener_adresCB.getSelectionModel().getSelectedItem() );
	        						zaznaczonyTrener.setKlub_id( adm_trener_klubCB.getSelectionModel().getSelectedItem() );
	        						zaznaczonyTrener.setLogin_( adm_trener_dodaj_logTF.getText() );
	        						zaznaczonyTrener.setHaslo_( adm_trener_dodaj_haslTF.getText() );
	        						//sesja do adm_trener - zmien
	        	        			Session sessionAdmTrener_zmien = factory.openSession();
	        	        			Transaction t_AdmTrener_zmien = sessionAdmTrener_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmTrener_zmien.update(zaznaczonyTrener);
	        	        				t_AdmTrener_zmien.commit();
	        	        				adm_trener_zatw_messg.setText("zmieniono rekord");
	        	        			}catch (Exception e){
	                    				if (t_AdmTrener_zmien != null) {
	                    					t_AdmTrener_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmTrener_zmien.close();
	        	        			}
	        					}
	        					else 
	            				{
	            					adm_trener_zatw_messg.setText("niepoprawne dane");
	            				}
	        				});
						}
					});
        			//trener - usun
					adm_trener_usun.setOnAction(adm_trener_usun_a->{
						Trener zaznaczonyTrener = TV_adm_trener.getSelectionModel().getSelectedItem();
						if(zaznaczonyTrener!=null)
						{
							//sesja do adm_trener_usun
	            			Session sessionAdmTrener_usun = factory.openSession();
	            			Transaction t_AdmTrener_usun = sessionAdmTrener_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego rekordu
	            				sessionAdmTrener_usun.remove(zaznaczonyTrener);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich rekordów
	                			@SuppressWarnings("unchecked")
	        					List<Trener> wynikAdmtrener_usun = sessionAdmTrener_usun.createQuery("from Trener").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Trener> OL_admTrener_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmtrener_usun.size(); i++)
	                			{
	                				OL_admTrener_usun.add( wynikAdmtrener_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_trener.setItems(OL_admTrener_usun);
	                			//
	                			t_AdmTrener_usun.commit();
	                			sessionAdmTrener_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmTrener_usun != null) {
	            					t_AdmTrener_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
						}
					});
				});
				
				//adm - grupa
				adm_tabGrupaB.setOnAction(adm_grupa_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Grupa\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela grupaów
    				TableView<Grupa> TV_adm_grupa = new TableView<>();
    				//kol-id grupy
    				TableColumn<Grupa, Integer> TC_admGrupa_id = new TableColumn<>("Id");
    				TC_admGrupa_id.setCellValueFactory(new PropertyValueFactory<Grupa, Integer>("id"));
    				TC_admGrupa_id.setMinWidth(50);
    				//kol-nazwa grupy
    				TableColumn<Grupa, String> TC_admGrupa_nazwa = new TableColumn<>("Nazwa");
    				TC_admGrupa_nazwa.setCellValueFactory(new PropertyValueFactory<Grupa, String>("nazwa"));
    				TC_admGrupa_nazwa.setMinWidth(250);
    				//kol-kategoria grupy
    				TableColumn<Grupa, String> TC_admGrupa_kategoria = new TableColumn<>("Kategoria");
    				TC_admGrupa_kategoria.setCellValueFactory(new Callback<CellDataFeatures<Grupa, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Grupa, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getKategoria_id().getNazwa() );
						}
    	            });
    				TC_admGrupa_kategoria.setMinWidth(150);
    				//kol-trener grupy
    				TableColumn<Grupa, String> TC_admGrupa_trener = new TableColumn<>("Trener (id: imie,nazwisko)");
    				TC_admGrupa_trener.setCellValueFactory(new Callback<CellDataFeatures<Grupa, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Grupa, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getTrener_id().getId() + ": " + arg0.getValue().getTrener_id().getImie() +
															" " + arg0.getValue().getTrener_id().getNazwisko() );
						}
    	            });
    				TC_admGrupa_trener.setMinWidth(300);
    				//sesja do adm_grupa
        			Session sessionAdmGrupa = factory.openSession();
        			Transaction t_AdmGrupa = sessionAdmGrupa.beginTransaction();
    				try {
    					//lista wszystkich grupaow
        				@SuppressWarnings("unchecked")
						List<Grupa> wynikAdmGrupa = sessionAdmGrupa.createQuery("from Grupa").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Grupa> OL_admGrupa = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmGrupa.size(); i++)
        				{
        					OL_admGrupa.add( wynikAdmGrupa.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_grupa.getColumns().addAll(TC_admGrupa_id, TC_admGrupa_nazwa, TC_admGrupa_kategoria, TC_admGrupa_trener);
        				//dodanie elementow list do tabeli
        				TV_adm_grupa.setItems(OL_admGrupa);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_grupa);
    				}finally {
    					t_AdmGrupa.commit();
    					sessionAdmGrupa.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_grupa_HB = new HBox();
        			adm_grupa_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_grupa_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_grupa_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_grupa_usun = new Button("Usuñ zaznaczony rekord");
        			adm_grupa_HB.getChildren().addAll(adm_grupa_dodaj, adm_grupa_zmien, adm_grupa_usun);
        			panel_prawy.getChildren().addAll(adm_grupa_HB);	
        			//elementy potrzebne do dodawania, edycji ::
        			//nazwa
    				HBox adm_grupa_dodaj_nazwaHB = new HBox();
    				adm_grupa_dodaj_nazwaHB.getStyleClass().add("center");
    				Label adm_grupa_dodaj_nazwaL = new Label("Nazwa: "); 
    				TextField adm_grupa_dodaj_nazwaTF = new TextField(); 
    				Label adm_grupa_dodaj_nazwaM = new Label(); 
    				adm_grupa_dodaj_nazwaL.setId("adm_edycjaL");
    				adm_grupa_dodaj_nazwaTF.setId("adm_edycjaTF");
    				adm_grupa_dodaj_nazwaM.setId("adm_edycjaM");
    				adm_grupa_dodaj_nazwaHB.getChildren().addAll(adm_grupa_dodaj_nazwaL, adm_grupa_dodaj_nazwaTF, adm_grupa_dodaj_nazwaM);
    				//kategoria - comboBox
    				//sesja do comboBox
    				Session sessionAdmGrupa_kategoria_CB = factory.openSession();
        			Transaction t_AdmGrupa_kategoria_CB = sessionAdmGrupa_kategoria_CB.beginTransaction();
        			//OL_list
        			ObservableList<Kategoria> OL_admGrupa_kategoria = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Kategoria> wynikAdmGrupa_kategoria = sessionAdmGrupa_kategoria_CB.createQuery("from Kategoria").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmGrupa_kategoria.size(); i++)
        				{
        					OL_admGrupa_kategoria.add( wynikAdmGrupa_kategoria.get(i) );
        				}
        			}finally {
        				t_AdmGrupa_kategoria_CB.commit();
        				sessionAdmGrupa_kategoria_CB.close();
        			}
        			final ComboBox<Kategoria> adm_grupa_kategoriaCB = new ComboBox<Kategoria>(OL_admGrupa_kategoria);
    				adm_grupa_kategoriaCB.setPromptText("wybierz kategoriê");
    				HBox adm_grupa_dodaj_kategoriaHB = new HBox();
    				adm_grupa_dodaj_kategoriaHB.getStyleClass().add("center");
    				Label adm_grupa_dodaj_kategoriaL = new Label("Kategoria: "); 
    				Label adm_grupa_dodaj_kategoriaM = new Label(); 
    				adm_grupa_dodaj_kategoriaL.setId("adm_edycjaL");
    				adm_grupa_kategoriaCB.setId("adm_edycjaTF");
    				adm_grupa_dodaj_kategoriaM.setId("adm_edycjaM");
    				adm_grupa_dodaj_kategoriaHB.getChildren().addAll(adm_grupa_dodaj_kategoriaL, adm_grupa_kategoriaCB, adm_grupa_dodaj_kategoriaM);
    				//trener - comboBox
    				//sesja do comboBox
    				Session sessionAdmGrupa_trener_CB = factory.openSession();
        			Transaction t_AdmGrupa_trener_CB = sessionAdmGrupa_trener_CB.beginTransaction();
        			//OL_list
        			ObservableList<Trener> OL_admGrupa_trener = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Trener> wynikAdmGrupa_trener = sessionAdmGrupa_trener_CB.createQuery("from Trener").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmGrupa_trener.size(); i++)
        				{
        					OL_admGrupa_trener.add( wynikAdmGrupa_trener.get(i) );
        				}
        			}finally {
        				t_AdmGrupa_trener_CB.commit();
        				sessionAdmGrupa_trener_CB.close();
        			}
        			final ComboBox<Trener> adm_grupa_trenerCB = new ComboBox<Trener>(OL_admGrupa_trener);
    				adm_grupa_trenerCB.setPromptText("wybierz trenera");
    				HBox adm_grupa_dodaj_trenerHB = new HBox();
    				adm_grupa_dodaj_trenerHB.getStyleClass().add("center");
    				Label adm_grupa_dodaj_trenerL = new Label("Trener: "); 
    				Label adm_grupa_dodaj_trenerM = new Label(); 
    				adm_grupa_dodaj_trenerL.setId("adm_edycjaL");
    				adm_grupa_trenerCB.setId("adm_edycjaTF");
    				adm_grupa_dodaj_trenerM.setId("adm_edycjaM");
    				adm_grupa_dodaj_trenerHB.getChildren().addAll(adm_grupa_dodaj_trenerL, adm_grupa_trenerCB, adm_grupa_dodaj_trenerM);
    				//messg - wiadomosc o poprawnosci wszystkich danych
    				HBox adm_grupa_zatw_messgHB = new HBox();
    				adm_grupa_zatw_messgHB.getStyleClass().add("center");
    				Button adm_grupa_zatw_zatwB = new Button("zatw");
    				Label adm_grupa_zatw_messg = new Label();
    				adm_grupa_zatw_messg.getStyleClass().add("adm_dodajMessg");
    				adm_grupa_zatw_messgHB.getChildren().addAll(adm_grupa_zatw_messg, adm_grupa_zatw_zatwB);
    				//wzorce
    				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,40}");
    				//////////////////
        			//grupa - dodaj
        			adm_grupa_dodaj.setOnAction(adm_grupa_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//dodanie wszystkich HB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_grupa_dodaj_nazwaHB, adm_grupa_dodaj_kategoriaHB, 
        													adm_grupa_dodaj_trenerHB, adm_grupa_zatw_messgHB);
        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
        				adm_grupa_zatw_zatwB.setText("dodaj");
        				//akcja przycisku zatwierdŸ (tutaj dodaj)
        				adm_grupa_zatw_zatwB.setOnAction(adm_grupa_zatw_dodaj_a->{
        					//pobranie tekstu do sprawdzenia wzorców
        					Matcher testNazwa = nazwaP.matcher( adm_grupa_dodaj_nazwaTF.getText() );
        					//sprawdzenie wzorców i comboBoxów pojedynczo
        					if(testNazwa.matches())
        						adm_grupa_dodaj_nazwaM.setText(" ok");
        					else 
        						adm_grupa_dodaj_nazwaM.setText(" niepoprawna nazwa");
        					if(adm_grupa_kategoriaCB.getSelectionModel().getSelectedItem()!=null)
        						adm_grupa_dodaj_kategoriaM.setText(" ok");
        					else
        						adm_grupa_dodaj_kategoriaM.setText(" wybierz kategoriê");
        					if(adm_grupa_trenerCB.getSelectionModel().getSelectedItem()!=null)
        						adm_grupa_dodaj_trenerM.setText(" ok");
        					else
        						adm_grupa_dodaj_trenerM.setText(" wybierz trenera");
        					//sprawdzenie wszystkich wzorców i comboBoxów razem
        					if( testNazwa.matches() && adm_grupa_kategoriaCB.getSelectionModel().getSelectedItem()!=null &&
        							adm_grupa_trenerCB.getSelectionModel().getSelectedItem()!=null )
        					{
        						//nowy rekord
        						Grupa nowaGrupa = new Grupa(adm_grupa_dodaj_nazwaTF.getText(), adm_grupa_kategoriaCB.getSelectionModel().getSelectedItem(),
        														adm_grupa_trenerCB.getSelectionModel().getSelectedItem() );
        						//sesja do adm_grupa - dodaj
        	        			Session sessionAdmGrupa_dodaj = factory.openSession();
        	        			Transaction t_AdmGrupa_dodaj = sessionAdmGrupa_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmGrupa_dodaj.save(nowaGrupa);
        	        				t_AdmGrupa_dodaj.commit();
        	        				adm_grupa_zatw_messg.setText("dodano nowy rekord");
        	        			}catch (Exception e){
                    				if (t_AdmGrupa_dodaj != null) {
                    					t_AdmGrupa_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmGrupa_dodaj.close();
        	        			}
        					}
        					else 
            				{
            					adm_grupa_zatw_messg.setText("niepoprawne dane");
            				}
        				});
        			});
        			//////////////////
        			//grupa - zmien
					adm_grupa_zmien.setOnAction(adm_grupa_zmien_a->{
						Grupa zaznaczonaGrupa = TV_adm_grupa.getSelectionModel().getSelectedItem();
						if(zaznaczonaGrupa!=null)
						{
							panel_prawy.getChildren().clear();
	        				//dodanie wszystkich HB do panelu prawego
	        				panel_prawy.getChildren().addAll(adm_grupa_dodaj_nazwaHB, adm_grupa_dodaj_kategoriaHB, 
	        													adm_grupa_dodaj_trenerHB, adm_grupa_zatw_messgHB);
	        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
	        				adm_grupa_zatw_zatwB.setText("zmieñ");
	        				//
	        				//wype³nienie TF'ów danymi zaznaczonego rekord
	        				//
	        				if(zaznaczonaGrupa.getNazwa()!=null)
	        					adm_grupa_dodaj_nazwaTF.setText( zaznaczonaGrupa.getNazwa() );
	        				adm_grupa_kategoriaCB.setValue( zaznaczonaGrupa.getKategoria_id() );
	        				adm_grupa_trenerCB.setValue( zaznaczonaGrupa.getTrener_id() );
	        				//akcja przycisku zatwierdŸ (tutaj dodaj)
	        				adm_grupa_zatw_zatwB.setOnAction(adm_grupa_zatw_zmien_a->{
	        					//pobranie tekstu do sprawdzenia wzorców
	        					Matcher testNazwa = nazwaP.matcher( adm_grupa_dodaj_nazwaTF.getText() );
	        					//sprawdzenie wzorców i comboBoxów pojedynczo
	        					if(testNazwa.matches())
	        						adm_grupa_dodaj_nazwaM.setText(" ok");
	        					else 
	        						adm_grupa_dodaj_nazwaM.setText(" niepoprawna nazwa");
	        					if(adm_grupa_kategoriaCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_grupa_dodaj_kategoriaM.setText(" ok");
	        					else
	        						adm_grupa_dodaj_kategoriaM.setText(" wybierz kategoriê");
	        					if(adm_grupa_trenerCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_grupa_dodaj_trenerM.setText(" ok");
	        					else
	        						adm_grupa_dodaj_trenerM.setText(" wybierz trenera");
	        					//sprawdzenie wszystkich wzorców i comboBoxów razem
	        					if( testNazwa.matches() && adm_grupa_kategoriaCB.getSelectionModel().getSelectedItem()!=null &&
	        							adm_grupa_trenerCB.getSelectionModel().getSelectedItem()!=null )
	        					{
	        						//zmiany w zaznaczonym rekordzie
	        						zaznaczonaGrupa.setNazwa( adm_grupa_dodaj_nazwaTF.getText() );
	        						zaznaczonaGrupa.setKategoria_id( adm_grupa_kategoriaCB.getSelectionModel().getSelectedItem() );
	        						zaznaczonaGrupa.setTrener_id( adm_grupa_trenerCB.getSelectionModel().getSelectedItem() );
	        						//sesja do adm_grupa - zmieñ
	        	        			Session sessionAdmGrupa_zmien = factory.openSession();
	        	        			Transaction t_AdmGrupa_zmien = sessionAdmGrupa_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmGrupa_zmien.update(zaznaczonaGrupa);
	        	        				t_AdmGrupa_zmien.commit();
	        	        				adm_grupa_zatw_messg.setText("zmieniono rekord");
	        	        			}catch (Exception e){
	                    				if (t_AdmGrupa_zmien != null) {
	                    					t_AdmGrupa_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmGrupa_zmien.close();
	        	        			}
	        					}
	        					else 
	            				{
	            					adm_grupa_zatw_messg.setText("niepoprawne dane");
	            				}
	        				});
						}
					});
					//////////////////
        			//grupa - usun
					adm_grupa_usun.setOnAction(adm_grupa_usun_a->{
						Grupa zaznaczonaGrupa = TV_adm_grupa.getSelectionModel().getSelectedItem();
						if(zaznaczonaGrupa!=null)
						{
							//sesja do adm_grupa_usun
	            			Session sessionAdmGrupa_usun = factory.openSession();
	            			Transaction t_AdmGrupa_usun = sessionAdmGrupa_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego rekordu
	            				sessionAdmGrupa_usun.remove(zaznaczonaGrupa);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich rekordów
	                			@SuppressWarnings("unchecked")
	        					List<Grupa> wynikAdmgrupa_usun = sessionAdmGrupa_usun.createQuery("from Grupa").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Grupa> OL_admGrupa_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmgrupa_usun.size(); i++)
	                			{
	                				OL_admGrupa_usun.add( wynikAdmgrupa_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_grupa.setItems(OL_admGrupa_usun);
	                			//
	                			t_AdmGrupa_usun.commit();
	                			sessionAdmGrupa_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmGrupa_usun != null) {
	            					t_AdmGrupa_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
						}
					});
				});
				
				//adm - sala
				adm_tabSalaB.setOnAction(adm_sala_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Sala\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela sal
    				TableView<Sala> TV_adm_sala = new TableView<>();
    				//kol-id sali
    				TableColumn<Sala, Integer> TC_admSala_id = new TableColumn<>("Id");
    				TC_admSala_id.setCellValueFactory(new PropertyValueFactory<Sala, Integer>("id"));
    				TC_admSala_id.setMinWidth(50);
    				//kol-nazwa sali
    				TableColumn<Sala, String> TC_admSala_nazwa = new TableColumn<>("Nazwa");
    				TC_admSala_nazwa.setCellValueFactory(new PropertyValueFactory<Sala, String>("nazwa"));
    				TC_admSala_nazwa.setMinWidth(400);
    				//kol-adres sali
    				TableColumn<Sala, String> TC_admSala_adres = new TableColumn<>("Adres (id: ulica,miasto)");
    				TC_admSala_adres.setCellValueFactory(new Callback<CellDataFeatures<Sala, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Sala, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getAdres_id().getId() + ": " + arg0.getValue().getAdres_id().getUlica() +
															", " + arg0.getValue().getAdres_id().getMiasto_id().getNazwa() );
						}
    	            });
    				TC_admSala_adres.setMinWidth(400);
    				//sesja do adm_sala
        			Session sessionAdmSala = factory.openSession();
        			Transaction t_AdmSala = sessionAdmSala.beginTransaction();
    				try {
    					//lista wszystkich salaow
        				@SuppressWarnings("unchecked")
						List<Sala> wynikAdmSala = sessionAdmSala.createQuery("from Sala").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Sala> OL_admSala = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmSala.size(); i++)
        				{
        					OL_admSala.add( wynikAdmSala.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_sala.getColumns().addAll(TC_admSala_id, TC_admSala_nazwa, TC_admSala_adres);
        				//dodanie elementow list do tabeli
        				TV_adm_sala.setItems(OL_admSala);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_sala);
    				}finally {
    					t_AdmSala.commit();
    					sessionAdmSala.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_sala_HB = new HBox();
        			adm_sala_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_sala_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_sala_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_sala_usun = new Button("Usuñ zaznaczony rekord");
        			adm_sala_HB.getChildren().addAll(adm_sala_dodaj, adm_sala_zmien, adm_sala_usun);
        			panel_prawy.getChildren().addAll(adm_sala_HB);	
        			//
        			//elementy potrzebne do dodawania, edycji ::
        			//nazwa
    				HBox adm_sala_dodaj_nazwaHB = new HBox();
    				adm_sala_dodaj_nazwaHB.getStyleClass().add("center");
    				Label adm_sala_dodaj_nazwaL = new Label("Nazwa: "); 
    				TextField adm_sala_dodaj_nazwaTF = new TextField(); 
    				Label adm_sala_dodaj_nazwaM = new Label(); 
    				adm_sala_dodaj_nazwaL.setId("adm_edycjaL");
    				adm_sala_dodaj_nazwaTF.setId("adm_edycjaTF");
    				adm_sala_dodaj_nazwaM.setId("adm_edycjaM");
    				adm_sala_dodaj_nazwaHB.getChildren().addAll(adm_sala_dodaj_nazwaL, adm_sala_dodaj_nazwaTF, adm_sala_dodaj_nazwaM);
    				//adres - comboBox
    				//sesja do comboBox
        			Session sessionAdmSala_CB = factory.openSession();
        			Transaction t_AdmSala_CB = sessionAdmSala_CB.beginTransaction();
        			//OL_list
        			ObservableList<Adres> OL_admSala_adres = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Adres> wynikAdmSala_adres = sessionAdmSala_CB.createQuery("from Adres").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmSala_adres.size(); i++)
        				{
        					OL_admSala_adres.add( wynikAdmSala_adres.get(i) );
        				}
        			}finally {
        				t_AdmSala_CB.commit();
        				sessionAdmSala_CB.close();
        			}
    				final ComboBox adm_sala_adresCB = new ComboBox(OL_admSala_adres);
    				adm_sala_adresCB.setPromptText("wybierz adres");
    				HBox adm_sala_dodaj_adresHB = new HBox();
    				adm_sala_dodaj_adresHB.getStyleClass().add("center");
    				Label adm_sala_dodaj_adresL = new Label("Adres: "); 
    				Label adm_sala_dodaj_adresM = new Label(); 
    				adm_sala_dodaj_adresL.setId("adm_edycjaL");
    				adm_sala_adresCB.setId("adm_edycjaTF");
    				adm_sala_dodaj_adresM.setId("adm_edycjaM");
    				adm_sala_dodaj_adresHB.getChildren().addAll(adm_sala_dodaj_adresL, adm_sala_adresCB, adm_sala_dodaj_adresM);
    				//messg - wiadomosc o poprawnosci wszystkich danych
    				HBox adm_sala_zatw_messgHB = new HBox();
    				adm_sala_zatw_messgHB.getStyleClass().add("center");
    				Button adm_sala_zatw_zatwB = new Button("zatw");
    				Label adm_sala_zatw_messg = new Label();
    				adm_sala_zatw_messg.getStyleClass().add("adm_dodajMessg");
    				adm_sala_zatw_messgHB.getChildren().addAll(adm_sala_zatw_messg, adm_sala_zatw_zatwB);
    				//wzorce
    				Pattern nazwaP = Pattern.compile("[A-Za-z]{3,70}");
    				//////////////
        			//sala - dodaj
        			adm_sala_dodaj.setOnAction(adm_sala_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//dodanie wszystkich HB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_sala_dodaj_nazwaHB, adm_sala_dodaj_adresHB, adm_sala_zatw_messgHB);
        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
        				adm_sala_zatw_zatwB.setText("dodaj");
        				//akcja przycisku zatwierdŸ (tutaj dodaj)
        				adm_sala_zatw_zatwB.setOnAction(adm_sala_zatw_dodaj_a->{
        					//pobranie tekstu do sprawdzenia wzorców
        					Matcher testNazwa = nazwaP.matcher( adm_sala_dodaj_nazwaTF.getText() );
        					//sprawdzenie wzorców i comboBoxów pojedynczo
        					if(testNazwa.matches())
        						adm_sala_dodaj_nazwaM.setText(" ok");
        					else 
        						adm_sala_dodaj_nazwaM.setText(" niepoprawna nazwa");
        					if(adm_sala_adresCB.getSelectionModel().getSelectedItem()!=null)
        						adm_sala_dodaj_adresM.setText(" ok");
        					else
        						adm_sala_dodaj_adresM.setText(" wybierz adres");
        					//sprawdzenie wszystkich wzorców i comboBoxów razem
        					if( testNazwa.matches() && (adm_sala_adresCB.getSelectionModel().getSelectedItem()!=null) ) 
        					{
        						Sala nowaSala = new Sala( adm_sala_dodaj_nazwaTF.getText(), (Adres)adm_sala_adresCB.getSelectionModel().getSelectedItem() );
        						//sesja do adm_sala - dodaj
        	        			Session sessionAdmSala_dodaj = factory.openSession();
        	        			Transaction t_AdmSala_dodaj = sessionAdmSala_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmSala_dodaj.save(nowaSala);
        	        				t_AdmSala_dodaj.commit();
        	        				adm_sala_zatw_messg.setText("dodano nowy rekord");
        	        			}catch (Exception e){
                    				if (t_AdmSala_dodaj != null) {
                    					t_AdmSala_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmSala_dodaj.close();
        	        			}
        					}
        					else 
            				{
            					adm_sala_zatw_messg.setText("niepoprawne dane");
            				}
        				});
        			});
        			//////////////
        			//sala - zmien
					adm_sala_zmien.setOnAction(adm_sala_zmien_a->{
						Sala zaznaczonaSala = TV_adm_sala.getSelectionModel().getSelectedItem();
						if(zaznaczonaSala!=null)
						{
							panel_prawy.getChildren().clear();
	        				//dodanie wszystkich HB do panelu prawego
	        				panel_prawy.getChildren().addAll(adm_sala_dodaj_nazwaHB, adm_sala_dodaj_adresHB, adm_sala_zatw_messgHB);
	        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
	        				adm_sala_zatw_zatwB.setText("zmieñ");
	        				//
	        				//wype³nienie TF'ów danymi zaznaczonego rekordu
	        				if( zaznaczonaSala.getNazwa()!=null )
	        					adm_sala_dodaj_nazwaTF.setText( zaznaczonaSala.getNazwa() );
	        				adm_sala_adresCB.setValue( zaznaczonaSala.getAdres_id() );
	        				//
	        				//akcja przycisku zatwierdŸ (tutaj dodaj)
	        				adm_sala_zatw_zatwB.setOnAction(adm_sala_zatw_dodaj_a->{
	        					//pobranie tekstu do sprawdzenia wzorców
	        					Matcher testNazwa = nazwaP.matcher(adm_sala_dodaj_nazwaTF.getText() );
	        					//sprawdzenie wzorców pojedynczo
	            				if(testNazwa.matches())
	        						adm_sala_dodaj_nazwaM.setText(" ok");
	        					else 
	        						adm_sala_dodaj_nazwaM.setText(" niepoprawna nazwa");
	            				if(adm_sala_adresCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_sala_dodaj_adresM.setText(" ok");
	        					else
	        						adm_sala_dodaj_adresM.setText(" wybierz adres");
	        					//sprawdzenie wszystkich wzorców i comboBoxów razem
	        					if( testNazwa.matches() && (adm_sala_adresCB.getSelectionModel().getSelectedItem()!=null) ) 
	        					{
	        						//zmiany w zaznaczonym rekordzie
	            					zaznaczonaSala.setNazwa( adm_sala_dodaj_nazwaTF.getText() );
	            					zaznaczonaSala.setAdres_id( (Adres)adm_sala_adresCB.getSelectionModel().getSelectedItem() );
	        						//sesja do adm_sala - zmien
	        	        			Session sessionAdmSala_zmien = factory.openSession();
	        	        			Transaction t_AdmSala_zmien = sessionAdmSala_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmSala_zmien.update(zaznaczonaSala);
	        	        				t_AdmSala_zmien.commit();
	        	        				adm_sala_zatw_messg.setText("zmieniono rekord");
	        	        			}catch (Exception e){
	                    				if (t_AdmSala_zmien != null) {
	                    					t_AdmSala_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmSala_zmien.close();
	        	        			}
	        					}
	        					else 
	            				{
	            					adm_sala_zatw_messg.setText("niepoprawne dane");
	            				}
	            					
	        				});
						}
					});
					//////////////
        			//sala - usun
					adm_sala_usun.setOnAction(adm_sala_usun_a->{
						Sala zaznaczonaSala = TV_adm_sala.getSelectionModel().getSelectedItem();
						if(zaznaczonaSala!=null) 
						{
							//sesja do adm_sala_usun
	            			Session sessionAdmSala_usun = factory.openSession();
	            			Transaction t_AdmSala_usun = sessionAdmSala_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego rekordu
	            				sessionAdmSala_usun.remove(zaznaczonaSala);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich rekordów
	                			@SuppressWarnings("unchecked")
	        					List<Sala> wynikAdmsala_usun = sessionAdmSala_usun.createQuery("from Sala").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Sala> OL_admSala_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmsala_usun.size(); i++)
	                			{
	                				OL_admSala_usun.add( wynikAdmsala_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_sala.setItems(OL_admSala_usun);
	                			//
	                			t_AdmSala_usun.commit();
	                			sessionAdmSala_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmSala_usun != null) {
	            					t_AdmSala_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
						}
					});
				});
				
				//adm - zajecia 
				adm_tabZajeciaB.setOnAction(adm_zajecia_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Zajecia\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela zajeciaów
    				TableView<Zajecia> TV_adm_zajecia = new TableView<>();
    				//kol-id zajec
    				TableColumn<Zajecia, Integer> TC_admZajecia_id = new TableColumn<>("Id");
    				TC_admZajecia_id.setCellValueFactory(new PropertyValueFactory<Zajecia, Integer>("id"));
    				TC_admZajecia_id.setMinWidth(50);
    				//kol-godz rozpoczecia zajec
    				TableColumn<Zajecia, Integer> TC_admZajecia_godzRoz = new TableColumn<>("Od");
    				TC_admZajecia_godzRoz.setCellValueFactory(new PropertyValueFactory<Zajecia, Integer>("godzRozpoczecia"));
    				TC_admZajecia_godzRoz.setMinWidth(80);
    				//kol-godz zakonczenia zajec
    				TableColumn<Zajecia, Integer> TC_admZajecia_godzZak = new TableColumn<>("Do");
    				TC_admZajecia_godzZak.setCellValueFactory(new PropertyValueFactory<Zajecia, Integer>("godzZakonczenia"));
    				TC_admZajecia_godzZak.setMinWidth(80);
    				//kol-dzienTyg zajec
    				TableColumn<Zajecia, String> TC_admZajecia_dzien = new TableColumn<>("Dzieñ tygodnia");
    				TC_admZajecia_dzien.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getDzienTygodnia_id().getNazwa() );
						}
    	            });
    				TC_admZajecia_dzien.setMinWidth(170);
    				//kol-grupa zajec
    				TableColumn<Zajecia, String> TC_admZajecia_grupa = new TableColumn<>("Grupa (id: nazwa)");
    				TC_admZajecia_grupa.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getGrupa_id().getId() + ": " +  arg0.getValue().getGrupa_id().getNazwa() );
						}
    	            });
    				TC_admZajecia_grupa.setMinWidth(200);
    				//kol-sala zajec
    				TableColumn<Zajecia, String> TC_admZajecia_sala = new TableColumn<>("Sala (id: ulica,miasto)");
    				TC_admZajecia_sala.setCellValueFactory(new Callback<CellDataFeatures<Zajecia, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Zajecia, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getSala_id().getId() + ": " + 
															arg0.getValue().getSala_id().getAdres_id().getUlica() + ", " + 
															arg0.getValue().getSala_id().getAdres_id().getMiasto_id().getNazwa());
						}
    	            });
    				TC_admZajecia_sala.setMinWidth(250);
    				//sesja do adm_zajecia
        			Session sessionAdmZajecia = factory.openSession();
        			Transaction t_AdmZajecia = sessionAdmZajecia.beginTransaction();
    				try {
    					//lista wszystkich zajeciaow
        				@SuppressWarnings("unchecked")
						List<Zajecia> wynikAdmZajecia = sessionAdmZajecia.createQuery("from Zajecia").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Zajecia> OL_admZajecia = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmZajecia.size(); i++)
        				{
        					OL_admZajecia.add( wynikAdmZajecia.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_zajecia.getColumns().addAll(TC_admZajecia_id, TC_admZajecia_godzRoz, TC_admZajecia_godzZak, TC_admZajecia_dzien, 
        															TC_admZajecia_grupa, TC_admZajecia_sala);
        				//dodanie elementow list do tabeli
        				TV_adm_zajecia.setItems(OL_admZajecia);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_zajecia);
    				}finally {
    					t_AdmZajecia.commit();
    					sessionAdmZajecia.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_zajecia_HB = new HBox();
        			adm_zajecia_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_zajecia_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_zajecia_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_zajecia_usun = new Button("Usuñ zaznaczony rekord");
        			adm_zajecia_HB.getChildren().addAll(adm_zajecia_dodaj, adm_zajecia_zmien, adm_zajecia_usun);
        			panel_prawy.getChildren().addAll(adm_zajecia_HB);	
        			//elementy potrzebne do dodawania, edycji ::
        			//godzinaRozpoczecia
    				HBox adm_zajecia_dodaj_godzRozpHB = new HBox();
    				adm_zajecia_dodaj_godzRozpHB.getStyleClass().add("center");
    				Label adm_zajecia_dodaj_godzRozpL = new Label("Godzina rozpoczêcia: "); 
    				TextField adm_zajecia_dodaj_godzRozpTF = new TextField(); 
    				Label adm_zajecia_dodaj_godzRozpM = new Label(); 
    				adm_zajecia_dodaj_godzRozpL.setId("adm_edycjaL");
    				adm_zajecia_dodaj_godzRozpTF.setId("adm_edycjaTF");
    				adm_zajecia_dodaj_godzRozpM.setId("adm_edycjaM");
    				adm_zajecia_dodaj_godzRozpHB.getChildren().addAll(adm_zajecia_dodaj_godzRozpL, adm_zajecia_dodaj_godzRozpTF, adm_zajecia_dodaj_godzRozpM);
    				//godzinaZakonczenia
    				HBox adm_zajecia_dodaj_godzZakHB = new HBox();
    				adm_zajecia_dodaj_godzZakHB.getStyleClass().add("center");
    				Label adm_zajecia_dodaj_godzZakL = new Label("Godzina rozpoczêcia: "); 
    				TextField adm_zajecia_dodaj_godzZakTF = new TextField(); 
    				Label adm_zajecia_dodaj_godzZakM = new Label(); 
    				adm_zajecia_dodaj_godzZakL.setId("adm_edycjaL");
    				adm_zajecia_dodaj_godzZakTF.setId("adm_edycjaTF");
    				adm_zajecia_dodaj_godzZakM.setId("adm_edycjaM");
    				adm_zajecia_dodaj_godzZakHB.getChildren().addAll(adm_zajecia_dodaj_godzZakL, adm_zajecia_dodaj_godzZakTF, adm_zajecia_dodaj_godzZakM);
    				//dzienTygodnia - comboBox
    				//sesja do comboBox
    				Session sessionAdmZajecia_dzienTyg_CB = factory.openSession();
        			Transaction t_AdmZajecia_dzienTyg_CB = sessionAdmZajecia_dzienTyg_CB.beginTransaction();
        			//OL_list
        			ObservableList<DzienTygodnia> OL_admZajecia_dzienTyg = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<DzienTygodnia> wynikAdmZajecia_dzienTyg = sessionAdmZajecia_dzienTyg_CB.createQuery("from DzienTygodnia").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmZajecia_dzienTyg.size(); i++)
        				{
        					OL_admZajecia_dzienTyg.add( wynikAdmZajecia_dzienTyg.get(i) );
        				}
        			}finally {
        				t_AdmZajecia_dzienTyg_CB.commit();
        				sessionAdmZajecia_dzienTyg_CB.close();
        			}
        			final ComboBox<DzienTygodnia> adm_zajecia_dzienTygCB = new ComboBox<DzienTygodnia>(OL_admZajecia_dzienTyg);
    				adm_zajecia_dzienTygCB.setPromptText("wybierz dzienTyg");
    				HBox adm_zajecia_dodaj_dzienTygHB = new HBox();
    				adm_zajecia_dodaj_dzienTygHB.getStyleClass().add("center");
    				Label adm_zajecia_dodaj_dzienTygL = new Label("DzienTygodnia: "); 
    				Label adm_zajecia_dodaj_dzienTygM = new Label(); 
    				adm_zajecia_dodaj_dzienTygL.setId("adm_edycjaL");
    				adm_zajecia_dzienTygCB.setId("adm_edycjaTF");
    				adm_zajecia_dodaj_dzienTygM.setId("adm_edycjaM");
    				adm_zajecia_dodaj_dzienTygHB.getChildren().addAll(adm_zajecia_dodaj_dzienTygL, adm_zajecia_dzienTygCB, adm_zajecia_dodaj_dzienTygM);
    				//grupa - comboBox
    				//sesja do comboBox
    				Session sessionAdmZajecia_grupa_CB = factory.openSession();
        			Transaction t_AdmZajecia_grupa_CB = sessionAdmZajecia_grupa_CB.beginTransaction();
        			//OL_list
        			ObservableList<Grupa> OL_admZajecia_grupa = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Grupa> wynikAdmZajecia_grupa = sessionAdmZajecia_grupa_CB.createQuery("from Grupa").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmZajecia_grupa.size(); i++)
        				{
        					OL_admZajecia_grupa.add( wynikAdmZajecia_grupa.get(i) );
        				}
        			}finally {
        				t_AdmZajecia_grupa_CB.commit();
        				sessionAdmZajecia_grupa_CB.close();
        			}
        			final ComboBox<Grupa> adm_zajecia_grupaCB = new ComboBox<Grupa>(OL_admZajecia_grupa);
    				adm_zajecia_grupaCB.setPromptText("wybierz grupa");
    				HBox adm_zajecia_dodaj_grupaHB = new HBox();
    				adm_zajecia_dodaj_grupaHB.getStyleClass().add("center");
    				Label adm_zajecia_dodaj_grupaL = new Label("Grupa: "); 
    				Label adm_zajecia_dodaj_grupaM = new Label(); 
    				adm_zajecia_dodaj_grupaL.setId("adm_edycjaL");
    				adm_zajecia_grupaCB.setId("adm_edycjaTF");
    				adm_zajecia_dodaj_grupaM.setId("adm_edycjaM");
    				adm_zajecia_dodaj_grupaHB.getChildren().addAll(adm_zajecia_dodaj_grupaL, adm_zajecia_grupaCB, adm_zajecia_dodaj_grupaM);
    				//sala - comboBox
    				//sesja do comboBox
    				Session sessionAdmZajecia_sala_CB = factory.openSession();
        			Transaction t_AdmZajecia_sala_CB = sessionAdmZajecia_sala_CB.beginTransaction();
        			//OL_list
        			ObservableList<Sala> OL_admZajecia_sala = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Sala> wynikAdmZajecia_sala = sessionAdmZajecia_sala_CB.createQuery("from Sala").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmZajecia_sala.size(); i++)
        				{
        					OL_admZajecia_sala.add( wynikAdmZajecia_sala.get(i) );
        				}
        			}finally {
        				t_AdmZajecia_sala_CB.commit();
        				sessionAdmZajecia_sala_CB.close();
        			}
        			final ComboBox<Sala> adm_zajecia_salaCB = new ComboBox<Sala>(OL_admZajecia_sala);
    				adm_zajecia_salaCB.setPromptText("wybierz sala");
    				HBox adm_zajecia_dodaj_salaHB = new HBox();
    				adm_zajecia_dodaj_salaHB.getStyleClass().add("center");
    				Label adm_zajecia_dodaj_salaL = new Label("Sala: "); 
    				Label adm_zajecia_dodaj_salaM = new Label(); 
    				adm_zajecia_dodaj_salaL.setId("adm_edycjaL");
    				adm_zajecia_salaCB.setId("adm_edycjaTF");
    				adm_zajecia_dodaj_salaM.setId("adm_edycjaM");
    				adm_zajecia_dodaj_salaHB.getChildren().addAll(adm_zajecia_dodaj_salaL, adm_zajecia_salaCB, adm_zajecia_dodaj_salaM);
    				//messg - wiadomosc o poprawnosci wszystkich danych
    				HBox adm_zajecia_zatw_messgHB = new HBox();
    				adm_zajecia_zatw_messgHB.getStyleClass().add("center");
    				Button adm_zajecia_zatw_zatwB = new Button("zatw");
    				Label adm_zajecia_zatw_messg = new Label();
    				adm_zajecia_zatw_messg.getStyleClass().add("adm_dodajMessg");
    				adm_zajecia_zatw_messgHB.getChildren().addAll(adm_zajecia_zatw_messg, adm_zajecia_zatw_zatwB);
    				//wzorce
    				Pattern godzRozpP = Pattern.compile("(2[0-3]|[01]?[0-9]):([0-5]?[0-9])");
    				Pattern godzZakP = Pattern.compile("(2[0-3]|[01]?[0-9]):([0-5]?[0-9])");
    				////////////////////
        			//zajecia - dodaj
        			adm_zajecia_dodaj.setOnAction(adm_zajecia_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//dodanie wszystkich HB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_zajecia_dodaj_godzRozpHB, adm_zajecia_dodaj_godzZakHB, adm_zajecia_dodaj_dzienTygHB,
        													adm_zajecia_dodaj_grupaHB, adm_zajecia_dodaj_salaHB, adm_zajecia_zatw_messgHB);
        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
        				adm_zajecia_zatw_zatwB.setText("dodaj");
        				//akcja przycisku zatwierdŸ (tutaj dodaj)
        				adm_zajecia_zatw_zatwB.setOnAction(adm_sala_zatw_dodaj_a->{
        					//pobranie tekstu do sprawdzenia wzorców
        					Matcher testGodzRozp = godzRozpP.matcher( adm_zajecia_dodaj_godzRozpTF.getText() );
        					Matcher testGodzZak = godzZakP.matcher( adm_zajecia_dodaj_godzZakTF.getText() );
        					//sprawdzenie wzorców i comboBoxów pojedynczo
        					if(testGodzRozp.matches())
        						adm_zajecia_dodaj_godzRozpM.setText(" ok");
        					else 
        						adm_zajecia_dodaj_godzRozpM.setText(" niepoprawna godzina");
        					if(testGodzZak.matches())
        						adm_zajecia_dodaj_godzZakM.setText(" ok");
        					else 
        						adm_zajecia_dodaj_godzZakM.setText(" niepoprawna godzina");
        					if(adm_zajecia_dzienTygCB.getSelectionModel().getSelectedItem()!=null)
        						adm_zajecia_dodaj_dzienTygM.setText(" ok");
        					else
        						adm_zajecia_dodaj_dzienTygM.setText(" wybierz dzienTyg");
        					if(adm_zajecia_grupaCB.getSelectionModel().getSelectedItem()!=null)
        						adm_zajecia_dodaj_grupaM.setText(" ok");
        					else
        						adm_zajecia_dodaj_grupaM.setText(" wybierz grupê");
        					if(adm_zajecia_salaCB.getSelectionModel().getSelectedItem()!=null)
        						adm_zajecia_dodaj_salaM.setText(" ok");
        					else
        						adm_zajecia_dodaj_salaM.setText(" wybierz salê");
        					//sprawdzenie wszystkich wzorców i comboBoxów razem
        					if( testGodzRozp.matches() && testGodzZak.matches() && (adm_zajecia_dzienTygCB.getSelectionModel().getSelectedItem()!=null) &&
        							(adm_zajecia_grupaCB.getSelectionModel().getSelectedItem()!=null) && 
        							(adm_zajecia_salaCB.getSelectionModel().getSelectedItem()!=null) )
        					{
        						//parsownie Strging'a do sql.Time
        						//1) parsowanie String'a do longa (milisekundy)
        						//2) utworzenie sql.Time u¿ywaj¹c konstruktora wykorzystuj¹cego milisekundy
        						DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        						long rozpMs=0;
								try {
									rozpMs = dateFormat.parse( adm_zajecia_dodaj_godzRozpTF.getText() ).getTime();
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
        						long zakMs=0;
								try {
									zakMs = dateFormat.parse( adm_zajecia_dodaj_godzZakTF.getText() ).getTime();
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								//nowy rekord
        						Zajecia noweZajecia = new Zajecia( new Time(rozpMs), new Time(zakMs), (DzienTygodnia)adm_zajecia_dzienTygCB.getSelectionModel().getSelectedItem(),
        											(Grupa)adm_zajecia_grupaCB.getSelectionModel().getSelectedItem(), (Sala)adm_zajecia_salaCB.getSelectionModel().getSelectedItem());
        						//sesja do adm_zajecia - dodaj
        	        			Session sessionAdmZajecia_dodaj = factory.openSession();
        	        			Transaction t_AdmZajecia_dodaj = sessionAdmZajecia_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmZajecia_dodaj.save(noweZajecia);
        	        				t_AdmZajecia_dodaj.commit();
        	        				adm_zajecia_zatw_messg.setText("dodano nowy rekord");
        	        			}catch (Exception e){
                    				if (t_AdmZajecia_dodaj != null) {
                    					t_AdmZajecia_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmZajecia_dodaj.close();
        	        			}
        					}
        					else 
            				{
            					adm_zajecia_zatw_messg.setText("niepoprawne dane");
            				}
        				});
        			});
        			////////////////////
        			//zajecia - zmien
					adm_zajecia_zmien.setOnAction(adm_zajecia_zmien_a->{
						Zajecia zaznaczoneZajecia = TV_adm_zajecia.getSelectionModel().getSelectedItem();
						if(zaznaczoneZajecia!=null)
						{
							panel_prawy.getChildren().clear();
	        				//dodanie wszystkich HB do panelu prawego
	        				panel_prawy.getChildren().addAll(adm_zajecia_dodaj_godzRozpHB, adm_zajecia_dodaj_godzZakHB, adm_zajecia_dodaj_dzienTygHB,
	        													adm_zajecia_dodaj_grupaHB, adm_zajecia_dodaj_salaHB, adm_zajecia_zatw_messgHB);
	        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
	        				adm_zajecia_zatw_zatwB.setText("zmieñ");
	        				//
	        				//wype³nienie TF'ów danymi zaznaczonego rekord
	        				adm_zajecia_dodaj_godzRozpTF.setText( Integer.toString(zaznaczoneZajecia.getGodzRozpoczecia().getHours()) + ":" + 
	        														Integer.toString(zaznaczoneZajecia.getGodzRozpoczecia().getMinutes()));
	        				adm_zajecia_dodaj_godzZakTF.setText( Integer.toString(zaznaczoneZajecia.getGodzZakonczenia().getHours()) + ":" + 
																	Integer.toString(zaznaczoneZajecia.getGodzZakonczenia().getMinutes()));
	        				adm_zajecia_dzienTygCB.setValue( zaznaczoneZajecia.getDzienTygodnia_id() );
	        				adm_zajecia_grupaCB.setValue( zaznaczoneZajecia.getGrupa_id() );
	        				adm_zajecia_salaCB.setValue( zaznaczoneZajecia.getSala_id() );
	        				//
	        				//akcja przycisku zatwierdŸ (tutaj dodaj)
	        				adm_zajecia_zatw_zatwB.setOnAction(adm_zajecia_zatw_dodaj_a->{
	        					//pobranie tekstu do sprawdzenia wzorców
	        					Matcher testGodzRozp = godzRozpP.matcher( adm_zajecia_dodaj_godzRozpTF.getText() );
	        					Matcher testGodzZak = godzZakP.matcher( adm_zajecia_dodaj_godzZakTF.getText() );
	        					//sprawdzenie wzorców i comboBoxów pojedynczo
	        					if(testGodzRozp.matches())
	        						adm_zajecia_dodaj_godzRozpM.setText(" ok");
	        					else 
	        						adm_zajecia_dodaj_godzRozpM.setText(" niepoprawna godzina");
	        					if(testGodzZak.matches())
	        						adm_zajecia_dodaj_godzZakM.setText(" ok");
	        					else 
	        						adm_zajecia_dodaj_godzZakM.setText(" niepoprawna godzina");
	        					if(adm_zajecia_dzienTygCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_zajecia_dodaj_dzienTygM.setText(" ok");
	        					else
	        						adm_zajecia_dodaj_dzienTygM.setText(" wybierz dzienTyg");
	        					if(adm_zajecia_grupaCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_zajecia_dodaj_grupaM.setText(" ok");
	        					else
	        						adm_zajecia_dodaj_grupaM.setText(" wybierz grupê");
	        					if(adm_zajecia_salaCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_zajecia_dodaj_salaM.setText(" ok");
	        					else
	        						adm_zajecia_dodaj_salaM.setText(" wybierz salê");
	        					//sprawdzenie wszystkich wzorców i comboBoxów razem
	        					if( testGodzRozp.matches() && testGodzZak.matches() && (adm_zajecia_dzienTygCB.getSelectionModel().getSelectedItem()!=null) &&
	        							(adm_zajecia_grupaCB.getSelectionModel().getSelectedItem()!=null) && 
	        							(adm_zajecia_salaCB.getSelectionModel().getSelectedItem()!=null) )
	        					{
	        						//parsownie Strging'a do sql.Time
	        						//1) parsowanie String'a do longa (milisekundy)
	        						//2) utworzenie sql.Time u¿ywaj¹c konstruktora wykorzystuj¹cego milisekundy
	        						DateFormat dateFormat = new SimpleDateFormat("hh:mm");
	        						long rozpMs=0;
									try {
										rozpMs = dateFormat.parse( adm_zajecia_dodaj_godzRozpTF.getText() ).getTime();
									} catch (ParseException e1) {
										e1.printStackTrace();
									}
	        						long zakMs=0;
									try {
										zakMs = dateFormat.parse( adm_zajecia_dodaj_godzZakTF.getText() ).getTime();
									} catch (ParseException e1) {
										e1.printStackTrace();
									}
	        						//zmiany w zaznaczonym rekordzie
	        						zaznaczoneZajecia.setGodzRozpoczecia( new Time(rozpMs) );
	        						zaznaczoneZajecia.setGodzZakonczenia( new Time(zakMs) );
	        						zaznaczoneZajecia.setDzienTygodnia_id( adm_zajecia_dzienTygCB.getSelectionModel().getSelectedItem() );
	        						zaznaczoneZajecia.setGrupa_id( adm_zajecia_grupaCB.getSelectionModel().getSelectedItem() );
	        						zaznaczoneZajecia.setSala_id( adm_zajecia_salaCB.getSelectionModel().getSelectedItem() );
	        						//sesja do adm_zajecia - zmien
	        	        			Session sessionAdmZajecia_zmien = factory.openSession();
	        	        			Transaction t_AdmZajecia_zmien = sessionAdmZajecia_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmZajecia_zmien.update(zaznaczoneZajecia);
	        	        				t_AdmZajecia_zmien.commit();
	        	        				adm_zajecia_zatw_messg.setText("zmieniono rekord");
	        	        			}catch (Exception e){
	                    				if (t_AdmZajecia_zmien != null) {
	                    					t_AdmZajecia_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmZajecia_zmien.close();
	        	        			}
	        					}
	        					else 
	            				{
	            					adm_zajecia_zatw_messg.setText("niepoprawne dane");
	            				}	
	        				});
						}
					});
					////////////////////
        			//zajecia - usun
					adm_zajecia_usun.setOnAction(adm_zajecia_usun_a->{
						Zajecia zaznaczoneZajecia = TV_adm_zajecia.getSelectionModel().getSelectedItem();
						if(zaznaczoneZajecia!=null)
						{
							//sesja do adm_zajecia_usun
	            			Session sessionAdmZajecia_usun = factory.openSession();
	            			Transaction t_AdmZajecia_usun = sessionAdmZajecia_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego rekordu
	            				sessionAdmZajecia_usun.remove(zaznaczoneZajecia);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich rekordów
	                			@SuppressWarnings("unchecked")
	        					List<Zajecia> wynikAdmzajecia_usun = sessionAdmZajecia_usun.createQuery("from Zajecia").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Zajecia> OL_admZajecia_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmzajecia_usun.size(); i++)
	                			{
	                				OL_admZajecia_usun.add( wynikAdmzajecia_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_zajecia.setItems(OL_admZajecia_usun);
	                			//
	                			t_AdmZajecia_usun.commit();
	                			sessionAdmZajecia_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmZajecia_usun != null) {
	            					t_AdmZajecia_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
						}
					});
				});
				
				//adm - zawodnik - dok
				adm_tabZawodnikB.setOnAction(adm_zawodnik_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Zawodnik\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela zawodników
    				TableView<Zawodnik> TV_adm_zawodnik = new TableView<>();
    				//kol-id zawodnika
    				TableColumn<Zawodnik, Integer> TC_admZawodnik_id = new TableColumn<>("Id");
    				TC_admZawodnik_id.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("id"));
    				TC_admZawodnik_id.setMinWidth(100);
    				//kol-imie zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_imie = new TableColumn<>("Imie");
    				TC_admZawodnik_imie.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("imie"));
    				TC_admZawodnik_imie.setMinWidth(150);
    				//kol-nazw zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_nazw = new TableColumn<>("Nazwisko");
    				TC_admZawodnik_nazw.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("nazwisko"));
    				TC_admZawodnik_nazw.setMinWidth(180);
    				//kol-rokUr trenera
    				TableColumn<Zawodnik, String> TC_admZawodnik_rok = new TableColumn<>("Rok urodzenia");
    				TC_admZawodnik_rok.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("rokUrodzenia"));
    				TC_admZawodnik_rok.setMinWidth(180);
    				//kol-wzrost zawodnika
    				TableColumn<Zawodnik, Integer> TC_admZawodnik_wzrost = new TableColumn<>("Wzrost");
    				TC_admZawodnik_wzrost.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("wzrost"));
    				TC_admZawodnik_wzrost.setMinWidth(100);
    				//kol-waga zawodnika
    				TableColumn<Zawodnik, Integer> TC_admZawodnik_waga = new TableColumn<>("Waga");
    				TC_admZawodnik_waga.setCellValueFactory(new PropertyValueFactory<Zawodnik, Integer>("waga"));
    				TC_admZawodnik_waga.setMinWidth(100);
    				//kol-nrTel trenera
    				TableColumn<Zawodnik, String> TC_admZawodnik_tel = new TableColumn<>("Nr telefonu");
    				TC_admZawodnik_tel.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("nrTelefonu"));
    				TC_admZawodnik_tel.setMinWidth(150);
    				//kol-pozycja zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_pozycja = new TableColumn<>("Pozycja");
    				TC_admZawodnik_pozycja.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getPozycja_id().getNazwa() );
						}
    	            });
    				TC_admZawodnik_pozycja.setMinWidth(100);
    				//kol-adres zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_adres = new TableColumn<>("Adres (id: ulica,miasto)");
    				TC_admZawodnik_adres.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getAdres_id().getId() + ": " + arg0.getValue().getAdres_id().getUlica() +
															", " + arg0.getValue().getAdres_id().getMiasto_id().getNazwa() );
						}
    	            });
    				TC_admZawodnik_adres.setMinWidth(400);
    				//kol-klub zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_klub = new TableColumn<>("Klub");
    				TC_admZawodnik_klub.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getKlub_id().getNazwa() );
						}
    	            });
    				TC_admZawodnik_klub.setMinWidth(200);
    				//kol-grupa zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_grupa = new TableColumn<>("Grupa (id: nazwa)");
    				TC_admZawodnik_grupa.setCellValueFactory(new Callback<CellDataFeatures<Zawodnik, String>,ObservableValue<String>>(){
						@Override
						public ObservableValue<String> call(CellDataFeatures<Zawodnik, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().getGrupa_id().getId() + ": " + arg0.getValue().getGrupa_id().getNazwa() );
						}
    	            });
    				TC_admZawodnik_grupa.setMinWidth(300);
    				//kol-login zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_log = new TableColumn<>("Login");
    				TC_admZawodnik_log.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("login_"));
    				TC_admZawodnik_log.setMinWidth(170);
    				//kol-haslo zawodnika
    				TableColumn<Zawodnik, String> TC_admZawodnik_haslo = new TableColumn<>("Has³o");
    				TC_admZawodnik_haslo.setCellValueFactory(new PropertyValueFactory<Zawodnik, String>("haslo_"));
    				TC_admZawodnik_haslo.setMinWidth(170);
    				//sesja do adm_zawodnik
        			Session sessionAdmZawodnik = factory.openSession();
        			Transaction t_AdmZawodnik = sessionAdmZawodnik.beginTransaction();
    				try {
    					//lista wszystkich zawodnikow
        				@SuppressWarnings("unchecked")
						List<Zawodnik> wynikAdmZawodnik = sessionAdmZawodnik.createQuery("from Zawodnik").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Zawodnik> OL_admZawodnik = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmZawodnik.size(); i++)
        				{
        					OL_admZawodnik.add( wynikAdmZawodnik.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_zawodnik.getColumns().addAll(TC_admZawodnik_id, TC_admZawodnik_imie, TC_admZawodnik_nazw, TC_admZawodnik_rok, TC_admZawodnik_wzrost,
        											TC_admZawodnik_waga, TC_admZawodnik_tel, TC_admZawodnik_pozycja, TC_admZawodnik_adres, TC_admZawodnik_klub,
        											TC_admZawodnik_grupa, TC_admZawodnik_log, TC_admZawodnik_haslo);
        				//dodanie elementow list do tabeli
        				TV_adm_zawodnik.setItems(OL_admZawodnik);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_zawodnik);
    				}finally {
    					t_AdmZawodnik.commit();
    					sessionAdmZawodnik.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_zawodnik_HB = new HBox();
        			adm_zawodnik_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_zawodnik_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_zawodnik_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_zawodnik_usun = new Button("Usuñ zaznaczony rekord");
        			adm_zawodnik_HB.getChildren().addAll(adm_zawodnik_dodaj, adm_zawodnik_zmien, adm_zawodnik_usun);
        			panel_prawy.getChildren().addAll(adm_zawodnik_HB);	
        			//elementy potrzebne do dodawania, edycji ::
        			//imie
    				HBox adm_zaw_dodaj_imieHB = new HBox();
    				adm_zaw_dodaj_imieHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_imieL = new Label("Imie: "); 
    				TextField adm_zaw_dodaj_imieTF = new TextField(); 
    				Label adm_zaw_dodaj_imieM = new Label(); 
    				adm_zaw_dodaj_imieL.setId("adm_edycjaL");
    				adm_zaw_dodaj_imieTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_imieM.setId("adm_edycjaM");
    				adm_zaw_dodaj_imieHB.getChildren().addAll(adm_zaw_dodaj_imieL, adm_zaw_dodaj_imieTF, adm_zaw_dodaj_imieM);
    				//nazwisko
    				HBox adm_zaw_dodaj_nazwHB = new HBox();
    				adm_zaw_dodaj_nazwHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_nazwL = new Label("Nazwisko: "); 
    				TextField adm_zaw_dodaj_nazwTF = new TextField(); 
    				Label adm_zaw_dodaj_nazwM = new Label(); 
    				adm_zaw_dodaj_nazwL.setId("adm_edycjaL");
    				adm_zaw_dodaj_nazwTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_nazwM.setId("adm_edycjaM");
    				adm_zaw_dodaj_nazwHB.getChildren().addAll(adm_zaw_dodaj_nazwL, adm_zaw_dodaj_nazwTF, adm_zaw_dodaj_nazwM);
    				//rokUrodzenia
    				HBox adm_zaw_dodaj_rokUrHB = new HBox();
    				adm_zaw_dodaj_rokUrHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_rokUrL = new Label("Rok urodzenia: "); 
    				TextField adm_zaw_dodaj_rokUrTF = new TextField(); 
    				Label adm_zaw_dodaj_rokUrM = new Label(); 
    				adm_zaw_dodaj_rokUrL.setId("adm_edycjaL");
    				adm_zaw_dodaj_rokUrTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_rokUrM.setId("adm_edycjaM");
    				adm_zaw_dodaj_rokUrHB.getChildren().addAll(adm_zaw_dodaj_rokUrL, adm_zaw_dodaj_rokUrTF, adm_zaw_dodaj_rokUrM);
    				//wzrost
    				HBox adm_zaw_dodaj_wzrostHB = new HBox();
    				adm_zaw_dodaj_wzrostHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_wzrostL = new Label("Wzrost: "); 
    				TextField adm_zaw_dodaj_wzrostTF = new TextField(); 
    				Label adm_zaw_dodaj_wzrostM = new Label(); 
    				adm_zaw_dodaj_wzrostL.setId("adm_edycjaL");
    				adm_zaw_dodaj_wzrostTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_wzrostM.setId("adm_edycjaM");
    				adm_zaw_dodaj_wzrostHB.getChildren().addAll(adm_zaw_dodaj_wzrostL, adm_zaw_dodaj_wzrostTF, adm_zaw_dodaj_wzrostM);
    				//waga
    				HBox adm_zaw_dodaj_wagaHB = new HBox();
    				adm_zaw_dodaj_wagaHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_wagaL = new Label("Waga: "); 
    				TextField adm_zaw_dodaj_wagaTF = new TextField(); 
    				Label adm_zaw_dodaj_wagaM = new Label(); 
    				adm_zaw_dodaj_wagaL.setId("adm_edycjaL");
    				adm_zaw_dodaj_wagaTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_wagaM.setId("adm_edycjaM");
    				adm_zaw_dodaj_wagaHB.getChildren().addAll(adm_zaw_dodaj_wagaL, adm_zaw_dodaj_wagaTF, adm_zaw_dodaj_wagaM);
    				//nrTelefonu
    				HBox adm_zaw_dodaj_nrTelHB = new HBox();
    				adm_zaw_dodaj_nrTelHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_nrTelL = new Label("Nr telefonu: "); 
    				TextField adm_zaw_dodaj_nrTelTF = new TextField(); 
    				Label adm_zaw_dodaj_nrTelM = new Label(); 
    				adm_zaw_dodaj_nrTelL.setId("adm_edycjaL");
    				adm_zaw_dodaj_nrTelTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_nrTelM.setId("adm_edycjaM");
    				adm_zaw_dodaj_nrTelHB.getChildren().addAll(adm_zaw_dodaj_nrTelL, adm_zaw_dodaj_nrTelTF, adm_zaw_dodaj_nrTelM);
    				//pozycja - comboBox
    				//sesja do comboBox
    				Session sessionAdmZaw_pozycja_CB = factory.openSession();
        			Transaction t_AdmZaw_pozycja_CB = sessionAdmZaw_pozycja_CB.beginTransaction();
        			//OL_list
        			ObservableList<Pozycja> OL_admZaw_pozycja = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Pozycja> wynikAdmZaw_pozycja = sessionAdmZaw_pozycja_CB.createQuery("from Pozycja").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmZaw_pozycja.size(); i++)
        				{
        					OL_admZaw_pozycja.add( wynikAdmZaw_pozycja.get(i) );
        				}
        			}finally {
        				t_AdmZaw_pozycja_CB.commit();
        				sessionAdmZaw_pozycja_CB.close();
        			}
        			final ComboBox<Pozycja> adm_zaw_pozycjaCB = new ComboBox<Pozycja>(OL_admZaw_pozycja);
    				adm_zaw_pozycjaCB.setPromptText("wybierz pozycja");
    				HBox adm_zaw_dodaj_pozycjaHB = new HBox();
    				adm_zaw_dodaj_pozycjaHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_pozycjaL = new Label("Pozycja: "); 
    				Label adm_zaw_dodaj_pozycjaM = new Label(); 
    				adm_zaw_dodaj_pozycjaL.setId("adm_edycjaL");
    				adm_zaw_pozycjaCB.setId("adm_edycjaTF");
    				adm_zaw_dodaj_pozycjaM.setId("adm_edycjaM");
    				adm_zaw_dodaj_pozycjaHB.getChildren().addAll(adm_zaw_dodaj_pozycjaL, adm_zaw_pozycjaCB, adm_zaw_dodaj_pozycjaM);
    				//adres - comboBox
    				//sesja do comboBox
    				Session sessionAdmZaw_adres_CB = factory.openSession();
        			Transaction t_AdmZaw_adres_CB = sessionAdmZaw_adres_CB.beginTransaction();
        			//OL_list
        			ObservableList<Adres> OL_admZaw_adres = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Adres> wynikAdmZaw_adres = sessionAdmZaw_adres_CB.createQuery("from Adres").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmZaw_adres.size(); i++)
        				{
        					OL_admZaw_adres.add( wynikAdmZaw_adres.get(i) );
        				}
        			}finally {
        				t_AdmZaw_adres_CB.commit();
        				sessionAdmZaw_adres_CB.close();
        			}
        			final ComboBox<Adres> adm_zaw_adresCB = new ComboBox<Adres>(OL_admZaw_adres);
    				adm_zaw_adresCB.setPromptText("wybierz adres");
    				HBox adm_zaw_dodaj_adresHB = new HBox();
    				adm_zaw_dodaj_adresHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_adresL = new Label("Adres: "); 
    				Label adm_zaw_dodaj_adresM = new Label(); 
    				adm_zaw_dodaj_adresL.setId("adm_edycjaL");
    				adm_zaw_adresCB.setId("adm_edycjaTF");
    				adm_zaw_dodaj_adresM.setId("adm_edycjaM");
    				adm_zaw_dodaj_adresHB.getChildren().addAll(adm_zaw_dodaj_adresL, adm_zaw_adresCB, adm_zaw_dodaj_adresM);
    				//klub - comboBox
    				//sesja do comboBox
    				Session sessionAdmZaw_klub_CB = factory.openSession();
        			Transaction t_AdmZaw_klub_CB = sessionAdmZaw_klub_CB.beginTransaction();
        			//OL_list
        			ObservableList<Klub> OL_admZaw_klub = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Klub> wynikAdmZaw_klub = sessionAdmZaw_klub_CB.createQuery("from Klub").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmZaw_klub.size(); i++)
        				{
        					OL_admZaw_klub.add( wynikAdmZaw_klub.get(i) );
        				}
        			}finally {
        				t_AdmZaw_klub_CB.commit();
        				sessionAdmZaw_klub_CB.close();
        			}
        			final ComboBox<Klub> adm_zaw_klubCB = new ComboBox<Klub>(OL_admZaw_klub);
    				adm_zaw_klubCB.setPromptText("wybierz klub");
    				HBox adm_zaw_dodaj_klubHB = new HBox();
    				adm_zaw_dodaj_klubHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_klubL = new Label("Klub: "); 
    				Label adm_zaw_dodaj_klubM = new Label(); 
    				adm_zaw_dodaj_klubL.setId("adm_edycjaL");
    				adm_zaw_klubCB.setId("adm_edycjaTF");
    				adm_zaw_dodaj_klubM.setId("adm_edycjaM");
    				adm_zaw_dodaj_klubHB.getChildren().addAll(adm_zaw_dodaj_klubL, adm_zaw_klubCB, adm_zaw_dodaj_klubM);
    				//grupa - comboBox
    				//sesja do comboBox
    				Session sessionAdmZaw_grupa_CB = factory.openSession();
        			Transaction t_AdmZaw_grupa_CB = sessionAdmZaw_grupa_CB.beginTransaction();
        			//OL_list
        			ObservableList<Grupa> OL_admZaw_grupa = FXCollections.observableArrayList();
        			try {
        				@SuppressWarnings("unchecked")
						List<Grupa> wynikAdmZaw_grupa = sessionAdmZaw_grupa_CB.createQuery("from Grupa").getResultList();
        				//przepisanie el. listy do OL_listy
        				for(int i=0; i<wynikAdmZaw_grupa.size(); i++)
        				{
        					OL_admZaw_grupa.add( wynikAdmZaw_grupa.get(i) );
        				}
        			}finally {
        				t_AdmZaw_grupa_CB.commit();
        				sessionAdmZaw_grupa_CB.close();
        			}
        			final ComboBox<Grupa> adm_zaw_grupaCB = new ComboBox<Grupa>(OL_admZaw_grupa);
    				adm_zaw_grupaCB.setPromptText("wybierz grupa");
    				HBox adm_zaw_dodaj_grupaHB = new HBox();
    				adm_zaw_dodaj_grupaHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_grupaL = new Label("Grupa: "); 
    				Label adm_zaw_dodaj_grupaM = new Label(); 
    				adm_zaw_dodaj_grupaL.setId("adm_edycjaL");
    				adm_zaw_grupaCB.setId("adm_edycjaTF");
    				adm_zaw_dodaj_grupaM.setId("adm_edycjaM");
    				adm_zaw_dodaj_grupaHB.getChildren().addAll(adm_zaw_dodaj_grupaL, adm_zaw_grupaCB, adm_zaw_dodaj_grupaM);
    				//login
    				HBox adm_zaw_dodaj_logHB = new HBox();
    				adm_zaw_dodaj_logHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_logL = new Label("Login: "); 
    				TextField adm_zaw_dodaj_logTF = new TextField(); 
    				Label adm_zaw_dodaj_logM = new Label(); 
    				adm_zaw_dodaj_logL.setId("adm_edycjaL");
    				adm_zaw_dodaj_logTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_logM.setId("adm_edycjaM");
    				adm_zaw_dodaj_logHB.getChildren().addAll(adm_zaw_dodaj_logL, adm_zaw_dodaj_logTF, adm_zaw_dodaj_logM);
    				//haslo
    				HBox adm_zaw_dodaj_haslHB = new HBox();
    				adm_zaw_dodaj_haslHB.getStyleClass().add("center");
    				Label adm_zaw_dodaj_haslL = new Label("Has³o: "); 
    				TextField adm_zaw_dodaj_haslTF = new TextField(); 
    				Label adm_zaw_dodaj_haslM = new Label(); 
    				adm_zaw_dodaj_haslL.setId("adm_edycjaL");
    				adm_zaw_dodaj_haslTF.setId("adm_edycjaTF");
    				adm_zaw_dodaj_haslM.setId("adm_edycjaM");
    				adm_zaw_dodaj_haslHB.getChildren().addAll(adm_zaw_dodaj_haslL, adm_zaw_dodaj_haslTF, adm_zaw_dodaj_haslM);
    				//messg - wiadomosc o poprawnosci wszystkich danych
    				HBox adm_zaw_zatw_messgHB = new HBox();
    				adm_zaw_zatw_messgHB.getStyleClass().add("center");
    				Button adm_zaw_zatw_zatwB = new Button("zatw");
    				Label adm_zaw_zatw_messg = new Label();
    				adm_zaw_zatw_messg.getStyleClass().add("adm_dodajMessg");
    				adm_zaw_zatw_messgHB.getChildren().addAll(adm_zaw_zatw_messg, adm_zaw_zatw_zatwB);
    				//wzorce
    				Pattern imieP = Pattern.compile("[A-Z][a-z]{2,15}");
    				Pattern nazwP = Pattern.compile("[A-Z][a-z]{2,31}");
    				Pattern rokP = Pattern.compile("[12][0-9]{3}");
    				Pattern wzrostP = Pattern.compile("[0-9]{2,3}");
    				Pattern wagaP = Pattern.compile("[0-9]{2,3}");
    				Pattern telP = Pattern.compile("[1-9][0-9]{8}");
    				Pattern logP = Pattern.compile("[A-Za-z0-9]{3,32}");
    				Pattern haslP = Pattern.compile("[A-Za-z0-9]{5,32}");
        			///////////////////
        			//zawodnik - dodaj
        			adm_zawodnik_dodaj.setOnAction(adm_zawodnik_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//dodanie wszystkich HB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_zaw_dodaj_imieHB, adm_zaw_dodaj_nazwHB, adm_zaw_dodaj_rokUrHB, 
        												adm_zaw_dodaj_wzrostHB, adm_zaw_dodaj_wagaHB, adm_zaw_dodaj_nrTelHB, 
        												adm_zaw_dodaj_pozycjaHB, adm_zaw_dodaj_adresHB, 
        												adm_zaw_dodaj_klubHB, adm_zaw_dodaj_grupaHB,
        												adm_zaw_dodaj_logHB, adm_zaw_dodaj_haslHB, adm_zaw_zatw_messgHB);
        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
        				adm_zaw_zatw_zatwB.setText("dodaj");
        				//akcja przycisku zatwierdŸ (tutaj dodaj)
        				adm_zaw_zatw_zatwB.setOnAction(adm_zaw_zatw_dodaj_a->{
        					//pobranie tekstu do sprawdzenia wzorców
        					Matcher testImie = imieP.matcher( adm_zaw_dodaj_imieTF.getText() );
        					Matcher testNazw = nazwP.matcher( adm_zaw_dodaj_nazwTF.getText() );
        					Matcher testRok = rokP.matcher( adm_zaw_dodaj_rokUrTF.getText() );
        					Matcher testWzrost = wzrostP.matcher( adm_zaw_dodaj_wzrostTF.getText() );
        					Matcher testWaga = wagaP.matcher( adm_zaw_dodaj_wagaTF.getText() );
        					Matcher testTel = telP.matcher( adm_zaw_dodaj_nrTelTF.getText() );
        					Matcher testLog = logP.matcher( adm_zaw_dodaj_logTF.getText() );
        					Matcher testHasl = haslP.matcher( adm_zaw_dodaj_haslTF.getText() );
        					//sprawdzenie wzorców i comboBoxów
        					if(testImie.matches())
        						adm_zaw_dodaj_imieM.setText(" ok");
        					else 
        						adm_zaw_dodaj_imieM.setText(" niepoprawne imie");
        					if(testNazw.matches())
        						adm_zaw_dodaj_nazwM.setText(" ok");
        					else 
        						adm_zaw_dodaj_nazwM.setText(" niepoprawne nazwisko");
        					if(testRok.matches())
        						adm_zaw_dodaj_rokUrM.setText(" ok");
        					else 
        						adm_zaw_dodaj_rokUrM.setText(" niepoprawny rok");
        					if(testWzrost.matches())
        						adm_zaw_dodaj_wzrostM.setText(" ok");
        					else 
        						adm_zaw_dodaj_wzrostM.setText(" niepoprawny wzrost");
        					if(testWaga.matches())
        						adm_zaw_dodaj_wagaM.setText(" ok");
        					else 
        						adm_zaw_dodaj_wagaM.setText(" niepoprawna waga");
        					if(testTel.matches())
        						adm_zaw_dodaj_nrTelM.setText(" ok");
        					else 
        						adm_zaw_dodaj_nrTelM.setText(" niepoprawny nr telefonu");
        					if(adm_zaw_pozycjaCB.getSelectionModel().getSelectedItem()!=null)
        						adm_zaw_dodaj_pozycjaM.setText(" ok");
        					else
        						adm_zaw_dodaj_pozycjaM.setText(" wybierz pozycjê");	
        					if(adm_zaw_adresCB.getSelectionModel().getSelectedItem()!=null)
        						adm_zaw_dodaj_adresM.setText(" ok");
        					else
        						adm_zaw_dodaj_adresM.setText(" wybierz adres");
        					if(adm_zaw_klubCB.getSelectionModel().getSelectedItem()!=null)
        						adm_zaw_dodaj_klubM.setText(" ok");
        					else
        						adm_zaw_dodaj_klubM.setText(" wybierz klub");
        					if(adm_zaw_grupaCB.getSelectionModel().getSelectedItem()!=null)
        						adm_zaw_dodaj_grupaM.setText(" ok");
        					else
        						adm_zaw_dodaj_grupaM.setText(" wybierz grupê");
        					if(testLog.matches())
        						adm_zaw_dodaj_logM.setText(" ok");
        					else 
        						adm_zaw_dodaj_logM.setText(" niepoprawny login");
        					if(testHasl.matches())
        						adm_zaw_dodaj_haslM.setText(" ok");
        					else 
        						adm_zaw_dodaj_haslM.setText(" niepoprawne has³o");
        					//sprawdzenie wszystkich wzorców i comboBoxów razem
        					if( testImie.matches() && testNazw.matches() && testRok.matches() && 
        							testWzrost.matches() && testWaga.matches() && testTel.matches() &&
        							adm_zaw_pozycjaCB.getSelectionModel().getSelectedItem()!=null && 
        							adm_zaw_adresCB.getSelectionModel().getSelectedItem()!=null && 
        							adm_zaw_klubCB.getSelectionModel().getSelectedItem()!=null && 
        							adm_zaw_grupaCB.getSelectionModel().getSelectedItem()!=null && 
        							testLog.matches() && testHasl.matches())
        					{
        						//nowy rekord
        						Zawodnik nowyZawodnik = new Zawodnik(adm_zaw_dodaj_imieTF.getText(), adm_zaw_dodaj_nazwTF.getText(), 
        												Integer.parseInt(adm_zaw_dodaj_rokUrTF.getText()), 
        												Integer.parseInt(adm_zaw_dodaj_wzrostTF.getText()),
        												Integer.parseInt(adm_zaw_dodaj_wagaTF.getText()),
        												Integer.parseInt(adm_zaw_dodaj_nrTelTF.getText()), 
        												adm_zaw_pozycjaCB.getSelectionModel().getSelectedItem(),
        												adm_zaw_adresCB.getSelectionModel().getSelectedItem(),
        												adm_zaw_klubCB.getSelectionModel().getSelectedItem(), 
        												adm_zaw_grupaCB.getSelectionModel().getSelectedItem(),
        												adm_zaw_dodaj_logTF.getText(), adm_zaw_dodaj_haslTF.getText());
        						//sesja do adm_zaw - dodaj
        	        			Session sessionAdmZaw_dodaj = factory.openSession();
        	        			Transaction t_AdmZaw_dodaj = sessionAdmZaw_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmZaw_dodaj.save(nowyZawodnik);
        	        				t_AdmZaw_dodaj.commit();
        	        				adm_zaw_zatw_messg.setText("dodano nowy rekord");
        	        			}catch (Exception e){
                    				if (t_AdmZaw_dodaj != null) {
                    					t_AdmZaw_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmZaw_dodaj.close();
        	        			}
        					}
        					else 
            				{
            					adm_zaw_zatw_messg.setText("niepoprawne dane");
            				}
        				});
        			});
        			///////////////////
        			//zawodnik - zmien
					adm_zawodnik_zmien.setOnAction(adm_zawodnik_zmien_a->{
						Zawodnik zaznaczonyZaw = TV_adm_zawodnik.getSelectionModel().getSelectedItem();
						if(zaznaczonyZaw!=null)
						{
							panel_prawy.getChildren().clear();
	        				//dodanie wszystkich HB do panelu prawego
	        				panel_prawy.getChildren().addAll(adm_zaw_dodaj_imieHB, adm_zaw_dodaj_nazwHB, adm_zaw_dodaj_rokUrHB, 
	        												adm_zaw_dodaj_wzrostHB, adm_zaw_dodaj_wagaHB, adm_zaw_dodaj_nrTelHB, 
	        												adm_zaw_dodaj_pozycjaHB, adm_zaw_dodaj_adresHB, 
	        												adm_zaw_dodaj_klubHB, adm_zaw_dodaj_grupaHB,
	        												adm_zaw_dodaj_logHB, adm_zaw_dodaj_haslHB, adm_zaw_zatw_messgHB);
	        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
	        				adm_zaw_zatw_zatwB.setText("zmieñ");
	        				//
	        				//wype³nienie TF'ów danymi zaznaczonego rekord
	        				adm_zaw_dodaj_imieTF.setText( zaznaczonyZaw.getImie() );
	        				adm_zaw_dodaj_nazwTF.setText( zaznaczonyZaw.getNazwisko() );
	        				adm_zaw_dodaj_rokUrTF.setText( Integer.toString(zaznaczonyZaw.getRokUrodzenia()) );
	        				adm_zaw_dodaj_wzrostTF.setText( Integer.toString(zaznaczonyZaw.getWzrost()) );
	        				adm_zaw_dodaj_wagaTF.setText( Integer.toString(zaznaczonyZaw.getWaga()) );
	        				adm_zaw_dodaj_nrTelTF.setText( Integer.toString(zaznaczonyZaw.getNrTelefonu()) );
	        				adm_zaw_pozycjaCB.setValue( zaznaczonyZaw.getPozycja_id() );
	        				adm_zaw_adresCB.setValue( zaznaczonyZaw.getAdres_id() );
	        				adm_zaw_klubCB.setValue( zaznaczonyZaw.getKlub_id() );
	        				adm_zaw_grupaCB.setValue( zaznaczonyZaw.getGrupa_id() );
	        				adm_zaw_dodaj_logTF.setText( zaznaczonyZaw.getLogin_() );
	        				adm_zaw_dodaj_haslTF.setText( zaznaczonyZaw.getHaslo_() );
	        				//
	        				//akcja przycisku zatwierdŸ (tutaj dodaj)
	        				adm_zaw_zatw_zatwB.setOnAction(adm_zaw_zatw_zmieñ_a->{
	        					//pobranie tekstu do sprawdzenia wzorców
	        					Matcher testImie = imieP.matcher( adm_zaw_dodaj_imieTF.getText() );
	        					Matcher testNazw = nazwP.matcher( adm_zaw_dodaj_nazwTF.getText() );
	        					Matcher testRok = rokP.matcher( adm_zaw_dodaj_rokUrTF.getText() );
	        					Matcher testWzrost = wzrostP.matcher( adm_zaw_dodaj_wzrostTF.getText() );
	        					Matcher testWaga = wagaP.matcher( adm_zaw_dodaj_wagaTF.getText() );
	        					Matcher testTel = telP.matcher( adm_zaw_dodaj_nrTelTF.getText() );
	        					Matcher testLog = logP.matcher( adm_zaw_dodaj_logTF.getText() );
	        					Matcher testHasl = haslP.matcher( adm_zaw_dodaj_haslTF.getText() );
	        					//sprawdzenie wzorców i comboBoxów
	        					if(testImie.matches())
	        						adm_zaw_dodaj_imieM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_imieM.setText(" niepoprawne imie");
	        					if(testNazw.matches())
	        						adm_zaw_dodaj_nazwM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_nazwM.setText(" niepoprawne nazwisko");
	        					if(testRok.matches())
	        						adm_zaw_dodaj_rokUrM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_rokUrM.setText(" niepoprawny rok");
	        					if(testWzrost.matches())
	        						adm_zaw_dodaj_wzrostM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_wzrostM.setText(" niepoprawny wzrost");
	        					if(testWaga.matches())
	        						adm_zaw_dodaj_wagaM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_wagaM.setText(" niepoprawna waga");
	        					if(testTel.matches())
	        						adm_zaw_dodaj_nrTelM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_nrTelM.setText(" niepoprawny nr telefonu");
	        					if(adm_zaw_pozycjaCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_zaw_dodaj_pozycjaM.setText(" ok");
	        					else
	        						adm_zaw_dodaj_pozycjaM.setText(" wybierz pozycjê");	
	        					if(adm_zaw_adresCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_zaw_dodaj_adresM.setText(" ok");
	        					else
	        						adm_zaw_dodaj_adresM.setText(" wybierz adres");
	        					if(adm_zaw_klubCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_zaw_dodaj_klubM.setText(" ok");
	        					else
	        						adm_zaw_dodaj_klubM.setText(" wybierz klub");
	        					if(adm_zaw_grupaCB.getSelectionModel().getSelectedItem()!=null)
	        						adm_zaw_dodaj_grupaM.setText(" ok");
	        					else
	        						adm_zaw_dodaj_grupaM.setText(" wybierz grupê");
	        					if(testLog.matches())
	        						adm_zaw_dodaj_logM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_logM.setText(" niepoprawny login");
	        					if(testHasl.matches())
	        						adm_zaw_dodaj_haslM.setText(" ok");
	        					else 
	        						adm_zaw_dodaj_haslM.setText(" niepoprawne has³o");
	        					//sprawdzenie wszystkich wzorców i comboBoxów razem
	        					if( testImie.matches() && testNazw.matches() && testRok.matches() && 
	        							testWzrost.matches() && testWaga.matches() && testTel.matches() &&
	        							adm_zaw_pozycjaCB.getSelectionModel().getSelectedItem()!=null && 
	        							adm_zaw_adresCB.getSelectionModel().getSelectedItem()!=null && 
	        							adm_zaw_klubCB.getSelectionModel().getSelectedItem()!=null && 
	        							adm_zaw_grupaCB.getSelectionModel().getSelectedItem()!=null && 
	        							testLog.matches() && testHasl.matches())
	        					{
	        						//zmiany w zaznaczonym rekordzie
	        						zaznaczonyZaw.setImie( adm_zaw_dodaj_imieTF.getText() );
	        						zaznaczonyZaw.setNazwisko( adm_zaw_dodaj_nazwTF.getText() );
	        						zaznaczonyZaw.setRokUrodzenia( Integer.parseInt(adm_zaw_dodaj_rokUrTF.getText()) );
	        						zaznaczonyZaw.setNrTelefonu( Integer.parseInt(adm_zaw_dodaj_nrTelTF.getText()) );
	        						zaznaczonyZaw.setWzrost( Integer.parseInt(adm_zaw_dodaj_wzrostTF.getText()) );
	        						zaznaczonyZaw.setWaga( Integer.parseInt(adm_zaw_dodaj_wagaTF.getText()) );
	        						zaznaczonyZaw.setPozycja_id( adm_zaw_pozycjaCB.getSelectionModel().getSelectedItem() );
	        						zaznaczonyZaw.setAdres_id( adm_zaw_adresCB.getSelectionModel().getSelectedItem() );
	        						zaznaczonyZaw.setKlub_id( adm_zaw_klubCB.getSelectionModel().getSelectedItem() );
	        						zaznaczonyZaw.setGrupa_id( adm_zaw_grupaCB.getSelectionModel().getSelectedItem() );
	        						zaznaczonyZaw.setLogin_( adm_zaw_dodaj_logTF.getText() );
	        						zaznaczonyZaw.setHaslo_( adm_zaw_dodaj_haslTF.getText() );
	        						//sesja do adm_zaw - zmien
	        	        			Session sessionAdmZaw_zmien = factory.openSession();
	        	        			Transaction t_AdmZaw_zmien = sessionAdmZaw_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmZaw_zmien.update(zaznaczonyZaw);
	        	        				t_AdmZaw_zmien.commit();
	        	        				adm_zaw_zatw_messg.setText("zmieniono rekord");
	        	        			}catch (Exception e){
	                    				if (t_AdmZaw_zmien != null) {
	                    					t_AdmZaw_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmZaw_zmien.close();
	        	        			}
	        					}
	        					else 
	            				{
	            					adm_zaw_zatw_messg.setText("niepoprawne dane");
	            				}
	        				});
						}
					});
					///////////////////
        			//zawodnik - usun
					adm_zawodnik_usun.setOnAction(adm_zawodnik_usun_a->{
						{
							Zawodnik zaznaczonyZaw = TV_adm_zawodnik.getSelectionModel().getSelectedItem();
							if(zaznaczonyZaw!=null)
							{
								//sesja do adm_zawodnik_usun
		            			Session sessionAdmZawodnik_usun = factory.openSession();
		            			Transaction t_AdmZawodnik_usun = sessionAdmZawodnik_usun.beginTransaction();
		            			try {
		            				//usuniêcie zaznaczonego rekordu
		            				sessionAdmZawodnik_usun.remove(zaznaczonyZaw);
		                			//odœwie¿enie stanu tabeli po usuniêciu
		                			//lista wszystkich rekordów
		                			@SuppressWarnings("unchecked")
		        					List<Zawodnik> wynikAdmzawodnik_usun = sessionAdmZawodnik_usun.createQuery("from Zawodnik").getResultList();
		                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
		                			ObservableList<Zawodnik> OL_admZawodnik_usun = FXCollections.observableArrayList();
		                			for(int i=0; i<wynikAdmzawodnik_usun.size(); i++)
		                			{
		                				OL_admZawodnik_usun.add( wynikAdmzawodnik_usun.get(i) );
		                			}
		                			//dodanie elementow list do tabeli
		                			TV_adm_zawodnik.setItems(OL_admZawodnik_usun);
		                			//
		                			t_AdmZawodnik_usun.commit();
		                			sessionAdmZawodnik_usun.close();
		            			}catch (Exception e){
		            				if (t_AdmZawodnik_usun != null) {
		            					t_AdmZawodnik_usun.rollback();
		            		         }
		            		         e.printStackTrace();
		            			}
							}
						}
					});
				});
				
				//adm - administrator
				adm_tabAdminB.setOnAction(adm_adm_a->{
					panel_prawy.getChildren().clear();
					//label - jaka to tabela
        			Label tabela = new Label("Tabela \"Administrator\"");
        			panel_prawy.getChildren().addAll(tabela);
        			//tablela administratorów
    				TableView<Administrator> TV_adm_administrator = new TableView<>();
    				//kol-id administratora
    				TableColumn<Administrator, Integer> TC_admAdministrator_id = new TableColumn<>("Id");
    				TC_admAdministrator_id.setCellValueFactory(new PropertyValueFactory<Administrator, Integer>("id"));
    				TC_admAdministrator_id.setMinWidth(100);
    				//kol-imie administratora
    				TableColumn<Administrator, String> TC_admAdministrator_imie = new TableColumn<>("Imie");
    				TC_admAdministrator_imie.setCellValueFactory(new PropertyValueFactory<Administrator, String>("imie"));
    				TC_admAdministrator_imie.setMinWidth(150);
    				//kol-nazw administratora
    				TableColumn<Administrator, String> TC_admAdministrator_nazw = new TableColumn<>("Nazwisko");
    				TC_admAdministrator_nazw.setCellValueFactory(new PropertyValueFactory<Administrator, String>("nazwisko"));
    				TC_admAdministrator_nazw.setMinWidth(180);
    				//kol-login administratora
    				TableColumn<Administrator, String> TC_admAdministrator_log = new TableColumn<>("Login");
    				TC_admAdministrator_log.setCellValueFactory(new PropertyValueFactory<Administrator, String>("login_"));
    				TC_admAdministrator_log.setMinWidth(170);
    				//kol-haslo administratora
    				TableColumn<Administrator, String> TC_admAdministrator_haslo = new TableColumn<>("Has³o");
    				TC_admAdministrator_haslo.setCellValueFactory(new PropertyValueFactory<Administrator, String>("haslo_"));
    				TC_admAdministrator_haslo.setMinWidth(170);
    				//sesja do adm_administrator
        			Session sessionAdmAdministrator = factory.openSession();
        			Transaction t_AdmAdministrator = sessionAdmAdministrator.beginTransaction();
    				try {
    					//lista wszystkich administratorow
        				@SuppressWarnings("unchecked")
						List<Administrator> wynikAdmAdministrator = sessionAdmAdministrator.createQuery("from Administrator").getResultList();
        				//lista obiektow - przepisanie elementow listy do OL_list
        				ObservableList<Administrator> OL_admAdministrator = FXCollections.observableArrayList();
        				for(int i=0; i<wynikAdmAdministrator.size(); i++)
        				{
        					OL_admAdministrator.add( wynikAdmAdministrator.get(i) );
        				}
        				//ustawienie kolumn
        				TV_adm_administrator.getColumns().addAll(TC_admAdministrator_id, TC_admAdministrator_imie, TC_admAdministrator_nazw,
        														TC_admAdministrator_log, TC_admAdministrator_haslo);
        				//dodanie elementow list do tabeli
        				TV_adm_administrator.setItems(OL_admAdministrator);
        				//dodanie tabeli do panelu prawego
        				panel_prawy.getChildren().addAll(TV_adm_administrator);
    				}finally {
    					t_AdmAdministrator.commit();
    					sessionAdmAdministrator.close();
    				}
    				//dodanie przycisków pod tabel¹ 
        			HBox adm_administrator_HB = new HBox();
        			adm_administrator_HB.getStyleClass().add("adm_CUD_przyciski");
        			Button adm_administrator_dodaj = new Button("Dodaj nowy rekord");
        			Button adm_administrator_zmien = new Button("Edytuj zaznaczony rekord");
        			Button adm_administrator_usun = new Button("Usuñ zaznaczony rekord");
        			adm_administrator_HB.getChildren().addAll(adm_administrator_dodaj, adm_administrator_zmien, adm_administrator_usun);
        			panel_prawy.getChildren().addAll(adm_administrator_HB);	
        			//elementy potrzebne do dodawania, edycji ::
        			//imie
    				HBox adm_administrator_dodaj_imieHB = new HBox();
    				adm_administrator_dodaj_imieHB.getStyleClass().add("center");
    				Label adm_administrator_dodaj_imieL = new Label("Imie: "); 
    				TextField adm_administrator_dodaj_imieTF = new TextField(); 
    				Label adm_administrator_dodaj_imieM = new Label(); 
    				adm_administrator_dodaj_imieL.setId("adm_edycjaL");
    				adm_administrator_dodaj_imieTF.setId("adm_edycjaTF");
    				adm_administrator_dodaj_imieM.setId("adm_edycjaM");
    				adm_administrator_dodaj_imieHB.getChildren().addAll(adm_administrator_dodaj_imieL, adm_administrator_dodaj_imieTF, adm_administrator_dodaj_imieM);
    				//nazwisko
    				HBox adm_administrator_dodaj_nazwHB = new HBox();
    				adm_administrator_dodaj_nazwHB.getStyleClass().add("center");
    				Label adm_administrator_dodaj_nazwL = new Label("Nazwisko: "); 
    				TextField adm_administrator_dodaj_nazwTF = new TextField(); 
    				Label adm_administrator_dodaj_nazwM = new Label(); 
    				adm_administrator_dodaj_nazwL.setId("adm_edycjaL");
    				adm_administrator_dodaj_nazwTF.setId("adm_edycjaTF");
    				adm_administrator_dodaj_nazwM.setId("adm_edycjaM");
    				adm_administrator_dodaj_nazwHB.getChildren().addAll(adm_administrator_dodaj_nazwL, adm_administrator_dodaj_nazwTF, adm_administrator_dodaj_nazwM);
    				//login
    				HBox adm_administrator_dodaj_logHB = new HBox();
    				adm_administrator_dodaj_logHB.getStyleClass().add("center");
    				Label adm_administrator_dodaj_logL = new Label("Login: "); 
    				TextField adm_administrator_dodaj_logTF = new TextField(); 
    				Label adm_administrator_dodaj_logM = new Label(); 
    				adm_administrator_dodaj_logL.setId("adm_edycjaL");
    				adm_administrator_dodaj_logTF.setId("adm_edycjaTF");
    				adm_administrator_dodaj_logM.setId("adm_edycjaM");
    				adm_administrator_dodaj_logHB.getChildren().addAll(adm_administrator_dodaj_logL, adm_administrator_dodaj_logTF, adm_administrator_dodaj_logM);
    				//haslo
    				HBox adm_administrator_dodaj_hasloHB = new HBox();
    				adm_administrator_dodaj_hasloHB.getStyleClass().add("center");
    				Label adm_administrator_dodaj_hasloL = new Label("Has³o: "); 
    				TextField adm_administrator_dodaj_hasloTF = new TextField(); 
    				Label adm_administrator_dodaj_hasloM = new Label(); 
    				adm_administrator_dodaj_hasloL.setId("adm_edycjaL");
    				adm_administrator_dodaj_hasloTF.setId("adm_edycjaTF");
    				adm_administrator_dodaj_hasloM.setId("adm_edycjaM");
    				adm_administrator_dodaj_hasloHB.getChildren().addAll(adm_administrator_dodaj_hasloL, adm_administrator_dodaj_hasloTF, adm_administrator_dodaj_hasloM);
    				//messg - wiadomosc o poprawnosci wszystkich danych
    				HBox adm_administrator_zatw_messgHB = new HBox();
    				adm_administrator_zatw_messgHB.getStyleClass().add("center");
    				Button adm_administrator_zatw_zatwB = new Button("zatw");
    				Label adm_administrator_zatw_messg = new Label();
    				adm_administrator_zatw_messg.getStyleClass().add("adm_dodajMessg");
    				adm_administrator_zatw_messgHB.getChildren().addAll(adm_administrator_zatw_messg, adm_administrator_zatw_zatwB);
    				//wzorce
    				Pattern imieP = Pattern.compile("[A-Z][a-z]{2,15}");
    				Pattern nazwP = Pattern.compile("[A-Z][a-z]{2,31}");
    				Pattern logP = Pattern.compile("[A-Za-z0-9]{3,32}");
    				Pattern hasloP = Pattern.compile("[A-Za-z0-9]{9,32}");
    				/////////////////////////
        			//administrator - dodaj
        			adm_administrator_dodaj.setOnAction(adm_administrator_dodaj_a->{
        				panel_prawy.getChildren().clear();
        				//dodanie wszystkich HB do panelu prawego
        				panel_prawy.getChildren().addAll(adm_administrator_dodaj_imieHB,adm_administrator_dodaj_nazwHB, adm_administrator_dodaj_logHB,
        													adm_administrator_dodaj_hasloHB, adm_administrator_zatw_messgHB);
        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
        				adm_administrator_zatw_zatwB.setText("dodaj");
        				//akcja przycisku zatwierdŸ (tutaj dodaj)
        				adm_administrator_zatw_zatwB.setOnAction(adm_administrator_zatw_dodaj_a->{
        					//pobranie tekstu do sprawdzenia wzorców
        					Matcher testImie = nazwP.matcher(adm_administrator_dodaj_imieTF.getText() );
        					Matcher testNazw = imieP.matcher(adm_administrator_dodaj_nazwTF.getText() );
        					Matcher testLog = logP.matcher(adm_administrator_dodaj_logTF.getText() );
        					Matcher testHaslo = hasloP.matcher(adm_administrator_dodaj_hasloTF.getText() );
        					//sprawdzenie wzorców pojedynczo
            				if(testImie.matches())
        						adm_administrator_dodaj_imieM.setText(" ok");
        					else 
        						adm_administrator_dodaj_imieM.setText(" niepoprawne imie");
            				if(testNazw.matches())
        						adm_administrator_dodaj_nazwM.setText(" ok");
        					else 
        						adm_administrator_dodaj_nazwM.setText(" niepoprawne nazwisko");
            				if(testLog.matches())
        						adm_administrator_dodaj_logM.setText(" ok");
        					else 
        						adm_administrator_dodaj_logM.setText(" niepoprawny login");
            				if(testHaslo.matches())
        						adm_administrator_dodaj_hasloM.setText(" ok");
        					else 
        						adm_administrator_dodaj_hasloM.setText(" niepoprawne has³o");
            				//sprawdzenie wszystkich wzorców razem
            				if( testImie.matches() && testNazw.matches() && testLog.matches() && testHaslo.matches() )
            				{
            					Administrator nowyAdmin = new Administrator(adm_administrator_dodaj_imieTF.getText(), adm_administrator_dodaj_nazwTF.getText(),
            															adm_administrator_dodaj_logTF.getText(), adm_administrator_dodaj_hasloTF.getText() );
            					//sesja do adm_administrator - dodaj
        	        			Session sessionAdmKlub_dodaj = factory.openSession();
        	        			Transaction t_AdmKlub_dodaj = sessionAdmKlub_dodaj.beginTransaction();
        	        			try {
        	        				sessionAdmKlub_dodaj.save(nowyAdmin);
        	        				t_AdmKlub_dodaj.commit();
        	        				adm_administrator_zatw_messg.setText("dodano nowy rekord");
        	        			}catch (Exception e){
                    				if (t_AdmKlub_dodaj != null) {
                    					t_AdmKlub_dodaj.rollback();
                    		         }
                    		         e.printStackTrace();
                    			}finally {
                    				sessionAdmKlub_dodaj.close();
        	        			}
            				}
            				else 
            				{
            					adm_administrator_zatw_messg.setText("niepoprawne dane");
            				}
        				});
        			});
    				/////////////////////////
        			//administrator - zmien
					adm_administrator_zmien.setOnAction(adm_administrator_zmien_a->{
						Administrator zaznaczonyAdmin = TV_adm_administrator.getSelectionModel().getSelectedItem();
						if (zaznaczonyAdmin!=null)
						{
							panel_prawy.getChildren().clear();
							//dodanie wszystkich HB do panelu prawego
	        				panel_prawy.getChildren().addAll(adm_administrator_dodaj_imieHB,adm_administrator_dodaj_nazwHB, adm_administrator_dodaj_logHB,
	        													adm_administrator_dodaj_hasloHB, adm_administrator_zatw_messgHB);
	        				//ustawienie tekstu na przycisku zatwierdzaj¹cym
	        				adm_administrator_zatw_zatwB.setText("zmieñ");
	        				//
	        				//wype³nienie TF'ów danymi zaznaczonego rekordu
	        				adm_administrator_dodaj_imieTF.setText( zaznaczonyAdmin.getImie() );
	        				adm_administrator_dodaj_nazwTF.setText( zaznaczonyAdmin.getNazwisko() );
	        				adm_administrator_dodaj_logTF.setText( zaznaczonyAdmin.getLogin_() );
	        				adm_administrator_dodaj_hasloTF.setText( zaznaczonyAdmin.getHaslo_() );
	        				//
	        				//akcja przycisku zatwierdŸ (tutaj zmieñ)
	        				adm_administrator_zatw_zatwB.setOnAction(adm_klub_zatw_dodaj_a->{
	        					//pobranie tekstu do sprawdzenia wzorców
	        					Matcher testImie = nazwP.matcher( adm_administrator_dodaj_imieTF.getText() );
	        					Matcher testNazw = imieP.matcher( adm_administrator_dodaj_nazwTF.getText() );
	        					Matcher testLog = logP.matcher( adm_administrator_dodaj_logTF.getText() );
	        					Matcher testHaslo = hasloP.matcher( adm_administrator_dodaj_hasloTF.getText() );
	        					//sprawdzenie wzorców pojedynczo
	            				if(testImie.matches())
	        						adm_administrator_dodaj_imieM.setText(" ok");
	        					else 
	        						adm_administrator_dodaj_imieM.setText(" niepoprawne imie");
	            				if(testNazw.matches())
	        						adm_administrator_dodaj_nazwM.setText(" ok");
	        					else 
	        						adm_administrator_dodaj_nazwM.setText(" niepoprawne nazwisko");
	            				if(testLog.matches())
	        						adm_administrator_dodaj_logM.setText(" ok");
	        					else 
	        						adm_administrator_dodaj_logM.setText(" niepoprawny login");
	            				if(testHaslo.matches())
	        						adm_administrator_dodaj_hasloM.setText(" ok");
	        					else 
	        						adm_administrator_dodaj_hasloM.setText(" niepoprawne has³o");
	            				//sprawdzenie wszystkich wzorców razem
	            				if( testImie.matches() && testNazw.matches() && testLog.matches() && testHaslo.matches() )
	            				{
	            					//zmiany w zaznaczonym rekordzie
	            					zaznaczonyAdmin.setImie( adm_administrator_dodaj_imieTF.getText() );
	            					zaznaczonyAdmin.setNazwisko( adm_administrator_dodaj_nazwTF.getText() );
	            					zaznaczonyAdmin.setLogin_( adm_administrator_dodaj_logTF.getText() );
	            					zaznaczonyAdmin.setHaslo_( adm_administrator_dodaj_hasloTF.getText() );
	            					//sesja do adm_administrator - zmieñ
	        	        			Session sessionAdmKlub_zmien = factory.openSession();
	        	        			Transaction t_AdmKlub_zmien = sessionAdmKlub_zmien.beginTransaction();
	        	        			try {
	        	        				sessionAdmKlub_zmien.update(zaznaczonyAdmin);
	        	        				t_AdmKlub_zmien.commit();
	        	        				adm_administrator_zatw_messg.setText("zmieniono rekord");
	        	        			}catch (Exception e){
	                    				if (t_AdmKlub_zmien != null) {
	                    					t_AdmKlub_zmien.rollback();
	                    		         }
	                    		         e.printStackTrace();
	                    			}finally {
	                    				sessionAdmKlub_zmien.close();
	        	        			}
	            				}
	            				else 
	            				{
	            					adm_administrator_zatw_messg.setText("niepoprawne dane");
	            				}
	        				});
						}
					});
    				/////////////////////////
        			//administrator - usun
					adm_administrator_usun.setOnAction(adm_administrator_usun_a->{
						Administrator zaznaczonyAdmin = TV_adm_administrator.getSelectionModel().getSelectedItem();
						if(zaznaczonyAdmin!=null) 
						{
							//sesja do adm_administrator_usun
	            			Session sessionAdmAdministrator_usun = factory.openSession();
	            			Transaction t_AdmAdministrator_usun = sessionAdmAdministrator_usun.beginTransaction();
	            			try {
	            				//usuniêcie zaznaczonego rekordu
	            				sessionAdmAdministrator_usun.remove(zaznaczonyAdmin);
	                			//odœwie¿enie stanu tabeli po usuniêciu
	                			//lista wszystkich rekordów
	                			@SuppressWarnings("unchecked")
	        					List<Administrator> wynikAdmadministrator_usun = sessionAdmAdministrator_usun.createQuery("from Administrator").getResultList();
	                			//lista obiektow - przepisanie elementow listy wynikowej do OL_list
	                			ObservableList<Administrator> OL_admAdministrator_usun = FXCollections.observableArrayList();
	                			for(int i=0; i<wynikAdmadministrator_usun.size(); i++)
	                			{
	                				OL_admAdministrator_usun.add( wynikAdmadministrator_usun.get(i) );
	                			}
	                			//dodanie elementow list do tabeli
	                			TV_adm_administrator.setItems(OL_admAdministrator_usun);
	                			//
	                			t_AdmAdministrator_usun.commit();
	                			sessionAdmAdministrator_usun.close();
	            			}catch (Exception e){
	            				if (t_AdmAdministrator_usun != null) {
	            					t_AdmAdministrator_usun.rollback();
	            		         }
	            		         e.printStackTrace();
	            			}
						}
					});
				});
    			
    			//adm - wyloguj
    			adm_WylogujB.setOnAction(aWyloguj_a->
    			{
    				//przywrócenie logowanie HBox zawierajacego 2xVbox(etykiety, pola)
    				panel_lewy.getChildren().clear();
    				panel_prawy.getChildren().clear();
    				panel_prawy.getChildren().addAll(logowanie);
    				//wyczyszczenie message label z ew. informacji "b³êdny login lub/i has³o"
    				messgL.setText("");
    			});
    		}
    		//b³êdny login lub/i has³o
    		else 
    		{
    			messgL.setText("B³êdny login lub/i has³o");;	
    		}
    		//wyczyszcznie pola hasla
    		//po b³êdnie podanym has³e lub po wylogowaniu w polu login pozostanie login
    		hasloTF.clear();
    	}
    	);

        zamknij.setOnAction(a_close->
        {
        	factory.close();
        	stage.close();
        }
        );
        
        zwin.setOnAction(a_zwin->
        {
        	stage.setIconified(true);
        }
        );
        	
		//
		Scene scena = new Scene(okno, 1200, 900);
		stage.setScene(scena);
		stage.show();
		
		// factory close
		//factory.close();
		
	}
	
	public static void main(String[] args)
	{	
		launch(args);
		

		
	}
}

/*StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  

SessionFactory factory = meta.getSessionFactoryBuilder().build();  
Session session = factory.openSession();

try {	
	Transaction t = session.beginTransaction();
	
	@SuppressWarnings("unchecked")
	List<Zawodnik> wynikL = session.createQuery("from Zawodnik").getResultList();
	for(Zawodnik temp : wynikL) {
		System.out.println(temp);
	}
	
	//String p1 = wynikL.get(0).getHaslo_();
	//System.out.println(p1);
	
	t.commit();
}finally {
	session.close(); 
	factory.close();
}*/

/*
 * 
 */

/*for(int i=0; i<wynikL.size(); i++)
System.out.println(wynikL.get(i).getGrupa_id());*/

/*String hql = "from Administrator";
Query query = session.createQuery(hql);
@SuppressWarnings({ "rawtypes", "unused" })
List results = query.getResultList();

for(int i=0; i<results.size(); i++)
System.out.println(results.get(i));*/
