package az.developia;



import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class StudentsController implements Initializable {
	
@FXML	
private TextField studentName,studentSurname,studentPhone;	

@FXML
private DatePicker studentBirthday;

@FXML
private ComboBox<String> studentSector;

	
@FXML	
private void saveStudent() {
	String name = studentName.getText();
	String surname= studentSurname.getText();
	String phone= studentPhone.getText();
	LocalDate birthday =studentBirthday.getValue();
	
	try {
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/java5-test?useSSL=false", "root", "150687");
//		Statement st=conn.createStatement();
//		st.executeUpdate("insert into students (name,surname,phone) " + "values ('"+name+"','"+surname+"','"+phone+"');");
//		conn.close();
		
		PreparedStatement pst=conn.prepareStatement("insert into students (name,surname,phone,birthday) values (?,?,?,?);");
		pst.setString(1, name);
		pst.setString(2, surname);
		pst.setString(3, phone);
	    pst.setDate(4,Date.valueOf(birthday));
		pst.executeUpdate();
		conn.close();
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}


@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	List<String> sectors = Arrays.asList("Az","Rus","Ingilis");
	studentSector.getItems().addAll(sectors);	
}
	
}
