import { Injectable, EventEmitter, Output } from '@angular/core';
import { Music } from '../Interface/music';
import { BddServiceService } from './bdd-service.service';
import { Playlist } from '../Interface/playlist';
import { NgModel, FormsModule, FormGroup, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';


@Injectable({
  providedIn: 'root'
})

export class DataService {
  tabPlaylist: Playlist[];
  tabLibrary: Music[];
  tabMusicPlaylist: Music[];
  musicForm: FormGroup;
  idMusic: number;
  selectedValue: string;
  nomPlaylist: string = "";
  statesearchBar: boolean = false;
  stateplaylistBar: boolean = false;
  idautoPlaylist:number =1;

  constructor(private bdd: BddServiceService, private formBuilder: FormBuilder) { }

  // SHOW LIBRARY
  showMusic() {
    this.tabLibrary = [];
    let getMusic = this.bdd.getMusic();
    getMusic.subscribe((data: any) => {
      for (let i: number = 0; i < data.length; i++) {
        console.log("connection ok");
        this.tabLibrary.push(data[i]);
      }
    }, (error) => {
      console.log('Erreur ! : ' + error);
    });
  }

  // SHOW MUSIC PLAYLISTS
  showMusicPlaylist() {
    this.tabMusicPlaylist = [];
    let getMusic = this.bdd.getMusic();
    getMusic.subscribe((data: any) => {
      for (let i: number = 0; i < data.length; i++) {
        console.log("connection ok");
        this.tabMusicPlaylist.push(data[i]);
      }
    }, (error) => {
      console.log('Erreur ! : ' + error);
    });
  }

  // ###### SUPPRIMER UN MORCEAU DE LA LIBRAIRIE ######
  deleteMusic(music) {
    let deleteM = this.bdd.deleteMusicLibrary(music.id);
    deleteM.subscribe(() => {
      let index = this.tabLibrary.indexOf(music);
      this.tabLibrary.splice(index, 1);
    }, (error) => {
      console.log("error");
    }
    );
  }

  // ###### SUPPRIMER UN MORCEAU D'UNE PLAYLIST ######
  deleteMusicPlaylist(music, playlist) {
    console.log(music.id);
    console.log(playlist.nom);
    console.log(this.tabPlaylist);
    let newPlaylist: Playlist;
    if (music.id != null || undefined) {
      for (let i: number = 0; i < this.tabPlaylist.length; i++) {
        if (playlist.nom === this.tabPlaylist[i].nom) {
          console.log("valeur présente");
          let index = this.tabPlaylist[i].idMusic.indexOf(music.id);
          console.log(index);
          if (index > -1) {
            this.tabPlaylist[i].idMusic.splice(index, 1);
            newPlaylist = this.tabPlaylist[i];
          }
          console.log(newPlaylist);
        } else console.log("valeur non présente");
      }
    } else console.log("id music est null");
    return this.bdd.updatePlaylist(newPlaylist).subscribe(() => {
      console.log("suppression effectué");
      this.showPlaylists();
    },
      (error) => {
        console.log("error");
      }
    );
  }

  // ##### SUPPRESSION MUSIC D'UNE PLAYLIST EN FAISANT UN UPDATE DES PLAYLISTS #####
  deletePlaylist(playlist) {
    this.bdd.deletePlaylist(playlist);
    let index: number = this.tabPlaylist.indexOf(playlist);
    this.tabPlaylist.splice(index, 1);
  }

  // ###### SHOW PLAYLIST ######
  showPlaylists() {
    this.tabPlaylist = [];
    let getPlayliste = this.bdd.getPlaylists();
    getPlayliste.subscribe((data: any) => {
      console.log(data);
      for (var i: number = 0; i < data.length; i++) {
        this.tabPlaylist.push(data[i]);
      }
      console.log(this.tabPlaylist);
    }, (error) => {
      console.log('Erreur ! : ' + error);
    });
  }

  // ###### AJOUT MORCEAU PLAYLIST #####
  addMusicPlaylist() {
    let newPlaylist: Playlist;
    console.log(this.tabPlaylist);

    if (this.idMusic != null || undefined) {      
      for (let i: number = 0; i < this.tabPlaylist.length; i++) {
        if (this.selectedValue === this.tabPlaylist[i].nom) {
          console.log("valeur présente");
          this.tabPlaylist[i].idMusic.push(this.idMusic);
          newPlaylist = this.tabPlaylist[i];
          console.log(newPlaylist);
        } else console.log("valeur non présente");
      }
    } else console.log("id music est null");
    return this.bdd.updatePlaylist(newPlaylist).subscribe(() => {
      console.log("enregistrement terminé");
    },
      (error) => {
        console.log("error");
      }
    );
  }

  // ### AJOUT PLAYLIST ###
  addPlaylists() {
    let playlist = {
      "id": (this.tabPlaylist.length+1),
      "nom": this.nomPlaylist,
      "idMusic": []
    };
    return this.bdd.addPlaylist(playlist).subscribe(() => {
      console.log(playlist.id);
      console.log('ajouté !');
      this.showPlaylists()
    },
      (error) => {
        console.log('Erreur ! : ' + error);
      });
  }

  // AFFICHAGE 
  opensearchBar() {
    if (this.statesearchBar === false) {
      document.getElementById('togglesearch').style.marginTop = "70px";
      document.getElementById('togglesearch').style.transition = "all 500ms";
      this.statesearchBar = true;
    } else {
      document.getElementById('togglesearch').style.marginTop = "0px";
      document.getElementById('togglesearch').style.transition = "all 500ms";
      this.statesearchBar = false;
    }
  }
}
