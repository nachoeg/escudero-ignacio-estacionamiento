import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(
    this.isAuthenticated()
  );
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: { phone: string; password: string }) {
    return this.http
      .post<{ token: string }>(
        `${environment.apiUrl}api/auth/login`,
        credentials
      )
      .pipe(
        tap((response) => {
          if (response.token) {
            this.saveToken(response.token);
            this.isAuthenticatedSubject.next(true);
          }
        })
      );
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.isAuthenticatedSubject.next(false);
  }

  isAuthenticated() {
    const token = this.getToken();
    return token !== null;
  }
}
