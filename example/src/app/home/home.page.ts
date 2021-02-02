import { Component } from '@angular/core';
import { Plugins } from '@capacitor/core';
import { ToastController } from '@ionic/angular';
import { IZaloUser } from 'capacitor-zalo-login';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  zaloUser?: IZaloUser;
  constructor(
    private toastCtrl: ToastController,
  ) {}

  login() {
    Plugins.ZaloLogin
      .login()
      .then(zaloUser => {
        this.zaloUser = zaloUser;
      })
      .catch(e => {
        this.toastCtrl.create({
          message: e.message,
          color: 'danger',
          duration: 2000
        }).then(t => t.present());

        console.error('zaloLogin error:', e)
      });
  }

}
