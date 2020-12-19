import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
 
  authGuard: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router) { }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
  
    this.authService.currentAuthentication.subscribe(auth => {
     console.log(this.authGuard);
     this.authGuard = auth;
     console.log(auth);
    });
    console.log(this.authGuard);
    return this.authGuard;
  }   
}

    //  console.log(this.authService.isAuthenticated());
    // if(this.authService.isAuthenticated()) {
    //   return true;
    // } else {
            
    //   this.router.navigate(['/login'], {

    //     queryParams: {
    // 	  return: state.url
    // 	}
    //    });
    //    return false;
    // }