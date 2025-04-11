import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
}
