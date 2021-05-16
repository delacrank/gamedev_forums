import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from "../models/user.model";
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { TokenService } from "../services/token.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _authenticated;

  user: User;

  constructor(
     private router: Router,
     private http: HttpClient,
     private token: TokenService) {
     this.user = new User();
  }

  set username(username: string) {
    this.user.username = username;
  }

  get username(): string {
    return this.user.username;
  }

  authenticate(credentials): Observable<any> {
    console.log('generating token');
    return this.http.post("http://localhost:8080/api/user/login", credentials ).pipe(
      catchError((error: HttpErrorResponse) => {
          return throwError(error || 'Error');
	})
      );
  }

  logout() {
    this.authenticated = false;
    console.log(this.authenticated);
  }

  get authenticated(): boolean {
    return this._authenticated;
  }

  set authenticated(val: boolean) {
    this._authenticated = val;
  }

  isAuthenticated() {
    return this.token.getToken() != null;
  }
}
