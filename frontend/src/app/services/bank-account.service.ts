import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { CreateAccountRequest, BankAccount } from '../model';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class BankAccountService {

  constructor(private httpClient: HttpClient) { }

  create(createAccountRequest: CreateAccountRequest): Observable<BankAccount> {
    return this.httpClient.post<BankAccount>('http://localhost:8080/api/v1/accounts', createAccountRequest, httpOptions);
  }
}
