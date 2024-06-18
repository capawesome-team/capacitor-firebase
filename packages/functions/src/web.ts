import { WebPlugin } from '@capacitor/core';
import {
  getFunctions,
  httpsCallable,
  httpsCallableFromURL,
} from 'firebase/functions';

import type {
  CallByNameOptions,
  CallByNameResult,
  CallByUrlOptions,
  CallResult,
  FunctionsPlugin,
} from './definitions';

export class FunctionsWeb extends WebPlugin implements FunctionsPlugin {
  public async callByName(
    options: CallByNameOptions,
  ): Promise<CallByNameResult> {
    const functions = getFunctions();
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
}
