import { Bytes } from './bytes';
import { DocumentReference } from './document-reference';
import { FieldValue } from './field-value';
import { GeoPoint } from './geopoint';
import {
  deserializeSpecialNumber,
  serializeSpecialNumber,
} from './special-number';
import { Timestamp } from './timestamp';

export function serializeData(data: any): any {
  if (data === null || data === undefined) {
    return data;
  }
  if (typeof data === 'number' && !Number.isFinite(data)) {
    return serializeSpecialNumber(data);
  }
  if (
    data instanceof Timestamp ||
    data instanceof GeoPoint ||
    data instanceof Bytes ||
    data instanceof DocumentReference ||
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
    if (data.__type__ === 'bytes') {
      return Bytes.fromBase64String(data.bytes);
    }
    if (data.__type__ === 'documentReference') {
      return DocumentReference.fromPath(data.path);
    }
    if (data.__type__ === 'number') {
      return deserializeSpecialNumber(data.value);
    }
    const result: Record<string, any> = {};
    for (const key of Object.keys(data)) {
      result[key] = deserializeData(data[key]);
    }
    return result;
  }
  return data;
}
