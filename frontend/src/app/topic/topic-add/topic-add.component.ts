import { Component, OnInit } from '@angular/core';
import { Topic } from "../../models/topic.model";
import { TopicService } from "../../services/topic.service";
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-topic-add',
  templateUrl: './topic-add.component.html',
  styleUrls: ['./topic-add.component.css']
})
export class TopicAddComponent implements OnInit {
  topic: Topic;
  category: string;
  error: any;
  message: any;

  constructor(
    private topicService: TopicService,
    private route: ActivatedRoute
  ) {
    this.topic = new Topic();
  }

  processForm(f) {
    if(f.valid) {
      console.log("topic title: " + this.topic.title + " topic content: " + this.topic.content + " categories name: " + this.category);
      this.topicService.saveTopic(this.topic, this.category).subscribe(
       response => {
         if(response?.status == 200) {
           console.log(response);
       	   this.message = response;
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
       }
       );
     }
  }

  ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
        this.category = params.get('category');
      });

  }


}
