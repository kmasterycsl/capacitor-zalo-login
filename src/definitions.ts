declare module '@capacitor/core' {
  interface PluginRegistry {
    ZaloLogin: ZaloLoginPlugin;
  }
}

export interface ZaloLoginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
