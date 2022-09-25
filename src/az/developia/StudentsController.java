package az.developia;



import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentsController implements Initializable {
	
@FXML	
private TextField studentName,studentSurname,studentPhone;	

@FXML
private DatePicker studentBirthday;

@FXML
private ComboBox<String> studentSector;

@FXML
private TableView<Student> studentsTable ; 

@FXML
private TableColumn<Student,Integer> idColumn;
@FXML
private TableColumn<Student,String> nameColumn;
@FXML
private TableColumn<Student,String> surnameColumn;
@FXML
private TableColumn<Student,String> phoneColumn;
@FXML
private TableColumn<Student,Date> birthdayColumn;
@FXML
private TableColumn<Student,String> sectorColumn;


private boolean updateMode=false;
private int selectedStudentId=0;

	
@FXML	
private void saveStudent() {
	String name = studentName.getText();
	String surname= studentSurname.getText();
	String phone= studentPhone.getText();
	LocalDate birthday =studentBirthday.getValue();
	String sector = studentSector.getValue();
	
	try {
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/java5-test?useSSL=false", "root", "150687");
//		Statement st=conn.createStatement();
//		st.executeUpdate("insert into students (name,surname,phone) " + "values ('"+name+"','"+surname+"','"+phone+"');");
//		conn.close();
		
		if(updateMode) {
			PreparedStatement pst=conn.prepareStatement("update students set " + "name=?,surname=?,phone=?,sector=? where id=?");
			pst.setString(1, name);
			pst.setString(2, surname);
			pst.setString(3, phone);
//		    pst.setDate(4,Date.valueOf(birthday));
		    pst.setString(4, sector);
		    pst.setInt(5, selectedStudentId);
			pst.executeUpdate();
			updateMode=false; 
		}
		else {
		
		PreparedStatement pst=conn.prepareStatement("insert into students (name,surname,phone,birthday,sector) values (?,?,?,?,?);");
		pst.setString(1, name);
		pst.setString(2, surname);
		pst.setString(3, phone);
	    pst.setDate(4,Date.valueOf(birthday));
	    pst.setString(5, sector);
		pst.executeUpdate();
		}
		conn.close();		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	loadStudents();
}



@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	
	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
	phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
	birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
	sectorColumn.setCellValueFactory(new PropertyValueFactory<>("sector"));
	
	List<String> sectors = Arrays.asList("Az","Rus","Ingilis");
	studentSector.getItems().addAll(sectors);	
	loadStudents();
	
}
public void loadStudents() {
	try {
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/java5-test?useSSL=false", "root", "150687");
	
		PreparedStatement pst=conn.prepareStatement("select * from students");
		ResultSet rs = pst.executeQuery();
		ArrayList<Student> students = new ArrayList<Student>();
		
		while(rs.next()) {
			
			Student s = new Student(rs.getInt("id"),rs.getString("name"),rs.getString("surname"),rs.getString("phone"),
					rs.getDate("birthday"),rs.getString("sector"));
			students.add(s);			
		}
		ObservableList<Student> list = FXCollections.observableArrayList();
		list.addAll(students);
		studentsTable.setItems(list);
		conn.close();		
		
	} catch (Exception e) {
		e.printStackTrace();
	}		
}

@FXML
public void deleteStudent() {
	Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
	if(selectedStudent==null) {
		Notifications.create().text("Select item!").title("Warning").showError();		
	}
	else {
	int studentId= selectedStudent.getId();	
	
	try {
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/java5-test?useSSL=false", "root", "150687");

		PreparedStatement pst=conn.prepareStatement("delete from students where id=?");
		pst.setInt(1, studentId);
		pst.executeUpdate();
		conn.close();		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	loadStudents();	
}}

@FXML
public void updateStudent() {
	Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
	if(selectedStudent==null) {
		Notifications.create().text("Select item!").title("Warning").showError();		
	}
	else {
	
		studentName.setText(selectedStudent.getName());	
		studentSurname.setText(selectedStudent.getSurname());	
		studentPhone.setText(selectedStudent.getPhone());	
//		studentBirthday.setDate(selectedStudent.getBirthday());
		studentSector.setValue(selectedStudent.getSector());
		
		selectedStudentId=selectedStudent.getId();
		updateMode=true;
	
	}
}
	
}
