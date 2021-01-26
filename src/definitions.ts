declare module '@capacitor/core' {
  interface PluginRegistry {
    ZaloLogin: ZaloLoginPlugin;
  }
}

export enum LoginVia {
  APP = 'APP',
  WEB = 'APP',
  APP_OR_WEB = 'APP_OR_WEB',
}
export interface IZaloUser {
  id: string;
  name: string;
  gender: string;
  birthday: string;
  picture: {
    data: {
      url: string;
    }
  };
}
export interface ZaloLoginPlugin {
  login(options: { loginVia: LoginVia }): Promise<IZaloUser>;
  getApplicationHashKey(): Promise<{ signature: string }>;
}
