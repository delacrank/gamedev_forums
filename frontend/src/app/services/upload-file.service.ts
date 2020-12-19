import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { User } from "../models/user.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  private usersUrl = "/api";
  private getFilesUrl = "/files";

  private user: User;

  constructor(private http: HttpClient) {
    this.user = new User();
  }

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', this.usersUrl + "/uploader", formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  getFiles(imageName: string): Observable<Blob> {
    return this.http.get("http://localhost:8080/files/" + imageName, { responseType: 'blob' });
  }
}