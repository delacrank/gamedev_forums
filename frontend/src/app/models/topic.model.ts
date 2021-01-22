export class Topic {
       private _id: number;
       closed: boolean;
       content: string;
       creation_date: string;
       last_update_date: string;
       title: string;
       views: number;

       constructor() {}

       get id(): any {
         return this._id;
       }

       set id(value: any) {
         this._id = value;
       }
   
}
