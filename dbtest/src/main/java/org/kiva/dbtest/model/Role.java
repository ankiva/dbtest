package org.kiva.dbtest.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class Role {

	@Id
	private String type;
	private String privileges;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Role other = (Role) obj;
		if (privileges == null) {
			if (other.privileges != null) {
				return false;
			}
		} else if (!privileges.equals(other.privileges)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [type=");
		builder.append(type);
		builder.append(", privileges=");
		builder.append(privileges);
		builder.append("]");
		return builder.toString();
	}
}
