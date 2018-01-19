import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ApartmentDetailPage } from './apartment-detail';

@NgModule({
  declarations: [
    ApartmentDetailPage,
  ],
  imports: [
    IonicPageModule.forChild(ApartmentDetailPage),
  ],
})
export class ApartmentDetailPageModule {}
