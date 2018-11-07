import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { BddServiceService } from '../services/bdd-service.service';
import { DataService } from '../services/data.service';
import { Music } from '../Interface/music';
import { Playlist } from '../Interface/playlist';
import {MatSidenavModule} from '@angular/material/sidenav';


@Component({
  selector: 'app-music-playlist',
  templateUrl: './music-playlist.component.html',
  styleUrls: ['./music-playlist.component.css']
})

export class MusicPlaylistComponent implements OnInit {
  activePlaylist: boolean = false;
  mesMusiques: Music[] = [];
  @Output() musicUrl = new EventEmitter<any>();
  divActive: number;

  constructor(private bdd: BddServiceService, private dataservice: DataService) { }

  ngOnInit() {
    this.dataservice.showMusicPlaylist(); 
    this.dataservice.showPlaylists();
  }

  // ##### CONTROLEURS DES PANNEAUX
  activer(i) {
    this.activePlaylist = !this.activePlaylist;
    this.divActive = i;
  }

  // ##### Fonction pour afficher seulement les musiques présentes dans les playlists
  comparer(music, playlist) {
    for (var i = 0; i < playlist.idMusic.length; ++i) {
      if (music.id == playlist.idMusic[i]) {
        return true;
      }
    }
  }

  // ##### JOUER LE MORCEAU #####
  playMusic(music) {
    this.musicUrl.emit(music);
  }
}