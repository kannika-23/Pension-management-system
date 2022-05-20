import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class PensionerDetails { 

  public aadhar?: number;
  public username?: String;
  public dob? : Date;
  public typselffam? : boolean;
  public pan? : String;
  public salaryearned? : number;
  public allowances? : number;
  public bankName? : String;
  public typepubpri? : boolean;
  public accountnumber? : number;

}
