import { WebPlugin } from '@capacitor/core';
import {
  connectFunctionsEmulator,
  getFunctions,
  httpsCallable,
  httpsCallableFromURL,
} from 'firebase/functions';

import type {
  CallByNameOptions,
  CallByNameResult,
  CallByUrlOptions,
  CallResult,
  FirebaseFunctionsPlugin,
  UseEmulatorOptions,
} from './definitions';

export class FirebaseFunctionsWeb
  extends WebPlugin
  implements FirebaseFunctionsPlugin
{
  public async callByName(
    options: CallByNameOptions,
  ): Promise<CallByNameResult> {
    const functions = getFunctions(undefined, options.region);
    const callable = httpsCallable(functions, options.name);
    const result = await callable(options.data);
    return {
      data: result.data,
    };
  }

  public async callByUrl(options: CallByUrlOptions): Promise<CallResult> {
    const functions = getFunctions();
    const callable = httpsCallableFromURL(functions, options.url);
    const result = await callable(options.data);
    return {
      data: result.data,
    };
  }

  public async useEmulator(options: UseEmulatorOptions): Promise<void> {
    const functions = getFunctions();
    const port = options.port || 5001;
    connectFunctionsEmulator(functions, options.host, port);
  }
}
