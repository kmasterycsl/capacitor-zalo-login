import { WebPlugin } from '@capacitor/core';
import { IZaloUser, LoginVia, ZaloLoginPlugin } from './definitions';

export class ZaloLoginWeb extends WebPlugin implements ZaloLoginPlugin {
  constructor() {
    super({
      name: 'ZaloLogin',
      platforms: ['web'],
    });
  }

  //@ts-ignore
  login(options?: { loginVia: LoginVia }): Promise<IZaloUser> {
    return Promise.reject(`Web implementation is not available now.`);
  }

  logout(): Promise<void> {
    return Promise.reject(`Web implementation is not available now.`);
  }

  getApplicationHashKey() {
    return Promise.reject('Application hash key is not available in web environment.');
  }
}

const ZaloLogin = new ZaloLoginWeb();

export { ZaloLogin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(ZaloLogin);
