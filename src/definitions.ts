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
  oauthCode?: string;
}
export interface ZaloLoginPlugin {
  login(options?: { loginVia: LoginVia }): Promise<IZaloUser>;
  getProfile(): Promise<IZaloUser>;
  logout(): Promise<void>;
  getApplicationHashKey(): Promise<{ signature: string }>;
}
