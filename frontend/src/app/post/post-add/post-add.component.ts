import { Component, OnInit } from '@angular/core';
import { Post } from "../../models/post.model";
import { PostService } from "../../services/post.service";

@Component({
  selector: 'app-post-add',
  templateUrl: './post-add.component.html',
  styleUrls: ['./post-add.component.css']
})
export class PostAddComponent implements OnInit {
  catName = 'game-development';
  post: Post;
  error: any;
  message: any;

  constructor(
    private postService: PostService
  ) {
    this.post = new Post();
  }

  ngOnInit(): void {
  }

  processForm(f) {
    if(f.valid) {
      console.log("post content: " + this.post.content);
      this.postService.savePost(this.catName, this.post.id, this.post).subscribe(
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


}
