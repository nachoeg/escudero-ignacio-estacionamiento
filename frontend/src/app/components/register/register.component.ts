import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert/alert.service';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule],
})
export class RegisterComponent implements OnInit {
  email = '';
  password = '';
  phone = '';
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {}

  register() {
    if (!this.email || !this.password || !this.phone) {
      this.errorMessage = 'Por favor, completa todos los campos.';
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.email)) {
      this.errorMessage = 'El correo electrónico no es válido.';
      return;
    }

    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(this.phone)) {
      this.errorMessage = 'El número de teléfono no es válido.';
      return;
    }

    const user = {
      email: this.email,
      password: this.password,
      phone: this.phone,
    };

    this.authService.register(user).subscribe({
      next: (response) => {
        console.log(response);
        this.router.navigate(['/iniciar-sesion']);
        this.alertService.showAlert(
          '¡Registro exitoso! Ahora puedes iniciar sesión.',
          'success'
        );
      },
      error: (error) => {
        console.log(error);
        this.errorMessage =
          error.error?.message || 'Error al registrar usuario.';
      },
    });
  }
}
