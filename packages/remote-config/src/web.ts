import { WebPlugin } from '@capacitor/core';
import {
  activate,
  fetchAndActivate,
  fetchConfig,
  getAll,
  getRemoteConfig,
  getValue,
} from 'firebase/remote-config';
import type { Value } from 'firebase/remote-config';

import type {
  AddConfigUpdateListenerOptionsCallback,
  FirebaseRemoteConfigPlugin,
  GetAllResult,
  GetAllResultValue,
  GetBooleanResult,
  GetInfoResult,
  GetNumberResult,
  GetOptions,
  GetStringResult,
  RemoveConfigUpdateListenerOptions,
  SetMinimumFetchIntervalOptions,
  SetDefaultsOptions,
  SetSettingsOptions,
} from './definitions';
import { GetValueSource, LastFetchStatus } from './definitions';

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
    const value = getValue(remoteConfig, options.key);
    return {
      value: value.asBoolean(),
      source: this.mapValueSource(value.getSource()),
    };
  }

  public async getNumber(options: GetOptions): Promise<GetNumberResult> {
    const remoteConfig = getRemoteConfig();
    const value = getValue(remoteConfig, options.key);
    return {
      value: value.asNumber(),
      source: this.mapValueSource(value.getSource()),
    };
  }

  public async getString(options: GetOptions): Promise<GetStringResult> {
    const remoteConfig = getRemoteConfig();
    const value = getValue(remoteConfig, options.key);
    return {
      value: value.asString(),
      source: this.mapValueSource(value.getSource()),
    };
  }

  public async getAll(): Promise<GetAllResult> {
    const remoteConfig = getRemoteConfig();
    const all = getAll(remoteConfig);
    const values: Record<string, GetAllResultValue> = {};
    for (const key of Object.keys(all)) {
      const value = all[key];
      values[key] = {
        value: value.asString(),
        source: this.mapValueSource(value.getSource()),
      };
    }
    return { values };
  }

  public async getInfo(): Promise<GetInfoResult> {
    const remoteConfig = getRemoteConfig();

    const status = {
      'success': LastFetchStatus.Success,
      'failure': LastFetchStatus.Failure,
      'throttle': LastFetchStatus.Throttled,
      'no-fetch-yet': LastFetchStatus.NoFetchYet,
    };

    return {
      lastFetchTime: remoteConfig.fetchTimeMillis,
      lastFetchStatus: status[remoteConfig.lastFetchStatus],
    };
  }

  public async setMinimumFetchInterval(
    options: SetMinimumFetchIntervalOptions,
  ): Promise<void> {
    const remoteConfig = getRemoteConfig();
    remoteConfig.settings.minimumFetchIntervalMillis =
      options.minimumFetchIntervalInSeconds * 1000;
  }

  public async setDefaults(options: SetDefaultsOptions): Promise<void> {
    const remoteConfig = getRemoteConfig();
    remoteConfig.defaultConfig = options.defaults;
  }

  public async setSettings(options: SetSettingsOptions): Promise<void> {
    const remoteConfig = getRemoteConfig();
    if (options.fetchTimeoutInSeconds !== undefined) {
      remoteConfig.settings.fetchTimeoutMillis =
        options.fetchTimeoutInSeconds * 1000;
    }
    if (options.minimumFetchIntervalInSeconds !== undefined) {
      remoteConfig.settings.minimumFetchIntervalMillis =
        options.minimumFetchIntervalInSeconds * 1000;
    }
  }

  public async addConfigUpdateListener(
    _callback: AddConfigUpdateListenerOptionsCallback,
  ): Promise<string> {
    this.throwUnimplementedError();
  }

  public async removeConfigUpdateListener(
    _options: RemoveConfigUpdateListenerOptions,
  ): Promise<void> {
    this.throwUnimplementedError();
  }

  private mapValueSource(
    source: ReturnType<Value['getSource']>,
  ): GetValueSource {
    switch (source) {
      case 'default':
        return GetValueSource.Default;
      case 'remote':
        return GetValueSource.Remote;
      case 'static':
      default:
        return GetValueSource.Static;
    }
  }

  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
