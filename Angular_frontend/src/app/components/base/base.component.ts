import { Component, OnInit } from '@angular/core';
import { PensionCharges } from '../models/PensionCharges.model';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { ServicespmpService } from '../services/servicespmp.service'

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit {

  constructor(private service: ServicespmpService, private router: Router) { }

  penioncharges!: PensionCharges;
  update = false;

  ngOnInit(): void {
    if (this.service.pensioncharges == null) {
      this.router.navigate(['/home']);
    } else {
      this.penioncharges = this.service.pensioncharges;
    }
  }

  onlogout = () => {
    localStorage.clear();
    this.router.navigate(['/home']);
  }

  onback = () => this.router.navigate(['/index']);


}
