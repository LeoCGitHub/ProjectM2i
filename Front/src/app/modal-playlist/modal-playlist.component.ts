import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';


@Component({
  selector: 'app-modal-playlist',
  templateUrl: './modal-playlist.component.html',
  styleUrls: ['./modal-playlist.component.css']
})
export class ModalPlaylistComponent implements OnInit {

  constructor(private dataservice: DataService) { }

  ngOnInit() {
  }

}
