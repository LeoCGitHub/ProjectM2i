package com.music;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicRepository {

	public HashMap<Integer, MusicDto> musicLists = new HashMap<>();

	public MusicRepository() {
		MusicDto music = new MusicDto();
		music.id = 1;
		music.titre = "titre";
		music.artiste = "artiste";
		music.album = "album";
		music.genre = "genre";
		music.annee = 2008;
		music.image = "url img";
		music.file = "url mp3";
	}

	public void add(MusicDto music) {
		musicLists.put(music.id, music);
	}
	
	public MusicDto get(Integer id) {
		return musicLists.get(id);
	}	

}
