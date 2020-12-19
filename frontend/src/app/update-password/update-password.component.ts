import { Component, OnInit } from '@angular/core';
import { User } from "../models/user.model";
import { UserService } from "../services/user.service";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent implements OnInit {

  public error: any;
  public message: any;
  user: User;
    
  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) { this.user = new User(); }

  processForm(f) {
      if(f.valid) {
	   console.log("pass: " + this.user.newPassword + " matchingPass: " + this.user.matchingPassword + "passwordToken: " + this.user.token);
	   this.userService.savePass(this.user).subscribe(
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
     this.route.queryParams.subscribe(params => {
       this.user.token = params['token'];
     });
  }

}
