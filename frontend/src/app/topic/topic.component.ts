import { Component, OnInit } from '@angular/core';
import { Topic } from "../models/topic.model";
import { TopicService } from "../services/topic.service";
import { CategoriesService } from "../services/categories.service";

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {
  topics: Topic[];
  error: any[];

  constructor(
    private topicService: TopicService
  ) { }

  ngOnInit(): void {
    this.topicService.getTopicsByCategoriesId(1)
    .subscribe(
      data => {
        this.topics = data;
      },
      err => {
        this.error = err;
      });
  }

}
