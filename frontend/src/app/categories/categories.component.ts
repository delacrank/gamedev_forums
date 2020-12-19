import { Component, OnInit } from '@angular/core';
import { Categories } from "../models/categories.model";
import { Topic } from "../models/topic.model";
import { CategoriesService } from "../services/categories.service";
import { TopicService } from "../services/topic.service";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
  categories: Categories[];
  categoriesCount = new Map<string, number>();  
  error: any[];

  constructor(
    private categoriesService: CategoriesService,
    private topicService: TopicService
  ) { }

  ngOnInit(): void {
    this.categoriesService.getCategories()
    .subscribe(
      data => {
        this.categories = data;
      },
      err => {
        this.error = err;
      });
    for(let i = 1; i < 4; i++) {
	this.categoriesService.getCategoriesCountByTopic(i)
	.subscribe(
	  data => {
	      let topicCount = Number(data);
	      this.categoriesCount.set("topic" + i, topicCount);
	  },
	  err => {
	    this.error = err;
	  });

	this.categoriesService.getCategoriesCountByPost(i)
	.subscribe(
	  data => {
	      let postCount = Number(data);
	      this.categoriesCount.set("post" + i, postCount);
	  },
	  err => {
	    this.error = err;
	  });
     }
  }

}
