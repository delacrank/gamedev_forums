import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Topic } from "../models/topic.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {  }

  getTopicByName(catName: string): Observable<Topic[]> {
    return this.http.get<Topic[]>(`http://localhost:8080/api/forum/${catName}`);
  }

  getTopicsByCategoriesName(catName: string): Observable<Topic[]> {
    return this.http.get<Topic[]>(`http://localhost:8080/api/forum/${catName}`);
  }

  saveTopic(topic: Topic, catName: string) {
    return this.http.post<Topic>(`http://localhost:8080/api/forum/${catName}/addTopic`, topic, { observe: 'response' });
  }
}
