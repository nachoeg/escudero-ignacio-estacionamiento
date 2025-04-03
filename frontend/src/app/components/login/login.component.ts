import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert/alert.service';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
})
export class LoginComponent implements OnInit {
  phone = '';
  password = '';
  errorMessage = '';

  constructor(
    public authService: AuthService,
    private router: Router,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {}

  login() {
    const user = {
      phone: this.phone,
      password: this.password,
    };
    console.log(user);
    this.authService.login(user).subscribe({
      next: (response) => {
        console.log(response);
        this.router.navigate(['/']);
        if (response) {
          this.alertService.showAlert('¡Inicio de sesión exitoso!', 'success');
          this.errorMessage = '';
        }
      },
      error: (error) => {
        console.log(error);
        const errorMessage = error.error?.message || 'Error al iniciar sesión.';
        this.errorMessage = error.status + ' - ' + errorMessage;
      },
    });
  }
}
