import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { IndexComponent } from './components/index/index.component';
import { BaseComponent } from './components/base/base.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'index', component: IndexComponent },
  { path: 'base', component: BaseComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
