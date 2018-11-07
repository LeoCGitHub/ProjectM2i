import { Component, OnInit, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  @Output() searchText = new EventEmitter<any>();
  @Output() searchCriteria = new EventEmitter<any>();

  tabCriteria: string[] = ['Tous', 'Artiste', 'Titre', 'Album', 'Genre'];

  constructor() { }

  ngOnInit() {
  }

  onSearchChange(e){
    this.searchText.emit(e);
    console.log(e);
  }

  onCriteriaChange(e) {
    this.searchCriteria.emit(e);
    console.log(e);
  }



}
