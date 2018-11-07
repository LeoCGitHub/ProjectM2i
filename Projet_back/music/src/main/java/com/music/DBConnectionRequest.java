package com.music;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBConnectionRequest<T> {
	@Autowired
	MusicRepository repo;
	@Autowired
	PlaylistRepository plrepo;

//	##### CONNECTION #####	
	public Connection getConnection() throws SQLException {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/AppMusic", "XXXX", "XXXXX");
			System.out.println(
					c.getMetaData().getDatabaseProductName() + " " + c.getMetaData().getDatabaseProductVersion());
			c.setAutoCommit(false);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

//	##### GET ALL SQL #####
	public ArrayList<MusicDto> getAll() throws SQLException {
		repo.musicLists.clear();
		Connection c = getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(String.format("SELECT * FROM t_music"));
		while (rs.next()) {
			MusicDto music = new MusicDto();
			music.id = rs.getInt("id");
			music.titre = rs.getString("titre");
			music.artiste = rs.getString("artiste");
			music.album = rs.getString("album");
			music.genre = rs.getString("genre");
			music.annee = rs.getInt("annee");
			music.image = rs.getString("image");
			music.file = rs.getString("file");
			repo.add(music);
		}
		System.out.println("Opened database successfully");
		c.commit();
		c.close();
		return new ArrayList<>(repo.musicLists.values());
	}

//	##### AJOUTE MUSIQUE BDD SQL #####
	public void addMusic(MusicDto music) throws SQLException {
		try {
			Connection c2 = getConnection();
			c2.setAutoCommit(false);
			PreparedStatement stmt = c2.prepareStatement(
					"INSERT INTO t_music(titre, artiste, album, genre, annee, image, file, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			stmt.setString(1, music.titre);
			stmt.setString(2, music.artiste);
			stmt.setString(3, music.album);
			stmt.setString(4, music.genre);
			stmt.setInt(5, music.annee);
			stmt.setString(6, music.image);
			stmt.setString(7, music.file);
			stmt.setInt(8, music.id);
			stmt.executeUpdate();
			c2.commit();
			c2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	##### SUPPRESSION MUSIQUE #####
	public void deleteMusic(Integer id) throws SQLException {
		try {
			Connection c3 = getConnection();
			c3.setAutoCommit(false);
			PreparedStatement stmt = c3.prepareStatement("DELETE FROM t_music WHERE id=?;");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			repo.musicLists.remove(id);
			c3.commit();
			c3.close();
			System.out.println("Element supprimé");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

//	##### SHOW PLAYLISTS #####
	public ArrayList<PlayListDto> getAllPlaylists() throws SQLException {
		plrepo.lists.clear();
		Connection c = getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(String.format("SELECT * FROM t_playlist"));
		while (rs.next()) {
			PlayListDto playlist = new PlayListDto();
			playlist.id = rs.getInt("id");
			playlist.nom = rs.getString("nom");
			playlist.idMusic = getMusicFromPlayList(playlist.id);
			plrepo.add(playlist);
			System.out.println(playlist.toString());
		}
		System.out.println("Opened database successfully");
		c.commit();
		c.close();
		System.out.println(plrepo.lists.values());
		return new ArrayList<>(plrepo.lists.values());
	}

//	##### AJOUTER UNE PLAYLIST #####
	public void addPlayList(PlayListDto playlist) throws SQLException {
		try {
			Connection c2 = getConnection();
			c2.setAutoCommit(false);
			PreparedStatement stmt = c2.prepareStatement("INSERT INTO t_playlist(nom, id) VALUES (?, ?);");
			stmt.setString(1, playlist.nom);
			stmt.setInt(2, playlist.id);
			stmt.executeUpdate();
			System.out.println(playlist.nom);
			c2.commit();
			c2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	##### AJOUTER UNE PLAYLIST #####
	public void addMusicPlayList(PlayListDto playlist) throws SQLException {
		try {
			Object[] myIntArray = new Object[playlist.idMusic.size()];
			for (int i = 0; i < playlist.idMusic.size(); i++) {
				myIntArray[i] = playlist.idMusic.get(i);
			}

			Connection c2 = getConnection();
			c2.setAutoCommit(false);
			PreparedStatement stmt = c2.prepareStatement("UPDATE t_playlist SET musics = ? WHERE id = ?;");
			Array array = c2.createArrayOf("integer", myIntArray);
			stmt.setArray(1, array);
			stmt.setInt(2, playlist.id);
			stmt.executeUpdate();
			System.out.println(playlist.nom);
			c2.commit();
			c2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	##### DELETE PLAYLIST #####
	public void deletePlaylist(Integer id) throws SQLException {
		try {
			Connection c3 = getConnection();
			c3.setAutoCommit(false);
			PreparedStatement stmt = c3.prepareStatement("DELETE FROM t_playlist WHERE id=?;");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			plrepo.lists.remove(id);
			System.out.println(plrepo.lists);
			c3.commit();
			c3.close();
			System.out.println("Element supprimé");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	// Ajouter les id des musiques dans le tableau idMusic du PlayListRepository
	public ArrayList<Integer> getMusicFromPlayList(int id) throws SQLException {
		ArrayList<Integer> listofMusicOfPlaylist = new ArrayList<>();
		System.out.println(id);
		Connection c = getConnection();
		PreparedStatement stmt = c.prepareStatement("SELECT musics FROM t_playlist WHERE id = " + id + ";");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Array musics = rs.getArray("musics");
			if (musics != null) {
				Integer[] str_musics = (Integer[]) musics.getArray();
				// il faut transformer l'array d'objet en int[]
				int[] str_musics2 = new int[str_musics.length];
				for (int i = 0; i < str_musics.length; i++) {
					str_musics2[i] = str_musics[i];
					listofMusicOfPlaylist.add(str_musics2[i]);
				}
			} else
				listofMusicOfPlaylist = new ArrayList<>();
		}
		System.out.println("rs est null");
		c.commit();
		c.close();
		return listofMusicOfPlaylist;
	}

}
