import { Component, OnInit } from '@angular/core';
import { User } from "../models/user.model";
import { UserMapper } from "../models/user-mapper.model";
import { AuthService } from "../services/auth.service";
import { TokenService } from "../services/token.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    user: User;
    public error: any;
    
    constructor(
      private router: Router,
      private authService: AuthService,
      private token: TokenService)
    {  this.user = new User(); }
    
    processForm(f) {
	if(f.valid) {

	    this.authService.authenticate(this.user).subscribe(
	      data => {
		this.error = null;
	        this.token.saveToken(data.token);
		this.authService.authenticated = true;
		console.log(this.authService.authenticated); 
		//this.router.navigate(['/user/' + this.user.username]);
		 
  	      },
	      err => {
	        console.log(err.error.message);
		this.error = err.error.message;
	      }
	    );
	} 	
    }


    ngOnInit(): void {
       		
    }
}