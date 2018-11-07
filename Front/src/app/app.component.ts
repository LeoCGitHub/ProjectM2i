import { Component } from '@angular/core';
import { Music } from './Interface/music';
import { DataService } from './services/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Front';
  musicUrl: string = "";

  constructor(private dataservice: DataService) { }


  changeMusicUrl(e) {
    this.musicUrl = e.file;
  }



}