import { Component, OnInit } from '@angular/core';
import { User } from "../models/user.model";
import { UserService } from "../services/user.service";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  public error: any;
  public message: any;
  user: User;
    
  constructor(private userService: UserService) { this.user = new User(); }

  processForm(f) {
         if(f.valid) {
	   this.userService.updatePass(this.user).subscribe(
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
