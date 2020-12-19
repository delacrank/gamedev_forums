import { Component, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { User } from "../models/user.model";
import { UserService } from "../services/user.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  public message: any;
  user: User;
  payload = new HttpParams();
  
  constructor(private userService: UserService) { this.user = new User(); }

  processForm(f) {
    this.payload = this.payload.append('email', this.user.email);  

    if(f.valid) {
      console.log(" email: " + this.user.email);
      this.userService.resetUserPass(this.payload).subscribe(
      res => {
         if(res?.status == 200) {
	   console.log(res);
	   this.message = res;
	 } else {
	   this.message = "";
	 }
       });
     }
  }

  ngOnInit(): void {
  }

}
