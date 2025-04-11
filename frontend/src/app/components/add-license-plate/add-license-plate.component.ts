import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LicensePlate } from 'src/app/models/license-plate/license-plate.model';
import { AlertService } from 'src/app/services/alert/alert.service';
import { LicensePlateService } from 'src/app/services/license-plate/license-plate.service';

@Component({
  selector: 'app-add-license-plate',
  templateUrl: './add-license-plate.component.html',
  styleUrls: ['./add-license-plate.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule],
})
export class AddLicensePlateComponent implements OnInit {
  plate = '';
  errorMessage = '';

  constructor(
    private licensePlateService: LicensePlateService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {}

  addLicensePlate() {
    if (!/^[A-Z]{3}[0-9]{3}$|^[A-Z]{2}[0-9]{3}[A-Z]{2}$/.test(this.plate)) {
      this.errorMessage = 'Formato de placa inválido.';
      return;
    }

    this.errorMessage = '';

    const licensePlate: LicensePlate = {
      plate: this.plate,
    };

    this.licensePlateService.addLicensePlate(licensePlate).subscribe({
      next: (response) => {
        console.log(response);
        this.plate = '';
        this.alertService.showAlert('¡Placa añadida exitosamente!', 'success');
      },
      error: (error) => {
        console.log(error);
        const errorMessage =
          error.error?.message || 'Error al añadir la placa.';
        this.errorMessage = error.status + ' - ' + errorMessage;
      },
    });
  }
}
