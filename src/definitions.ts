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
  uid: string;
  displayName: string;
  code: string;
}
export interface ZaloLoginPlugin {
  login(options: { loginVia: LoginVia }): Promise<IZaloUser>;
  getApplicationHashKey(): Promise<{ signature: string }>;
}
