import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  login(loginRequest: LoginRequest): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/v1/login', loginRequest, httpOptions);
  }
}
