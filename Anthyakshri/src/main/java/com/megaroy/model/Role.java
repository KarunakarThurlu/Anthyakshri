package com.megaroy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Roles_table")
public class Role {
	@Id
	@GeneratedValue( generator = "roleIDGenerator")
	@GenericGenerator(name="roleIDGenerator",strategy = "com.megaroy.customgenerators.RoleIdGenerator")
	private String roleId;
	private String roleName;
	
	public Role() {
		super();
	}
	public Role(String roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
