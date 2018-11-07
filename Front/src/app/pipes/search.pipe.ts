import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchPipe'
})
export class SearchPipe implements PipeTransform {

  transform(music: any, txtSearch: any, cri: any): any {
    if (!txtSearch) return music;
    console.log("critère entré :" + cri);
    return music.filter(function (music) {
      if (cri === "Tous" || cri === "") {
        let all: string = music.titre + music.artiste + music.album + music.genre;
        return all.toLowerCase().includes(txtSearch.toLowerCase());
      }
      else if (cri === "Artiste") {
        return music.artiste.toLowerCase().includes(txtSearch.toLowerCase());
      }
      else if (cri === "Titre") {
        return music.titre.toLowerCase().includes(txtSearch.toLowerCase());
      }
      else if (cri === "Genre") {
        return music.genre.toLowerCase().includes(txtSearch.toLowerCase());
      }
      else if (cri === "Album") {
        return music.album.toLowerCase().includes(txtSearch.toLowerCase());
      }
    });
  }
}
