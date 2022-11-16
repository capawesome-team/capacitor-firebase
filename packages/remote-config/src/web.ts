import { WebPlugin } from '@capacitor/core';
import {
  activate,
  fetchAndActivate,
  fetchConfig,
  getBoolean,
  getNumber,
  getRemoteConfig,
  getString,
} from 'firebase/remote-config';

import type {
  FirebaseRemoteConfigPlugin,
  GetBooleanResult,
  GetNumberResult,
  GetOptions,
  GetStringResult,
  SetMinimumFetchIntervalOptions,
} from './definitions';

export class FirebaseRemoteConfigWeb
  extends WebPlugin
  implements FirebaseRemoteConfigPlugin
{
  public async activate(): Promise<void> {
    const remoteConfig = getRemoteConfig();
    await activate(remoteConfig);
  }

  public async fetchAndActivate(): Promise<void> {
    const remoteConfig = getRemoteConfig();
    await fetchAndActivate(remoteConfig);
  }

  public async fetchConfig(): Promise<void> {
    const remoteConfig = getRemoteConfig();
    await fetchConfig(remoteConfig);
  }

  public async getBoolean(options: GetOptions): Promise<GetBooleanResult> {
    const remoteConfig = getRemoteConfig();
    const value = getBoolean(remoteConfig, options.key);
    return { value };
  }

  public async getNumber(options: GetOptions): Promise<GetNumberResult> {
    const remoteConfig = getRemoteConfig();
    const value = getNumber(remoteConfig, options.key);
    return { value };
  }

  public async getString(options: GetOptions): Promise<GetStringResult> {
    const remoteConfig = getRemoteConfig();
    const value = getString(remoteConfig, options.key);
    return { value };
  }

  public async setMinimumFetchInterval(
    options: SetMinimumFetchIntervalOptions,
  ): Promise<void> {
    const remoteConfig = getRemoteConfig();
    remoteConfig.settings.minimumFetchIntervalMillis =
      options.minimumFetchIntervalInSeconds * 1000;
  }
}
