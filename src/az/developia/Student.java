package az.developia;

import java.sql.Date;
import java.time.LocalDate;

public class Student {
private Integer id;
private String name;
private String surname;
private String phone;
private Date birthday;
private String sector;




public Student(Integer id, String name, String surname, String phone, Date birthday, String sector) {
	super();
	this.id = id;
	this.name = name;
	this.surname = surname;
	this.phone = phone;
	this.birthday = birthday;
	this.sector = sector;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSurname() {
	return surname;
}
public void setSurname(String surname) {
	this.surname = surname;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public Date getBirthday() {
	return birthday;
}
public void setBirthday(Date birthday) {
	this.birthday = birthday;
}
public String getSector() {
	return sector;
}
public void setSector(String sector) {
	this.sector = sector;
}

}
