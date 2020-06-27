import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './services/token-storage.service';
import { Router } from '@angular/router';
import { EventService } from './services/event.service';
import { LoginSuccessfulEvent } from './model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  isLoggedIn = false;
  constructor(private tokenStorageService: TokenStorageService, private router: Router,
              private eventService: EventService<LoginSuccessfulEvent>) {}

  ngOnInit() {
    this.isLoggedIn = Boolean(this.tokenStorageService.getToken());
    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }
    this.eventService.getSubject().subscribe(event => {
      this.isLoggedIn = true;
    });
  }

  logout() {
    this.tokenStorageService.clearToken();
  }
}
