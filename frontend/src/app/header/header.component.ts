import { Component, OnInit } from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { finalize } from "rxjs/operators";
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pipe } from 'rxjs';
import { User } from '../models/user.model';

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
    private route: ActivatedRoute)
  {
   this.user = new User();
   this.authService.authenticate(undefined, undefined);
  }

  toggleCollapsed(): void {
    this.collapsed = !this.collapsed;
  }

  logout() {
     this.http.post('http://localhost:8080/logout', {}).pipe(
     finalize(() => {
	 this.authService.authenticated = false;
	 this.authService.changeSource(false);
	 this.router.navigate(['/login']);
     })).subscribe();
  }

  get authenticated(): boolean {
    return this.authService.authenticated;
  }

  get username(): string {
    return this.authService.username;
  }

  ngOnInit(): void {

  }

}
