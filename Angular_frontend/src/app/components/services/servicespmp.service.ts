import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, catchError, EMPTY } from 'rxjs';
import { User } from '../models/user.model';
import { Router } from '@angular/router';
import { PensionerDetails } from '../models/pensionerdetails.model';
import { PensionCharges } from '../models/PensionCharges.model';
import { RegisterUser } from '../models/registeruser.model';



@Injectable({
  providedIn: 'root'
})
export class ServicespmpService {

readonly baseurl = 'http://localhost:8765';
readonly loginurl = '/pmp/pensioner-auth/login';
readonly registerurl = '/pmp/pensioner-auth/register';
readonly getdetailsurl = '/pmp/process-pension/getdetails/';
readonly conformationUrl = '/pmp/process-pension/getpensionconformationdetails';
readonly getpensiourl = '/pmp/process-pension/getpension';
readonly updateurl = '/pmp/process-pension/updatepensionerdetails';

readonly headers = new HttpHeaders()
  .set('content-type', 'application/json')
  .set('Authorization', window.localStorage.getItem('token')!);

  constructor(private http: HttpClient, private router: Router) { }

  pensionUser?: PensionerDetails;
  pensioncharges?: PensionCharges;
  registerUser?: RegisterUser;

  login(user: User): Observable<String> {

    return this.http.post(this.baseurl + this.loginurl, user).pipe(
      map(data => {
        window.localStorage.setItem('userId', user.userId!.toString());
        window.localStorage.setItem('token', JSON.stringify(data).substring(JSON.stringify(data).indexOf('B'), JSON.stringify(data).length-2));
        return "logged";
      })
    )
  }

  register(newUser: PensionerDetails, loginCred: User): Observable<any> {

    this.registerUser = { 'pensioner': newUser, 'daoUser': loginCred };

    return this.http.post(this.baseurl + this.registerurl, this.registerUser).pipe(
      map(data => {
        return data;
      })
    )
  }

  details(): Observable<PensionerDetails> {

    return this.http.get(this.baseurl + this.getdetailsurl + window.localStorage.getItem('userId'), { 'headers': this.headers }).pipe(
      map(data => {
        return data;
      }),
      catchError((err: HttpErrorResponse) => {
        this.errorService("unauthorized");
        return EMPTY;
      })
    )
  }

  conformationcheck(pensioner: PensionerDetails): Observable<PensionerDetails> {


    return this.http.post(this.baseurl + this.conformationUrl, pensioner, { 'headers': this.headers }).pipe(
      map(data => {
        return data;
      }),
      catchError((err: HttpErrorResponse) => {
        this.errorService(err?.error.message);
         return EMPTY;
      })
    )

  }

  getPension(pensioner: PensionerDetails): Observable<PensionCharges> {

    return this.http.post(this.baseurl + this.getpensiourl, pensioner, { 'headers': this.headers }).pipe(
      map(data => {
        this.pensioncharges = data;
        return data;
      }),
      catchError((err: HttpErrorResponse) => {
        this.errorService(err?.error.message);
         return EMPTY;
      })
    )
  }

  updatedetails(pensioner: PensionerDetails): Observable<PensionerDetails> {

    return this.http.post(this.baseurl + this.updateurl, pensioner, { 'headers': this.headers }).pipe(
      map(data => {
        return data;
      }),
      catchError((err: HttpErrorResponse) => {
        this.errorService(err?.error.message);
        return EMPTY;

      })
    )
  }

  errorService(msg: string) {
    alert(msg);
    this.router.navigate(['/home']);
  }


}
