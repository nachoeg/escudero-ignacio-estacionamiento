import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';

import { AuthInterceptor } from './interceptors/auth.interceptor';
import { AuthGuard } from './guards/auth.guard';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AddCreditComponent } from './components/add-credit/add-credit.component';
import { TransactionHistoryComponent } from './components/transaction-history/transaction-history.component';
import { HomeComponent } from './components/home/home.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AlertComponent } from './components/alert/alert.component';
import { AddLicensePlateComponent } from './components/add-license-plate/add-license-plate.component';
import { ErrorInterceptor } from './interceptors/error.interceptor';

const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'cargar-credito',
    component: AddCreditComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'movimientos',
    component: TransactionHistoryComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'agregar-patente',
    component: AddLicensePlateComponent,
    canActivate: [AuthGuard],
  },
  { path: 'iniciar-sesion', component: LoginComponent },
  { path: 'registrarse', component: RegisterComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    NavbarComponent,
    AlertComponent,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
  exports: [RouterModule],
})
export class AppModule {}
