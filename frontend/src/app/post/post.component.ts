import { Component, OnInit } from '@angular/core';
import { Post } from "../models/post.model";
import { PostService } from "../services/post.service";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  posts: Post[];
  error: any[];

  constructor(
    private postService: PostService
  ) { }

  ngOnInit(): void {
    this.postService.getPostsByTopicId(1)
    .subscribe(
      data => {
        this.posts = data;
      },
      err => {
        this.error = err;
      });

  }

}
