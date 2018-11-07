import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { MusicLibraryComponent } from './music-library/music-library.component';
import { MusicPlaylistComponent } from './music-playlist/music-playlist.component';
import { SearchPipe } from './pipes/search.pipe';
import { DataService } from './services/data.service';
import { FileUploadModule } from 'ng2-file-upload';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ModalPlaylistComponent } from './modal-playlist/modal-playlist.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MusicLibraryComponent,
    MusicPlaylistComponent,
    SearchPipe,
    ModalPlaylistComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FileUploadModule,
    BrowserAnimationsModule

  ],
  providers: [DataService, SearchPipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
