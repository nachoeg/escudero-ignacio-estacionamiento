import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AccountService } from 'src/app/services/account/account.service';
import { AlertService } from 'src/app/services/alert/alert.service';

@Component({
  selector: 'app-add-credit',
  templateUrl: './add-credit.component.html',
  styleUrls: ['./add-credit.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule],
})
export class AddCreditComponent implements OnInit {
  amount: number | null = null;
  errorMessage = '';

  constructor(
    private accountService: AccountService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {}

  addCredit() {
    if (this.amount === null) {
      this.errorMessage = 'Por favor ingrese un monto.';
      return;
    }

    if (this.amount < 100) {
      this.errorMessage = 'El monto minimo es 100.';
      return;
    }

    this.errorMessage = '';

    this.accountService.addCredit(this.amount).subscribe({
      next: (response) => {
        console.log(response);
        this.alertService.showAlert(
          '¡Crédito añadido exitosamente!',
          'success'
        );
        this.amount = null;
      },
      error: (error) => {
        console.log(error);
        const errorMessage =
          error.error?.message || 'Error al añadir el crédito.';
        this.errorMessage = error.status + ' - ' + errorMessage;
      },
    });
  }
}
