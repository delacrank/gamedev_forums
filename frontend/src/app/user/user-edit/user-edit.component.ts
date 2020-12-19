import { Component, OnInit } from '@angular/core';
import { User } from "../../models/user.model";
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from "../../services/user.service";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  public error: any;
  public message: any;
  user: User;
    
  constructor(
    private route: ActivatedRoute,
    private userService: UserService) {
    this.user = new User();
  }

  processForm(f) {
    
    if(f.valid) {
    console.log(this.user);
    console.log(this.user.bio);

    this.userService.updateUser(this.user.username, this.user).subscribe(
      res => {
	if(res?.status == 200) {
	  console.log(res);
	  this.message = res;
	} else {
	  this.message = "";
	}
      },
      err => {
	if(err?.stauts >= 400) {
	  console.log(err);
	  this.error = err;
	} else {
	  this.error = {};
	}
      });
    }
  }
	  
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.user.username = params.get('username')
    });
  }

}
