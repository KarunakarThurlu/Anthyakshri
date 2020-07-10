package com.megaroy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_attribute")
public class UserAttributes {

	@Id
	@GeneratedValue
	private String userAttrId;
}
