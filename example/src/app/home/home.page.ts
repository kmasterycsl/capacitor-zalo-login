import { ChangeDetectorRef, Component } from '@angular/core';
import { Plugins } from '@capacitor/core';
import { ToastController } from '@ionic/angular';
import { IZaloUser } from 'capacitor-zalo-login';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  response?: any;
  constructor(
    private toastCtrl: ToastController,
  ) { }

  login() {
    this.response = {};
    Plugins.ZaloLogin
      .login()
      .then(r => this.onSuccess(r))
      .catch(e => this.onError(e));
  }

  logout() {
    this.response = {};
    Plugins.ZaloLogin
      .logout()
      .then(r => this.onSuccess(r))
      .catch(e => this.onError(e));
  }

  getProfile() {
    this.response = {};
    Plugins.ZaloLogin
      .getProfile()
      .then(r => this.onSuccess(r))
      .catch(e => this.onError(e));
  }

  clearOutput() {
    this.response = {};
  }

  private onSuccess(res) {
    this.response = res;
  }

  private onError(e) {
    this.toastCtrl.create({
      message: e.message,
      color: 'danger',
      duration: 2000
    }).then(t => t.present());

    console.error('error:', e)
  }
}
