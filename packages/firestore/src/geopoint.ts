export class GeoPoint {
  readonly latitude: number;
  readonly longitude: number;

  constructor(latitude: number, longitude: number) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  toJSON(): { __type__: 'geopoint'; latitude: number; longitude: number } {
    return {
      __type__: 'geopoint',
      latitude: this.latitude,
      longitude: this.longitude,
    };
  }
}
