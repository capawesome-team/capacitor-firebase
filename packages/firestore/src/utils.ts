import { GeoPoint } from './geopoint';
import { Timestamp } from './timestamp';

export function deserializeData(data: any): any {
  if (data === null || data === undefined) {
    return data;
  }
  if (Array.isArray(data)) {
    return data.map(item => deserializeData(item));
  }
  if (typeof data === 'object') {
    if (data.__type__ === 'timestamp') {
      return new Timestamp(data.seconds, data.nanoseconds);
    }
    if (data.__type__ === 'geopoint') {
      return new GeoPoint(data.latitude, data.longitude);
    }
    const result: Record<string, any> = {};
    for (const key of Object.keys(data)) {
      result[key] = deserializeData(data[key]);
    }
    return result;
  }
  return data;
}
