import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from "../models/user.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl = "/api/user";

  private user: User;

  constructor(private http: HttpClient) {
    this.user = new User();
  }

  getUserByUsername(username: string) {
    return this.http.get<User>(this.usersUrl + "/" + username);
  }

  updateUser(username: string, user: User) {    
    return this.http.put<User>("http://localhost:8080/api/user/" + username + "/edit", user, {observe: 'response' });
  }

  saveUser(user: User) {
    return this.http.post<User>(this.usersUrl + "/registration", user, { observe: 'response' });
  }

  resetUserPass(user: any) {
    return this.http.post(this.usersUrl + "/resetPassword", user, { observe: 'response' });
  }

  updatePass(user: User) {
    return this.http.post<User>(this.usersUrl + "/updatePassword", user, { observe: 'response' });
  }

  savePass(user: User) {
    return this.http.post<User>(this.usersUrl + "/savePassword", user, { observe: 'response' });
  }

}


