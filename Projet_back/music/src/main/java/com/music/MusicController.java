package com.music;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MusicController {
	@Autowired
	DBConnectionRequest database;
	@Autowired
	MusicRepository repo;
	@Autowired
	PlaylistRepository plrepo;

	// Afficher toutes les musiques
	@GetMapping("/music")
	public JsonNode getAllMusic() throws SQLException {
		ArrayList<MusicDto> list = database.getAll();
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rootNode = mapper.createArrayNode();
		for (MusicDto music : list) {
			// System.out.println(music.titre);
			ObjectNode objectnode = rootNode.addObject();
			objectnode.put("id", music.id);
			objectnode.put("titre", music.titre);
			objectnode.put("artiste", music.artiste);
			objectnode.put("album", music.album);
			objectnode.put("genre", music.genre);
			objectnode.put("annee", music.annee);
			objectnode.put("image", music.image);
			objectnode.put("file", music.file);
			System.out.println(objectnode.asText());
		}
		System.out.println("rootNode json was returned from getAll MusicController");
		return rootNode;
	}

//	 Ajout d'une musique
	@PostMapping("/music")
	public void createMusic(@RequestBody MusicDto music) throws Exception {
//		if (repo.get(music.id) != null) { // verifie si la musique déjà existe via l'id
//			throw new Exception("Music already existing");
//		}
		repo.add(music);
		database.addMusic(music); // ajoute la musique à la BDD
	}

	@DeleteMapping("/music/{id}")
	public void deleteMusic(@PathVariable("id") int id) throws Exception {
		if (repo.get(id) == null) {
			throw new Exception("Music doesn't exist"); // permet de tester si l'id de la musique n'est pas nul
		}
		database.deleteMusic(id); // si la musique n'est pas nulle (vide), alors "supprimer la musique",
	}

	// UPDATE MUSIC PAS ENCORE FAIT EN ANGULAR
//	@PutMapping("/playlists/{id}")
//	public int updatePLaylist(@RequestBody PlayListDto playlist, @PathVariable("id") int id) throws Exception {
//		if (repo.get(music.id) == null) { // verifie que la liste avec l'id existe
//			throw new Exception("Music doesn't exist");
//		}
//		repo.add(music); // ajoute la musique modifiée (ou non) à la hashmap return list.id;
//		repo.addMusic(music); 
//		return music.id;
//	}


//	##### PLAYLIST #####
	@GetMapping("/playlists")
	public ArrayList<PlayListDto> getAllPlaylists() throws SQLException {
		return database.getAllPlaylists();
	}

	@PostMapping("/playlists")
	public void createPlayList(@RequestBody PlayListDto playList) throws Exception {
		if (plrepo.get(playList.id) != null) { // verifie si la musique déjà existe via l'id
			throw new Exception("PlayList already existing");
		}
		plrepo.add(playList);
		database.addPlayList(playList); // ajoute la musique à la BDD
	}

	@DeleteMapping("/playlists/{id}")
	public void deletePlaylist(@PathVariable("id") int id) throws Exception {
		if (plrepo.get(id) == null) {
			throw new Exception("Music doesn't exist"); // permet de tester si l'id de la musique n'est pas nul
		}
		database.deletePlaylist(id); // si la musique n'est pas nulle (vide), alors "supprimer la musique",
	}
	
	@PutMapping("/playlists/{id}")
	public void updatePlaylist(@RequestBody PlayListDto playList, @PathVariable("id") int id) throws Exception {
		if (plrepo.get(id) == null) {
			throw new Exception("Music doesn't exist"); 
		}
		plrepo.add(playList);
		database.addMusicPlayList(playList);
	}
	
	//second put ?
	
//	##### récupération du fichier #####
	@RequestMapping(value = "/file", consumes = { "multipart/form-data" }, method = RequestMethod.POST)
	public void upload(MultipartHttpServletRequest request) throws IOException {
		final String UPLOAD_DIR = System.getProperty("user.home") + "/Desktop/M2i v2/Front/src/assets/Media";
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());

		File uploadDir = new File(UPLOAD_DIR);
		uploadDir.mkdirs();

		StringBuilder sb = new StringBuilder();
		String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();

		byte[] bytes = file.getBytes();
		Path path = Paths.get(uploadFilePath);
		Files.write(path, bytes);
		sb.append(uploadFilePath);
		System.out.println(sb.toString());
	}
	
	
}
