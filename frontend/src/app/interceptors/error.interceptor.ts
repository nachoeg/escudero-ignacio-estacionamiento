import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { AlertService } from '../services/alert/alert.service';
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private router: Router,
    private alertService: AlertService,
    private authService: AuthService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.alertService.showAlert(
            'Tu sesión ha expirado. Por favor, inicia sesión nuevamente.',
            'danger'
          );
          this.authService.logout();
          this.router.navigate(['/iniciar-sesion']);
        } else if (error.status === 403) {
          this.alertService.showAlert(
            'No tienes permiso para realizar esta acción.',
            'danger'
          );
        } else if (error.status === 500) {
          this.alertService.showAlert(
            'Ocurrió un error en el servidor. Por favor, intenta más tarde.',
            'danger'
          );
        } else {
          this.alertService.showAlert(`Error: ${error.message}`, 'danger');
        }
        return throwError(() => error);
      })
    );
  }
}
