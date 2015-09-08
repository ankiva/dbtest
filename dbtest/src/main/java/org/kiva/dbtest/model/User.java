package org.kiva.dbtest.model;

import java.util.Date;

public class User {

	private String userName;
	private String firstName;
	private String lastName;
	private Integer age;
	private Character sex;
	private Date birthDate;
	private Date created;
	private Boolean smart;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Character getSex() {
		return sex;
	}
	public void setSex(Character sex) {
		this.sex = sex;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Boolean getSmart() {
		return smart;
	}
	public void setSmart(Boolean smart) {
		this.smart = smart;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userName=");
		builder.append(userName);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", age=");
		builder.append(age);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", birthDate=");
		builder.append(birthDate);
		builder.append(", created=");
		builder.append(created);
		builder.append(", smart=");
		builder.append(smart);
		builder.append("]");
		return builder.toString();
	}
}
