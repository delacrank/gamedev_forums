import { User } from "./user.model";

export class UserMapper {
    username: string;
    password: string;
    matchingPassword: string;
    email: string;       

    public mapUser(user: User): User {
       const userModel = new UserMapper();
       userModel.username = user.username;
       userModel.password = user.password;
       userModel.matchingPassword = user.matchingPassword;
       userModel.email = user.email;
       return userModel;
    }
}
