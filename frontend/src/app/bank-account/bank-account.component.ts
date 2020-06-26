import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-bank-account',
  templateUrl: './bank-account.component.html',
  styleUrls: ['./bank-account.component.css']
})
export class BankAccountComponent implements OnInit {

  accountTypes = ['SAVINGS'];
  currencies = ['USD'];

  isSubmitted: boolean;

  createBankAccountForm: FormGroup;
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.createBankAccountForm = this.formBuilder.group({
      accountType: ['', Validators.required],
      currency: ['', Validators.required]
    });
  }

  onSubmit() {
    console.log('submitted');
    this.isSubmitted = true;
  }

  get f() { return this.createBankAccountForm.controls; }

  changeAccountType(event) {
    console.log(this.createBankAccountForm.controls);
    this.createBankAccountForm.controls.accountType.setValue(event.target.value, {
      onlySelf: true
    });
  }

  changeCurrency(event) {
    console.log(this.createBankAccountForm.controls);
    this.createBankAccountForm.controls.currency.setValue(event.target.value, {
      onlySelf: true
    });
  }
}
