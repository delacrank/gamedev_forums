import { Component, OnInit } from '@angular/core';
import { User } from "../models/user.model";
import { Register } from "../models/register.model";
import { UserService } from "../services/user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public error: any;
  public message: any;
  user: User;
  register: Register;
  
  constructor(private userService: UserService) { this.user = new User(); }

  processForm(f) {
         if(f.valid) {
	   console.log("username: " + this.user.username + " email: " + this.user.email + " password: " + this.user.password + "matching pass: " + this.user.matchingPassword);
	   
	   this.register = new Register();
	   this.register.username = this.user.username;
   	   this.register.password = this.user.password;
	   this.register.matchingPassword = this.user.matchingPassword;
	   this.register.email = this.user.email;

	   this.userService.saveUser(this.register).subscribe(
	       res => {
	         if(res?.status == 200) {
		   console.log(res);
		   this.message = res;
		 } else {
		   this.message = "";
		 }
	       },
	       err => {
	         if(err?.status >= 400) {
		   console.log(err);
		   this.error = err;
		 } else {
		   this.error = {};
		 }
	    });
	  }
  }

  ngOnInit(): void {
  }

}
