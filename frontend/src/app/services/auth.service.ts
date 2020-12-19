import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from "../models/user.model";
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private usersUrl = "/api/user";
  private _authenticated = false;
  private authenticatedSource = new BehaviorSubject<boolean>(false);
  currentAuthentication = this.authenticatedSource.asObservable();

  user: User;

  constructor(
     private router: Router,
     private http: HttpClient) {
     this.user = new User();
  }

  set username(username: string) {
    this.user.username = username;
  }

  get username(): string {
    return this.user.username;
  }

  authenticate(credentials, callback) {
     const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.email + ':' + credentials.password) } : {});

      this.http.get(this.usersUrl, {headers: headers}).subscribe(

      response => {
        if(response !== null) {
          if(response['name']) {	    
            this.authenticated = true;
	    this.changeSource(true);
    	    this.username = response['principal'].username;
          } else {
            this.authenticated = false;
	    this.changeSource(false);
          }
        }
        return callback && callback();
     });
   }

  get authenticated(): boolean {
    return this._authenticated;
  }

  set authenticated(val: boolean) {
    this._authenticated = val;
  }

  changeSource(val: boolean) {
    this.authenticatedSource.next(val);
  }
}
