import { FieldValue } from './field-value';
import { GeoPoint } from './geopoint';
import { Timestamp } from './timestamp';

export function serializeData(data: any): any {
  if (data === null || data === undefined) {
    return data;
  }
  if (
    data instanceof Timestamp ||
    data instanceof GeoPoint ||
    data instanceof FieldValue
  ) {
    return data.toJSON();
  }
  if (Array.isArray(data)) {
    return data.map(item => serializeData(item));
  }
  if (typeof data === 'object') {
    const result: Record<string, any> = {};
    for (const key of Object.keys(data)) {
      result[key] = serializeData(data[key]);
    }
    return result;
  }
  return data;
}

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
