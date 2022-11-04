import { WebPlugin } from '@capacitor/core';
import { FirebaseAppCheckPlugin } from './definitions';

export class FirebaseAppCheckWeb
  extends WebPlugin
  implements FirebaseAppCheckPlugin
{
  public async echo(): Promise<void> {
  }
}
