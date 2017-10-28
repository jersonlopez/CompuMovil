const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);
exports.sendPushNotification = functions.database
.ref('/Apartment/{apartmentId}')
.onWrite(event => {
  const payload = {
    notification: {
      tittle:'Nueva publicacion disponible',
      body:'Nueva publicacion disponible',
      badge:'1',
      sound:'default'
    }
  };
  return admin.database().ref('Devices').once('value').then(allToken => {
    if(allToken.val()){
      const token = Object.keys(allToken.val());
      return admin.messaging().sendToDevice(token, payload).then(response => {
      });
    };
  });
});
