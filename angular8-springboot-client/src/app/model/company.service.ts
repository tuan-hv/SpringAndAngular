import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {


  private baseUrl = 'http://localhost:8080/tuanden/api/v1/companies';

  constructor(private http: HttpClient) { }

  getCompaniesList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  getCompany(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createCompany(employee: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, employee);
  }

  updateCompany(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, value);
  }

  deleteCompany(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}
