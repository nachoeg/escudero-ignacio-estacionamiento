import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Transaction } from 'src/app/models/transaction/transaction.model';
import { AccountService } from 'src/app/services/account/account.service';

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css'],
  standalone: true,
  imports: [CommonModule],
})
export class TransactionHistoryComponent implements OnInit {
  loading = true;
  error = false;
  transactions: Transaction[] = [];

  constructor(private accountService: AccountService) {}

  refresh() {
    this.loading = true;
    this.accountService.getTransactionHistory().subscribe({
      next: (data) => {
        console.log(data);
        this.transactions = data;
        this.loading = false;
        this.error = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      },
    });
  }

  ngOnInit(): void {
    this.refresh();
  }
}
