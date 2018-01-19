import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ApartmentListPage } from './apartment-list';

@NgModule({
  declarations: [
    ApartmentListPage,
  ],
  imports: [
    IonicPageModule.forChild(ApartmentListPage),
  ],
})
export class ApartmentListPageModule {}
