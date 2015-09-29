package org.kiva.dbtest.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class User {
	@Id
	private String userName;
	
	private String firstName;
	private String lastName;
	private Integer age;
	private Character sex;
	private Date birthDate;
	private Date created;
	private Boolean smart;
	
	@ManyToOne
	private Company company;
	
	@ManyToMany
	private List<Role> roles;
	
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (age == null) {
			if (other.age != null) {
				return false;
			}
		} else if (!age.equals(other.age)) {
			return false;
		}
		if (birthDate == null) {
			if (other.birthDate != null) {
				return false;
			}
		} else if (!birthDate.equals(other.birthDate)) {
			return false;
		}
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
			return false;
		}
		if (created == null) {
			if (other.created != null) {
				return false;
			}
		} else if (!created.equals(other.created)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (roles == null) {
			if (other.roles != null) {
				return false;
			}
		} else if (!roles.equals(other.roles)) {
			return false;
		}
		if (sex == null) {
			if (other.sex != null) {
				return false;
			}
		} else if (!sex.equals(other.sex)) {
			return false;
		}
		if (smart == null) {
			if (other.smart != null) {
				return false;
			}
		} else if (!smart.equals(other.smart)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
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
