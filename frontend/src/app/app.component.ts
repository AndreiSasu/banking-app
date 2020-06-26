import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  isLoggedIn = false;
  constructor(private tokenStorageService: TokenStorageService, private router: Router) {}

  ngOnInit() {
    this.isLoggedIn = Boolean(this.tokenStorageService.getToken());
    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }
  }

  logout() {
    this.tokenStorageService.clearToken();
  }
}
