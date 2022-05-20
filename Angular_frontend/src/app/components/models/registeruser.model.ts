import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from './user.model';
import { PensionerDetails } from './pensionerdetails.model';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class RegisterUser { 

public pensioner?: PensionerDetails;
public daoUser?: User;

}