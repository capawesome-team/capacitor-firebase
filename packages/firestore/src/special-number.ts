import type { FirestoreNumber } from './definitions';

export function serializeSpecialNumber(value: number): FirestoreNumber {
  if (Number.isNaN(value)) {
    return { __type__: 'number', value: 'NaN' };
  }
  if (value === Infinity) {
    return { __type__: 'number', value: 'Infinity' };
  }
  return { __type__: 'number', value: '-Infinity' };
}

export function deserializeSpecialNumber(value: string): number {
  if (value === 'NaN') {
    return NaN;
  }
  if (value === 'Infinity') {
    return Infinity;
  }
  if (value === '-Infinity') {
    return -Infinity;
  }
  return Number(value);
}
