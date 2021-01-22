import { Component, OnInit } from '@angular/core';
import { Topic } from "../models/topic.model";
import { Categories } from "../models/categories.model";
import { TopicService } from "../services/topic.service";
import { CategoriesService } from "../services/categories.service";
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {
  topics: Topic[];
  categories: Categories;
  error: any[];

  constructor(
    private route: ActivatedRoute,
    private topicService: TopicService
  ) { this.categories = new Categories(); }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
    this.categories.name = params.get('category');
    this.topicService.getTopicsByCategoriesName(this.categories.name)
    .subscribe(
      data => {
        this.topics = data;
      },
      err => {
        this.error = err;
      });
   });  
  }

}
