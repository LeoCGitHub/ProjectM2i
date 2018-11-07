package com.music;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class PlaylistRepository {

	public HashMap<Integer, PlayListDto> lists = new HashMap<>();

	public PlaylistRepository() {
		PlayListDto playlist = new PlayListDto();
	}	

	public void add(PlayListDto list) {
		lists.put(list.id, list);
	}

	public PlayListDto get(int id) {
		return lists.get(id);
	}

	public ArrayList<PlayListDto> getAllP() {
		System.out.println("lol");
		return new ArrayList<>(lists.values());
	}
}
