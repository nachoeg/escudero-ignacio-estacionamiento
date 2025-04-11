import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

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

  constructor() {}

  refresh() {
    // this.loading = true;
    // // Simulate an API call
    // setTimeout(() => {
    //   this.loading = false;
    //   this.error = false;
    // }, 2000);
  }

  ngOnInit(): void {}
}
