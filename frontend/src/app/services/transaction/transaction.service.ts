import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  constructor() {}

  getTransactions() {
    return [
      {
        id: 1,
        date: '2023-10-01',
        amount: 100,
        description: 'Payment for services',
      },
      {
        id: 2,
        date: '2023-10-05',
        amount: -50,
        description: 'Refund',
      },
      {
        id: 3,
        date: '2023-10-10',
        amount: 200,
        description: 'Payment for goods',
      },
    ];
  }
}
