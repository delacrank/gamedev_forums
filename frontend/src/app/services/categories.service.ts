import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Categories } from "../models/categories.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  private categoriesUrl = "/api/categories";

  private categories: Categories[];

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Categories[]> {
    return this.http.get<Categories[]>("http://localhost:8080/api/categories/");
  }

  getCategoriesCountByTopic(categoriesId: number) {
    return this.http.get("http://localhost:8080/api/categories/topic-count/" + categoriesId);
  }

  getCategoriesCountByPost(categoriesId: number) {
    return this.http.get("http://localhost:8080/api/categories/post-count/" + categoriesId);
  }
}
