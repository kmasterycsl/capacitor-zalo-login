import { WebPlugin } from '@capacitor/core';
import { ZaloLoginPlugin } from './definitions';

export class ZaloLoginWeb extends WebPlugin implements ZaloLoginPlugin {
  constructor() {
    super({
      name: 'ZaloLogin',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const ZaloLogin = new ZaloLoginWeb();

export { ZaloLogin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(ZaloLogin);
