import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { LoginRequest, LoginSuccessfulEvent } from '../model';
import { TokenStorageService } from '../services/token-storage.service';
import { EventService } from '../services/event.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted: boolean;

  error: any;

  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private router: Router, private tokenStorageService: TokenStorageService,
              private eventService: EventService<LoginSuccessfulEvent>) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    console.log('submitting');
    this.submitted = true;
    const loginRequest = new LoginRequest();
    loginRequest.userName = this.f.username.value;
    loginRequest.password = this.f.password.value;

    this.authService.login(loginRequest)
      .subscribe(
        jwtToken => {
          this.tokenStorageService.saveToken(jwtToken.accessToken);
          this.router.navigate(['/bank-account']);
          this.eventService.getSubject().next(new LoginSuccessfulEvent());
        },
        error => {
          this.error = error;
          this.submitted = false;
    });
  }
}
