import { Component } from '@angular/core';
import { ViewChild } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import {AngularFireAuth} from 'angularfire2/auth'
import {ApartmentListPage} from '../apartment-list/apartment-list';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
  providers: [AngularFireAuth]
})
export class HomePage {

@ViewChild('username') user;
@ViewChild('password') password;

  constructor(private fire:AngularFireAuth,public navCtrl: NavController, public alertCtrl: AlertController) {
  }

  showAlert() {
    let alert = this.alertCtrl.create({
      title: 'Excelente!',
      subTitle: 'Te acabas de loguear una de las mejores apps!',
      buttons: ['Aceptar']
    });
    alert.present();
  }

 cameOn():void{
    this.navCtrl.push(ApartmentListPage);
  }

  validar(){
  this.fire.auth.signInWithEmailAndPassword(this.user.value,this.password.value)
  .then(data => {
    this.showAlert();
    this.cameOn();
  })
  .catch(error=> {
    console.log(error.message);
  })
  }
}
