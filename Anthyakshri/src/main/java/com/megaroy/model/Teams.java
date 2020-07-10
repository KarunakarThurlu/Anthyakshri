package com.megaroy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="teams_table")
public class Teams {
	
	@Id
	@GeneratedValue
	private String teamId;
	private String teamName;
	private Integer noOfTeams;
	private Integer noOfTeamMembers;
	private Integer teamScore;
	private Integer teamRank;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="Teams_Users_Table",joinColumns = @JoinColumn(name="teamId"),inverseJoinColumns = @JoinColumn(name="userId"))
	private Set<User> users=new HashSet<>();
	
	public Teams() {
		super();
	}

	public Teams(String teamId, String teamName, Integer noOfTeams, Integer noOfTeamMembers, Integer teamScore,
			Integer teamRank, Set<User> users) {
		super();
		this.teamId = teamId;
		this.teamName = teamName;
		this.noOfTeams = noOfTeams;
		this.noOfTeamMembers = noOfTeamMembers;
		this.teamScore = teamScore;
		this.teamRank = teamRank;
		this.users = users;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getNoOfTeams() {
		return noOfTeams;
	}

	public void setNoOfTeams(Integer noOfTeams) {
		this.noOfTeams = noOfTeams;
	}

	public Integer getNoOfTeamMembers() {
		return noOfTeamMembers;
	}

	public void setNoOfTeamMembers(Integer noOfTeamMembers) {
		this.noOfTeamMembers = noOfTeamMembers;
	}

	public Integer getTeamScore() {
		return teamScore;
	}

	public void setTeamScore(Integer teamScore) {
		this.teamScore = teamScore;
	}

	public Integer getTeamRank() {
		return teamRank;
	}

	public void setTeamRank(Integer teamRank) {
		this.teamRank = teamRank;
	}
	
	

}
