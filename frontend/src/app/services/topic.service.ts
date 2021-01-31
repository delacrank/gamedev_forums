import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Topic } from "../models/topic.model";
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

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
     const headers = new HttpHeaders({'Authorization': 'Basic ' + sessionStorage.getItem("token")});
    console.log(sessionStorage.getItem("token"));
    const options = {
      headers: headers,
      observe: 'response' as 'response'
    };
    return this.http.post<Topic>(`http://localhost:8080/api/forum/${catName}/addTopic`, topic, options);
  }
}
