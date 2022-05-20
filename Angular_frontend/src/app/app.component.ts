
import { Component } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {ServicespmpService } from '../app/components/services/servicespmp.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor( private service: ServicespmpService,private router:Router) { }

  ngOnInit( ): void {

    setTimeout(() => {
      alert("your session timed out");
      localStorage.clear();
    }, 5 * 60 * 1000);
    this.router.navigate(['/home']);

  }

  title = 'pmpPortal';
  navbarOpen = false;

  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

}
