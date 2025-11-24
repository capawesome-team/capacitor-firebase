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
  AddConfigUpdateListenerOptionsCallback,
  FirebaseRemoteConfigPlugin,
  GetBooleanResult,
  GetInfoResult,
  GetNumberResult,
  GetOptions,
  GetStringResult,
  RemoveConfigUpdateListenerOptions,
  SetMinimumFetchIntervalOptions,
  SetSettingsOptions,
} from './definitions';
import { LastFetchStatus } from './definitions';

export class FirebaseRemoteConfigWeb extends WebPlugin implements FirebaseRemoteConfigPlugin {
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

  public async getInfo(): Promise<GetInfoResult> {
    const remoteConfig = getRemoteConfig();

    const status = {
      success: LastFetchStatus.Success,
      failure: LastFetchStatus.Failure,
      throttle: LastFetchStatus.Throttled,
      'no-fetch-yet': LastFetchStatus.NoFetchYet,
    };

    return {
      lastFetchTime: remoteConfig.fetchTimeMillis,
      lastFetchStatus: status[remoteConfig.lastFetchStatus],
    };
  }

  public async setMinimumFetchInterval(options: SetMinimumFetchIntervalOptions): Promise<void> {
    const remoteConfig = getRemoteConfig();
    remoteConfig.settings.minimumFetchIntervalMillis = options.minimumFetchIntervalInSeconds * 1000;
  }

  public async setSettings(options: SetSettingsOptions): Promise<void> {
    const remoteConfig = getRemoteConfig();
    if (options.fetchTimeoutInSeconds !== undefined) {
      remoteConfig.settings.fetchTimeoutMillis = options.fetchTimeoutInSeconds * 1000;
    }
    if (options.minimumFetchIntervalInSeconds !== undefined) {
      remoteConfig.settings.minimumFetchIntervalMillis = options.minimumFetchIntervalInSeconds * 1000;
    }
  }

  public async addConfigUpdateListener(_callback: AddConfigUpdateListenerOptionsCallback): Promise<string> {
    this.throwUnimplementedError();
  }

  public async removeConfigUpdateListener(_options: RemoveConfigUpdateListenerOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
