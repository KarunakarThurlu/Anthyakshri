package com.megaroy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="feedback_table")
public class Feedback {
	
	@Id
	@GeneratedValue
	private String feedbackId;

}
