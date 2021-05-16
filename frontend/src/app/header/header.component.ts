import { Component, OnInit } from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { finalize } from "rxjs/operators";
import { pipe } from 'rxjs';
import { User } from '../models/user.model';
import { TokenService } from "../services/token.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public collapsed = true;
  user: User;  
  
  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private router: Router,
    private token: TokenService)
  {
   this.user = new User();
  }

  toggleCollapsed(): void {
    this.collapsed = !this.collapsed;
  }

  logout() {
     this.authService.authenticated = false;
     this.token.removeToken();
  }

  get authenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  get username(): string {
    return this.authService.username;
  }

  ngOnInit(): void {

  }

}
