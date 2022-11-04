import { WebPlugin } from '@capacitor/core';
import { FirebaseRemoteConfigPlugin } from './definitions';

export class FirebaseRemoteConfigWeb
  extends WebPlugin
  implements FirebaseRemoteConfigPlugin
{
  public async echo(): Promise<void> {
  }
}
