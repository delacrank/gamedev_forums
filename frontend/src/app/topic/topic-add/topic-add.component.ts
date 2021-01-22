import { Component, OnInit } from '@angular/core';
import { Topic } from "../../models/topic.model";
import { TopicService } from "../../services/topic.service";

@Component({
  selector: 'app-topic-add',
  templateUrl: './topic-add.component.html',
  styleUrls: ['./topic-add.component.css']
})
export class TopicAddComponent implements OnInit {
  topic: Topic;
  error: any;
  message: any;

  constructor(
    private topicService: TopicService
  ) {
    this.topic = new Topic();
  }

  ngOnInit(): void {
  }

  processForm(f) {
    if(f.valid) {
      console.log("topic title: " + this.topic.title + " topic content: " + this.topic.content);
      this.topicService.saveTopic(this.topic, this.topic.id).subscribe(
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
