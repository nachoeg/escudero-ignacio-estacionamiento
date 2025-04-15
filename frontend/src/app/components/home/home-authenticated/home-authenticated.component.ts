import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { User } from 'src/app/models/user/user.model';
import { AlertService } from 'src/app/services/alert/alert.service';
import { ParkingSessionService } from 'src/app/services/parking-session/parking-session.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-home-authenticated',
  templateUrl: './home-authenticated.component.html',
  styleUrls: ['./home-authenticated.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
})
export class HomeAuthenticatedComponent implements OnInit {
  loading = true;
  error = false;
  userData: User | null = null;
  selectedLicensePlate = '';
  pesos = '0';
  centavos = '00';

  constructor(
    private userService: UserService,
    private parkingSessionService: ParkingSessionService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.refreshUserData();
  }

  refreshUserData() {
    this.loading = true;
    this.userService.getUserData().subscribe({
      next: (response) => {
        this.userData = response;
        console.log('User data:', this.userData);
        this.pesos = Math.trunc(response.balance).toLocaleString();
        this.centavos = (response.balance % 1).toFixed(2).split('.')[1];
        this.error = false;
        this.loading = false;
      },
      error: (error) => {
        this.error = true;
        this.loading = false;
      },
    });
  }

  startParkingSession() {
    if (this.selectedLicensePlate == '') {
      this.alertService.showAlert(
        'Por favor selecciona una patente',
        'warning'
      );
      return;
    }

    this.parkingSessionService
      .startSession(this.selectedLicensePlate)
      .subscribe({
        next: (response) => {
          this.refreshUserData();
        },
        error: (error) => {
          console.error('Error al iniciar estacionamiento', error);
        },
      });
  }

  stopParkingSession() {
    if (this.userData?.parkingSession?.id) {
      this.parkingSessionService
        .stopSession(this.userData.parkingSession.id)
        .subscribe({
          next: (response) => {
            this.alertService.showAlert(
              `Estacionamiento finalizado`,
              'success'
            );
            this.refreshUserData();
          },
          error: (error) => {
            console.error('Error al finalizar estacionamiento', error);
          },
        });
    }
  }
}
