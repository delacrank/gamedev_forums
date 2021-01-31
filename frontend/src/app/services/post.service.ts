import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Post } from "../models/post.model";
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {  }

  getPostsByTopicId(catName: string, id: number): Observable<Post[]> {
    return this.http.get<Post[]>(`http://localhost:8080/api/forum/${catName}/${id}`);
  }

  savePost(catName: string, id: number, post: Post) {
    const headers = new HttpHeaders({'Authorization': 'Basic ' + sessionStorage.getItem("token")});

   const options = {
    headers: headers,
    observe: 'response' as 'response'
   };

    return this.http.post<Post>(`http://localhost:8080/api/forum/${catName}/${id}/save`, post, options);
  }

}
