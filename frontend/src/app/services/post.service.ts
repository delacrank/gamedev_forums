import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Post } from "../models/post.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {  }

  getPostsByTopicId(catName: string, id: number): Observable<Post[]> {
    return this.http.get<Post[]>(`http://localhost:8080/api/forum/${catName}/${id}`);
  }

  savePost(catName: string, id: number, post: Post) {
    return this.http.post<Post>(`http://localhost:8080/api/forum/${catName}/${id}/save`, post, { observe: 'response' });
  }

}
