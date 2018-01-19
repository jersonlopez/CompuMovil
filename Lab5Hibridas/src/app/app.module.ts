import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';
import {AngularFireModule} from 'angularfire2';
import {AngularFireDatabaseModule, AngularFireDatabase} from 'angularfire2/database-deprecated'

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { ApartmentListPage } from '../pages/apartment-list/apartment-list';
import { ApartmentDetailPage } from '../pages/apartment-detail/apartment-detail';
import { AngularFireAuthModule } from 'angularfire2/auth';
import 'firebase/storage';

const FIREBASE_CONFIG=
  {
    apiKey: "AIzaSyDg3yOrtPtfsDkq_Ku8UWABpLrN8D0_gLw",
    authDomain: "lab4fcm-7825a.firebaseapp.com",
    databaseURL: "https://lab4fcm-7825a.firebaseio.com",
    projectId: "lab4fcm-7825a",
    storageBucket: "lab4fcm-7825a.appspot.com",
    messagingSenderId: "658462530001"
  };



@NgModule({
  declarations: [
    MyApp,
    HomePage,
    ApartmentListPage,
    ApartmentDetailPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp),
    AngularFireModule.initializeApp(FIREBASE_CONFIG),
    AngularFireDatabaseModule,
    AngularFireAuthModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    ApartmentListPage,
    ApartmentDetailPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    AngularFireDatabase,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
