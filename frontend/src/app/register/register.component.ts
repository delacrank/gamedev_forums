import { Component, OnInit } from '@angular/core';
import { User } from "../models/user.model";
import { UserMapper } from "../models/user-mapper.model";
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
  userMapper: UserMapper;
  
  constructor(private userService: UserService) {
  		      this.user = new User();
		      this.userMapper = new UserMapper();
		      }

  processForm(f) {
         if(f.valid) {
  	   this.user = this.userMapper.mapUser(this.user);
	   console.log(this.user);
	   this.userService.saveUser(this.user).subscribe(
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
