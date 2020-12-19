import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from "@angular/platform-browser";
import { UploadFileService } from '../services/upload-file.service';
import { UserService } from '../services/user.service';
import { User } from "../models/user.model";
import { ActivatedRoute, ParamMap } from '@angular/router';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
  styleUrls: ['./upload-files.component.css']
})
export class UploadFilesComponent implements OnInit {

  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  img: any;
  user: User;

  fileInfos: Observable<any>;

  constructor(
    private route: ActivatedRoute,
    private uploadService: UploadFileService,
    private sanitizer: DomSanitizer,
    private userService: UserService) {
        this.user = new User();
    }

  selectFile(event): void {
    this.selectedFiles = event.target.files;
    this.user.image = event.target.files[0].name;
  }

  upload(): void {
    this.progress = 0;

    this.currentFile = this.selectedFiles.item(0);
      
    
    this.uploadService.upload(this.currentFile).subscribe(
      event => {
	if (event.type === HttpEventType.UploadProgress) {
	  this.progress = Math.round(100 * event.loaded / event.total);
          this.userService.updateUser(this.user.username, this.user).subscribe();
	} else if (event instanceof HttpResponse) {
	  this.message = event.body.message;
	}
      },
      err => {
	this.progress = 0;
	this.message = 'Could not upload the file!';
	this.currentFile = undefined;
      });
    this.selectedFiles = undefined;
  }

  ngOnInit(): void {
    
    this.route.paramMap.subscribe(params => {
      this.user.username = params.get('username')
    });

    this.userService.getUserByUsername(this.user.username)
     .subscribe(
        data => {
	  this.user.image = data.image;
          console.log(this.user.image);
	  this.uploadService.getFiles(this.user.image)
            .subscribe((blob : any) => {
            let objectURL = URL.createObjectURL(blob);       
            this.img = this.sanitizer.bypassSecurityTrustUrl(objectURL);   
          });  	  
    });
  }

}
