export class User {
  id: number;
  email: string;
  phone: string;
  password: string;
  balance: number;
  licensePlates: string[];

  constructor(
    id: number,
    email: string,
    phone: string,
    password: string,
    balance: number,
    licensePlates: string[]
  ) {
    this.id = id;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.balance = balance;
    this.licensePlates = licensePlates;
  }
}
