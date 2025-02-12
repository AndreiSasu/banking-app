import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BankAccountService } from '../services/bank-account.service';
import { CreateAccountRequest, BankAccount } from '../model';

@Component({
  selector: 'app-bank-account',
  templateUrl: './bank-account.component.html',
  styleUrls: ['./bank-account.component.css']
})
export class BankAccountComponent implements OnInit {

  accountTypes = ['SAVINGS'];
  currencies = ['USD'];

  isSubmitted: boolean;
  bankAccount: BankAccount;
  error: any;

  createBankAccountForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private bankAccountService: BankAccountService) { }

  ngOnInit() {
    this.createBankAccountForm = this.formBuilder.group({
      accountType: ['', Validators.required],
      currency: ['', Validators.required]
    });
  }

  onSubmit() {
    this.isSubmitted = true;

    if (this.createBankAccountForm.invalid) {
      return;
    }

    const createBankAccountRequest = new CreateAccountRequest();
    createBankAccountRequest.accountType = this.f.accountType.value;
    createBankAccountRequest.currency = this.f.currency.value;

    this.bankAccountService.create(createBankAccountRequest).subscribe(bankAccount => {
      this.bankAccount = bankAccount;
    }, httpErrorResponse => {
      this.error = httpErrorResponse.error;
    });
  }

  get f() { return this.createBankAccountForm.controls; }

  changeAccountType(event) {
    this.createBankAccountForm.controls.accountType.setValue(event.target.value, {
      onlySelf: true
    });
  }

  changeCurrency(event) {
    this.createBankAccountForm.controls.currency.setValue(event.target.value, {
      onlySelf: true
    });
  }
}
