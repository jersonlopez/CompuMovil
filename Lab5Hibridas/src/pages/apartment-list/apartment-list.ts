import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { AngularFireDatabase, FirebaseListObservable} from 'angularfire2/database-deprecated';
import { ApartmentDetailPage } from '../apartment-detail/apartment-detail';
import { FirebaseApp } from 'angularfire2';
import { LoadingController } from 'ionic-angular';

@IonicPage()
@Component({
  selector: 'page-apartment-list',
  templateUrl: 'apartment-list.html'
})
export class ApartmentListPage {

apartments: FirebaseListObservable<any[]>;

  constructor(private db: AngularFireDatabase, public navCtrl: NavController, 
    public loadingCtrl: LoadingController,
     public navParams: NavParams, private database: AngularFireDatabase,private fb: FirebaseApp) {
  var path = '/Apartment/';  
  this.apartments = db.list(path);  
  }
  presentLoading() {
    let loader = this.loadingCtrl.create({
      content: "Espere por favor...",
      duration: 1000
    });
    loader.present();
  }

toDetailView(apartment: any){
  this.presentLoading();
  this.navCtrl.push(ApartmentDetailPage,{
    datos: apartment
  });

}  ionViewDidLoad() {
    console.log('ionViewDidLoad ApartmentListPage');
  }

}
