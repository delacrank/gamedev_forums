import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Post } from "../models/post.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {  }

  getPostsByTopicId(id: number): Observable<Post[]> {
    return this.http.get<Post[]>("http://localhost:8080/api/topic/" + id);
  }

}
