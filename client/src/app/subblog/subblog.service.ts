import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SubblogModel } from './subblog-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubblogService {

  constructor(private http: HttpClient) { }

  getAllSubblogs(): Observable<Array<SubblogModel>> {
    return this.http.get<Array<SubblogModel>>('http://localhost:8080/api/subblog');
  }

  createSubblog(subblogModel: SubblogModel): Observable<SubblogModel> {
    return this.http.post<SubblogModel>('http://localhost:8080/api/subblog', subblogModel);
  }
}
