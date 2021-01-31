export class User {
       private _username?: string;
       matchingPassword?: string;
       oldPassword?: string;
       newPassword?: string;
       password: string;
       token?: string;
       image?: any;
       bio?: string;
       email: string;       

       constructor(){  }

       get username(): string {
         return this._username;
       }

       set username(val: string) {
         this._username = val;
       }
}
