import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Topic } from "../models/topic.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {  }

  getTopicByName(categoryName: string): Observable<Topic[]> {
    return this.http.get<Topic[]>("http://localhost:8080/api/categories/" + categoryName);
  }

  getTopicsByCategoriesId(id: number): Observable<Topic[]> {
    return this.http.get<Topic[]>("http://localhost:8080/api/categories/" + id);
  }
}
