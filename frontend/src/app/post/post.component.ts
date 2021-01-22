import { Component, OnInit } from '@angular/core';
import { Post } from "../models/post.model";
import { Topic } from "../models/topic.model";
import { Categories } from "../models/categories.model";
import { PostService } from "../services/post.service";
import { TopicService } from "../services/topic.service";
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  categories: Categories;
  posts: Post[];
  topic: Topic;
  error: any[];

  constructor(
    private postService: PostService,
    private topicService: TopicService,
    private route: ActivatedRoute
  ) {
    this.categories = new Categories();
    this.topic = new Topic();
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
    this.categories.name = params.get('category');
    this.topic.id = Number(params.get('topic-id'));
    console.log(this.topic.id);
    this.postService.getPostsByTopicId(this.categories.name, this.topic.id)
    .subscribe(
      data => {
        this.posts = data;
      },
      err => {
        this.error = err;
      });
    });
      
    // this.topicService.get
  }

}
