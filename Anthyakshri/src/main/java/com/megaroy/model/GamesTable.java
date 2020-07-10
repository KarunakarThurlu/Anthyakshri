package com.megaroy.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Games_Table")
public class GamesTable {
	
	@Id
	@GeneratedValue
	private Integer playerId;
	private String playerName;
	private Integer gamesPlayed;
	private Integer highestScore;
	private Integer gamesOwn;
	private Integer gamesLose;
	private Integer gamesTie;
	private Integer totalPoints;
	private Integer rank;
	private Integer coinsAvailable;
	private Integer coinsremaining;
	
	@ManyToOne
	@JoinColumn(name="user_id_fk")
	private User user;

	public GamesTable(Integer playerId, String playerName, Integer gamesPlayed, Integer highestScore, Integer gamesOwn,
			Integer gamesLose, Integer gamesTie, Integer totalPoints, Integer rank, Integer coinsAvailable,
			Integer coinsremaining, User user) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
		this.gamesPlayed = gamesPlayed;
		this.highestScore = highestScore;
		this.gamesOwn = gamesOwn;
		this.gamesLose = gamesLose;
		this.gamesTie = gamesTie;
		this.totalPoints = totalPoints;
		this.rank = rank;
		this.coinsAvailable = coinsAvailable;
		this.coinsremaining = coinsremaining;
		this.user = user;
	}

	public GamesTable() {
		super();
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public Integer getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(Integer highestScore) {
		this.highestScore = highestScore;
	}

	public Integer getGamesOwn() {
		return gamesOwn;
	}

	public void setGamesOwn(Integer gamesOwn) {
		this.gamesOwn = gamesOwn;
	}

	public Integer getGamesLose() {
		return gamesLose;
	}

	public void setGamesLose(Integer gamesLose) {
		this.gamesLose = gamesLose;
	}

	public Integer getGamesTie() {
		return gamesTie;
	}

	public void setGamesTie(Integer gamesTie) {
		this.gamesTie = gamesTie;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getCoinsAvailable() {
		return coinsAvailable;
	}

	public void setCoinsAvailable(Integer coinsAvailable) {
		this.coinsAvailable = coinsAvailable;
	}

	public Integer getCoinsremaining() {
		return coinsremaining;
	}

	public void setCoinsremaining(Integer coinsremaining) {
		this.coinsremaining = coinsremaining;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
