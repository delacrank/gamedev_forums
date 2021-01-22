import { Component, OnInit } from '@angular/core';
import { User } from "../models/user.model";
import { UserService } from "../services/user.service";
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User;
  error: any[];

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) { this.user = new User(); }

  ngOnInit() {    
    this.route.paramMap.subscribe(params => {
      this.user.username = params.get('username');
      this.userService.getUserByUsername(this.user.username)
      .subscribe(
        data => {
	  this.user.bio = data.bio;
	  this.user.image = data.image;
	},
	err => {
	  this.error = err;
	});
    });

  }

}
