export class LoginRequest {
  userName: string;
  password: string;
}

export class CreateAccountRequest {
    accountType: string;
    currency: string;
}

export class BankAccount {
    iban: string;
}

export class LoginSuccessfulEvent {
    constructor() {}
}

