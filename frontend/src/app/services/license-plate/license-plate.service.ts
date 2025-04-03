import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LicensePlate } from 'src/app/models/license-plate/license-plate.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LicensePlateService {
  private baseUrl = environment.apiUrl + 'api/license-plates';

  constructor(private http: HttpClient) {}

  addLicensePlate(licensePlate: LicensePlate) {
    return this.http.post(`${this.baseUrl}`, licensePlate);
  }
}
