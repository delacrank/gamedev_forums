import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule  } from '@angular/common/http';
import { AuthService } from "./services/auth.service";
import { UserService } from "./services/user.service";
import { CategoriesService } from "./services/categories.service";
import { TopicService } from "./services/topic.service";
import { UploadFileService } from "./services/upload-file.service";
import { AuthGuardService } from "./services/auth-guard.service";
import { LoginComponent } from './login/login.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { BadUserComponent } from './bad-user/bad-user.component';
import { SuccessRegistrationComponent } from './success-registration/success-registration.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { UserComponent } from './user/user.component';
import { UserEditComponent } from './user/user-edit/user-edit.component';
import { UploadFilesComponent } from './upload-files/upload-files.component';
import { CategoriesComponent } from './categories/categories.component';
import { TopicComponent } from './topic/topic.component';
import { PostComponent } from './post/post.component';
import { TopicAddComponent } from './topic/topic-add/topic-add.component';
import { PostAddComponent } from './post/post-add/post-add.component';

import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS
} from '@angular/common/http';

// Suppress pop up box from failed login
@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    RegisterComponent,
    LoginComponent,
    ResetPasswordComponent,
    BadUserComponent,
    SuccessRegistrationComponent,
    ChangePasswordComponent,
    UpdatePasswordComponent,
    PageNotFoundComponent,
    UserComponent,
    UserEditComponent,
    UploadFilesComponent,
    CategoriesComponent,
    TopicComponent,
    PostComponent,
    TopicAddComponent,
    PostAddComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [ AuthService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }, UserService, CategoriesService, UploadFileService, AuthGuardService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
