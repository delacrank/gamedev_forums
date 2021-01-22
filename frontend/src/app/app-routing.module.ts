import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { BadUserComponent } from './bad-user/bad-user.component';
import { SuccessRegistrationComponent } from './success-registration/success-registration.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { UserComponent } from './user/user.component';
import { TopicComponent } from './topic/topic.component';
import { TopicAddComponent } from './topic/topic-add/topic-add.component';
import { PostComponent } from './post/post.component';
import { PostAddComponent } from './post/post-add/post-add.component';
import { UserEditComponent } from './user/user-edit/user-edit.component';
import { AuthGuardService } from './services/auth-guard.service';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
 { path: '', component: HomeComponent },
 { path: 'register', component: RegisterComponent },
 { path: 'login', component: LoginComponent },
 { path: 'resetPassword', component: ResetPasswordComponent },
 { path: 'badUser', component: BadUserComponent },
 { path: 'successRegistration', component: SuccessRegistrationComponent },
 { path: 'changePassword', component: ChangePasswordComponent },
 { path: 'updatePassword/:token', component: UpdatePasswordComponent },
 { path: 'updatePassword', component: UpdatePasswordComponent },
 { path: 'user/:username', component: UserComponent },
 { path: 'user/:username/edit', component: UserEditComponent, canActivate: [AuthGuardService] },
 { path: 'forum/:category', component: TopicComponent },
 { path: 'forum/:category/topic-add', component: TopicAddComponent },
 { path: 'forum/:category/:topic-id', component: PostComponent },
 { path: 'forum/:category/:topic-id/post-add', component: PostAddComponent },
 { path: '**', component: PageNotFoundComponent }
 ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
