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
  // JSON cannot transport non-finite IEEE 754 doubles; carry them across the
  // Capacitor bridge as `{ __type__: "double", value: "NaN" | "Infinity" |
  // "-Infinity" }` and let the platform layer rebuild the primitive.
  if (typeof data === 'number' && !Number.isFinite(data)) {
    return { __type__: 'double', value: serializeNonFiniteValue(data) };
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
    if (data.__type__ === 'double') {
      return deserializeNonFiniteValue(data.value);
    }
    const result: Record<string, any> = {};
    for (const key of Object.keys(data)) {
      result[key] = deserializeData(data[key]);
    }
    return result;
  }
  return data;
}

export function serializeNonFiniteValue(
  n: number,
): 'NaN' | 'Infinity' | '-Infinity' {
  if (Number.isNaN(n)) return 'NaN';
  return n > 0 ? 'Infinity' : '-Infinity';
}

export function deserializeNonFiniteValue(value: unknown): number {
  if (value === 'NaN') return Number.NaN;
  if (value === 'Infinity') return Number.POSITIVE_INFINITY;
  if (value === '-Infinity') return Number.NEGATIVE_INFINITY;
  // Unknown — fall back to NaN rather than throwing so a single bad payload
  // doesn't tear down the listener.
  return Number.NaN;
}
