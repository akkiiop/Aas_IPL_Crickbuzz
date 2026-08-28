package com.akshay.iplcrickbuzz.dto;

public class TeamRequestDTO {
	
	private String teamName;	
	private String shortName;
	private String city;
	private String captain;
	private String homeGround;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCaptain() {
		return captain;
	}
	public void setCaptain(String captain) {
		this.captain = captain;
	}
	public String getHomeGround() {
		return homeGround;
	}
	public void setHomeGround(String homeGround) {
		this.homeGround = homeGround;
	}
	
	
}
