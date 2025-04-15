import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from 'src/app/models/transaction/transaction.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private baseUrl = environment.apiUrl + 'api/accounts';

  constructor(private http: HttpClient) {}

  addCredit(amount: number): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/add-credit`, { amount });
  }

  getTransactionHistory(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.baseUrl}/transactions-history`);
  }
}
