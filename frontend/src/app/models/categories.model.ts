export class Categories {
       description: string;
       name: string;
       private _topicCount: any;
       private _postCount: any;
       construction() {}

       get topicCount(): any {
         return this._topicCount;
       }

       set topicCount(value: any) {
         this._topicCount = value;
       }

       get postCount(): any {
         return this._postCount;
       }

       set postCount(value: any) {
         this._postCount = value;
       }
}
