package com.music;

public class MusicDto {
	public int id;
	public String titre;
	public String artiste;
	public String album;
	public String genre;
	public int annee;
	public String image;
	public String file;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicDto other = (MusicDto) obj;
		if (id != other.id)
			return false;
		return true;
	}



}