import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ParkingSessionService {
  private baseUrl = environment.apiUrl + 'api/parking-sessions';

  constructor(private http: HttpClient) {}

  startSession(plate: string): Observable<any> {
    return this.http.post(this.baseUrl, { plate });
  }

  stopSession(sessionId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/${sessionId}/stop`, {});
  }
}
