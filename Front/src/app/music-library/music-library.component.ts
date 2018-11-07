import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Music } from '../Interface/music';
import { BddServiceService } from '../services/bdd-service.service';
import { NgModel, FormsModule, FormGroup, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Playlist } from '../Interface/playlist';
import { DataService } from '../services/data.service';
import { FileUploader } from 'ng2-file-upload';

// const URL = '/api/';
const URL = 'assets/Images/';

@Component({
  selector: 'app-music-library',
  templateUrl: './music-library.component.html',
  styleUrls: ['./music-library.component.css']
})

export class MusicLibraryComponent implements OnInit {
  musicForm: FormGroup;
  selectedValue: string;
  txtSearch: string = "";
  criSearch: string = "";
  statesearchBar: boolean = false;

  @Output() musicUrl = new EventEmitter<any>();

  constructor(private bdd: BddServiceService, private formBuilder: FormBuilder, private dataservice: DataService) { }

  ngOnInit() {
    this.dataservice.showMusic();
    this.initFormMusic();
  }

  // Lance le morceau choisit sur le lecteur A FAIRE
  // playMusic(obj) {
  // }

  // AJOUTER UN MORCEAU A LA BIBLIOTHEQUE / AJOUTER AUX SERVICES
  addMusic() {
    let formValue: Music = this.musicForm.value;
    if (formValue.image === "") formValue.image = "default_cover.png";

    if (formValue.id !== 0) formValue.id = this.dataservice.tabLibrary.length+20;
    const url = "./assets/Media/";
    let tempimage: string = url + formValue.image.replace(/^.*\\/, "");
    let tempfile: string = url + formValue.file.replace(/^.*\\/, "");
    formValue.image = tempimage;
    formValue.file = tempfile;
    return this.bdd.addMusicLibrary(formValue).subscribe(() => {
      console.log("enregistrement terminé");
      this.dataservice.showMusic();
    },
      (error) => {
        console.log("error");
      }
    );
  }

  // SHOW PLAYLIST POUR BOUTON ; EN SERVICE A VOIR !
  showPlaylists(music) {
    this.dataservice.idMusic = music.id;
    this.dataservice.showPlaylists();
  }

  // Ajoute le morceau à la playlist EN SERVICE A VOIR !
  addMusicPlaylist() {
    this.dataservice.selectedValue = this.selectedValue;
    this.dataservice.addMusicPlaylist();
  }

  // Modifier les données du morceau A FAIRE
  // modifyMusic() {
  // }

  // #### UPLOAD FILE #####
  uploadFile(e) {
    console.log(e.target.files[0])
    console.log(this.musicForm);
    var formData = new FormData();
    formData.append("file", e.target.files[0]);
    this.bdd.addFile(formData).subscribe(() => {
      console.log("file envoyé")
    },
      (error) => {
        console.log("error");
      }
    );
  }

  // INITIALISATION DU FORMULAIRE MUSIQUE
  initFormMusic() {
    this.musicForm = this.formBuilder.group({
      id: '',
      titre: '',
      artiste: '',
      album: '',
      genre: '',
      annee: '',
      image: '',
      file: ''
    });
  }


  // ##### LECTURE FICHIER  #####
  playMusic(music) {
    this.musicUrl.emit(music);
  }

  // FONCTION DE GESTION DU PIPE
  search(txt) {
    this.txtSearch = txt;
  }

  criteria(cri) {
    this.criSearch = cri;
    console.log(this.criSearch);
  }

  previewFile(e) {
    console.log(e);
  }
}