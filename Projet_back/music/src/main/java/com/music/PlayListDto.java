package com.music;

import java.util.ArrayList;

public class PlayListDto {
	public int id;
	public String nom;
	public ArrayList<Integer> idMusic = new ArrayList();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PlayListDto other = (PlayListDto) obj;
		if (id != other.id) return false;
		return true;
	}
}
