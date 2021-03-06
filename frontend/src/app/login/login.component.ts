import { Component, OnInit } from '@angular/core';
import { User } from "../models/user.model";
import { UserMapper } from "../models/user-mapper.model";
import { AuthService } from "../services/auth.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    user: User;
    error: boolean = true;
    
    constructor(
      private router: Router,
      private authService: AuthService)
    {  this.user = new User(); }
    
    processForm(f) {
	if(f.valid) {
	    console.log(this.authService.currentAuthentication);
	    this.authService.authenticate(this.user, 
	    () => {
	         sessionStorage.setItem('token', btoa(this.user.email + ':' + this.user.password));
	    	 this.router.navigate(['/user/' + this.username]);
	    });
	    this.error = false;
	    return false;
	} 	
    }

    get username(): string {
      return this.authService.username;
    }

    ngOnInit(): void {
      sessionStorage.setItem('token', '');    		
    }
}

