import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { ServicespmpService } from '../services/servicespmp.service';
import { PensionerDetails } from '../models/pensionerdetails.model';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  userId?: number;
  pensionUser?: PensionerDetails;
  validate = false;
  number?: number;
  displayStyle = "none";
  showupdate = false;
  model?: PensionerDetails;
  showerror = false;
  showError?: String;

  constructor(private service: ServicespmpService, private router: Router) { }

  ngOnInit(): void {
    console.log(window.localStorage.getItem('userId'));
    if (window.localStorage.getItem('userId') == null) {
      this.router.navigate(['/home']);
    } else {
      this.service.details().subscribe({
        next: x => {
          this.pensionUser = x;
        },
        error: err => {
          this.showError = "unauthorized";
          this.router.navigate(['/home']);
        }
      });
    }
  }

  close() {
    this.showupdate = false;
  }

  onValidate() {
    this.validate = true;
    console.log(this.pensionUser!.aadhar);
    this.service.conformationcheck(this.pensionUser!).subscribe({
      next: x => {
        this.pensionUser = x;
        this.displayStyle = "block";
      },
      error: err => console.log(err)
    });
  }

  closePopup() {
    this.displayStyle = "none";
    this.service.getPension(this.pensionUser!).subscribe({
      next: x => {
        this.router.navigate(['/base']);
      },
      error: err => console.log(err)
    });

  }

  onUpdate() {
    console.log("in update");
    this.service.updatedetails(this.pensionUser!).subscribe({
      next: x => {
        this.showupdate = false;
      },
      error: err => {
        this.showError = err;
      }
    });


  }
}
