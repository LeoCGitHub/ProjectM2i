import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Music } from '../Interface/music';
import { Playlist } from '../Interface/playlist';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BddServiceService {

  constructor(private http: HttpClient) { }


  getPlaylists() {
    let url: string = 'http://localhost:8080/playlists';
    return this.http.get(url);
  }

  getMusic() {
    let url: string = 'http://localhost:8080/music';
    return this.http.get<Music>(url);
  }

  addMusicPlaylist(playlist: Playlist) {
    let url: string = "http://localhost:8080/playlists";
    return this.http.post<Music>(url, playlist);
  }

  addMusicLibrary(music: Music) {
    let url: string = "http://localhost:8080/music";
    return this.http.post<Music>(url, music);
  }

  addPlaylist(playlist) {
    return this.http
      .post('http://localhost:8080/playlists', playlist);
  }

  deleteMusicLibrary(id: number) {
    let url: string = "http://localhost:8080/music/" + id;
    console.log(id);
    return this.http.delete<Music>(url);
  }

  deletePlaylist(playlist) {
    this.http
      .delete('http://localhost:8080/playlists/' + playlist.id)
      .subscribe(
        () => {
          console.log('Supprimé !');
        },
        (error) => {
          console.log('Erreur ! : ' + error);
        });
  }

  updatePlaylist(playlist: Playlist): Observable<Playlist> {
    let url: string = 'http://localhost:8080/playlists/' + playlist.id;
    return this.http.put<Playlist>(url, playlist);
  }

  addFile(file) {
    return this.http
      .post('http://localhost:8080/file', file);
  }
}