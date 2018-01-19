import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { FirebaseApp } from 'angularfire2';
import * as firebase from 'firebase';

@IonicPage()
@Component({
  selector: 'page-apartment-detail',
  templateUrl: 'apartment-detail.html',
})
export class ApartmentDetailPage {
  apartment: any;
  constructor(public navCtrl: NavController, public navParams: NavParams,private fb: FirebaseApp) {
    this.apartment=navParams.get('datos');
  }
}