import { Component, OnInit } from '@angular/core';
import { User } from '../models/user.model';
import { Router } from '@angular/router';
import { ServicespmpService } from '../services/servicespmp.service';
import { PensionerDetails } from '../models/pensionerdetails.model';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private service: ServicespmpService,
    private router: Router) { }

  ngOnInit(): void {
    this.showStart = true;
    this.showlogin = false;
    this.showSignup = false;
  }

  showlogin = false;
  showSignup = false;
  showStart = false;
  loginUser: User = new User()!;
  model: PensionerDetails = new PensionerDetails()!;
  loginCred: User = new User()!;
  showerrorcontent = false;
  showError?: String;

  welcomelogin() {
    this.showStart = false;
    this.showlogin = true;
  }
  welcomesingup() {
    this.showStart = false;
    this.showSignup = true;
  }

  close() {
    this.showStart = true;
    this.showSignup = false;
    this.showlogin = false;
  
  }

  onLogin() {
    this.showSignup = false;
    this.showStart = false;
    this.service.login(this.loginUser).subscribe({
      next: x => {
        this.showlogin = false;
        setTimeout(() => {
          this.router.navigate(['/index']);
        }, 1500);
      },
      error: err => {
        this.showerrorcontent = true;
        this.showError = err.error.message;
      }
    });

  }

  onRegister() {
    this.showlogin = false;
    this.showStart = false;
    this.loginCred!.userId = this.model!.aadhar;
    this.service.register(this.model!, this.loginCred!).subscribe({
      next: x => {
        this.showSignup = false;
        this.showStart = true;
        (this.router).navigate(['/home']);
      },
      error: err => {
        this.showError = err.error.message;
      }
    });
  }

}
